package mias.entity.ai;

import java.util.Collections;
import java.util.LinkedList;

import mias.entity.EntityAttribute;
import mias.entity.action.Action;
import mias.entity.ai.need.AINeed;

public class AIController extends EntityAttribute {
	
	private LinkedList<AINeed> needs = new LinkedList<AINeed>();
	private Action action = null;

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

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
	
	public void addNeed(AINeed need){
		needs.add(need);
		Collections.sort(needs);
	}
	
	public void removeNeed(AINeed need){
		needs.remove(need);
	}
	
	public Action getNextAction(){
		for(AINeed n : needs){
			if (n.fulfilled()){
				n.remove();
			}
			else{
				n.decide();
				if(action != null){
					break;
				}
			}
		}
		Action temp = action;
		action = null;
		return temp;
	}
}
