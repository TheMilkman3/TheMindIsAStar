package mias.entity;

public class RenderedEntity extends PosEntity {

	private String texture;
	
	public RenderedEntity(long entityID, String name, String texture, long x, long y, long z) {
		super(entityID, name, x, y, z);
		this.texture = texture;
	}

	public String getTexture() {
		return texture;
	}
	
}
