package mias.entity;

import java.util.HashMap;

import mias.entity.action.Action;
import mias.entity.ai.AIController;
import mias.entity.attributes.PlayerControl;
import mias.entity.attributes.Updateable;
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
		Updateable up = (Updateable) this.getAttribute(EntityAttribute.UPDATEABLE);
		PlayerControl pc  = (PlayerControl) this.getAttribute(EntityAttribute.PLAYER_CONTROL);
		if (ai != null && up != null && pc == null){
			Action action = ai.getNextAction();
			if (action != null){
				up.SetTicksUntilUpdate(action.execute());
			}
			else{
				up.SetTicksUntilUpdate(10);
			}
		}
		else if(pc != null){
			Action playerAction = pc.getAction();
			if (playerAction != null){
				up.SetTicksUntilUpdate(playerAction.execute());
				pc.setAction(null);
			}
		}
		else if (up != null){
			up.SetTicksUntilUpdate(10);
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
