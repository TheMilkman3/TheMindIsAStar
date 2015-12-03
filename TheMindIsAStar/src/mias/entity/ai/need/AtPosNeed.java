package mias.entity.ai.need;

import mias.entity.PosEntity;
import mias.entity.action.MoveAction;
import mias.entity.ai.AIController;
import mias.util.WorldCoord;
import mias.util.pathfinding.Pathfinder;

public class AtPosNeed extends AINeed {
	
	WorldCoord targetDest;
	Pathfinder pf;

	public AtPosNeed(AIController parentController, AINeed parentNeed, WorldCoord targetDest) {
		super(parentController, parentNeed);
		this.targetDest = targetDest;
		if (owner() instanceof PosEntity){
			pf = new Pathfinder(((PosEntity)owner()).getPos(), targetDest, (PosEntity)owner());
		}
	}

	@Override
	public int getPriority() {
		return parentNeed.getPriority();
	}

	@Override
	protected void findNeedOrAction() {
		if (owner() instanceof PosEntity){
			PosEntity owner = (PosEntity)owner();
			if(pf.pathfind() != null){
				WorldCoord next = WorldCoord.subtract(pf.pathfind().pollFirst(), owner.getPos());
				this.decisionAction = new MoveAction(owner, next);
			}
		}
	}

	@Override
	public boolean fulfilled() {
		if (owner() instanceof PosEntity){
			return ((PosEntity)owner()).getPos().equals(targetDest);
		}
		return false;
	}
	
}
