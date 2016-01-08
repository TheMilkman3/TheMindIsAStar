package mias.render;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;

import com.jogamp.opengl.GL4;

public class GUIMenu extends GUIWindow {

	protected float characterSize = 0.01f;
	protected String header = null;
	protected LinkedList<MenuItem> menuItems = new LinkedList<MenuItem>();
	protected MenuFunction function = MenuFunction.NONE;
	protected Color menuColor = Color.WHITE;
	protected int numItems = 0;
	
	public GUIMenu(float x, float y, float width, float height, int depth) {
		super(x, y, width, height, depth);
		updateView();
	}
	
	public int getVisibleLines(){
		return (int)(height/characterSize);
	}
	
	public int getCharactersPerLine(){
		return (int)(width/characterSize);
	}
	

	@Override
	public synchronized void draw(GL4 gl4) {
		int i = 0;
		if (header != null){
			model.translate(0f, getVisibleLines() - 1f, 0);
			RenderHandler.instance().drawText(gl4, header, model.peek(), view.peek(), Color.WHITE);
			model.pop();
			i++;
		}
		if (!menuItems.isEmpty()){
			Iterator<MenuItem> iter = menuItems.listIterator();
			for (; i <= getVisibleLines() - 1; i++){
				MenuItem m = iter.next();
				model.translate(0f, getVisibleLines() - (float)(i) - 1f, 0);
				String string = m.getKey() + ": " + m.getItem();
				RenderHandler.instance().drawText(gl4, string, model.peek(), view.peek(), Color.WHITE);
				model.pop();
				if (!iter.hasNext()){
					break;
				}
			}
		}
	}
	
	public synchronized void addMenuItem(String item){
		menuItems.add(new MenuItem(Integer.toString(numItems).charAt(0), item));
		numItems++;
	}
	
	public void clearMenuItems(){
		menuItems.clear();
		numItems = 0;
	}
	
	@Override
	public void updateView() {
		view.clear();
		view.translate(0f, 0f, -40f);
		view.ortho(0f, (float)getCharactersPerLine(), 0f, (float)getVisibleLines(), 0.01f, 50f);
		view.scale(width, height, 1);
		view.translate((x * 2 - 1) + width, (y * 2 - 1) + height, 0f);
	}
	
	private class MenuItem{
		private char key;
		private String item;
		
		public MenuItem(char key, String item){
			this.key = key;
			this.item = item;
		}

		public char getKey() {
			return key;
		}

		public String getItem() {
			return item;
		}
	}
	
	public enum MenuFunction{
		NONE, PICK_UP, DROP, ATTACK_ENTITY
	}

	public MenuFunction getFunction() {
		return function;
	}

	public void setFunction(MenuFunction function) {
		this.function = function;
	}
	
	public String getItemFromInput(char key){
		for(MenuItem m : menuItems){
			if (m.key == key){
				return m.getItem();
			}
		}
		return null;
	}
	

	public void setHeader(String header) {
		this.header = header;
	}
}
