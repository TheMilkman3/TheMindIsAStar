package mias.entity.action;

import mias.entity.Entity;
import mias.entity.PosEntity;
import mias.util.WorldCoord;

public class MoveAction extends Action {
	
	private WorldCoord dest;
	private int speed;
	
	public MoveAction(Entity owner, WorldCoord dest, int speed) {
		super(owner);
		this.dest = dest;
		this.speed = speed;
	}
	
	public MoveAction(Entity owner, WorldCoord dest) {
		this(owner, dest, 10);
	}

	@Override
	public int execute() {
		if (owner instanceof PosEntity){
			((PosEntity) owner).setPos(WorldCoord.add(((PosEntity) owner).getPos(), dest));
			return speed;
		}
		return -1;
	}

}
