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
			dest.normalize();
			dest = WorldCoord.add(dest, ((PosEntity)owner).getPos());
			if (((PosEntity)owner).canPass(dest)){
				((PosEntity)owner).setPos(dest);
				return speed;
			}
		}
		return -1;
	}

}
