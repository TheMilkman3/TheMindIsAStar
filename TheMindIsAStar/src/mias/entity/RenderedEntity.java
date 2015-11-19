package mias.entity;

public class RenderedEntity extends PosEntity {

	private int textureID;
	
	public RenderedEntity(long entityID, String name, int textureID, long x, long y, long z) {
		super(entityID, name, x, y, z);
		this.textureID = textureID;
	}

	public int getTexture() {
		return textureID;
	}
	
}
