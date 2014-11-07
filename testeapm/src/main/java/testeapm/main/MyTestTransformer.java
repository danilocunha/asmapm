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


public class MyTestTransformer implements ClassFileTransformer {

	private  Logger log = Logger.getLogger(this.getClass().getName());
	
	private boolean isServletFilter(ClassReader cr) {
		String[] interfaces = cr.getInterfaces();

		for (String interfaceName : interfaces) {
			if (interfaceName.equals("javax/servlet/Filter")) {
				return true;
			}
		}
		return false;
	}
	
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classFileBuffer) throws IllegalClassFormatException {
		
		ClassReader cr = new ClassReader(classFileBuffer);		
		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
		
		if(isServletFilter(cr)) {
			return processClass(className, classBeingRedefined, classFileBuffer, cr);
		}
		return classFileBuffer;
	}
	
	private byte[] processClass(String className, Class<?> classBeingRedefined,
			byte[] classFileBuffer, ClassReader cr) {

		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
		// TraceClassVisitor tcv = new TraceClassVisitor(cw, pw);

		// ClassTracerAdaptor ca = new ClassTracerAdaptor(cw);
		HelloAdapter ca = new HelloAdapter(cw);

		cr.accept(ca, ClassReader.SKIP_FRAMES);		

		// TraceClassVisitor cv = new TraceClassVisitor(cw, this.pw);
		return cw.toByteArray();
		// return classFileBuffer;

	}

}
