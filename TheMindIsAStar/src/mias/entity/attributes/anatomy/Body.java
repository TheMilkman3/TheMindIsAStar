package mias.entity.attributes.anatomy;

import java.util.HashMap;
import java.util.LinkedList;

import mias.entity.EntityAttribute;
import mias.material.MaterialInstance;

public class Body extends EntityAttribute {
	
	//all blood in body
	protected MaterialInstance blood;
	protected float normalBloodVolume;
	
	protected float mass;
	
	protected float sight = 100f;
	protected float hearing = 100f;
	protected float smell = 100f;
	
	//map of parts organized by category
	protected HashMap<PartCategory, LinkedList<BodyPart>> parts = new HashMap<PartCategory, LinkedList<BodyPart>>();
	
	public float getBloodPercentage(){
		return blood.getVolume() / normalBloodVolume;
	}
	
	public void drainBlood(float amount){
		blood.setVolume(Math.max(blood.getVolume() - amount, 0));
	}
	
	public void addPart(BodyPart part){
		if (!parts.containsKey(part.getCategory())){
			parts.put(part.getCategory(), new LinkedList<BodyPart>());
		}
		parts.get(part.getCategory()).add(part);
	}
	
	public void removePart(BodyPart part){
		if (parts.containsKey(part.getCategory())){
			parts.get(part.getCategory()).remove(part);
		}
	}
	
	/**Returns number of parts that have the given category
	 * 
	 * @param c - Category
	 * @return number of parts that have the given category
	 */
	public int partsOfCategory(PartCategory c){
		return parts.get(c).size();
	}
	
	@Override
	public String attributeType() {
		return BODY;
	}

	@Override
	public void onGive() {
		
	}

	@Override
	public void onRemove() {
		
	}

}
