package asmapm;

import java.security.acl.Owner;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AnalyzerAdapter;
import org.objectweb.asm.commons.LocalVariablesSorter;

public class AddTimerAdaptor extends LocalVariablesSorter {

	public AnalyzerAdapter aa;
	private int time;
	private int maxStack;
	private String cName;
	private String mName;

	public AddTimerAdaptor(int access, String desc, MethodVisitor mv,
			String className, String methodName) {
		super(Opcodes.ASM4, access, desc, mv);
		this.cName = className;
		this.mName = methodName;
	}

	@Override
	public void visitCode() {
		mv.visitCode();
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System",
				"currentTimeMillis", "()J");
		time = newLocal(Type.LONG_TYPE);
		mv.visitVarInsn(Opcodes.LSTORE, time);

		// System.out.println("return de algo" + cName);
		/*
		 * mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System",
		 * "currentTimeMillis", "()J"); time = lvs.newLocal(Type.LONG_TYPE);
		 * mv.visitVarInsn(Opcodes.LSTORE, time); maxStack = 4;
		 */
	}

	@Override
	public void visitInsn(int opcode) {
		if ((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN)
				|| opcode == Opcodes.ATHROW) {
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System",
					"currentTimeMillis", "()J");
			mv.visitVarInsn(Opcodes.LLOAD, time);
			mv.visitInsn(Opcodes.LSUB);
			mv.visitFieldInsn(Opcodes.GETSTATIC, cName, "time", "J");
			mv.visitInsn(Opcodes.LADD);
			mv.visitFieldInsn(Opcodes.PUTSTATIC, cName, "time", "J");
		}
		super.visitInsn(opcode);
	}

	@Override
	public void visitMaxs(int maxStack, int maxLocals) {
		super.visitMaxs(maxStack + 4, maxLocals);
	}

}
