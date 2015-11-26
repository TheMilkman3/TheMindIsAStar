package mias.entity.ai.need;

import java.util.HashSet;

import mias.entity.IUpdatable;
import mias.entity.ai.AIController;

public abstract class AINeed {
	protected IUpdatable owner;
	protected AIController parentController;
	protected HashSet<AINeed> childNeeds = new HashSet<AINeed>();
	
	public AINeed(AIController parentController){
		this.parentController = parentController;
		owner = parentController.Owner();
	}
	
	public void addChild(AINeed child) {
		childNeeds.add(child);
	}
	
	public void remove() {
		parentController.Needs().remove(this);
	}
}
