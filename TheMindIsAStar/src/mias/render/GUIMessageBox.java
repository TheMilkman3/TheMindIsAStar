package mias.render;

import java.util.LinkedList;

import com.jogamp.opengl.GL4;

import mias.util.Message;

public class GUIMessageBox extends GUIWindow {
	
	protected boolean oldestAtTop;
	protected int visibleLines;
	protected int totalLines;
	protected float fontSize;
	protected LinkedList<Message> messages = new LinkedList<Message>();
	
	public GUIMessageBox(float x, float y, float width, float height, int depth) {
		super(x, y, width, height, depth);
	}

	@Override
	public void draw(GL4 gl4) {
		// TODO Auto-generated method stub

	}

}
