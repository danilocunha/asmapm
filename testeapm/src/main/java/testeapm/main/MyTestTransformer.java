package testeapm.main;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.logging.Logger;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import testeapm.classadapters.HelloAdapter;
import testeapm.config.ClassesConfig;

public class MyTestTransformer implements ClassFileTransformer {

	private Logger log = Logger.getLogger(this.getClass().getName());

	public boolean toInclude(String dotClassName) {

		for (String classToInclude : ClassesConfig.getInstance()
				.getClassesToInclude()) {
			if (dotClassName.startsWith(classToInclude)) {
				// System.out.println(dotClassName +
				// " nao foi instrumentalizada por skip");
				return true;
			}
		}

		for (String classToSkip : ClassesConfig.getInstance()
				.getClassesToSkip()) {
			if (dotClassName.startsWith(classToSkip)) {
				// System.out.println(dotClassName +
				// " nao foi instrumentalizada por skip");
				return false;
			}
		}
		return true;
	}
	
	private boolean isServletFilter(ClassReader cr) {
		String[] interfaces = cr.getInterfaces();

		for (String interfaceName : interfaces) {
			if (interfaceName.equals("javax/servlet/Filter")) {
				return true;
			}
		}
		return false;
	}

	private boolean isHttpServlet(ClassReader cr) {

		// String _className = cr.getClassName();
		String superName = cr.getSuperName();

		// String _dotClassName = _className.replace('/', '.');

		if (superName.equals("javax/servlet/http/HttpServlet")) {
			return true;

		}

		return false;
	}
	
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classFileBuffer) throws IllegalClassFormatException {

		String[] interfaces = null;

		ClassReader cr = new ClassReader(classFileBuffer);
		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);

		String dotClassName = className.replace('/', '.');
		
		if (!toInclude(dotClassName)) {
			/*
			 * if(dotClassName.startsWith("net.sourceforge.jtds")) {
			 * System.out.println(dotClassName +
			 * " nao foi instrumentalizada por skip"); }
			 */
			return classFileBuffer;
		}
		
		return processClass(className, classBeingRedefined,
				classFileBuffer, cr, ApmType.COMMOM_CLASS);
		/*String dotClassName = cr.getClassName().replace("/", ".");
		if (isHttpServlet(cr)) {
			return processClass(className, classBeingRedefined,
					classFileBuffer, cr, ApmType.COMMOM_CLASS);
		}
		
		
		if (dotClassName
				.startsWith("com.microsoft.sqlserver.jdbc.ISQLServerStatement")) {
			interfaces = cr.getInterfaces();
			for (String interfaceName : interfaces) {
				log.info("############################################");
				log.info("Classe: " + dotClassName + " Interface: "
						+ interfaceName);
				log.info("isInterface" + cr.getClass().isInterface());
				log.info("############################################");
			}
		}
		if (isServletFilter(cr)) {
			log.info("################### SERVLET #########################");
			log.info("Classe: " + dotClassName);			
			log.info("############################################");
			return processClass(className, classBeingRedefined,
					classFileBuffer, cr, ApmType.SERVLET);
		}*/
		//return classFileBuffer;
	}

	private byte[] processClass(String className, Class<?> classBeingRedefined,
			byte[] classFileBuffer, ClassReader cr, ApmType type) {

		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
		// TraceClassVisitor tcv = new TraceClassVisitor(cw, pw);

		// ClassTracerAdaptor ca = new ClassTracerAdaptor(cw);
		HelloAdapter ca = new HelloAdapter(cw, type);

		cr.accept(ca, ClassReader.SKIP_FRAMES);

		File outputDir = new File("/tmp/classes/");
		outputDir.mkdirs();
		DataOutputStream dout;
		String[] classNames = className.split("/");
		String lastOne = classNames[classNames.length - 1];
		try {
			dout = new DataOutputStream(new FileOutputStream(new File(
					outputDir, lastOne + ".class")));
			dout.write(cw.toByteArray());
			dout.close();
		} catch (FileNotFoundException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch

			e.printStackTrace();
		}
		// TraceClassVisitor cv = new TraceClassVisitor(cw, this.pw);
		return cw.toByteArray();
		// return classFileBuffer;

	}

}
