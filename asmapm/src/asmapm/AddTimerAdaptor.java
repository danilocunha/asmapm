package asmapm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;

public class AddTimerAdaptor extends ClassVisitor {
	private String owner;
	private boolean isInterface;

	public AddTimerAdaptor(ClassVisitor cv) {
		super(Opcodes.ASM4, cv);
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		cv.visit(version, access, name, signature, superName, interfaces);
		owner = name;
		isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature,
				exceptions);
		if (!isInterface && mv != null && !name.equals("<init>")) {
			mv = new AddTimerMethodAdapter(mv, owner, name, access, desc);
		}
		return mv;
	}

	class AddTimerMethodAdapter extends LocalVariablesSorter {
		private int time;
		private String cName;
		private String mName;
		
		public AddTimerMethodAdapter(MethodVisitor mv, String cName, String mName, int access, String desc) {
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
			//super.visitLdcInsn(this.cName);
			//super.visitLdcInsn(this.mName);
			super.visitMethodInsn(Opcodes.INVOKESTATIC,
					 "asmapm/Agent", "enter",
					 "()V");
			//System.out.println(cName + " " + mName);
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
				
				
				
				
				super.visitMethodInsn(Opcodes.INVOKESTATIC,
						 "asmapm/Agent", "leave",
						 "(Ljava/lang/String;Ljava/lang/String;J)V");
			}
			super.visitInsn(opcode);
		}

		@Override
		public void visitMaxs(int maxStack, int maxLocals) {
			super.visitMaxs(maxStack + 4, maxLocals);
		}
	}
}