package asmapm.adapters;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AnalyzerAdapter;
import org.objectweb.asm.commons.LocalVariablesSorter;

public class FilterMethodAdapter extends LocalVariablesSorter {
	public LocalVariablesSorter lvs;
	public AnalyzerAdapter aa;
	private int time;
	private int exception;

	private String cName;
	private String mName;
	private Label lTryBlockStart = new Label(), lTryBlockEnd = new Label(),
			lCatchBlockStart = new Label();

	public FilterMethodAdapter(MethodVisitor mv, String cName, String mName,
			int access, String desc) {
		super(Opcodes.ASM4, access, desc, mv);
		this.cName = cName;
		this.mName = mName;

	}

	@Override
	public void visitCode() {
		super.visitCode();

		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System",
				"currentTimeMillis", "()J", false);
		time = newLocal(Type.LONG_TYPE);
		mv.visitVarInsn(Opcodes.LSTORE, time);

		

		super.visitLdcInsn(this.cName);
		super.visitLdcInsn(this.mName);
		super.visitMethodInsn(Opcodes.INVOKESTATIC, "asmapm/Agent",
				"startprofile", "(Ljava/lang/String;Ljava/lang/String;)V", false);

		getFilterData();
		
		exception = newLocal(Type.getType(RuntimeException.class));
		mv.visitLabel(lTryBlockStart);

	}
	
	private void getFilterData() {
		mv.visitLdcInsn("contextPath");
		mv.visitVarInsn(Opcodes.ALOAD, 1);	
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE,
				"javax/servlet/http/HttpServletRequest", "getContextPath",
				"()Ljava/lang/String;", false);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "asmapm/Agent","addExtraData","(Ljava/lang/String;Ljava/lang/Object;)V", false);
		
		mv.visitLdcInsn("serverName");
		mv.visitVarInsn(Opcodes.ALOAD, 1);	
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE,
				"javax/servlet/http/HttpServletRequest", "getServerName",
				"()Ljava/lang/String;", false);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "asmapm/Agent","addExtraData","(Ljava/lang/String;Ljava/lang/Object;)V", false);
		
		mv.visitLdcInsn("serverPort");
		mv.visitVarInsn(Opcodes.ALOAD, 1);	
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE,
				"javax/servlet/http/HttpServletRequest", "getServerPort",
				"()I", false);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC,
				"java/lang/Integer", "toString",
				"(I)Ljava/lang/String;", false);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "asmapm/Agent","addExtraData","(Ljava/lang/String;Ljava/lang/Object;)V", false);
		
		//mv.visitLdcInsn("serverPort");
		/*mv.visitVarInsn(Opcodes.ALOAD, 0);	
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
				this.cName.replace(".", "/"), "teste",
				"()V");*/
		
		
		/*mv.visitLdcInsn("serverInfo");
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
				"javax/servlet/http/HttpServlet", "getServletContext",
				"()Ljavax/servlet/ServletContext;");
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
				"javax/servlet/http/HttpServlet", "getServletContext",
				"()Ljavax/servlet/ServletContext;");
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE,
				"javax/servlet/ServletContext", "getServerInfo",
				"()Ljava/lang/String;");
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "asmapm/Agent","addExtraData","(Ljava/lang/String;Ljava/lang/Object;)V");*/
		
		
	}

	@Override
	public void visitInsn(int opcode) {
		if ((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN)
				|| opcode == Opcodes.ATHROW) {
			super.visitLdcInsn(this.cName);
			super.visitLdcInsn(this.mName);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System",
					"currentTimeMillis", "()J", false);
			mv.visitVarInsn(Opcodes.LLOAD, time);
			mv.visitInsn(Opcodes.LSUB);

			// super.visitVarInsn(Opcodes.ALOAD, 0);
			/*
			 * super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, cName, "toString",
			 * "()Ljava/lang/String;");
			 */
			super.visitLdcInsn("testeeee");
			super.visitMethodInsn(Opcodes.INVOKESTATIC, "asmapm/Agent",
					"endprofile",
					"(Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;)V", false);
		}
		super.visitInsn(opcode);
	}

	@Override
	public void visitMaxs(int maxStack, int maxLocals) {
		mv.visitTryCatchBlock(lTryBlockStart, lTryBlockEnd, lCatchBlockStart,
				"java/lang/RuntimeException");
		mv.visitLabel(lTryBlockEnd);
		mv.visitLabel(lCatchBlockStart);

		mv.visitInsn(Opcodes.DUP);
		mv.visitVarInsn(Opcodes.ASTORE, exception);
		super.visitLdcInsn(this.cName);
		super.visitLdcInsn(this.mName);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System",
				"currentTimeMillis", "()J", false);
		mv.visitVarInsn(Opcodes.LLOAD, time);
		mv.visitInsn(Opcodes.LSUB);
		mv.visitVarInsn(Opcodes.ALOAD, exception);

		super.visitMethodInsn(Opcodes.INVOKESTATIC, "asmapm/Agent",
				"endprofile",
				"(Ljava/lang/String;Ljava/lang/String;JLjava/lang/RuntimeException;)V", false);

		mv.visitInsn(Opcodes.ATHROW);
		super.visitMaxs(maxStack, maxLocals);
	}
}