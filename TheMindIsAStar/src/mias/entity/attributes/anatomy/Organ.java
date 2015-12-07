package mias.entity.attributes.anatomy;

import mias.material.Material;
import mias.material.MaterialInstance;
import mias.material.MaterialState;

public class Organ {
	
	public static float NECROSIS_EFFECTIVENESS_MODIFIER = 1f,
			STARVATION_EFFECTIVENESS_MODIFIER = 1f,
			STRUCTURAL_DMG_EFFECTIVENESS_MODIFIER = 1f;
	
	public static final byte EXPOSED = 0,
			BEHIND_SKIN = 1,
			BEHIND_FAT = 2,
			BEHIND_MUSCLE = 3,
			BEHIND_BONE = 4;

	
	protected String name;
	protected OrganType type;
	
	//material organ composed of
	protected MaterialInstance material;
	
	//how exposed the organ is
	protected byte exposure = Organ.BEHIND_MUSCLE;
	
	//requirements of organ
	protected float oxygenRequired = 1f;
	protected float nutrientsRequired = 1f;
	
	//number of pain receptors
	protected short painReceptors = 5;
	
	//current status of organ
	protected float currentOxygen = 1f;
	protected float currentNutrients = 1f;
	protected float structuralDamage = 0;
	protected float starvation = 0;
	protected float necrosis = 0;
	
	public Organ(String name, OrganType type){
		this.name = name;
		this.type = type;
	}
	
	public float getMass(){
		return material.getMass();
	}
	
	
	public String getName() {
		return name;
	}

	public OrganType getType() {
		return type;
	}


	public void setType(OrganType type) {
		this.type = type;
	}


	public void setName(String name) {
		this.name = name;
	}

	public MaterialInstance getMaterial() {
		return material;
	}

	public void setMaterial(MaterialInstance material) {
		this.material = material;
	}

	public byte getExposure() {
		return exposure;
	}

	public void setExposure(byte exposure) {
		this.exposure = exposure;
	}

	public float getOxygenRequired() {
		return oxygenRequired;
	}

	public void setOxygenRequired(float oxygenRequired) {
		this.oxygenRequired = oxygenRequired;
	}

	public float getNutrientsRequired() {
		return nutrientsRequired;
	}

	public void setNutrientsRequired(float nutrientsRequired) {
		this.nutrientsRequired = nutrientsRequired;
	}

	public short getPainReceptors() {
		return painReceptors;
	}

	public void setPainReceptors(short painReceptors) {
		this.painReceptors = painReceptors;
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

	public float getStructuralDamage() {
		return structuralDamage;
	}

	public void setStructuralDamage(float structuralDamage) {
		this.structuralDamage = structuralDamage;
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
	
	public float getEffectiveness(){
		return Math.max(0f, 1f - (necrosis * Organ.NECROSIS_EFFECTIVENESS_MODIFIER + 
				structuralDamage * Organ.STRUCTURAL_DMG_EFFECTIVENESS_MODIFIER +
				starvation * Organ.STARVATION_EFFECTIVENESS_MODIFIER));
	}
	
	public static Organ humanBrain(){
		Organ brain = new Organ("human brain", OrganType.BRAIN);
		brain.setMaterial(new MaterialInstance(Material.brainMatter, MaterialState.SOLID, 11.3f, 6, 3108));
		brain.setExposure(Organ.BEHIND_BONE);
		brain.setPainReceptors((short)0);
		brain.setNutrientsRequired(2f);
		brain.setOxygenRequired(4f);
		return brain;
	}
}
