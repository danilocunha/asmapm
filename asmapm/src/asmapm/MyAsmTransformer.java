package asmapm;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

		String dotClassName = className.replace('/', '.');

		if (loader == null) {
			// Bootstrapclass
			return classfileBuffer;
		}

		if (!toInclude(dotClassName)) {
			// System.out.println(dotClassName +
			// " nao foi instrumentalizada por skip");
			return classfileBuffer;
		}

		ClassReader cr = new ClassReader(classfileBuffer);
		if ((isPreparedStatement(cr))) {
			// return classfileBuffer;
			return processClass(className, classBeingRedefined,
					classfileBuffer, cr, ApmType.JDBC);

		}

		if ((isServletFilter(cr))) {
			log.info("Processando como Filter a classe: " + className);
			 return processClassTeste(className, classBeingRedefined,
					classfileBuffer, cr, ApmType.FILTER);
			/*return processClass(className, classBeingRedefined,
					classfileBuffer, cr, ApmType.SERVLET);*/


		}

		if (isHttpServlet(cr)) {
			log.info("Processando como Servlet a classe: " + className);
			//return classfileBuffer;
			return processClass(className, classBeingRedefined,
					classfileBuffer, cr, ApmType.SERVLET);
		}

		// loader.getSystemClassLoader().
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

	private byte[] processClass(String className, Class<?> classBeingRedefined,
			byte[] classFileBuffer, ClassReader cr, ApmType apmType) {

		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
		// TraceClassVisitor tcv = new TraceClassVisitor(cw, pw);

		// ClassTracerAdaptor ca = new ClassTracerAdaptor(cw);
		AddTimerAdaptor ca = new AddTimerAdaptor(cw, apmType);

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
	
	private byte[] processClassTeste(String className, Class<?>classBeingRedefined,
			byte[] classFileBuffer, ClassReader cr, ApmType apmType) {

		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
		// TraceClassVisitor tcv = new TraceClassVisitor(cw, pw);

		// ClassTracerAdaptor ca = new ClassTracerAdaptor(cw);
		TesteAdaptor ca = new TesteAdaptor(cw, apmType);

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
