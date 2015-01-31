package asmapm;

import java.lang.reflect.Field;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AnalyzerAdapter;
import org.objectweb.asm.commons.LocalVariablesSorter;

import asmapm.ApmType;
import asmapm.adapters.TesteMethodAdder;

public class TesteAdaptor extends ClassVisitor {

	private String owner;
	private boolean isInterface;
	private boolean isJdbc;
	private boolean isFilter;
	private boolean isServlet;

	public TesteAdaptor(ClassVisitor cv, ApmType apmType) {
		super(Opcodes.ASM4, cv);

		ApmType at = ApmType.values()[apmType.ordinal()];
		switch (at) {
		case JDBC:
			// System.out.println("is JDBC");
			this.isJdbc = true;
		case SERVLET:
			// System.out.println("is SERVLET" + owner);
			this.isServlet = true;
			break;
		case FILTER:
			// System.out.println("is SERVLET" + owner);
			this.isFilter = true;
			break;
		}
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		this.owner = name;
		cv.visit(version, access, name, signature, superName, interfaces);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {

		MethodVisitor mv = cv.visitMethod(access, name, desc, signature,
				exceptions);		
		if (name.equals("doFilter")) {
			System.out.println("Acheiiiiiii um Metodo FILTER: "+ owner
			  +"::"+name+"::"+desc);
			TesteMethodAdder tma = new TesteMethodAdder(mv, owner, name, access, desc);
			tma.aa = new AnalyzerAdapter(owner, access, name, desc, tma);
			tma.lvs = new LocalVariablesSorter(access, desc, tma.aa);
			return tma.lvs;

		}
				
		return mv;
	}

}
