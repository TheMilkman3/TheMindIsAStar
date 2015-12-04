package mias.entity.attributes.anatomy;

import java.util.LinkedList;

import mias.material.MaterialInstance;

public class BodyPart {
	
	public static final byte VASCULAR = (byte)0b10000000,
			CONVEYS_CONSCIOUS_THOUGHT = (byte)0b01000000,
			CONVEYS_UNCONSCIOUS_THOUGHT = (byte)0b00100000;
			
	protected String name;
	
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
	
	//part requirements
	protected float nutrientsRequired;
	protected float oxygenRequired;
	
	//current status
	protected float currentOxygen;
	protected float currentNutrients;
	
	//damage trackers
	protected float severed;
	protected float crushed;
	protected float starvation;
	protected float necrosis;
	protected float woundSeverity;
	
	/*1. Vascular
	 *2. Conveys Conscious Thought 
	 *3. Conveys Unconscious Thought*/
	private byte flags;
	
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
	
	public void setFlags(byte values){
		flags = values;
	}
	
	public boolean isVascular(){
		return (flags & VASCULAR) == VASCULAR;
	}
	
	public BodyPart setVascular(boolean value){
		if(value){
			flags = (byte)(flags | VASCULAR);
		}
		else{
			flags = (byte)(flags & ~VASCULAR);
		}
		return this;
	}
	
	public boolean conveysConsciousThought(){
		return (flags & CONVEYS_CONSCIOUS_THOUGHT) == CONVEYS_CONSCIOUS_THOUGHT;
	}
	
	public BodyPart setConveysConsciousThought(boolean value){
		if(value){
			flags = (byte)(flags | 0b01000000);
		}
		else{
			flags = (byte)(flags & 0b1011111);
		}
		return this;
	}
	
	public boolean conveysUnconsciousThought(){
		return (flags & CONVEYS_UNCONSCIOUS_THOUGHT) == CONVEYS_UNCONSCIOUS_THOUGHT;
	}
	
	public BodyPart setConveysUnconsciousThought(boolean value){
		if(value){
			flags = (byte)(flags | CONVEYS_UNCONSCIOUS_THOUGHT);
		}
		else{
			flags = (byte)(flags & ~CONVEYS_UNCONSCIOUS_THOUGHT);
		}
		return this;
	}
}
