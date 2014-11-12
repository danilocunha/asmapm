package asmapm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AnalyzerAdapter;
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
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {

		MethodVisitor mv = cv.visitMethod(access, name, desc, signature,
				exceptions);

		boolean isConstructor = name.contains("<") || mv == null;
		if (isServlet) {
			
			if(!isInterface && mv != null && name.equals("doGet")
					&& !isConstructor) {
				System.out.println("Metodo SERVLET instrumentalizada: "+ owner
				 +"::"+name);
				ServletMethodAdapter sma = new ServletMethodAdapter(mv, owner, name, access, desc);
				sma.aa = new AnalyzerAdapter(owner, access, name, desc, sma);
				sma.lvs  = new LocalVariablesSorter(access, desc, sma.aa);
				
				return sma.lvs;

			}
			return mv;
		}
		
		if (isFilter) {

			
			/* System.out.println("Metodo do FILTER: "+ owner
			 +"::"+name);*/
			
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
				&& !isConstructor && owner.equals("com/mysql/jdbc/PreparedStatement")) {
			 System.out.println("Metodo JDBC instrumentalizado: "+ owner
						 +"::"+name);
			mv = new AddTimerSQLMethodAdapter(mv, owner, name, access, desc);
			 
			return mv;
		}
		//if (!isInterface && mv != null && !isConstructor) {
		if (!isInterface && mv != null) {
			mv = new AddTimerMethodAdapter(mv, owner, name, access, desc);
			 
		}
		return mv;
	}

}