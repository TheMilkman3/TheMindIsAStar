package mias.entity.attributes.anatomy;

import java.util.LinkedList;

import mias.material.MaterialInstance;

public class BodyPart {
			
	protected String name;
	protected BodyPartGroup group;
	
	//material components
	protected MaterialInstance skinMaterial;
	protected MaterialInstance fatMaterial;
	protected MaterialInstance muscleMaterial;
	protected MaterialInstance boneMaterial;
	
	//links to other parts
	protected BodyPart inwardLink;
	protected LinkedList<BodyPart> outwardLinks = new LinkedList<BodyPart>();
	
	//organs contained within part
	protected LinkedList<Organ> containedOrgans = new LinkedList<Organ>();
	
	//number of pain receptors
	protected short painReceptors = 5;
	
	//percentage of body's balance part provides
	protected float balanceProvided = 0;
	
	//part requirements
	protected float nutrientsRequired = 1f;
	protected float oxygenRequired = 1f;
	
	//current status
	protected float currentOxygen = 1f;
	protected float currentNutrients = 1f;
	
	//damage trackers
	protected float severed = 0;
	protected float crushed = 0;
	protected float starvation = 0;
	protected float necrosis = 0;
	protected float wear = 0;
	protected float woundSeverity = 0;
	
	
	
	public BodyPart(String name, BodyPartGroup group, BodyPart inwardLink) {
		this.name = name;
		this.group = group;
		if (inwardLink != null){
			this.inwardLink = inwardLink;
			this.inwardLink.addOutwardLinks(this);
		}
	}
	
	public BodyPart(String name, BodyPartGroup group){
		this(name, group, null);
	}

	public float getMass(){
		float totalMass = 0f;
		if (skinMaterial != null){
			totalMass += skinMaterial.getMass();
		}
		if (fatMaterial != null){
			totalMass += fatMaterial.getMass();
		}
		if (muscleMaterial != null){
			totalMass += muscleMaterial.getMass();
		}
		if (boneMaterial != null){
			totalMass += boneMaterial.getMass();
		}
		
		for(Organ o : this.containedOrgans){
			totalMass += o.getMass();
		}
		
		return totalMass;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MaterialInstance getSkinMaterial() {
		return skinMaterial;
	}

	public void setSkinMaterial(MaterialInstance skinMaterial) {
		this.skinMaterial = skinMaterial;
	}

	public MaterialInstance getFatMaterial() {
		return fatMaterial;
	}

	public void setFatMaterial(MaterialInstance fatMaterial) {
		this.fatMaterial = fatMaterial;
	}

	public MaterialInstance getMuscleMaterial() {
		return muscleMaterial;
	}

	public void setMuscleMaterial(MaterialInstance muscleMaterial) {
		this.muscleMaterial = muscleMaterial;
	}

	public MaterialInstance getBoneMaterial() {
		return boneMaterial;
	}

	public void setBoneMaterial(MaterialInstance boneMaterial) {
		this.boneMaterial = boneMaterial;
	}

	public BodyPart getInwardLink() {
		return inwardLink;
	}

	public void setInwardLink(BodyPart inwardLink) {
		this.inwardLink = inwardLink;
	}

	public short getPainReceptors() {
		return painReceptors;
	}

	public void setPainReceptors(short painReceptors) {
		this.painReceptors = painReceptors;
	}

	public float getBalanceProvided() {
		return balanceProvided;
	}

	public void setBalanceProvided(float balanceProvided) {
		this.balanceProvided = balanceProvided;
	}

	public float getNutrientsRequired() {
		return nutrientsRequired;
	}

	public void setNutrientsRequired(float nutrientsRequired) {
		this.nutrientsRequired = nutrientsRequired;
	}

	public float getOxygenRequired() {
		return oxygenRequired;
	}

	public void setOxygenRequired(float oxygenRequired) {
		this.oxygenRequired = oxygenRequired;
	}

	public float getCurrentOxygen() {
		return currentOxygen;
	}

	public void setCurrentOxygen(float currentOxygen) {
		this.currentOxygen = currentOxygen;
	}

	public float getCurrentNutrients() {
		return currentNutrients;
	}

	public void setCurrentNutrients(float currentNutrients) {
		this.currentNutrients = currentNutrients;
	}

	public float getSevered() {
		return severed;
	}

	public void setSevered(float severed) {
		this.severed = severed;
	}

	public float getCrushed() {
		return crushed;
	}

	public void setCrushed(float crushed) {
		this.crushed = crushed;
	}

	public float getStarvation() {
		return starvation;
	}

	public void setStarvation(float starvation) {
		this.starvation = starvation;
	}

	public float getNecrosis() {
		return necrosis;
	}

	public void setNecrosis(float necrosis) {
		this.necrosis = necrosis;
	}

	public float getWoundSeverity() {
		return woundSeverity;
	}

	public void setWoundSeverity(float woundSeverity) {
		this.woundSeverity = woundSeverity;
	}

	public LinkedList<BodyPart> getOutwardLinks() {
		return outwardLinks;
	}

	public LinkedList<Organ> getContainedOrgans() {
		return containedOrgans;
	}
	public void addOutwardLinks(BodyPart...parts){
		for (BodyPart p : parts){
			outwardLinks.add(p);
		}
	}
	public void removeOutwardLink(BodyPart p){
		outwardLinks.remove(p);
		p.inwardLink = null;
	}
	public void addContainedOrgans(Organ...organs){
		for (Organ o : organs){
			containedOrgans.add(o);
		}
	}
	public void removeContainedOrgans(Organ o){
		containedOrgans.remove(o);
	}

	public BodyPartGroup getGroup() {
		return group;
	}

	public void setGroup(BodyPartGroup group) {
		this.group = group;
	}
}
