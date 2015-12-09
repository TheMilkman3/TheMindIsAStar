package mias.render;

import java.util.Iterator;
import java.util.LinkedList;

import com.jogamp.opengl.GL4;

import mias.util.Message;

public class GUIMessageBox extends GUIWindow {
	
	protected int visibleLines = 10;
	protected int totalLines = 100;
	protected int selectedLine = 0;
	protected int characterWidth = 50;
	protected LinkedList<Message> messages = new LinkedList<Message>();
	
	public GUIMessageBox(float x, float y, float width, float height, int depth) {
		super(x, y, width, height, depth);
	}

	@Override
	public synchronized void draw(GL4 gl4) {
		if (!messages.isEmpty()){
			Iterator<Message> iter = messages.listIterator(selectedLine);
			for (int i = selectedLine; i <= visibleLines; i++){
				Message m = iter.next();
				RenderHandler.instance().drawText(gl4, m.string, model.peek(), view.peek(), m.type.getColor());
				if (!iter.hasNext()){
					break;
				}
			}
		}
	}

	public int getVisibleLines() {
		return visibleLines;
	}

	public void setVisibleLines(int visibleLines) {
		this.visibleLines = visibleLines;
	}

	public int getTotalLines() {
		return totalLines;
	}

	public void setTotalLines(int totalLines) {
		this.totalLines = totalLines;
	}

	public int getSelectedLine() {
		return selectedLine;
	}

	public void setSelectedLine(int selectedLine) {
		this.selectedLine = selectedLine;
	}
	
	public synchronized void addMessage(Message m) {
		messages.addFirst(m);
		if (messages.size() > totalLines){
			messages.removeLast();
		}
	}
	
	public synchronized void clearMessages(){
		messages.clear();
	}
}
