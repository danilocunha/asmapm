package asmapm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MyAdaptor extends ClassVisitor {

	public MyAdaptor(ClassVisitor arg1) {
		super(Opcodes.ASM4, arg1);
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		cv.visit(Opcodes.V1_5, access, name, signature, superName, interfaces);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		System.out.println("				" + name + desc);

		return cv.visitMethod(access, name, desc, signature, exceptions);
	}

}
