package mias.util;

import mias.entity.RenderedEntity;
import mias.entity.ai.AIController;
import mias.entity.ai.need.AIMarch;
import mias.entity.attributes.PlayerControl;
import mias.entity.attributes.Updateable;

public class EntityMaker {
	
	public static RenderedEntity testPlayer(){
		RenderedEntity player = new RenderedEntity("Player", 0, 0, 0);
		player.setTexture("entity_player");
		player.giveAttribute(new Updateable()).giveAttribute(new PlayerControl());
		return player;
	}
	
	public static RenderedEntity testNPC(){
		RenderedEntity npc = new RenderedEntity("Joe Test", 5, 0, 0);
		npc.setTexture("entity_test_npc");
		npc.giveAttribute(new Updateable());
		AIController ai = new AIController();
		ai.addNeed(new AIMarch(ai, null, WorldCoord.NORTH));
		npc.giveAttribute(ai);
		return npc;
	}
}
