package mias.entity.attributes.equipment;

import mias.entity.Entity;
import mias.entity.EntityAttribute;
import mias.entity.attributes.anatomy.BodyPart;
import mias.entity.attributes.anatomy.PartCategory;

public class EquipSlot {
	
	private Equippable equippedItem = null;
	private PartCategory category;
	private BodyPart associatedPart = null;
	
	public EquipSlot(PartCategory category){
		this.category = category;
	}
	
	public boolean canEquip(Entity e){
		Equippable equippable = (Equippable)e.getAttribute(EntityAttribute.EQUIPPABLE);
		return equippable != null && equippable.getCategory() == getCategory();
	}
	
	public void equip(Entity e){
		Equippable equippable = (Equippable)e.getAttribute(EntityAttribute.EQUIPPABLE);
		if(canEquip(e) && isEmpty()){
			equippedItem = equippable;
			equippedItem.setSlot(this);
		}
	}
	
	public Equippable getEquippedItem() {
		return equippedItem;
	}

	public void unequip(){
		if (equippedItem != null){
			equippedItem.setSlot(null);
			equippedItem = null;
		}
	}
	
	public boolean isEmpty(){
		return equippedItem == null;
	}

	public BodyPart getAssociatedPart() {
		return associatedPart;
	}

	public void setAssociatedPart(BodyPart associatedPart) {
		this.associatedPart = associatedPart;
	}

	public PartCategory getCategory() {
		return category;
	}
	
}
