package asmapm.adapters;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AnalyzerAdapter;
import org.objectweb.asm.commons.LocalVariablesSorter;

public class TesteMethodAdder extends MethodVisitor {

	public LocalVariablesSorter lvs;
	public AnalyzerAdapter aa;
	
	private int time;
	private int exception;

	private String cName;
	private String mName;
	private Label lTryBlockStart = new Label(), lTryBlockEnd = new Label(),
			lCatchBlockStart = new Label(), isNotStart = new Label();

	public TesteMethodAdder(MethodVisitor mv, String cName, String mName,
			int access, String desc) {
		super(Opcodes.ASM4, mv);
		
		this.cName = cName;
		this.mName = mName;

	}
	
	
	@Override
	public void visitCode() {
		super.visitCode();
		
		exception = lvs.newLocal(Type.getType(RuntimeException.class));
		time = lvs.newLocal(Type.LONG_TYPE);				
		
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System",
				"currentTimeMillis", "()J", false);
		
		mv.visitVarInsn(Opcodes.LSTORE, time);

		super.visitLdcInsn(this.cName);
		super.visitLdcInsn(this.mName);
		super.visitMethodInsn(Opcodes.INVOKESTATIC, "asmapm/Agent",
				"startprofile", "(Ljava/lang/String;Ljava/lang/String;)Z", false);
		super.visitJumpInsn(Opcodes.IFEQ, isNotStart);

		getFilterData();
		mv.visitLabel(isNotStart);
		mv.visitLabel(lTryBlockStart);
		
		
		

	}
	
	private void getFilterData() {
		
		
		mv.visitLdcInsn("serverName");
		mv.visitVarInsn(Opcodes.ALOAD, 1);	
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE,
				"javax/servlet/ServletRequest", "getServerName",
				"()Ljava/lang/String;", true);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "asmapm/Agent","addExtraData","(Ljava/lang/String;Ljava/lang/Object;)V", false);
		
		mv.visitLdcInsn("serverPort");
		mv.visitVarInsn(Opcodes.ALOAD, 1);	
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE,
				"javax/servlet/ServletRequest", "getServerPort",
				"()I", true);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC,
				"java/lang/Integer", "toString",
				"(I)Ljava/lang/String;", false);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "asmapm/Agent","addExtraData","(Ljava/lang/String;Ljava/lang/Object;)V", false);
		
		mv.visitLdcInsn("serverUrl");		
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitTypeInsn(Opcodes.CHECKCAST, "javax/servlet/http/HttpServletRequest");
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "javax/servlet/http/HttpServletRequest", "getRequestURL", "()Ljava/lang/StringBuffer;", true);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuffer", "toString", "()Ljava/lang/String;", false);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "asmapm/Agent","addExtraData","(Ljava/lang/String;Ljava/lang/Object;)V", false);
		
		mv.visitLdcInsn("serverInfo");
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitTypeInsn(Opcodes.CHECKCAST, "javax/servlet/http/HttpServletRequest");
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "javax/servlet/http/HttpServletRequest", "getSession", "()Ljavax/servlet/http/HttpSession;", true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "javax/servlet/http/HttpSession", "getServletContext", "()Ljavax/servlet/ServletContext;", true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "javax/servlet/ServletContext", "getServerInfo", "()Ljava/lang/String;", true);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "asmapm/Agent","addExtraData","(Ljava/lang/String;Ljava/lang/Object;)V", false);
		
		
		
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
			//mv.visitInsn(Opcodes.DUP);
			//mv.visitVarInsn(Opcodes.LSTORE, time);
			mv.visitInsn(Opcodes.LSUB);

			super.visitMethodInsn(Opcodes.INVOKESTATIC, "asmapm/Agent",
					"endprofile",
					"(Ljava/lang/String;Ljava/lang/String;J)V", false);
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
