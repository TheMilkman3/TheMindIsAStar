package mias.entity.ai.need;

import java.util.HashSet;

import mias.entity.Entity;
import mias.entity.action.Action;
import mias.entity.ai.AIController;

public abstract class AINeed implements Comparable<AINeed> {
	protected Entity owner;
	protected AIController parentController;
	protected HashSet<AINeed> childNeeds = new HashSet<AINeed>();
	protected AINeed decisionNeed = null;
	protected Action decisionAction = null;
	
	public AINeed(AIController parentController){
		this.parentController = parentController;
		owner = parentController.Owner();
	}
	
	public void addChild(AINeed child) {
		childNeeds.add(child);
	}
	
	public void remove() {
		if (childNeeds.size() > 0){
			for (AINeed child : childNeeds){
				parentController.removeNeed(child);
			}
		}
		parentController.removeNeed(this);
	}
	
	public abstract int getPriority();
	
	protected abstract void findNeedOrAction();
	
	public final void decide(){
		findNeedOrAction();
		if (decisionAction != null){
			parentController.setAction(decisionAction);
		}
		else if (decisionNeed != null){
			addChild(decisionNeed);
		}
		decisionNeed = null;
		decisionAction = null;
	}
	
	@Override
	public final int compareTo(AINeed n){
		if (this.getPriority() < n.getPriority()){
			return -1;
		}
		else if (this.getPriority() > n.getPriority()){
			return 1;
		}
		else{
			return 0;
		}
	}
}
