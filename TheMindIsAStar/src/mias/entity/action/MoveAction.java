package mias.entity.action;

import mias.entity.Entity;
import mias.entity.EntityAttribute;
import mias.entity.PosEntity;
import mias.entity.attributes.anatomy.Body;
import mias.util.WorldCoord;

public class MoveAction extends Action {
	
	private WorldCoord dest;
	private float speed;
	
	public MoveAction(Entity owner, WorldCoord dest) {
		super(owner);
		this.dest = dest;
		Body body = (Body)owner.getAttribute(EntityAttribute.BODY);
		if (body != null){
			if (body.isProne()){
				speed = body.getCrawlSpeed();
			}
			else{
				speed = body.getWalkSpeed();
			}
		}
		else{
			speed = 0;
		}
	}

	@Override
	public int execute() {
		if (owner instanceof PosEntity && speed > 0){
			dest = dest.normalize();
			dest = WorldCoord.add(dest, ((PosEntity)owner).getPos());
			if (((PosEntity)owner).canPass(dest)){
				((PosEntity)owner).setPos(dest);
				return (int)(1f / speed);
			}
		}
		return -1;
	}

}
