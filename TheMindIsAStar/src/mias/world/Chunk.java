package mias.world;

import java.nio.ByteBuffer;
import java.util.HashMap;

import mias.entity.PosEntity;
import mias.util.WorldCoord;

public class Chunk {

	public static final int CHUNK_WIDTH = 32;
	public static final int CHUNK_HEIGHT = 32;
	public static final int CHUNK_DEPTH = 32;
	public static final int METADATA_SIZE = 1;
	public static final int TEMPERATURE_SIZE = Short.BYTES;
	public static final int TILE_DATA_SIZE = 
			Short.BYTES/*ID*/ + METADATA_SIZE/*Metadata*/ + TEMPERATURE_SIZE/*Temperature*/; 
	public static final int NUMBER_OF_TILES = 
			CHUNK_WIDTH * CHUNK_HEIGHT * CHUNK_DEPTH;
	public static final int CHUNK_DATA_SIZE = TILE_DATA_SIZE * NUMBER_OF_TILES;
	public static final int ID_INDEX = 0, 
			METADATA_INDEX = ID_INDEX + Short.BYTES,
			TEMPERATURE_INDEX = METADATA_INDEX + 1;

	protected int chunkX;
	protected int chunkY;
	protected int chunkZ;

	protected HashMap<Integer, PosEntity> chunkEntites;
	protected ByteBuffer chunkData;

	public Chunk(int chunkX, int chunkY, int chunkZ, boolean allocate) {
		this.chunkX = chunkX;
		this.chunkY = chunkY;
		this.chunkZ = chunkZ;

		if(allocate){
			chunkData = ByteBuffer.allocate(CHUNK_DATA_SIZE);
		}
	}
	
	public Chunk(int chunkX, int chunkY, int chunkZ){
		this(chunkX, chunkY, chunkZ, true);
	}

	public short getTileID(int x, int y, int z) {
		return chunkData.getShort(tileIndex(x, y, z) + ID_INDEX);
	}

	public void setTileID(short tileID, int x, int y, int z) {
		chunkData.putShort(tileIndex(x, y, z) + ID_INDEX, tileID);
	}
	
	public byte getTileMetadata(int x, int y, int z){
		return chunkData.get(tileIndex(x, y, z) + METADATA_INDEX);
	}
	
	public void setTileMetadata(byte metadata, int x, int y, int z){
		chunkData.put(tileIndex(x, y, z) + METADATA_INDEX, metadata);
	}
	
	public int getTileTemperature(int x, int y, int z){
		short bits = chunkData.getShort(tileIndex(x, y, z) + TEMPERATURE_INDEX);
		return bits & 0xFFFF;
	}
	
	public void setTileTemperature(int temperature, int x, int y, int z){
		short bits = (short)temperature;
		chunkData.putShort(tileIndex(x, y, z) + TEMPERATURE_INDEX, bits);
	}
	
	public static int convertCoordinate(int x, int y, int z) {
		return ((y * CHUNK_WIDTH * CHUNK_DEPTH) + (z * CHUNK_WIDTH) + x);
	}
	
	public static WorldCoord worldToChunk(WorldCoord coord){
		int chunkX = (int)(Math.floorDiv(coord.x, Chunk.CHUNK_WIDTH));
		int chunkY = (int)(Math.floorDiv(coord.y, Chunk.CHUNK_HEIGHT));
		int chunkZ = (int)(Math.floorDiv(coord.z, Chunk.CHUNK_DEPTH));
		return new WorldCoord(chunkX, chunkY, chunkZ);
	}
	
	public static WorldCoord worldToTile(WorldCoord coord){
		int tileX = (int)(Math.floorMod(coord.x, Chunk.CHUNK_WIDTH));
		int tileY = (int)(Math.floorMod(coord.y, Chunk.CHUNK_HEIGHT));
		int tileZ = (int)(Math.floorMod(coord.z, Chunk.CHUNK_DEPTH));
		return new WorldCoord(tileX, tileY, tileZ);
	}
	
	private static int tileIndex(int x, int y, int z) {
		return convertCoordinate(x, y, z) * TILE_DATA_SIZE;
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
		return chunkData;
	}

	public void fromByteBuffer(ByteBuffer data) {
		this.chunkData = data;
	}

}
