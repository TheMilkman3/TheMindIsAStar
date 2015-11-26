package mias.world;

import java.util.HashMap;

import mias.entity.Entity;
import mias.entity.RenderedEntity;
import mias.render.GUIMap;
import mias.render.RenderHandler;

public class World {

	private static World instance;

	public static final int CHUNK_LOAD_RADIUS = 4;

	protected HashMap<Integer, Chunk> loadedChunks = new HashMap<Integer, Chunk>();
	protected HashMap<Long, Entity> loadedEntities = new HashMap<Long, Entity>();
	protected HashMap<Long, RenderedEntity> loadedRenderableEntities = new HashMap<Long, RenderedEntity>();

	protected RenderedEntity player;

	public World() {
		instance = this;
		Chunk firstChunk = new Chunk(0, 0, 0);
		addChunk(firstChunk);
		ChunkProvider.setDefaultChunk(firstChunk);
		GUIMap guiMap = new GUIMap(0f, 0f, 1f, 1f, 1);
		RenderHandler.instance().addGUIWindow(guiMap);
		player = new RenderedEntity("Player", 16, 0, 16).setTexture("entity_player");
		player.loadEntity(this);
		guiMap.setCameraCoord((int)player.getX() - 32, (int)player.getZ() - 20, 0);
		guiMap.setCameraDimensions(64, 40);
		guiMap.adjustForAspect();
		guiMap.activate();
	}

	public static World instance() {
		return instance;
	}

	public void addChunk(Chunk c) {
		loadedChunks.put(Chunk.convertCoordinate(c.getChunkX(), c.getChunkY(), c.getChunkZ()), c);
	}

	public RenderedEntity getPlayer() {
		return player;
	}

	public void setPlayer(RenderedEntity player) {
		this.player = player;
	}

	public HashMap<Integer, Chunk> getLoadedChunks() {
		return loadedChunks;
	}

	public HashMap<Long, Entity> getLoadedEntities() {
		return loadedEntities;
	}

	public HashMap<Long, RenderedEntity> getLoadedRenderableEntities() {
		return loadedRenderableEntities;
	}
}
