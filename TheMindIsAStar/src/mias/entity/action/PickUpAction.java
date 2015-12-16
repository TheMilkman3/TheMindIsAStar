package mias.entity.action;

import mias.entity.Entity;
import mias.entity.EntityAttribute;
import mias.entity.PosEntity;
import mias.entity.attributes.anatomy.Body;

public class PickUpAction extends Action {

	private PosEntity target;
	
	public PickUpAction(Entity owner, PosEntity target) {
		super(owner);
		this.target = target;
	}

	@Override
	public int execute() {
		Body body = (Body)owner.getAttribute(EntityAttribute.BODY);
		if (body != null){
			body.hold(target);
			return 10;
		}
		return -1;
	}

}
