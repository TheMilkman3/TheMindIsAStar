package mias.util;

import mias.entity.EntityAttribute;
import mias.entity.RenderedEntity;
import mias.entity.ai.AIController;
import mias.entity.ai.need.MoveToEntityNeed;
import mias.entity.attributes.PlayerControl;
import mias.entity.attributes.Updateable;
import mias.entity.attributes.anatomy.Body;
import mias.entity.attributes.anatomy.PartCategory;
import mias.world.World;

public class EntityMaker {
	
	public static RenderedEntity testPlayer(){
		RenderedEntity player = new RenderedEntity("Player", 0, 0, 0);
		player.setTexture("entity_player");
		player.giveAttribute(new Updateable()).giveAttribute(new PlayerControl());
		player.giveAttribute(Body.testBody());
		return player;
	}
	
	public static RenderedEntity testNPC(){
		RenderedEntity npc = new RenderedEntity("Joe Test", 10, 0, 0);
		npc.setTexture("entity_test_npc");
		npc.giveAttribute(new Updateable());
		AIController ai = new AIController();
		npc.giveAttribute(ai);
		ai.addNeed(new MoveToEntityNeed(ai, null, World.instance().getPlayer()));
		npc.giveAttribute(Body.testBody());
		Body body = (Body) npc.getAttribute(EntityAttribute.BODY);
		body.removePart(body.getPartsOfCategory(PartCategory.LUNG).getFirst());
		return npc;
	}
}
