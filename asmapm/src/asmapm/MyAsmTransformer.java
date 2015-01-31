package asmapm;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.TraceClassVisitor;

import asmapm.TesteAdaptor;
import asmapm.config.ClassesConfig;

public class MyAsmTransformer implements ClassFileTransformer {

	private final static Logger log = Logger.getLogger("Tranformer");

	private ClassLoader classLoader;

	public MyAsmTransformer() {
		super();
	}

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

	@Override
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {

		this.classLoader = loader;

		String dotClassName = className.replace('/', '.');

		if (loader == null) {
			// Bootstrapclass
			return classfileBuffer;
		}
		ClassReader cr = new ClassReader(classfileBuffer);

		if ((isServletFilter(cr.getClassName()))) {
			log.info("##################################################################");
			log.info("CLASSE PROCESSADA COMO FILTRO: " + cr.getClassName());
			log.info("##################################################################");
			
			return processClassTeste(className, classBeingRedefined,
					classfileBuffer, cr, ApmType.FILTER);
			/*
			 * return processClass(className, classBeingRedefined,
			 * classfileBuffer, cr, ApmType.SERVLET);
			 */

		}

		if (!toInclude(dotClassName)) {
			/*if(dotClassName.startsWith("net.sourceforge.jtds")) {
			System.out.println(dotClassName +
			" nao foi instrumentalizada por skip");
			}*/
			return classfileBuffer;
		}

		if ((isPreparedStatement(cr))) {
			if(dotClassName.startsWith("net.sourceforge.jtds")) {
				System.out.println(dotClassName +
						" foi instrumentalizada como prepared statement");
			}
			return processClass(className, classBeingRedefined,
					classfileBuffer, cr, ApmType.JDBC);

		}
		
		if ((isHttpClient(cr))) {
			
			System.out.println(dotClassName +
						" foi instrumentalizada como prepared statement");
			
			return processClass(className, classBeingRedefined,
					classfileBuffer, cr, ApmType.HTTP_CLIENT);

		}

		if (isHttpServlet(cr)) {
			log.info("Processando como Servlet a classe: " + className);
			// return classfileBuffer;
			return processClass(className, classBeingRedefined,
					classfileBuffer, cr, ApmType.SERVLET);
		}

		/*if (className.startsWith("br/gov/")) {
			log.info("Processando classe como comum: " + className);
		}*/
		return processClass(className, classBeingRedefined, classfileBuffer,
				cr, ApmType.COMMOM_CLASS);

	}

	private boolean isPreparedStatement(ClassReader cr) {
		String[] interfaces = cr.getInterfaces();

		for (String interfaceName : interfaces) {
			if (interfaceName.equals("java/sql/PreparedStatement")) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isHttpClient(ClassReader cr) {
		String name = cr.getClassName();

		
			if (name.equals("org/apache/commons/httpclient/HttpClient")) {
				return true;
			}
		
		return false;
	}
	

	private boolean isServletFilter(ClassReader cr) {

		String[] interfaces = cr.getInterfaces();

		/*
		 * log.info(
		 * "##################################################################"
		 * ); log.info("CLASSE PAI: " + cr.getSuperName()); log.info(
		 * "##################################################################"
		 * );
		 */
		for (String interfaceName : interfaces) {
			if (interfaceName.equals("javax/servlet/Filter")) {
				ClassesConfig.getInstance().addFilterClass(cr.getClassName());
				return true;
			}
		}
		try {
			ClassReader crSuper = new ClassReader(cr.getSuperName());

		} catch (IOException e) {

			e.printStackTrace();
		}
		if (ClassesConfig.getInstance().isFilterClass(cr.getSuperName())) {

			ClassesConfig.getInstance().addFilterClass(cr.getClassName());
			return true;
		}
		return false;
	}

	private boolean isServletFilter(String className) {

		ClassReader cr = null;
		String[] interfaces = null;

		String fileName = className.replace('.', '/') + ".class";
		InputStream is = null;

		try {
			is = (this.classLoader == null) ? ClassLoader
					.getSystemResourceAsStream(fileName) : this.classLoader
					.getResourceAsStream(fileName);
			cr = new ClassReader(is);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
				}
			}
		}

		interfaces = cr.getInterfaces();
		for (String interfaceName : interfaces) {
			if (interfaceName.equals("javax/servlet/Filter")) {
				return true;
			}
		}

		String superName = cr.getSuperName();
		if (superName != null && !superName.equals("java/lang/Object")) {
			return isServletFilter(cr.getSuperName());
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

	private byte[] processClass(String className, Class<?> classBeingRedefined,
			byte[] classFileBuffer, ClassReader cr, ApmType apmType) {

		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
		// TraceClassVisitor tcv = new TraceClassVisitor(cw, pw);

		// ClassTracerAdaptor ca = new ClassTracerAdaptor(cw);
		AddTimerAdaptor ca = new AddTimerAdaptor(cw, apmType);

		cr.accept(ca, ClassReader.EXPAND_FRAMES);

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
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TraceClassVisitor cv = new TraceClassVisitor(cw, this.pw);
		return cw.toByteArray();
		// return classFileBuffer;

	}

	private byte[] processClassTeste(String className,
			Class<?> classBeingRedefined, byte[] classFileBuffer,
			ClassReader cr, ApmType apmType) {

		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);		
		TesteAdaptor ca = new TesteAdaptor(cw, apmType);

		cr.accept(ca, ClassReader.EXPAND_FRAMES);

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
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TraceClassVisitor cv = new TraceClassVisitor(cw, this.pw);
		return cw.toByteArray();
		// return classFileBuffer;

	}

}
