package asmapm;

import java.util.logging.Logger;

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
import asmapm.adapters.HttpClientMethodAdapter;
import asmapm.adapters.ServletMethodAdapter;
import asmapm.adapters.TesteMethodAdder;
import asmapm.config.ClassesConfig;

public class AddTimerAdaptor extends ClassVisitor {
	private String owner;
	private boolean isInterface;
	private boolean isJdbc;
	private boolean isFilter = false;
	private boolean isServlet = false;
	private ApmType apmType;

	private Logger log = Logger.getLogger(this.getClass().getName());
	
	public AddTimerAdaptor(ClassVisitor cv, ApmType apmType) {
		super(Opcodes.ASM4, cv);
		
		this.apmType = apmType;
		ApmType at = ApmType.values()[apmType.ordinal()];
		switch (at) {
		case JDBC:
			// System.out.println("is JDBC");
			this.isJdbc = true;

			break;
		case SERVLET:
			// System.out.println("is SERVLET" + owner);
			this.isServlet = true;

			break;
		case FILTER:
			// System.out.println("is SERVLET" + owner);
			this.isFilter = true;

			break;
		default:
			break;
		}
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

		boolean isConstructor = name.contains("<") || mv == null;

		if (isServlet) {
			/*
			 * System.out.println("Entrou como servlet: " + owner + "::" +
			 * name);
			 */
			if (!isInterface && mv != null && name.equals("doGet")
					&& !isConstructor) {
				System.out.println("Metodo SERVLET instrumentalizada: " + owner
						+ "::" + name);
				log.fine("Servlet method instrumented: " + owner + "::" + name);
				ServletMethodAdapter sma = new ServletMethodAdapter(mv, owner,
						name, access, desc);
				sma.aa = new AnalyzerAdapter(owner, access, name, desc, sma);
				sma.lvs = new LocalVariablesSorter(access, desc, sma.aa);

				return sma.lvs;

			}
			return mv;
		}
		
		if (this.apmType.equals(ApmType.HTTP_CLIENT)) {
			/*
			 * System.out.println("Entrou como servlet: " + owner + "::" +
			 * name);
			 */
			if (!isInterface && mv != null && name.equals("executeMethod")
					&& !isConstructor && desc.equals("(Lorg/apache/commons/httpclient/HttpMethod;)I")) {
				System.out.println("Metodo HTTP CLIENT EXECUTE METHOD instrumentalizada: " + owner
						+ "::" + name + "Desc:" + desc);
				
				mv = new HttpClientMethodAdapter(mv, owner, name, access,
						 desc);
				
				return mv;

			}
			return mv;
		}

		if (isFilter) {

			if (name.equals("doFilter")) {
				
				FilterMethodAdapter fma = new FilterMethodAdapter(mv, owner, name, access, desc);
				fma.aa = new AnalyzerAdapter(owner, access, name, desc, fma);
				fma.lvs = new LocalVariablesSorter(access, desc, fma.aa);
				return fma.lvs;

			}

		}

		if (isFilter && !isInterface && mv != null && name.equals("doFilter")
				&& !isConstructor) {
			System.out.println("Entrou como filter: " + owner + "::" + name);
			mv = new FilterMethodAdapter(mv, owner, name, access, desc);
			// System.out.println("Metodo FILTER instrumentalizada: "+ owner
			// +"::"+name);
			return mv;
		}

		if (isJdbc) {
			
			/*System.out.println("Nome do m�todo: " +name);
			System.out.println("Se � contrutor: " +isConstructor);*/
			if (!isInterface
					&& mv != null
					&& name.equals("executeQuery")
					&& !isConstructor
					/*&& ClassesConfig.getInstance().getClassesToInclude()
							.contains(owner)*/) {
				System.out.println("Metodo JDBC instrumentalizado: " + owner
						+ "::" + name);
				mv = new AddTimerSQLMethodAdapter(mv, owner, name, access,
				 desc);

				
			}
			return mv;
		}
			
		
		if (!isInterface && mv != null) {
			mv = new AddTimerMethodAdapter(mv, owner, name, access, desc);

		}
		return mv;
	}

}