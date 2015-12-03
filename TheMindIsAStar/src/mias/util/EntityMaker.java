package mias.util;

import mias.entity.RenderedEntity;
import mias.entity.ai.AIController;
import mias.entity.ai.need.MoveToEntityNeed;
import mias.entity.attributes.PlayerControl;
import mias.entity.attributes.Updateable;
import mias.world.World;

public class EntityMaker {
	
	public static RenderedEntity testPlayer(){
		RenderedEntity player = new RenderedEntity("Player", 0, 0, 0);
		player.setTexture("entity_player");
		player.giveAttribute(new Updateable()).giveAttribute(new PlayerControl());
		return player;
	}
	
	public static RenderedEntity testNPC(){
		RenderedEntity npc = new RenderedEntity("Joe Test", 10, 0, 0);
		npc.setTexture("entity_test_npc");
		npc.giveAttribute(new Updateable());
		AIController ai = new AIController();
		npc.giveAttribute(ai);
		ai.addNeed(new MoveToEntityNeed(ai, null, World.instance().getPlayer()));
		return npc;
	}
}
