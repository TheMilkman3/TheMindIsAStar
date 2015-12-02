package mias.entity.action;

import mias.entity.Entity;

public abstract class Action {
	
	protected Entity owner;
	
	public Action(Entity owner){
		this.owner = owner;
	}
	
	/**
	 * Makes the entity execute the action, if possible,
	 * then returns the number of ticks the action takes.
	 * 
	 * @return the number of ticks the action takes.
	 */
	public abstract int execute();
}
