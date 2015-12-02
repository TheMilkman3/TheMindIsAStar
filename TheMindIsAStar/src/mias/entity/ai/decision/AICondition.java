package mias.entity.ai.decision;

import mias.entity.Entity;

public abstract class AICondition {
	
	public abstract int getWeight(Entity owner);
	
}
