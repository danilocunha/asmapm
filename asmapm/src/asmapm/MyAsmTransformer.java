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

	
	public MyAsmTransformer() {
		super();		
	}

	@Override
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {
		classesToSkip.add("apm.");
		classesToSkip.add("javassist.");
		classesToSkip.add("javax.");
		classesToSkip.add("java.");
		classesToSkip.add("sun.");
		classesToSkip.add("com.sun.");
		classesToSkip.add("org.");
		classesToSkip.add("org.apache.");
		classesToSkip.add("org.xml.");
		classesToSkip.add("org.w3c.");
		classesToSkip.add("com.mysql.");
		classesToSkip.add("antlr.");
		classesToSkip.add("sessionmon.");
		String dotClassName = className.replace('/', '.');

		for (String classToSkip : classesToSkip) {
			if (dotClassName.startsWith(classToSkip)) {
				// System.out.println(dotClassName +
				// " nao foi instrumentalizada por skip");
				return classfileBuffer;
			}
		}
		// System.out.println("Class eh " + dotClassName);
		// System.out.println(this.getClass().getClassLoader().toString());
		return processClass(className, classBeingRedefined, classfileBuffer);

		/*
		 * System.out.println(loader.toString()); if
		 * (this.getClass().getClassLoader().equals(loader)) { return
		 * processClass(className, classBeingRedefined, classfileBuffer); } else
		 * { //System.out.println("Oi eu aqui"); return classfileBuffer; }
		 */
	}

	private byte[] processClass(String className, Class classBeingRedefined,
			byte[] classFileBuffer) {
		PrintWriter pw = new PrintWriter(System.out);
		
		// SplunkJavaAgent.classLoaded(className);
		ClassReader cr = new ClassReader(classFileBuffer);
		// ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
		//TraceClassVisitor tcv = new TraceClassVisitor(cw, pw);

		// MyAdaptor cv = new MyAdaptor(cw);
		ClassTracerAdaptor ca = new ClassTracerAdaptor(cw);
		
		cr.accept(ca, ClassReader.SKIP_FRAMES);
		
		File outputDir=new File("/tmp/classes/");
		outputDir.mkdirs();
		DataOutputStream dout;
		String[] classNames = className.split("/");
		String lastOne = classNames[classNames.length-1];
		try {
			dout = new DataOutputStream(new FileOutputStream(new File(outputDir,lastOne + ".class")));
			dout.write(cw.toByteArray());
			dout.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//TraceClassVisitor cv = new TraceClassVisitor(cw, this.pw);
		return cw.toByteArray();
		// return classFileBuffer;

	}

}
