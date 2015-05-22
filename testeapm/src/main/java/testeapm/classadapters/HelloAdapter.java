package testeapm.classadapters;

import java.util.logging.Logger;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AnalyzerAdapter;
import org.objectweb.asm.commons.LocalVariablesSorter;

import testeapm.main.ApmType;

public class HelloAdapter extends ClassVisitor {

	private Logger log = Logger.getLogger(this.getClass().getName());
	
	private String cName;
	private boolean isInterface;
	private boolean isFilter;
	private ApmType type;

	public HelloAdapter(ClassVisitor cv) {
		super(Opcodes.ASM4, cv);
	}
	
	public HelloAdapter(ClassVisitor cv, ApmType type) {		
		super(Opcodes.ASM4, cv);
		this.type = type;
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
		
		if(type== ApmType.COMMOM_CLASS) {
			//log.info("Alterando metodo do servlet");
			mv = new HelloMethodAdapter(mv, cName, name, access, desc);
		}

		return mv;
	}

}
