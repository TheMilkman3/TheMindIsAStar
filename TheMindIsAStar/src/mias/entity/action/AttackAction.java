package mias.entity.action;

import mias.entity.Entity;
import mias.entity.EntityAttribute;
import mias.entity.PosEntity;
import mias.entity.attributes.anatomy.Body;
import mias.entity.attributes.equipment.StrikingSurface;
import mias.util.MessageType;
import mias.world.World;

public class AttackAction extends Action {
	
	Body targetedBody;
	
	public AttackAction(Entity owner, Body targetedBody) {
		super(owner);
		this.targetedBody = targetedBody;
	}

	@Override
	public int execute() {
		Body body = (Body)owner.getAttribute(EntityAttribute.BODY);
		if (body != null && targetedBody != null){
			StrikingSurface strikingSurface = null;
			for (PosEntity e : body.getHeldEntities()){
				if (e.hasAttribute(EntityAttribute.STRIKING_SURFACE)){
					strikingSurface = (StrikingSurface)e.getAttribute(EntityAttribute.STRIKING_SURFACE);
					break;
				}
			}
			if (strikingSurface != null){
				String message = targetedBody.applyGeneralDamage(strikingSurface.getDamage(100f), strikingSurface.getStrikeType());
				World.instance().sendMessage(message, MessageType.SELF);;
				return 10;
			}
		}
		return -1;
	}

}
