package mias.entity.attributes.anatomy;

import mias.material.Material;
import mias.material.MaterialInstance;
import mias.material.MaterialState;

public class Organ {
	
	public static final byte EXPOSED = 0,
			BEHIND_SKIN = 1,
			BEHIND_FAT = 2,
			BEHIND_MUSCLE = 3,
			BEHIND_BONE = 4;
	
	public static final byte CONSCIOUS_THOUGHT = (byte)0b10000000,
			UNCONSCIOUS_THOUGHT = (byte)0b01000000,
			CIRCULATES_BLOOD = (byte)0b00100000,
			ALLOWS_SPEECH = (byte)0b00010000,
			ALLOWS_REPRODUCTION = (byte)0b00001000,
			REQUIRES_UNCONSCIOUS_THOUGHT = (byte)0b00000100;
	
	protected String name;
	
	//material organ composed of
	protected MaterialInstance material;
	
	//how exposed the organ is
	protected byte exposure = Organ.BEHIND_MUSCLE;
	
	//senses provided by organ
	protected int visual = 0;
	protected int auditory = 0;
	protected int olfactory = 0;
	
	//fuctions provided by organ
	byte functions = 0;
	protected float detoxification = 0;
	protected float bloodOxidation = 0;
	protected float bloodNourishment = 0;
	protected float immunization = 0;
	protected float wasteRemoval = 0; 
	protected float bloodFlowFactor = 1f;
	
	//requirements of organ
	protected float oxygenRequired = 1f;
	protected float nutrientsRequired = 1f;
	
	//number of pain receptors
	protected short painReceptors = 5;
	
	//current status of organ
	protected float currentOxygen = 0;
	protected float currentNutrients = 0;
	protected float structuralDamage = 0;
	protected float starvation = 0;
	protected float necrosis = 0;
	
	public float getMass(){
		return material.getMass();
	}
	
	public void setFlags(int values){
		this.functions = (byte)values;
	}
	
	public boolean providesConsciousThought(){
		return (functions & CONSCIOUS_THOUGHT) == CONSCIOUS_THOUGHT;
	}
	
	public boolean providesUnconsciousThought(){
		return (functions & UNCONSCIOUS_THOUGHT) == UNCONSCIOUS_THOUGHT;
	}
	
	public boolean circulatesBlood(){
		return (functions & CIRCULATES_BLOOD) == CIRCULATES_BLOOD;
	}
	
	public boolean allowsSpeech(){
		return (functions & ALLOWS_SPEECH) == ALLOWS_SPEECH;
	}
	
	public boolean allowsReproduction(){
		return (functions & ALLOWS_REPRODUCTION) == ALLOWS_REPRODUCTION;
	}
	
	public boolean requiresConsciousThought(){
		return (functions & CONSCIOUS_THOUGHT) == CONSCIOUS_THOUGHT;
	}
	
	public String getName() {
		return name;
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

	public int getVisual() {
		return visual;
	}

	public void setVisual(int visual) {
		this.visual = visual;
	}

	public int getAuditory() {
		return auditory;
	}

	public void setAuditory(int auditory) {
		this.auditory = auditory;
	}

	public int getOlfactory() {
		return olfactory;
	}

	public void setOlfactory(int olfactory) {
		this.olfactory = olfactory;
	}

	public float getDetoxification() {
		return detoxification;
	}

	public void setDetoxification(short detoxification) {
		this.detoxification = detoxification;
	}

	public float getBloodOxidation() {
		return bloodOxidation;
	}

	public void setBloodOxidation(float bloodOxidation) {
		this.bloodOxidation = bloodOxidation;
	}

	public float getBloodNourishment() {
		return bloodNourishment;
	}

	public void setBloodNourishment(float bloodNourishment) {
		this.bloodNourishment = bloodNourishment;
	}

	public float getImmunization() {
		return immunization;
	}

	public void setImmunization(short immunization) {
		this.immunization = immunization;
	}

	public float getWasteRemoval() {
		return wasteRemoval;
	}

	public void setWasteRemoval(short wasteRemoval) {
		this.wasteRemoval = wasteRemoval;
	}

	public float getBloodFlowFactor() {
		return bloodFlowFactor;
	}

	public void setBloodFlowFactor(float bloodFlowFactor) {
		this.bloodFlowFactor = bloodFlowFactor;
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
	
	public static Organ humanBrain(){
		Organ brain = new Organ();
		brain.setMaterial(new MaterialInstance(Material.brainMatter, MaterialState.SOLID, 11.3f, 6, 3108));
		brain.setExposure(Organ.BEHIND_BONE);
		brain.setFlags(CONSCIOUS_THOUGHT | UNCONSCIOUS_THOUGHT);
		brain.setPainReceptors((short)0);
		brain.setNutrientsRequired(2f);
		brain.setOxygenRequired(4f);
		return brain;
	}
}
