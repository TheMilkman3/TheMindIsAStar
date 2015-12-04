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
	
	//percentage of balance provided
	protected float balance;
	
	//provides hand-like capabilities
	protected boolean allowsManipulation;
	
	public float getMass(){
		float totalMass = 0;
		for (BodyPart p : this.parts){
			totalMass += p.getMass();
		}
		return totalMass;
	}
}
