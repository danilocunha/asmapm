package testeapm.classadapters;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;

public class HelloMethodAdapter extends LocalVariablesSorter {

	
	private String cName;
	private String mName;
	
	private Label isNotStart = new Label();

	public HelloMethodAdapter(MethodVisitor mv, String cName, String mName,
			int access, String desc) {
		super(Opcodes.ASM4, access, desc, mv);

		this.cName = cName;
		this.mName = mName;

	}

	public void visitCode() {
		super.visitCode();

		/*
		 * mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out",
		 * "Ljava/io/PrintStream;"); mv.visitLdcInsn("Chamou " + cName + "::" +
		 * mName); mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
		 * "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
		 */

		super.visitLdcInsn(this.cName);
		super.visitLdcInsn(this.mName);
		super.visitMethodInsn(Opcodes.INVOKESTATIC, "testeapm/main/Agent",
				"startprofile", "(Ljava/lang/String;Ljava/lang/String;)Z",
				false);
		super.visitJumpInsn(Opcodes.IFEQ, isNotStart);

		mv.visitLabel(isNotStart);

	}
}
