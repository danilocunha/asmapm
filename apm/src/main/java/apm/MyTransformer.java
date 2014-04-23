package apm;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javassist.ByteArrayClassPath;
import javassist.ClassClassPath;
import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;

public class MyTransformer implements ClassFileTransformer {

	private int count;
	private long totalTime = 0;

	protected Set<String> instrumentedClasses = new HashSet<String>();

	protected List<String> classesToSkip = new ArrayList<String>();

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

		String dotClassName = className.replace('/', '.');

		// Only instrument a class once
		if (instrumentedClasses.contains(className)) {
			// System.out.println(dotClassName + " ja foi instrumentalizada");
			return null;
		}

		// Skip in the list of class prefixes to skip
		for (String classToSkip : classesToSkip) {
			if (dotClassName.startsWith(classToSkip)) {
				// System.out.println(dotClassName +
				// " nao foi instrumentalizada por skip");
				return null;
			}
		}

		byte[] byteCode = classfileBuffer;

		long instrumentationTime = System.currentTimeMillis();

		try {

			ClassPool cp = ClassPool.getDefault();
			//cp.appendClassPath(new LoaderClassPath(getClass().getClassLoader()));
			//cp.insertClassPath( new ByteArrayClassPath( dotClassName,
			 //classfileBuffer ) );
			//cp.appendClassPath(new ClassClassPath(this.getClass()));
			
			cp.insertClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));
			cp.appendClassPath(new LoaderClassPath(Thread.currentThread().getClass().getClassLoader()));
			// cp.insertClassPath(new ClassClassPath(this.getClass()));

			CtClass cc = cp.get(dotClassName);

			if (!cc.isFrozen()) {

				CtMethod[] targetMethods = cc.getDeclaredMethods();
				int i = 0;
				
				
				for (i = 0; i < targetMethods.length; i++) {
										
					if ((!targetMethods[i].isEmpty())) {
						
						targetMethods[i].addLocalVariable("elapsedTime",
								CtClass.longType);
						targetMethods[i]
								.insertBefore("elapsedTime = System.currentTimeMillis();");
						targetMethods[i]
								.insertAfter("{elapsedTime = System.currentTimeMillis() - elapsedTime;"
										+ "if(elapsedTime>1000) { "
										+ "System.out.println(\"Method "
										+ targetMethods[i].getLongName()
										+ " Executed in ms: \" + elapsedTime);"
										+ "StackTraceElement[] stes = Thread.currentThread().getStackTrace();"
										+ "for(StackTraceElement ste: stes) { System.out.println(ste); }"
										//+ "apm.printStack();"
										//+ "apm.printStack();"
										+ "}}");
						
										
					} else {
						System.out.println("Metodo vazio" + targetMethods[i].getLongName());
					}
				}
				
				byteCode = cc.toBytecode();
				cc.detach();
				instrumentedClasses.add(className);
			} else {
				System.out.println(dotClassName
						+ " nao foi instrumentalizada por ser frozen");
			}
			count++;
			// System.out.println(Integer.toString(count) + " - " + dotClassName
			// + " - " + Long.toString(totalTime));
		} catch (Exception ex) {
			ex.printStackTrace();
			// System.out.println("Nao consegui instrumentalizar a classe: " +
			// dotClassName);
		}
		instrumentationTime = System.currentTimeMillis() - instrumentationTime;
		totalTime = totalTime + instrumentationTime;
		// System.out.println("Intrumentation " + dotClassName +" time in ms:"
		// + instrumentationTime);
		
		return byteCode;

	}
	
	public void printStack() {
		StackTraceElement[] stes = Thread.currentThread().getStackTrace();
		for(StackTraceElement ste: stes){
			System.out.println(ste);
		}
	}

}
