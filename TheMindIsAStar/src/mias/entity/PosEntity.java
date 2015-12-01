package mias.entity;

import mias.world.Chunk;

public class PosEntity extends Entity {

	protected long x;
	protected long y;
	protected long z;

	public PosEntity(String name, long x, long y, long z) {
		super(name);
		setPos(x, y, z);
	}

	public long getX() {
		return x;
	}

	public long getY() {
		return y;
	}

	public long getZ() {
		return z;
	}
	
	public int getChunkX(){
		return Math.floorDiv((int)getX(), Chunk.CHUNK_WIDTH);
	}
	
	public int getChunkY(){
		return Math.floorDiv((int)getY(), Chunk.CHUNK_HEIGHT);
	}
	
	public int getChunkZ(){
		return Math.floorDiv((int)getZ(), Chunk.CHUNK_DEPTH);
	}

	public void setPos(long x, long y, long z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void offsetPos(long x, long y, long z) {
		setPos(this.x + x, this.y + y, this.z + z);
	}
}
