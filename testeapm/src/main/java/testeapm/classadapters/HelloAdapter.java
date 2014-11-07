package testeapm.classadapters;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class HelloAdapter extends ClassVisitor {

	private String cName;
	private boolean isInterface;
	private boolean isFilter;

	public HelloAdapter(ClassVisitor cv) {
		super(Opcodes.ASM4, cv);
	}

	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		cv.visit(version, access, name, signature, superName, interfaces);
		cName = name;
		isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
		for (final String interfaceName : interfaces) {

			if (interfaceName.equals("javax/servlet/Filter")) {
				this.isFilter = true;
			}

		}
	}

	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {

		MethodVisitor mv = cv.visitMethod(access, name, desc, signature,
				exceptions);

		boolean isConstructor = name.contains("<") || mv == null;
		if (isFilter) {

			if (!isInterface && mv != null && name.equals("doFilter")
					&& !isConstructor) {
				/*
				 * System.out.println("Metodo SERVLET instrumentalizada: "+
				 * cName +"::"+name);
				 */
				mv = new HelloMethodAdapter(mv, cName, name, access, desc);
			}
		}

		return mv;
	}

}
