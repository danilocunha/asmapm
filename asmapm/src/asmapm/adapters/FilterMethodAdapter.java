package asmapm.adapters;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;

public class FilterMethodAdapter extends LocalVariablesSorter {
	private int time;
	private int contextPath;

	private String cName;
	private String mName;

	public FilterMethodAdapter(MethodVisitor mv, String cName, String mName,
			int access, String desc) {
		super(Opcodes.ASM4, access, desc, mv);
		this.cName = cName;
		this.mName = mName;

	}

	@Override
	public void visitCode() {
		super.visitCode();

		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System",
				"currentTimeMillis", "()J");
		time = newLocal(Type.LONG_TYPE);		
		mv.visitVarInsn(Opcodes.LSTORE, time);
		
		contextPath = newLocal(Type.getType(String.class));
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitTypeInsn(Opcodes.CHECKCAST, "javax/servlet/http/HttpServletRequest");
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "javax/servlet/http/HttpServletRequest", "getContextPath", "()Ljava/lang/String;");
		mv.visitVarInsn(Opcodes.ASTORE, contextPath);
		
		super.visitLdcInsn(this.cName);
		super.visitLdcInsn(this.mName);
		super.visitMethodInsn(Opcodes.INVOKESTATIC, "asmapm/Agent",
				"startprofile", "(Ljava/lang/String;Ljava/lang/String;)V");

	}

	@Override
	public void visitInsn(int opcode) {
		if ((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN)
				|| opcode == Opcodes.ATHROW) {
			super.visitLdcInsn(this.cName);
			super.visitLdcInsn(this.mName);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System",
					"currentTimeMillis", "()J");
			mv.visitVarInsn(Opcodes.LLOAD, time);
			mv.visitInsn(Opcodes.LSUB);

			//super.visitVarInsn(Opcodes.ALOAD, 0);
			/*super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, cName, "toString",
					"()Ljava/lang/String;");*/
			mv.visitVarInsn(Opcodes.ALOAD, contextPath);
			super.visitMethodInsn(Opcodes.INVOKESTATIC, "asmapm/Agent",
					"endprofile",
					"(Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;)V");
		}
		super.visitInsn(opcode);
	}

	@Override
	public void visitMaxs(int maxStack, int maxLocals) {
		super.visitMaxs(maxStack + 4, maxLocals);
	}
}