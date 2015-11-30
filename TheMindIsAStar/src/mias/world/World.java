package mias.world;

import java.util.HashMap;
import java.util.LinkedList;

import mias.entity.Entity;
import mias.entity.EntityUpdateHandler;
import mias.entity.RenderedEntity;
import mias.render.GUIMap;
import mias.render.RenderHandler;
import mias.save.ChunkSaveHandler;

public class World {

	private static World instance;

	public static final int CHUNK_LOAD_RADIUS = 4;
	public static final long WORLD_WIDTH = 200;
	public static final long WORLD_HEIGHT = 100;
	public static final long WORLD_DEPTH = 200;

	protected HashMap<Long, Chunk> loadedChunks = new HashMap<Long, Chunk>();
	protected HashMap<Long, Entity> loadedEntities = new HashMap<Long, Entity>();
	protected HashMap<Long, RenderedEntity> loadedRenderableEntities = new HashMap<Long, RenderedEntity>();
	protected LinkedList<Chunk> chunkRenderList = new LinkedList<Chunk>();

	protected GUIMap guiMap;
	protected EntityUpdateHandler updateHandler;
	protected RenderedEntity player;
	protected int centerX, centerY, centerZ;

	public World() {
		instance = this;
		//chunks
		Chunk firstChunk = new Chunk(0, 0, 0);
		addChunk(firstChunk);
		ChunkProvider.setDefaultChunk(firstChunk);
		//entity update handler
		updateHandler = EntityUpdateHandler.instantiate();
		//player
		player = new RenderedEntity("Player", 16, 0, 16).setTexture("entity_player");
		player.loadEntity(this);
		centerX = player.getChunkX();
		centerY = player.getChunkY();
		centerZ = player.getChunkZ();
		loadChunksInRadius();
		//GUI setup
		guiMap = new GUIMap(0f, 0f, 1f, 1f, 1);
		RenderHandler.instance().addGUIWindow(guiMap);
		guiMap.setCameraCoord((int)player.getX() - 32, (int)player.getZ() - 20, 0);
		guiMap.setCameraDimensions(64, 40);
		guiMap.activate();
	}

	public static World instance() {
		return instance;
	}

	public void addChunk(Chunk c) {
		loadedChunks.put(chunkHash(c.getChunkX(), c.getChunkY(), c.getChunkZ()), c);
	}
	
	public Long chunkHash(int x, int y, int z){
		return (((long)y * WORLD_WIDTH * WORLD_DEPTH) + ((long)z * WORLD_WIDTH) + (long)x);
	}
	
	public RenderedEntity getPlayer() {
		return player;
	}

	public void setPlayer(RenderedEntity player) {
		this.player = player;
	}

	public HashMap<Long, Chunk> getLoadedChunks() {
		synchronized(loadedChunks){
			return loadedChunks;
		}
	}

	public HashMap<Long, Entity> getLoadedEntities() {
		synchronized(loadedEntities){
			return loadedEntities;
		}
	}

	public HashMap<Long, RenderedEntity> getLoadedRenderableEntities() {
		return loadedRenderableEntities;
	}

	public void loadChunksInRadius() {
		HashMap<Long, Chunk> tempLoadedChunks = new HashMap<Long, Chunk>();
		for (int x = centerX - CHUNK_LOAD_RADIUS; x <= centerX + CHUNK_LOAD_RADIUS; x++){
			for (int z = centerZ - CHUNK_LOAD_RADIUS; z <= centerZ + CHUNK_LOAD_RADIUS; z++){
				if (!loadedChunks.containsKey(chunkHash(x, 0, x))){
					Chunk c = ChunkSaveHandler.loadChunk(x, 0, z);
					if (c == null){
						c = new Chunk(x, 0, z);
						ChunkProvider.setDefaultChunk(c);
					}
					tempLoadedChunks.put(chunkHash(x, 0, z), c);
				}
			}
		}
		synchronized(loadedChunks){
			loadedChunks = tempLoadedChunks;
		}
	}

	public void update(){
		if (player.getChunkX() != centerX || player.getChunkY() != centerY 
				|| player.getChunkZ() != centerZ){
			centerX = player.getChunkX();
			centerY = player.getChunkY();
			centerZ = player.getChunkZ();
			loadChunksInRadius();
		}
		guiMap.setCameraCoord((int)player.getX() - 32, (int)player.getZ() - 20, (int)player.getY());
		
	}
}
