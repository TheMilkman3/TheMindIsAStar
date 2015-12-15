package mias.entity.attributes.equipment;

import mias.entity.EntityAttribute;
import mias.entity.attributes.anatomy.PartCategory;

public class Equippable extends EntityAttribute {

	private PartCategory category;
	private EquipSlot slot = null;
	
	public Equippable(PartCategory category){
		this.category = category;
	}
	
	
	public PartCategory getCategory() {
		return category;
	}



	public void setCategory(PartCategory category) {
		this.category = category;
	}



	public EquipSlot getSlot() {
		return slot;
	}



	public void setSlot(EquipSlot slot) {
		this.slot = slot;
	}



	@Override
	public String attributeType() {
		return EQUIPPABLE;
	}

	@Override
	public void onGive() {
	}

	@Override
	public void onRemove() {
	}

}
