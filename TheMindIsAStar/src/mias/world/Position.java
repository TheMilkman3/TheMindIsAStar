package mias.world;

import java.util.LinkedList;

import mias.entity.PosEntity;
import mias.util.WorldCoord;

public class Position {
	
	private LinkedList<PosEntity> entities = new LinkedList<PosEntity>();
	private final WorldCoord coord;
	
	public Position(WorldCoord coord){
		this.coord = coord;
	}

	public WorldCoord getCoord() {
		return coord;
	}
	
	public LinkedList<PosEntity> getEntities(){
		return entities;
	}
	
	public void place(PosEntity e){
		entities.add(e);
	}
	
	public void remove(PosEntity e){
		entities.remove(e);
	}
	
	public boolean isEmpty(){
		return entities.isEmpty();
	}
	
	public int numEntities(){
		return entities.size();
	}
}
