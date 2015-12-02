package mias.entity.ai.need;

import mias.entity.action.MoveAction;
import mias.entity.ai.AIController;
import mias.util.WorldCoord;

public class AIMarch extends AINeed {

	private WorldCoord direction;
	
	public AIMarch(AIController parentController, AINeed parentNeed, WorldCoord direction) {
		super(parentController, parentNeed);
		this.direction = direction;
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void findNeedOrAction() {
		this.decisionAction = new MoveAction(owner(), direction, 20);
	}

	@Override
	public boolean fulfilled() {
		return false;
	}

}
