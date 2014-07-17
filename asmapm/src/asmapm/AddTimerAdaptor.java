package asmapm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;

import asmapm.adapters.AddTimerMethodAdapter;
import asmapm.adapters.AddTimerSQLMethodAdapter;
import asmapm.adapters.FilterMethodAdapter;
import asmapm.adapters.ServletMethodAdapter;

public class AddTimerAdaptor extends ClassVisitor {
	private String owner;
	private boolean isInterface;
	private boolean isJdbc;
	private boolean isFilter;
	private boolean isServlet;

	public AddTimerAdaptor(ClassVisitor cv, ApmType apmType) {
		super(Opcodes.ASM4, cv);
		
		ApmType at = ApmType.values()[apmType.ordinal()];
		switch (at) {
			case JDBC:
				//System.out.println("is JDBC");
				this.isJdbc = true;
			case SERVLET:
				//System.out.println("is SERVLET" + owner);
				this.isServlet = true;
			break;
		}
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		cv.visit(version, access, name, signature, superName, interfaces);
		owner = name;
		isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
		for (final String interfaceName : interfaces) {


			if (interfaceName.equals("javax/servlet/Filter")) {
				this.isFilter = true;
			}

		}

		/*
		 * try {
		 * if(javax.servlet.http.HttpServlet.class.isAssignableFrom(Class.forName
		 * (name))) { System.out.println(name); } } catch
		 * (ClassNotFoundException e) { System.out.println("Nao achou" + name);
		 * }
		 */
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {

		MethodVisitor mv = cv.visitMethod(access, name, desc, signature,
				exceptions);

		boolean isConstructor = name.contains("<") || mv == null;
		if (isServlet && !isInterface && mv != null && name.equals("doGet")
				&& !isConstructor) {
			System.out.println("Metodo SERVLET instrumentalizada: "+ owner
					 +"::"+name);
			mv = new ServletMethodAdapter(mv, owner, name, access, desc);
			 
			return mv;
		}
		if (isFilter && !isInterface && mv != null && name.equals("doFilter")
				&& !isConstructor) {

			mv = new FilterMethodAdapter(mv, owner, name, access, desc);
			// System.out.println("Metodo FILTER instrumentalizada: "+ owner
			// +"::"+name);
			return mv;
		}
		//
		if (isJdbc && !isInterface && mv != null && name.equals("executeQuery")
				&& !isConstructor) {

			mv = new AddTimerSQLMethodAdapter(mv, owner, name, access, desc);
			 
			return mv;
		}
		if (!isInterface && mv != null && !isConstructor) {
			mv = new AddTimerMethodAdapter(mv, owner, name, access, desc);
			 
		}
		return mv;
	}

}