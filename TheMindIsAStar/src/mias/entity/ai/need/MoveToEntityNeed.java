package mias.entity.ai.need;

import mias.entity.PosEntity;
import mias.entity.action.MoveAction;
import mias.entity.ai.AIController;
import mias.util.WorldCoord;
import mias.util.pathfinding.Pathfinder;

public class MoveToEntityNeed extends AINeed {

	PosEntity target;
	Pathfinder pf;
	WorldCoord targetLastPos;
	
	public MoveToEntityNeed(AIController parentController, AINeed parentNeed, PosEntity target) {
		super(parentController, parentNeed);
		this.target = target;
		if (owner() instanceof PosEntity){
			targetLastPos = target.getPos();
			pf = new Pathfinder(((PosEntity)owner()).getPos(), targetLastPos, (PosEntity)owner());
		}
		
	}

	@Override
	public int getPriority() {
		return parentNeed.getPriority() + 1;
	}

	@Override
	protected void findNeedOrAction() {
		if (owner() instanceof PosEntity){
			PosEntity owner = (PosEntity)owner();
			if(!targetLastPos.equals(target.getPos())){
				targetLastPos = target.getPos();
				pf = new Pathfinder(((PosEntity)owner()).getPos(), targetLastPos, owner);
			}
			if(pf.pathfind() != null){
				WorldCoord next = WorldCoord.subtract(pf.pathfind().pollFirst(), owner.getPos());
				this.decisionAction = new MoveAction(owner, next);
			}
		}
	}

	@Override
	public boolean fulfilled() {
		if (WorldCoord.distance(((PosEntity)owner()).getPos(), target.getPos()) < 2.0){
			return true;
		}
		return false;
	}

}
