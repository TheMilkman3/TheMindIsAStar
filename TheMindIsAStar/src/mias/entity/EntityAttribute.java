package mias.entity;

public abstract class EntityAttribute {
	
	public static final String AI_CONTROLLER = "AIController",
	UPDATEABLE = "Updateable",
	BODY = "Body",
	PLAYER_CONTROL = "PlayerControl",
	EQUIPPABLE = "Equippable",
	STRIKING_SURFACE = "Striking Surface";
	
	protected Entity owner; 
	
	public void setOwner(Entity owner){
		this.owner =  owner;
	}
	
	public Entity Owner(){
		return owner;
	}
	
	public abstract String attributeType();
	
	public abstract void onGive();
	
	public abstract void onRemove();
}
