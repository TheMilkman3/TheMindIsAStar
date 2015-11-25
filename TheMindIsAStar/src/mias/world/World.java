package mias.world;

import java.util.HashMap;

import mias.entity.PosEntity;
import mias.render.GUIMap;
import mias.render.RenderHandler;

public class World {
	
	private static World instance;
	
	public static final int CHUNK_LOAD_RADIUS = 4;
	
	protected HashMap<Integer, Chunk> loadedChunks = new HashMap<Integer, Chunk>();
	
	protected PosEntity player;
	
	public World() {
		instance = this;
		Chunk firstChunk = new Chunk(0, 0, 0);
		addChunk(firstChunk);
		ChunkProvider.setDefaultChunk(firstChunk);
		GUIMap guiMap = new GUIMap(0f, 0f, 1f, 1f, 1);
		RenderHandler.instance().addGUIWindow(guiMap);
		guiMap.setCameraCoord(0, 0);
		guiMap.setCameraDimensions(32, 32);
		guiMap.activate();
	}
	
	public static World instance() {
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
	
	public HashMap<Integer, Chunk> getLoadedChunks(){
		return loadedChunks;
	}
}
