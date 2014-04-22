package asmapm;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.LocalVariablesSorter;

public class MethodTracerAdaptor extends AdviceAdapter {
	private String cName;
	private String mName;
	private String desc;
	private int time;
	public LocalVariablesSorter lvs;	

	public MethodTracerAdaptor(String className, String name, String desc,
			MethodVisitor mv, int access) {
		
		super(Opcodes.ASM4, mv, access, name, desc);
		this.cName = className;
		this.mName = name;

	}

	@Override
	protected void onMethodEnter() {				
		time = newLocal(Type.LONG_TYPE);  
		super.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J");			
		visitVarInsn(LSTORE, time);		
		System.out.println("Method Enter: " + this.cName + "::" + this.mName);
		//super.visitLdcInsn(this.cName);
		//super.visitLdcInsn(this.mName);
		//super.visitVarInsn(LLOAD, time);
		super.onMethodEnter();
	}

	@Override
	protected void onMethodExit(int opcode) {

		super.visitLdcInsn(this.cName);
		super.visitLdcInsn(this.mName);
		super.visitVarInsn(LLOAD, time);
		super.visitMethodInsn(Opcodes.INVOKESTATIC,
				 "asmapm/Agent", "testefunc",
				 "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;J)V");
		
		super.onMethodExit(opcode);
		//System.out.println("Passei por aqui no onExit");
		//super.visitMethodInsn(Opcodes.INVOKESTATIC, "asmapm/Agent","testefunc", "()V");
		/*
		 * mv.visitFieldInsn(GETSTATIC, cName, "timer", "J");
		 * mv.visitMethodInsn(INVOKESTATIC, "java/lang/System",
		 * "currentTimeMillis", "()J"); mv.visitInsn(LADD);
		 * mv.visitFieldInsn(PUTSTATIC, cName, "timer", "J");
		 * super.visitLdcInsn(cName); super.visitLdcInsn(mName);
		 * super.visitLdcInsn(desc); System.out.println("Passei por aqui"); //
		 * super.visitLdcInsn(); super.visitMethodInsn(Opcodes.INVOKESTATIC,
		 * "asmapm/Agent", "log",
		 * "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;J)V");
		 */

	}

	@Override
	public void visitMaxs(int maxStack, int maxLocals) {		
		super.visitMaxs(maxStack, maxLocals);
	}

}
