package mias.entity.action;

import mias.entity.Entity;
import mias.entity.EntityAttribute;
import mias.entity.PosEntity;
import mias.entity.attributes.anatomy.Body;

public class DropAction extends Action {

	private PosEntity target;
	
	public DropAction(Entity owner, PosEntity target) {
		super(owner);
		this.target = target;
	}

	@Override
	public int execute() {
		Body body = (Body)owner.getAttribute(EntityAttribute.BODY);
		if(body != null){
			if(target.getHeldBy() == owner){
				body.drop(target);
				return 10;
			}
		}
		return -1;
	}

}
