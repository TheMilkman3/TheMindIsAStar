package mias.entity;

import java.util.HashMap;

import mias.entity.ai.AIController;
import mias.world.World;

//thing test

public class Entity {

	private HashMap<String, EntityAttribute> attributes = new HashMap<String, EntityAttribute>(5);
	
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
	
	public void update(){
		AIController ai = (AIController) this.getAttribute(EntityAttribute.AI_CONTROLLER);
		if (ai != null){
			ai.update(World.instance());
		}
	}
	
	public boolean hasAttribute(String attributeName){
		return attributes.containsKey(attributeName);
	}
	
	public EntityAttribute getAttribute(String attributeName){
		return attributes.get(attributeName);
	}
	
	public Entity giveAttribute(EntityAttribute attribute){
		if (!hasAttribute(attribute.attributeType())){
			attributes.put(attribute.attributeType(), attribute);
			attribute.owner = this;
			attribute.onGive();
		}
		return this;
	}
	
	public Entity removeAttribute(String attributeType){
		EntityAttribute attribute = attributes.remove(attributeType);
		if(attribute != null){
			attribute.onRemove();
		}
		return this;
	}
}
