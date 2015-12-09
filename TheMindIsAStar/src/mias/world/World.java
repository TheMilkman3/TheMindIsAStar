package mias.world;

import java.util.HashMap;

import mias.entity.Entity;
import mias.entity.EntityUpdateHandler;
import mias.entity.RenderedEntity;
import mias.render.GUIMap;
import mias.render.GUIMessageBox;
import mias.render.RenderHandler;
import mias.save.ChunkSaveHandler;
import mias.tile.Tile;
import mias.util.EntityMaker;
import mias.util.Message;
import mias.util.MessageType;
import mias.util.TestHelper;
import mias.util.WorldCoord;

public class World {

	private static World instance;

	public static final int CHUNK_LOAD_RADIUS = 3;
	public static final long WORLD_WIDTH = 200;
	public static final long WORLD_HEIGHT = 100;
	public static final long WORLD_DEPTH = 200;

	protected HashMap<Long, Chunk> loadedChunks = new HashMap<Long, Chunk>();
	protected HashMap<Long, Entity> loadedEntities = new HashMap<Long, Entity>();
	protected HashMap<Long, RenderedEntity> loadedRenderableEntities = new HashMap<Long, RenderedEntity>();

	protected GUIMap guiMap;
	protected EntityUpdateHandler updateHandler;
	protected RenderedEntity player;
	protected int centerX, centerY, centerZ;
	protected boolean playerDone;

	private GUIMessageBox guiMessage;

	public World() {
		instance = this;
		//chunks
		Chunk firstChunk = new Chunk(0, 0, 0);
		addChunk(firstChunk);
		ChunkProvider.setDefaultChunk(firstChunk);
		//entity update handler
		updateHandler = EntityUpdateHandler.instantiate();
		//player
		player = EntityMaker.testPlayer();
		player.loadEntity(this);
		RenderedEntity testNPC = EntityMaker.testNPC();
		testNPC.loadEntity(this);
		centerX = player.getChunkX();
		centerY = player.getChunkY();
		centerZ = player.getChunkZ();
		loadChunksInRadius();
		TestHelper.setTileCube(Tile.wallTile, 5, 0, -5, 5, 0, 5);
		//GUI setup
		guiMap = new GUIMap(0f, 0.25f, 1f, 1f, 1);
		guiMessage = new GUIMessageBox(0f, 0f, 1f, 0.25f, 2);
		RenderHandler.instance().addGUIWindow(guiMap);
		guiMap.setCameraDimensions(32, 32);
		guiMap.centerOn(player);
		RenderHandler.instance().addGUIWindow(guiMessage);
		guiMap.activate();
		guiMessage.activate();
		guiMessage.addMessage(new Message("Test message", MessageType.SELF));
	}

	public static World instance() {
		return instance;
	}

	public void addChunk(Chunk c) {
		loadedChunks.put(chunkHash(c.getChunkX(), c.getChunkY(), c.getChunkZ()), c);
	}
	
	public static Long chunkHash(int x, int y, int z){
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
				Chunk c = this.getChunk(x, 0, z);
				if (c == null){
					c = ChunkSaveHandler.loadChunk(x, 0, z);
					if (c == null){
						c = new Chunk(x, 0, z);
						ChunkProvider.setDefaultChunk(c);
					}
				}
				tempLoadedChunks.put(chunkHash(x, 0, z), c);
			}
		}
		synchronized(loadedChunks){
			loadedChunks = tempLoadedChunks;
		}
	}
	
	public Chunk getChunk(int x, int y, int z){
		return loadedChunks.get(chunkHash(x, y, z));
	}
	
	public short getTileID(long x, long y, long z){
		int chunkX = (int)(Math.floorDiv(x, Chunk.CHUNK_WIDTH));
		int chunkY = (int)(Math.floorDiv(y, Chunk.CHUNK_HEIGHT));
		int chunkZ = (int)(Math.floorDiv(z, Chunk.CHUNK_DEPTH));
		int tileX = (int)(Math.floorMod(x, Chunk.CHUNK_WIDTH));
		int tileY = (int)(Math.floorMod(y, Chunk.CHUNK_HEIGHT));
		int tileZ = (int)(Math.floorMod(z, Chunk.CHUNK_DEPTH));
		Chunk c = getChunk(chunkX, chunkY, chunkZ);
		if (c != null){
			return c.getTileID(tileX, tileY, tileZ);
		}
		else{
			return -1;
		}
	}
	
	public short getTileID(WorldCoord c){
		return getTileID(c.x, c.y, c.z);
	}
	
	public void setTileID(short tileID, long x, long y, long z){
		int chunkX = (int)(Math.floorDiv(x, Chunk.CHUNK_WIDTH));
		int chunkY = (int)(Math.floorDiv(y, Chunk.CHUNK_HEIGHT));
		int chunkZ = (int)(Math.floorDiv(z, Chunk.CHUNK_DEPTH));
		int tileX = (int)(Math.floorMod(x, Chunk.CHUNK_WIDTH));
		int tileY = (int)(Math.floorMod(y, Chunk.CHUNK_HEIGHT));
		int tileZ = (int)(Math.floorMod(z, Chunk.CHUNK_DEPTH));
		Chunk c = getChunk(chunkX, chunkY, chunkZ);
		if (c != null){
			c.setTileID(tileID, tileX, tileY, tileZ);
		}
	}
	
	public void update(){
		EntityUpdateHandler.instance().updateEntites();
		synchronized(guiMap){
			if (player.getChunkX() != centerX || player.getChunkY() != centerY 
					|| player.getChunkZ() != centerZ){
				centerX = player.getChunkX();
				centerY = player.getChunkY();
				centerZ = player.getChunkZ();
				loadChunksInRadius();
			}
			guiMap.centerOn(player);
		}
	}
}
