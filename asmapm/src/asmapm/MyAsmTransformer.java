package asmapm;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.TraceClassVisitor;

public class MyAsmTransformer implements ClassFileTransformer {

	protected Set<String> instrumentedClasses = new HashSet<String>();

	protected List<String> classesToSkip = new ArrayList<String>();

	protected List<String> classesToInclude = new ArrayList<String>();
	
	protected List<String> classesToSkipAfterSQLCheck = new ArrayList<String>();

	public MyAsmTransformer() {
		super();
		classesToInclude.add("java.sql");
		

		classesToSkip.add("apm.");
		classesToSkip.add("asmapm.");
		classesToSkip.add("javassist.");
		classesToSkip.add("javax.");
		classesToSkip.add("java.");
		classesToSkip.add("sun.");
		classesToSkip.add("com.sun.");
		classesToSkip.add("org.");
		
		classesToSkip.add("org.xml.");
		classesToSkip.add("org.w3c.");
		//classesToSkip.add("com.mysql.");
		classesToSkip.add("antlr.");
		classesToSkip.add("sessionmon.");
		classesToSkip.add("apple.");
		
		classesToSkipAfterSQLCheck.add("com.mysql");
	}

	public boolean toInclude(String dotClassName) {
		

		for (String classToInclude : classesToInclude) {
			if (dotClassName.startsWith(classToInclude)) {
				// System.out.println(dotClassName +
				// " nao foi instrumentalizada por skip");
				return true;
			}
		}

		for (String classToSkip : classesToSkip) {
			if (dotClassName.startsWith(classToSkip)) {
				// System.out.println(dotClassName +
				// " nao foi instrumentalizada por skip");
				return false;
			}
		}
		return true;
	}
	
	public boolean toIncludeAfterSQLCheck(String dotClassName) {
		
		for (String classToSkip : classesToSkipAfterSQLCheck) {
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
		if(!toIncludeAfterSQLCheck(dotClassName)) {
			return classfileBuffer;
			
		}
		
		return processClass(className, classBeingRedefined, classfileBuffer, cr);

	}
	
	private boolean isSQLCall(ClassReader cr) {
		String[] interfaces = cr.getInterfaces();

		for (String interfaceName : interfaces) {
			if(interfaceName.equals("java/sql/PreparedStatement")) {
				return true;
			}
		}
		return false;
	}

	private byte[] processClass(String className, Class classBeingRedefined,
			byte[] classFileBuffer, ClassReader cr) {
		PrintWriter pw = new PrintWriter(System.out);

		// SplunkJavaAgent.classLoaded(className);
		
		// ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
		// TraceClassVisitor tcv = new TraceClassVisitor(cw, pw);

		// MyAdaptor cv = new MyAdaptor(cw);
		// ClassTracerAdaptor ca = new ClassTracerAdaptor(cw);
		AddTimerAdaptor ca = new AddTimerAdaptor(cw);

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
