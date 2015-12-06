package mias.entity.attributes.anatomy;

import java.util.LinkedList;

public class BodyPartGroup {
	
	protected String name;
	
	//parts in group
	protected LinkedList<BodyPart> parts = new LinkedList<BodyPart>();
	
	//locomotion provided
	protected int walkSpeed;
	protected int crawlSpeed;
	protected int flySpeed;
	protected int swimSpeed;
	
	//how well part group can manipulate objects like a hand
	protected ManipulationLevel manipulationLevel = ManipulationLevel.NONE;
	
	public float getMass(){
		float totalMass = 0;
		for (BodyPart p : this.parts){
			totalMass += p.getMass();
		}
		return totalMass;
	}
	
	public void addParts(BodyPart...bodyParts){
		for(BodyPart p : bodyParts){
			parts.add(p);
		}
	}
	
	public void removePart(BodyPart p){
		parts.remove(p);
		p.setGroup(null);
	}
	
	public BodyPartGroup clone(){
		BodyPartGroup clone = new BodyPartGroup();
		clone.walkSpeed = walkSpeed;
		clone.crawlSpeed = crawlSpeed;
		clone.flySpeed = flySpeed;
		clone.swimSpeed = swimSpeed;
		return clone;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWalkSpeed() {
		return walkSpeed;
	}

	public void setWalkSpeed(int walkSpeed) {
		this.walkSpeed = walkSpeed;
	}

	public int getCrawlSpeed() {
		return crawlSpeed;
	}

	public void setCrawlSpeed(int crawlSpeed) {
		this.crawlSpeed = crawlSpeed;
	}

	public int getFlySpeed() {
		return flySpeed;
	}

	public void setFlySpeed(int flySpeed) {
		this.flySpeed = flySpeed;
	}

	public int getSwimSpeed() {
		return swimSpeed;
	}

	public void setSwimSpeed(int swimSpeed) {
		this.swimSpeed = swimSpeed;
	}

	public ManipulationLevel getManipulationLevel() {
		return manipulationLevel;
	}

	public void setManipulationLevel(ManipulationLevel manipulationLevel) {
		this.manipulationLevel = manipulationLevel;
	}

	public LinkedList<BodyPart> getParts() {
		return parts;
	}



	public enum ManipulationLevel{
		NONE, TENTACLE, CLAW, SIMPLE_HAND, COMPLEX_HAND;
	}
}
