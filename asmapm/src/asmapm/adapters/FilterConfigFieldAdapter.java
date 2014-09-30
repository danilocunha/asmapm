package asmapm.adapters;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.LocalVariablesSorter;

public class FilterConfigFieldAdapter extends LocalVariablesSorter {

	private String cName;
	private String mName;

	public FilterConfigFieldAdapter(MethodVisitor mv, String cName,
			String mName, int access, String desc) {
		super(Opcodes.ASM4, access, desc, mv);

		this.cName = cName;
		this.mName = mName;

	}

	@Override
	public void visitCode() {
		super.visitCode();
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		// PUTFIELD testeservlet/HelloFilter.config :
		// Ljavax/servlet/FilterConfig;
		mv.visitFieldInsn(Opcodes.PUTFIELD, cName, "asmapmConfig",
				"Ljavax/servlet/FilterConfig;");
		

		
		// mv.visitMethodInsn(Opcodes.INVOKESTATIC,
		// "asmapm/Agent","addExtraData","(Ljava/lang/String;Ljava/lang/Object;)V");

	}

	@Override
	public void visitInsn(int opcode) {
		super.visitInsn(opcode);
	}

	@Override
	public void visitMaxs(int maxStack, int maxLocals) {
		super.visitMaxs(maxStack, maxLocals);
	}
}
