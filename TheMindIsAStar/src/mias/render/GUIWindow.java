package mias.render;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.math.Matrix4;

import mias.render.util.MatrixStack;

public abstract class GUIWindow {
	
	protected int x, y, width, height;
	protected int depth;
	protected boolean active = false;
	
	protected MatrixStack model, view;
	
	public GUIWindow(int x, int y, int width, int height, int depth) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.depth = depth;
	}
	
	public abstract void draw(GL4 gl4);
	
	public Matrix4 mvpMatrix(){
		Matrix4 mvp = new Matrix4();
		mvp.multMatrix(view.peek());
		mvp.multMatrix(model.peek());
		return mvp;
	}
	
	public void activate(){
		active = true;
	}
	
	public void deactivate(){
		active = false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public MatrixStack getModel() {
		return model;
	}

	public MatrixStack getView() {
		return view;
	}
}
