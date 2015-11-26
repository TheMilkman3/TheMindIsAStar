package mias.world;

import java.nio.ByteBuffer;
import java.util.HashMap;

import mias.entity.PosEntity;

public class Chunk {

	public static final int CHUNK_WIDTH = 32;
	public static final int CHUNK_HEIGHT = 32;
	public static final int CHUNK_DEPTH = 32;

	protected int chunkX;
	protected int chunkY;
	protected int chunkZ;

	protected HashMap<Integer, PosEntity> chunkEntites;
	protected short[] chunkTiles;

	public Chunk(int chunkX, int chunkY, int chunkZ) {
		this.chunkX = chunkX;
		this.chunkY = chunkY;
		this.chunkZ = chunkZ;

		chunkTiles = new short[CHUNK_WIDTH * CHUNK_HEIGHT * CHUNK_DEPTH];
	}

	public short getTileID(int x, int y, int z) {
		return chunkTiles[convertCoordinate(x, y, z)];
	}

	public void setTileID(short tileID, int x, int y, int z) {
		chunkTiles[convertCoordinate(x, y, z)] = tileID;
	}

	public static int convertCoordinate(int x, int y, int z) {
		return ((y * CHUNK_WIDTH * CHUNK_DEPTH) + (z * CHUNK_WIDTH) + x);
	}

	public static int getSize() {
		return CHUNK_WIDTH * CHUNK_HEIGHT * CHUNK_DEPTH;
	}

	public int getChunkX() {
		return chunkX;
	}

	public int getChunkY() {
		return chunkY;
	}

	public int getChunkZ() {
		return chunkZ;
	}

	public ByteBuffer toByteBuffer() {
		int capacity = 4 + getSize() * 2;
		ByteBuffer data = ByteBuffer.allocate(capacity);
		data.putInt(getSize());
		for (int i = 0; i <= CHUNK_WIDTH - 1; i++) {
			for (int j = 0; j <= CHUNK_HEIGHT - 1; j++) {
				for (int k = 0; k <= CHUNK_DEPTH - 1; k++) {
					data.putShort(getTileID(i, j, k));
				}
			}
		}
		return data;
	}

	public boolean fromByteBuffer(ByteBuffer data) {
		if (data.getInt() == getSize()) {
			for (int i = 0; i <= CHUNK_WIDTH - 1; i++) {
				for (int j = 0; j <= CHUNK_HEIGHT - 1; j++) {
					for (int k = 0; k <= CHUNK_DEPTH - 1; k++) {
						setTileID(data.getShort(), i, j, k);
					}
				}
			}
			return true;
		}
		return false;
	}

}
