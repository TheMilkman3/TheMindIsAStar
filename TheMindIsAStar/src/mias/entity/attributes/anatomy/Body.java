package mias.entity.attributes.anatomy;

import java.util.LinkedList;

import mias.entity.EntityAttribute;
import mias.material.MaterialInstance;

public class Body extends EntityAttribute {
	
	//all blood in body
	protected MaterialInstance blood;
	
	protected float mass;
	
	//groups comprising body
	protected LinkedList<BodyPartGroup> partGroups = new LinkedList<BodyPartGroup>();
	
	public void assessValues(){
		mass = 0;
		for (BodyPartGroup g : this.partGroups){
			mass += g.getMass();
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
