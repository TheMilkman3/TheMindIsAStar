package mias.util;

import java.awt.Color;

public class MessageType {
	public static final MessageType SOUND = new MessageType(Color.YELLOW), 
			SPEECH = new MessageType(Color.BLUE), 
			SIGHT = new MessageType(Color.WHITE), 
			SELF = new MessageType(Color.GREEN);
	
	private Color color;
	
	private MessageType(Color color){
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
