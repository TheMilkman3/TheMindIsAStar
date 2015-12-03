package mias.tile;

import java.util.HashMap;

public class Tile {

	private short tileID;
	private String unLocalizedName;
	private String texture;

	private static HashMap<Short, Tile> tileMap = new HashMap<Short, Tile>();
	private static short nextID = 0;

	public static short airTile = new Tile("air").setTexture(null).getTileID();
	public static short grassTile = new Tile("grass").setTexture("tile_grass").getTileID();
	public static short wallTile = new Tile("wall").setTexture("tile_wall").getTileID();

	private Tile(String unLocalizedName) {
		this.tileID = nextID;
		nextID++;
		this.unLocalizedName = unLocalizedName;
		tileMap.put(this.tileID, this);
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

	public String getTexture() {
		return texture;
	}

	public Tile setTexture(String texture) {
		this.texture = texture;
		return this;
	}

	public static Tile getTile(short tileID) {
		return tileMap.get(tileID);
	}

	public static String getTexture(short tileID) {
		return getTile(tileID).texture;
	}
}
