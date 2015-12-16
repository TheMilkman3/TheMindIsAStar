package mias.entity.attributes.equipment;

import mias.entity.EntityAttribute;
import mias.material.MaterialInstance;

public abstract class StrikingSurface extends EntityAttribute {

	protected MaterialInstance material;
	
	public StrikingSurface(MaterialInstance material){
		this.material = material;
	}
	
	public abstract float getDamageMultiplier();
	
	public float getDamage(float strikeStrength){
		return material.getMass() * strikeStrength * getDamageMultiplier();
	}
	
	public abstract StrikeType getStrikeType();
	
	@Override
	public String attributeType() {
		return STRIKING_SURFACE;
	}

	@Override
	public void onGive() {
	}

	@Override
	public void onRemove() {
	}
	
	public enum StrikeType{
		CRUSHING, PIERCING, CUTTING
	}

}
