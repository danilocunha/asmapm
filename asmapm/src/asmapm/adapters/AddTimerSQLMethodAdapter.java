package asmapm.adapters;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;



public class AddTimerSQLMethodAdapter extends LocalVariablesSorter {
	private int time;
	
	private String cName;
	private String mName;
	

	public AddTimerSQLMethodAdapter(MethodVisitor mv, String cName, String mName, int access, String desc) {
		super(Opcodes.ASM4, access, desc, mv);
		this.cName = cName;
		this.mName = mName;
		
	}

	@Override
	public void visitCode() {
		super.visitCode();
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System",
				"currentTimeMillis", "()J", false);
		time = newLocal(Type.LONG_TYPE);

		mv.visitVarInsn(Opcodes.LSTORE, time);
		super.visitLdcInsn(this.cName);
		super.visitLdcInsn(this.mName);
		super.visitMethodInsn(Opcodes.INVOKESTATIC,
				 "asmapm/Agent", "enter",
				 "(Ljava/lang/String;Ljava/lang/String;)V", false);
		
		
		
		
	}

	@Override
	public void visitInsn(int opcode) {
		if ((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN)
				|| opcode == Opcodes.ATHROW) {
			super.visitLdcInsn(this.cName);
			super.visitLdcInsn(this.mName);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System",
					"currentTimeMillis", "()J", false);
			mv.visitVarInsn(Opcodes.LLOAD, time);
			mv.visitInsn(Opcodes.LSUB);
			
			super.visitVarInsn(Opcodes.ALOAD, 0);
			getSqlStatementField(); 
			super.visitMethodInsn(Opcodes.INVOKESTATIC,
					 "asmapm/Agent", "leaveSql",
					 "(Ljava/lang/String;Ljava/lang/String;JLjava/lang/Object;)V", false);
		}
		super.visitInsn(opcode);
	}

	private void getSqlStatementField() {
		
		super.visitFieldInsn(Opcodes.GETFIELD, cName, "sqlCommand", "Ljava/lang/String;");
	}

	@Override
	public void visitMaxs(int maxStack, int maxLocals) {
		super.visitMaxs(maxStack + 4, maxLocals);
	}
}