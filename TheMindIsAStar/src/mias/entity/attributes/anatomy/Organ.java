package mias.entity.attributes.anatomy;

import mias.material.MaterialInstance;

public class Organ {
	
	public static final byte EXPOSED = 0,
			BEHIND_SKIN = 1,
			BEHIND_FAT = 2,
			BEHIND_MUSCLE = 3,
			BEHIND_BONE = 4;
	
	public static final byte CONSCIOUS_THOUGHT = (byte)0b10000000,
			UNCONCIOUS_THOUGHT = (byte)0b01000000,
			CIRCULATES_BLOOD = (byte)0b00100000,
			ALLOWS_SPEECH = (byte)0b00010000,
			ALLOWS_REPRODUCTION = (byte)0b00001000,
			REQUIRES_UNCONSCIOUS_THOUGHT = (byte)0b00000100;
	
	protected String name;
	
	//material organ composed of
	protected MaterialInstance material;
	
	//how exposed the organ is
	protected byte exposure;
	
	//senses provided by organ
	protected int visual;
	protected int auditory;
	protected int olfactory;
	
	//fuctions provided by organ
	byte functions;
	public short detoxification;
	public float bloodOxidation;
	public float bloodNourishment;
	public short immunization;
	public short wasteRemoval; 
	public float bloodFlowFactor;
	
	//requirements of organ
	public float oxygenRequired;
	public float nutrientsRequired;
	
	//number of pain receptors
	protected short painReceptors;
	
	//current status of organ
	public float currentOxygen;
	public float currentNutrients;
	public float structuralDamage;
	public float starvation;
	public float necrosis;
	
	public float getMass(){
		return material.getMass();
	}
}
