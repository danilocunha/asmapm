package asmapm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;

public class AddTimerAdaptor extends ClassVisitor {
	private String owner;
	private boolean isInterface;
	private boolean isJdbc;

	public AddTimerAdaptor(ClassVisitor cv) {
		super(Opcodes.ASM4, cv);
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		cv.visit(version, access, name, signature, superName, interfaces);
		owner = name;
		isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
		for (final String interfaceName : interfaces) {
			if(interfaceName.equals("java/sql/PreparedStatement")) {
				this.isJdbc = true;
			}
		}
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature,
				exceptions);
		if(isJdbc && !isInterface && mv != null && name.equals("executeQuery")) {
			mv = new AddTimerSQLMethodAdapter(mv, owner, name, access, desc);
			return mv;
		}
		if (!isInterface && mv != null && !name.equals("<init>")) {
			mv = new AddTimerMethodAdapter(mv, owner, name, access, desc);
		}
		return mv;
	}

	
}