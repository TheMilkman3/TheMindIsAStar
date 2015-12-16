package mias.render;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.math.Matrix4;

import mias.render.util.MatrixStack;

public abstract class GUIWindow implements Comparable<GUIWindow> {

	protected float x, y, width, height;
	protected int depth;
	protected boolean active = false;
	protected RenderHandler renderHandler = null;

	protected MatrixStack model = new MatrixStack(), view = new MatrixStack();

	public GUIWindow(float x, float y, float width, float height, int depth) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.updateView();
	}

	public abstract void draw(GL4 gl4);

	public Matrix4 mvpMatrix() {
		Matrix4 mvp = new Matrix4();
		mvp.multMatrix(view.peek());
		mvp.multMatrix(model.peek());
		return mvp;
	}

	public void activate() {
		active = true;
	}

	public void deactivate() {
		active = false;
	}

	public void setCoord(float x, float y) {
		this.x = x;
		this.y = y;
		this.updateView();
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public void setDimensions(float width, float height) {
		this.width = width;
		this.height = height;
		this.updateView();
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
		if (renderHandler != null) {
			renderHandler.sortGUIWindows();
		}
	}

	public MatrixStack getModel() {
		return model;
	}

	public MatrixStack getView() {
		return view;
	}

	@Override
	public int compareTo(GUIWindow arg0) {
		if (depth > arg0.getDepth()) {
			return 1;
		} else if (depth == arg0.getDepth()) {
			return 0;
		} else {
			return -1;
		}
	}

	public void setRenderHandler(RenderHandler renderHandler) {
		this.renderHandler = renderHandler;
	}

	public void updateView() {
		view.clear();
		view.scale(width, height, 1);
		view.translate(x, y, 0);
	}
	
	public void adjustForAspect(){
		float aspect = renderHandler.aspectRatio();
		if(aspect > 1f) {
			
		}
	}
	
	public boolean inFocus(){
		return renderHandler.getFocusedWindow() == this;
	}
	
	public void focus(){
		renderHandler.focusOnWindow(this);
	}
}
