package mias.tile;

import java.util.HashMap;

public class Tile {
	
	private short tileID;
	private String unLocalizedName;
	private int textureID;
	
	private HashMap<Short, Tile> tileMap = new HashMap<Short, Tile>();
	private static short nextID = 0;
	
	public static short airTile = createTile("air", 0);
	public static short dirtTile = createTile("dirt", 0);
	
	private Tile(String unLocalizedName, int textureID) {
		this.tileID = nextID;
		nextID++;
		this.unLocalizedName = unLocalizedName;
		this.textureID = textureID;
		tileMap.put(this.tileID, this);
	}
	
	public static short createTile(String unLocalizedName, int textureID) {
		Tile t = new Tile(unLocalizedName, textureID);
		return t.getTileID();
	}

	public short getTileID() {
		return tileID;
	}

	public String getUnLocalizedName() {
		return unLocalizedName;
	}
	
	public String getName() {
		return unLocalizedName;
	}

	public int getTextureID() {
		return textureID;
	}
		
}
