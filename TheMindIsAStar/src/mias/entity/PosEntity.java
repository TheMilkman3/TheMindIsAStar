package mias.entity;

public class PosEntity extends Entity {
	
	protected long x;
	protected long y;
	protected long z;
	
	public PosEntity(long entityID, String name, long x, long y, long z) {
		super(entityID, name);
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public long getX() {
		return x;
	}

	public void setX(long x) {
		this.x = x;
	}

	public long getY() {
		return y;
	}

	public void setY(long y) {
		this.y = y;
	}

	public long getZ() {
		return z;
	}

	public void setZ(long z) {
		this.z = z;
	}
	
	
}
