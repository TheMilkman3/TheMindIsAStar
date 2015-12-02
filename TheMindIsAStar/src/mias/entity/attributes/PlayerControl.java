package mias.entity.attributes;

import mias.entity.EntityAttribute;
import mias.entity.EntityUpdateHandler;
import mias.entity.action.Action;

public class PlayerControl extends EntityAttribute {

	private Action action;
	
	@Override
	public String attributeType() {
		return EntityAttribute.PLAYER_CONTROL;
	}

	@Override
	public void onGive() {
		Updateable up = (Updateable)owner.getAttribute(UPDATEABLE);
		if (up != null){
			EntityUpdateHandler.instance().removeFromUpdateList(up);
			EntityUpdateHandler.instance().setPlayer(up);
		}
	}

	@Override
	public void onRemove() {
		// TODO Auto-generated method stub

	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

}
