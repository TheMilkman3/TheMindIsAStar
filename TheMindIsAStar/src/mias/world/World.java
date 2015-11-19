package mias.world;

import java.util.HashMap;

import mias.entity.PosEntity;

public class World {
	
	private static World instance;
	
	public static final int CHUNK_LOAD_RADIUS = 4;
	
	protected HashMap<Integer, Chunk> loadedChunks;
	
	protected PosEntity player;
	
	private World() {
		loadedChunks = new HashMap<Integer, Chunk>();
		Chunk firstChunk = new Chunk(0, 0, 0);
		addChunk(firstChunk);
		ChunkProvider.setDefaultChunk(firstChunk);
	}
	
	public static World instance() {
		if (instance == null) {
			instance = new World();
		}
		return instance;
	}
	
	public void addChunk(Chunk c) {
		loadedChunks.put(Chunk.convertCoordinate(c.getChunkX(), c.getChunkY(), c.getChunkZ()), c);
	}

	public PosEntity getPlayer() {
		return player;
	}

	public void setPlayer(PosEntity player) {
		this.player = player;
	}
	
	
}
