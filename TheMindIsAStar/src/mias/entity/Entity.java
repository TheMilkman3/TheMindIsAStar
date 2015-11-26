package mias.entity;

import mias.world.World;

//thing test

public class Entity {

	protected long entityID;
	protected String name;

	private static long lastEntityID = 0;

	public Entity(String name) {
		this.name = name;
		entityID = lastEntityID;
		lastEntityID++;
	}

	public long getEntityID() {
		return entityID;
	}

	public boolean isRenderable() {
		return false;
	}

	public void loadEntity(World w) {
		w.getLoadedEntities().put(entityID, this);
	}
}
