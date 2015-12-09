package mias.render;

import java.util.Iterator;
import java.util.LinkedList;

import com.jogamp.opengl.GL4;

import mias.util.Message;

public class GUIMessageBox extends GUIWindow {
	
	protected int maxMessages = 100;
	protected int selectedLine = 0;
	protected float characterSize = 0.01f;
	protected LinkedList<Message> messages = new LinkedList<Message>();
	
	public GUIMessageBox(float x, float y, float width, float height, int depth) {
		super(x, y, width, height, depth);
		updateView();
	}

	@Override
	public synchronized void draw(GL4 gl4) {
		if (!messages.isEmpty()){
			Iterator<Message> iter = messages.listIterator(selectedLine);
			for (int i = 0; i <= getVisibleLines() - 1; i++){
				Message m = iter.next();
				model.translate(0f, getVisibleLines() - (float)(i) - 1f, 0);
				RenderHandler.instance().drawText(gl4, m.string, model.peek(), view.peek(), m.type.getColor());
				model.pop();
				if (!iter.hasNext()){
					break;
				}
			}
		}
	}
	
	public int getVisibleLines(){
		return (int)(height/characterSize);
	}
	
	public int getCharactersPerLine(){
		return (int)(width/characterSize);
	}
	
	public int getSelectedLine() {
		return selectedLine;
	}

	public void setSelectedLine(int selectedLine) {
		this.selectedLine = selectedLine;
	}
	
	public synchronized void addMessage(Message m) {
		messages.addFirst(m);
		while(messages.size() > maxMessages){
			messages.removeLast();
		}
	}
	
	public synchronized void clearMessages(){
		messages.clear();
	}

	@Override
	public void updateView() {
		view.clear();
		view.translate(0f, 0f, -40f);
		view.ortho(0f, (float)getCharactersPerLine(), 0f, (float)getVisibleLines(), 0.01f, 50f);
		view.scale(width, height, 1);
		view.translate((x * 2 - 1) + width, (y * 2 - 1) + height, 0f);
	}
	
	
}
