package mias.entity.ai;

import java.util.LinkedList;

import mias.entity.EntityAttribute;
import mias.entity.ai.need.AINeed;
import mias.world.World;

public class AIController extends EntityAttribute {
	
	private LinkedList<AINeed> needs = new LinkedList<AINeed>();
	
	public LinkedList<AINeed> Needs() {
		return needs;
	}

	@Override
	public String attributeType() {
		return EntityAttribute.AI_CONTROLLER;
	}

	@Override
	public void onGive() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRemove() {
		// TODO Auto-generated method stub
		
	}
	
	public void update(World world){
		
	}
}
