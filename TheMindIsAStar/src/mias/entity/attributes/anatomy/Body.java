package mias.entity.attributes.anatomy;

import java.util.HashMap;
import java.util.LinkedList;

import mias.entity.EntityAttribute;
import mias.entity.EntityUpdateHandler;
import mias.material.MaterialInstance;
import mias.util.MessageType;
import mias.world.World;

public class Body extends EntityAttribute {
	
	public static final double NECROSIS_PROGRESSION = 1.02;
	
	public static final float SENTIENCE_THRESHOLD = 0.3f, 
			BRAIN_DEATH_THRESHOLD = 0.1f,
			BASE_SUFFOCATION_RATE = 0.02f;
	
	//all blood in body
	protected MaterialInstance blood;
	protected float normalBloodVolume = 0f;
	
	protected BodyPart center = null;
	
	protected float walkSpeed = 0f;
	protected float crawlSpeed = 0f;
	
	protected boolean suffocating = false;
	protected boolean dead = false;
	
	
	//map of parts organized by category
	protected HashMap<PartCategory, LinkedList<BodyPart>> parts = new HashMap<PartCategory, LinkedList<BodyPart>>();
	
	//number of parts in each category for the body's essential functions to act normally.
	protected HashMap<PartCategory, Integer> neededParts = new HashMap<PartCategory, Integer>();
	
	public void update(){
		boolean startsSuffocating = false;
		boolean dies = false;
		float oxygenDeprivation = 1f - (oxygenFactor() * getBloodPercentage());
		if (oxygenDeprivation > 0f){
			if(!suffocating){
				startsSuffocating = true;
			}
			suffocating = true;
		}
		else{
			suffocating = false;
		}
		for (LinkedList<BodyPart> categories : parts.values()){
			for (BodyPart part : categories){
				for (BodyLayer layer : part.getLayers()){
					if(layer.getOxygenDeprivation() < 1f){
						layer.adjustOxygenDeprivation(oxygenDeprivation * layer.getMaterial().getOxygenStarvationRate() * BASE_SUFFOCATION_RATE);
					}
					else{
						layer.adjustNecrosis(oxygenDeprivation * layer.getMaterial().getOxygenStarvationRate() * BASE_SUFFOCATION_RATE);
					}
					if(layer.getNecrosis() > 0f){
						layer.setNecrosis((float)Math.pow(layer.getNecrosis(), NECROSIS_PROGRESSION));
					}
				}
			}
		}
		if (!isSentient() && neededPartsOfCategory(PartCategory.BRAIN) > 0){
			owner.removeAttribute(AI_CONTROLLER);
			if (!dead){
				dies = true;
			}
			dead = true;
		}
		generateMessages(startsSuffocating, dies);
	}
	
	//functions that sends messages to player
	public void generateMessages(boolean startsSuffocating, boolean dies){
		MessageType  messageType;
		String targetRef;
		if (owner.isPlayer()){
			messageType = MessageType.SELF;
			targetRef = "You";
		}
		else{
			messageType = MessageType.SIGHT;
			targetRef = owner.getName();
		}
		if (startsSuffocating){
			World.instance().sendMessage(targetRef + (targetRef == "You" ? " are" : " is") + " suffocating!", messageType);
		}
		if (dies){
			World.instance().sendMessage(targetRef + (targetRef == "You" ? " have" : " has") + " died!", messageType);
		}
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
	
	public float heartFactor(){
		if (partsOfCategory(PartCategory.HEART) > 0){

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
	
	public float categoryEffectiveness(PartCategory category, boolean highest){
		float effec;
		if (highest){
			effec = 0;
		}
		else{
			effec = 1f;
		}
		for (BodyPart part : getPartsOfCategory(category)){
			if (highest){
				effec = Math.max(effec, part.getEffectiveness());
			}
			else{
				effec = Math.min(effec, part.getEffectiveness());
			}
		}
		return effec;
	}
	
	public void drainBlood(float amount){
		blood.setVolume(Math.max(blood.getVolume() - amount, 0));
	}
	
	public void addPart(BodyPart part){
		if (!parts.containsKey(part.getCategory())){
			parts.put(part.getCategory(), new LinkedList<BodyPart>());
		}
		parts.get(part.getCategory()).add(part);
		for(BodyPart link : part.getLinks()){
			addPart(link);
		}
		for(BodyPart link : part.getInternals()){
			addPart(link);
		}
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
	
	public MaterialInstance getBlood() {
		return blood;
	}

	public void setBlood(MaterialInstance blood) {
		this.blood = blood;
	}

	public float getNormalBloodVolume() {
		return normalBloodVolume;
	}

	public void setNormalBloodVolume(float normalBloodVolume) {
		this.normalBloodVolume = normalBloodVolume;
	}

	public float getWalkSpeed() {
		return walkSpeed;
	}

	public void setWalkSpeed(float walkSpeed) {
		this.walkSpeed = walkSpeed;
	}

	public float getCrawlSpeed() {
		return crawlSpeed;
	}

	public void setCrawlSpeed(float crawlSpeed) {
		this.crawlSpeed = crawlSpeed;
	}

	public BodyPart getCenter() {
		return center;
	}

	public void setCenter(BodyPart center) {
		if (center.getInwardLink() == null){
			this.center = center;
		}
	}

	@Override
	public String attributeType() {
		return BODY;
	}

	@Override
	public void onGive() {
		EntityUpdateHandler.instance().addBody(this);
	}

	@Override
	public void onRemove() {
		EntityUpdateHandler.instance().removeBody(this);
	}
	
	public Body fullCopy(){
		Body copy = partialCopy();
		copy.blood = this.blood.copy();
		copy.center = center.fullCopy();
		copy.addPart(copy.center);
		return copy;
	}
	
	public Body partialCopy(){
		Body copy = new Body();
		copy.normalBloodVolume = this.normalBloodVolume;
		copy.walkSpeed = this.walkSpeed;
		copy.crawlSpeed = this.crawlSpeed;
		for(PartCategory c : this.neededParts.keySet()){
			copy.neededParts.put(c, this.neededParts.get(c));
		}
		return copy;
	}
	
}
