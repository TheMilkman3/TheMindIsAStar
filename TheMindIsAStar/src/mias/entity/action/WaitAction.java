package mias.entity.action;

import mias.entity.Entity;

public class WaitAction extends Action {

	int ticks;
	
	public WaitAction(Entity owner, int ticks) {
		super(owner);
		this.ticks = ticks;
	}

	@Override
	public int execute() {
		return ticks;
	}

}
