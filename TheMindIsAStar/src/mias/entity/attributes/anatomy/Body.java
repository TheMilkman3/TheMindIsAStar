package mias.entity.attributes.anatomy;

import java.util.HashMap;
import java.util.LinkedList;

import mias.entity.EntityAttribute;
import mias.entity.PosEntity;
import mias.entity.RenderedEntity;
import mias.material.MaterialInstance;

public class Body extends EntityAttribute {
	
	//all blood in body
	protected MaterialInstance blood;
	protected float normalBloodVolume;
	protected float circulationRate;
	protected float bloodOxygenLevel;
	protected float metabolicRate;
	protected float oxygenIntake;
	
	protected float mass;
	
	//groups comprising body
	protected LinkedList<BodyPartGroup> partGroups = new LinkedList<BodyPartGroup>();
	
	protected HashMap<OrganType, Organ[]> organs = new HashMap<OrganType, Organ[]>();
	
	protected int sight = 20;
	protected int hearing = 20;
	protected int smell = 20;
	
	public void assessValues(){
		mass = 0;
		for (BodyPartGroup g : this.partGroups){
			mass += g.getMass();
		}
	}
	
	public float getBloodPercentage(){
		return blood.getVolume() / normalBloodVolume;
	}
	
	public void drainBlood(float amount){
		blood.setVolume(Math.max(blood.getVolume() - amount, 0));
	}
	
	public void removePart(BodyPart part){
		part.getGroup().removePart(part);
		for(BodyPart p : part.getOutwardLinks()){
			removePart(p);
		}
	}
	
	public RenderedEntity severPart(BodyPart part){
		PosEntity owner = (PosEntity)this.Owner();
		RenderedEntity newEntity = new RenderedEntity(part.name, owner.getX(), owner.getY(), owner.getZ());
		Body newBody = new Body();
		LinkedList<Organ> organsToAdd = new LinkedList<Organ>();
		LinkedList<BodyPartGroup> groupsToAdd = new LinkedList<BodyPartGroup>();
		LinkedList<BodyPart> partsToProcess = new LinkedList<BodyPart>();
		partsToProcess.add(part);
		
		//find all parts to put into new entity
		for(BodyPart p : partsToProcess){
			partsToProcess.addAll(p.getOutwardLinks());
			if (!groupsToAdd.contains(p.getGroup())){
				groupsToAdd.add(p.getGroup());
			}
			organsToAdd.addAll(p.getContainedOrgans());
		}
		
		//copy the number of organs needed for each type into new entity
		for(OrganType t : organs.keySet()){
			Organ[] list = new Organ[organs.get(t).length];
			newBody.organs.put(t, list);
		}
		
		//add all organs from severed parts into new entity
		for(Organ o : organsToAdd){
			Organ[] list = newBody.organs.get(o.getType());
			for (int i = 0; i <= list.length - 1; i++){
				if (list[i] == null){
					list[i] = o;
				}
			}
		}
		
		//clone all body groups that the severed parts are part of.
		//remove parts from old group and them to cloned group
		for(BodyPartGroup g : groupsToAdd){
			BodyPartGroup gClone = g.clone();
			LinkedList<BodyPart> partsToAdd = new LinkedList<BodyPart>();
			for(BodyPart p : g.getParts()){
				if(partsToProcess.contains(p)){
					partsToAdd.add(p);
					p.setGroup(gClone);
				}
			}
			gClone.getParts().addAll(partsToAdd);
			g.getParts().removeAll(partsToAdd);
			newBody.partGroups.add(gClone);
		}
		
		part.setInwardLink(null);
		
		newEntity.giveAttribute(newBody);
		
		return newEntity;
	}
	
	public void calculateBloodFlow(){
		circulationRate = 0;
		for(Organ heart : getOrgans(OrganType.HEART)){
			double circFactor = percentOrgansPresent(OrganType.HEART);
			//TODO
		}
	}
	
	public void removeOrgan(Organ o){
		Organ[] list = organs.get(o.type);
		for (int i = 0; i <= list.length - 1; i++){
			
		}
	}
	
	public int numberOfOrgans(OrganType type){
		return organs.get(type).length;
	}
	
	public int currentNumberOfOrgans(OrganType type){
		int total = 0;
		for (Organ o : getOrgans(type)){
			if (o != null){
				total++;
			}
		}
		return total;
	}
	
	public Organ[] getOrgans(OrganType type){
		return organs.get(type);
	}
	
	public float percentOrgansPresent(OrganType type){
		if(numberOfOrgans(type) == 0){
			return -1;
		}
		else{
			return ((float)numberOfOrgans(type))/((float)currentNumberOfOrgans(type));
		}
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
