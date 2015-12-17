package mias.entity.attributes.anatomy;

import java.util.HashMap;
import java.util.LinkedList;

import mias.entity.EntityAttribute;
import mias.entity.EntityUpdateHandler;
import mias.entity.PosEntity;
import mias.entity.attributes.equipment.StrikingSurface.StrikeType;
import mias.material.MaterialInstance;
import mias.util.MessageType;
import mias.util.RNG;
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
	protected boolean prone = false;
	protected boolean conscious = true;
	
	
	//map of parts organized by category
	protected HashMap<PartCategory, LinkedList<BodyPart>> parts = new HashMap<PartCategory, LinkedList<BodyPart>>();
	
	//number of parts in each category for the body's essential functions to act normally.
	protected HashMap<PartCategory, Integer> neededParts = new HashMap<PartCategory, Integer>();
	
	//entities held in body's hands
	protected HashMap<BodyPart, PosEntity> handSlots = new HashMap<BodyPart, PosEntity>(2);
	
	public void update(){
		boolean startsSuffocating = false;
		boolean dies = false;
		boolean fallsDown = false;
		float oxygenDeprivation = 1f - (oxygenFactor() * getBloodPercentage() * heartFactor());
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
		if(!canBeConscious()){
			conscious = false;
		}
		if (!conscious || (neededPartsOfCategory(PartCategory.FOOT) > 0 && averageCategoryEffectiveness(PartCategory.FOOT) <= 0.5f)){
			if (!prone){
				fallsDown = true;
			}
			prone = true;
		}
		if (isBrainDead()){
			if (!dead){
				dies = true;
			}
			dead = true;
		}
		generateMessages(startsSuffocating, dies, fallsDown);
	}
	
	public String applyTargetedDamage(float damage, StrikeType strikeType, BodyPart target){
		if (!target.getInternals().isEmpty()){
			float random = RNG.getFloat();
			float totalWeight = 0f;
			for(BodyPart internal : target.getInternals()){
				totalWeight += internal.getVolume()/target.getVolume();
				if (random <= totalWeight){
					return applyTargetedDamage(damage, strikeType, internal);
				}
			}
		}
		target.applyDamage(damage, strikeType);
		return "Attacks " + owner.getName() + "'s " + target.getName() + ".";
	}
	
	public String applyGeneralDamage(float damage, StrikeType strikeType){
		float random = RNG.getFloat();
		float totalVolume = getVolume();
		float totalWeight = 0f;
		for (BodyPart part : getExternalParts()){
			totalWeight += part.getVolume()/totalVolume;
			if (random <= totalWeight){
				return applyTargetedDamage(damage, strikeType, part);
			}
		}
		return null;
	}
	
	public float getVolume(){
		float volume = 0;
		for (BodyPart part : getExternalParts()){
			volume += part.getVolume();
		}
		return volume;
	}
	
	//functions that sends messages to player
	public void generateMessages(boolean startsSuffocating, boolean dies, boolean fallsDown){
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
		if (fallsDown){
			World.instance().sendMessage(targetRef + (targetRef == "You" ? " fall" : " falls") + " to the ground.", messageType);
		}
		if (dies){
			World.instance().sendMessage(targetRef + (targetRef == "You" ? " have" : " has") + " died!", messageType);
		}
	}
	
	public LinkedList<BodyPart> getExternalParts(){
		LinkedList<BodyPart> externalParts = new LinkedList<BodyPart>();
		for (LinkedList<BodyPart> list : parts.values()){
			for (BodyPart part : list){
				if (part.getExternal() == null){
					externalParts.add(part);
				}
			}
		}
		return externalParts;
	}
	
	public float getBloodPercentage(){
		if(normalBloodVolume == 0){
			return 0f;
		}
		return blood.getVolume() / normalBloodVolume;
	}
	
	public boolean canBeConscious(){
		return brainFactor() > SENTIENCE_THRESHOLD;
	}
	
	public boolean isBrainDead(){
		return brainFactor() <= BRAIN_DEATH_THRESHOLD;
	}
	
	public float brainFactor(){
		return categoryEffectiveness(PartCategory.BRAIN, true);
	}
	
	public float heartFactor(){
		return averageCategoryEffectiveness(PartCategory.HEART);
	}
	
	public float oxygenFactor(){
		if (neededPartsOfCategory(PartCategory.BRAIN) > 0 && brainFactor() <= BRAIN_DEATH_THRESHOLD){
			return 0;
		}
		return averageCategoryEffectiveness(PartCategory.LUNG);
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
	
	public float averageCategoryEffectiveness(PartCategory category){
		int neededParts = neededPartsOfCategory(category);
		if (neededParts <= 0){
			return 0;
		}
		float effec = 0;
		for(BodyPart part : getPartsOfCategory(category)){
			effec += part.getEffectiveness();
		}
		return effec / ((float)neededParts);
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
		if(part.getCategory() == PartCategory.HAND){
			handSlots.put(part, null);
		}
	}
	
	public void removePart(BodyPart part){
		if (parts.containsKey(part.getCategory())){
			parts.get(part.getCategory()).remove(part);
			for(BodyPart link : part.getLinks()){
				removePart(link);
			}
			for(BodyPart link : part.getInternals()){
				removePart(link);
			}
		}
		handSlots.remove(part);
	}
	
	public void hold(PosEntity e){
		for (BodyPart p : handSlots.keySet()){
			if (handSlots.get(p) == null){
				handSlots.put(p, e);
				e.setHeldBy((PosEntity)this.owner);
				return;
			}
		}
	}
	
	public void drop(BodyPart part){
		if(handSlots.containsKey(part)){
			PosEntity e = handSlots.get(part);
			handSlots.put(part, null);
			if (!handSlots.containsValue(e)){
				e.setHeldBy(null);
			}
		}
	}
	
	public void drop(PosEntity target){
		for(BodyPart part : getPartsWithHandSlots()){
			if(handSlots.get(part) == target){
				drop(part);
			}
		}
	}
	
	public LinkedList<BodyPart> getPartsWithHandSlots(){
		LinkedList<BodyPart> partsWithHandSlots = new LinkedList<BodyPart>();
		for(BodyPart part : handSlots.keySet()){
			partsWithHandSlots.add(part);
		}
		return partsWithHandSlots;
	}
	
	public LinkedList<PosEntity> getHeldEntities(){
		LinkedList<PosEntity> heldEntities = new LinkedList<PosEntity>();
		for (PosEntity e : handSlots.values()){
			if (e != null){
				heldEntities.add(e);
			}
		}
		return heldEntities;
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

	public boolean isSuffocating() {
		return suffocating;
	}

	public void setSuffocating(boolean suffocating) {
		this.suffocating = suffocating;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public boolean isProne() {
		return prone;
	}

	public void setProne(boolean prone) {
		this.prone = prone;
	}

	public boolean isConscious() {
		return conscious;
	}

	public void setConscious(boolean conscious) {
		this.conscious = conscious;
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
