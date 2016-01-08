package mias.entity;

import java.util.HashMap;

import mias.tile.Tile;
import mias.util.WorldCoord;
import mias.world.Chunk;
import mias.world.Position;
import mias.world.World;

public class PosEntity extends Entity {

	private WorldCoord coord;
	private PosEntity heldBy = null;

	public PosEntity(String name, long x, long y, long z) {
		super(name);
		setPos(x, y, z);
	}

	public long getX() {
		return coord.x;
	}

	public long getY() {
		return coord.y;
	}

	public long getZ() {
		return coord.z;
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
		setPos(new WorldCoord(x, y, z));
	}
	
	public void setPos(WorldCoord coord){
		HashMap<WorldCoord, Position> tileContent = World.instance().getTileContent();
		if (this.coord != null){
			tileContent.get(this.coord).remove(this);
			if (tileContent.get(this.coord).isEmpty()){
				tileContent.remove(this.coord);
			}
		}
		if (!tileContent.containsKey(coord)){
			tileContent.put(coord, new Position(coord));
		}
		tileContent.get(coord).place(this);
		this.coord = coord;
	}

	public void offsetPos(long x, long y, long z) {
		setPos(coord.x + x, coord.y + y, coord.z + z);
	}

	public WorldCoord getPos() {
		if (coord == null && isHeld()){
			return heldBy.getPos();
		}
		return coord;
	}
	
	public boolean canPass(WorldCoord coord){
		return coord.inLoadedChunk(World.instance()) && World.instance().getTileID(coord) != Tile.wallTile;
	}
	

	public PosEntity getHeldBy() {
		return heldBy;
	}
	
	public boolean isHeld(){
		return heldBy != null;
	}
	

	public void setHeldBy(PosEntity heldBy) {
		if (heldBy != null){
			setPos(null);
		}
		else{
			if(isHeld()){
				setPos(getHeldBy().getPos());
			}
		}
		this.heldBy = heldBy;
	}
	
}
