package mias.entity.attributes.anatomy;

import java.util.HashMap;
import java.util.LinkedList;

import mias.entity.EntityAttribute;
import mias.material.Material;
import mias.material.MaterialInstance;
import mias.material.MaterialState;
import mias.util.MessageType;
import mias.world.World;

public class Body extends EntityAttribute {
	
	public static final double NECROSIS_PROGRESSION = 1.02;
	public static final float SENTIENCE_THRESHOLD = 0.3f, 
			BRAIN_DEATH_THRESHOLD = 0.1f,
			BASE_SUFFOCATION_RATE = 0.02f;
	
	//all blood in body
	protected MaterialInstance blood;
	protected float normalBloodVolume;
	
	protected float walkSpeed = 0.1f;
	protected float crawlSpeed = 0.02f;
	
	
	//map of parts organized by category
	protected HashMap<PartCategory, LinkedList<BodyPart>> parts = new HashMap<PartCategory, LinkedList<BodyPart>>();
	
	//number of parts in each category for the body's essential functions to act normally.
	protected HashMap<PartCategory, Integer> neededParts = new HashMap<PartCategory, Integer>();
	
	public void update(){
		float oxygenDeprivation = 1f - (oxygenFactor() * getBloodPercentage());
		if (oxygenDeprivation > 0f){
			World.instance().sendMessage(owner.getName() + " is suffocating!", MessageType.SIGHT);
		}
		for (LinkedList<BodyPart> categories : parts.values()){
			for (BodyPart part : categories){
				for (BodyLayer layer : part.getLayers()){
					if(layer.getOxygenDeprivation() < 1f){
						layer.adjustOxygenDeprivation(oxygenDeprivation * layer.getMaterial().getOxygenStarvationRate() * BASE_SUFFOCATION_RATE);
					}
					else{
						layer.adjustNecrosis(oxygenDeprivation * layer.getMaterial().getOxygenStarvationRate() * BASE_SUFFOCATION_RATE);
						World.instance().sendMessage(owner.getName() + " is necrotizing!", MessageType.SIGHT);
					}
					if(layer.getNecrosis() > 0f){
						layer.setNecrosis((float)Math.pow(layer.getNecrosis(), NECROSIS_PROGRESSION));
						World.instance().sendMessage(owner.getName() + " is necrotizing!", MessageType.SIGHT);
					}
				}
			}
		}
		if (!isSentient() && neededPartsOfCategory(PartCategory.BRAIN) > 0){
			owner.removeAttribute(AI_CONTROLLER);
			World.instance().sendMessage(owner.getName() + " is brain dead!", MessageType.SIGHT);
		}
		//TODO process bloodloss
	}
	
	public float getBloodPercentage(){
		if(normalBloodVolume == 0){
			return 0f;
		}
		return blood.getVolume() / normalBloodVolume;
	}
	
	public boolean isSentient(){
		return brainFactor() > SENTIENCE_THRESHOLD;
	}
	
	public float brainFactor(){
		if (partsOfCategory(PartCategory.BRAIN) > 0){
			float highest = 0f;
			for(BodyPart brain : getPartsOfCategory(PartCategory.BRAIN)){
				highest = Math.max(highest, brain.getEffectiveness());
			}
			return highest;
		}
		return 0;
	}
	
	public float oxygenFactor(){
		int neededLungs = neededPartsOfCategory(PartCategory.LUNG);
		float total = 0f;
		if (neededLungs <= 0 || (neededPartsOfCategory(PartCategory.BRAIN) > 0 && brainFactor() <= BRAIN_DEATH_THRESHOLD)){
			return 0;
		}
		else{
			float fraction = 1f/(float)neededLungs;
			for (BodyPart lung : parts.get(PartCategory.LUNG)){
				total += lung.getEffectiveness() * fraction;
			}
			return total;
		}
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
	
	/**Returns the number of parts needed by the body to act normally.
	 * 
	 * @param c - Category
	 * @return the number of parts needed by the body to act normally.
	 */
	public int neededPartsOfCategory(PartCategory c){
		return neededParts.get(c);
	}
	
	public LinkedList<BodyPart> getPartsOfCategory(PartCategory c){
		return parts.get(c);
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
	
	public static Body testBody(){
		Body test = new Body();
		BodyPart brain = BodyPart.defaultBrain();
		BodyPart lung1 = BodyPart.defaultLung();
		BodyPart lung2 = BodyPart.defaultLung();
		brain.addLink(lung1);
		brain.addLink(lung2);
		test.addPart(brain);
		test.addPart(lung1);
		test.addPart(lung2);
		test.neededParts.put(PartCategory.LUNG, 2);
		test.neededParts.put(PartCategory.BRAIN, 1);
		test.blood = defaultBlood();
		test.normalBloodVolume = 5f;
		return test;
	}
	
	public static MaterialInstance defaultBlood(){
		MaterialInstance blood = new MaterialInstance(Material.BLOOD, MaterialState.LIQUID, 5f, 0f, 3102);
		return blood;
	}
}
