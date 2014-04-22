package asmapm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClassTracerAdaptor extends ClassVisitor {
	private String className;
	private boolean isInterface;

	public ClassTracerAdaptor(ClassVisitor cv) {
		super(Opcodes.ASM4, cv);
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		cv.visit(version, access, name, signature, superName, interfaces);

		className = name;
		isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
		// System.out.println(name + " extends " + superName + " {");

	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature,
				exceptions);
		if (!isInterface) {
			
			mv = new MethodTracerAdaptor(className, name, desc, mv, access);
			//mv = new AddTimerAdaptor(access, desc, 	mv, className, name);
		}
		// System.out.println("    " + name + desc);

		return mv;
	}

	

}
