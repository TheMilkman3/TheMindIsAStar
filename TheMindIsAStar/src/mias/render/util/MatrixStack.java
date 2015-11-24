package mias.render.util;

import java.util.Stack;

import com.jogamp.opengl.math.Matrix4;

public class MatrixStack {
	private Stack<Matrix4> stack = new Stack<Matrix4>();
	
	public MatrixStack(){
		Matrix4 identity = new Matrix4();
		identity.loadIdentity();
		stack.push(identity);
	}
	
	public void push(Matrix4 m) {
		if (stack.size() > 1) {
			m.multMatrix(stack.peek());
		}
		stack.push(m);
	}
	
	public Matrix4 pop(){
		if(stack.size() > 1){
			return stack.pop();
		}
		return stack.peek();
	}
	
	public Matrix4 peek(){
		return stack.peek();
	}
	
	public void translate(float x, float y, float z) {
		Matrix4 trans = new Matrix4();
		trans.translate(x, y, z);
		stack.push(trans);
	}
}
