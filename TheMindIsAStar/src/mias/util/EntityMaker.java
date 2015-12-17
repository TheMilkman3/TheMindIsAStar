package mias.util;

import mias.entity.EntityAttribute;
import mias.entity.RenderedEntity;
import mias.entity.ai.AIController;
import mias.entity.ai.need.MoveToEntityNeed;
import mias.entity.attributes.PlayerControl;
import mias.entity.attributes.Updateable;
import mias.entity.attributes.anatomy.Body;
import mias.entity.attributes.anatomy.BodyTemplate;
import mias.entity.attributes.anatomy.PartCategory;
import mias.entity.attributes.equipment.Blade;
import mias.material.Material;
import mias.material.MaterialInstance;
import mias.material.MaterialState;
import mias.world.World;

public class EntityMaker {
	
	public static RenderedEntity testPlayer(long x, long y, long z){
		RenderedEntity player = new RenderedEntity("Player", x, y, z);
		player.setTexture("entity_player");
		player.giveAttribute(new Updateable()).giveAttribute(new PlayerControl());
		player.giveAttribute(BodyTemplate.getTemplate("human").getBody());
		return player;
	}
	
	public static RenderedEntity testNPC(long x, long y, long z){
		RenderedEntity npc = new RenderedEntity("Joe Test", x, y, z);
		npc.setTexture("entity_test_npc");
		npc.giveAttribute(new Updateable());
		AIController ai = new AIController();
		npc.giveAttribute(ai);
		ai.addNeed(new MoveToEntityNeed(ai, null, World.instance().getPlayer()));
		npc.giveAttribute(BodyTemplate.getTemplate("human").getBody());
		Body body = (Body)npc.getAttribute(EntityAttribute.BODY);
		//body.removePart(body.getPartsOfCategory(PartCategory.LEG).getFirst());
		return npc;
	}
	
	public static RenderedEntity testWeapon(long x, long y, long z){
		RenderedEntity weapon = new RenderedEntity("Sword", x, y, z);
		weapon.setTexture("entity_weapon");
		MaterialInstance weaponMaterial = new MaterialInstance(Material.getMaterial("iron"), MaterialState.SOLID, 
				2f, 1f, 0);
		weapon.giveAttribute(new Blade(weaponMaterial, 3f));
		return weapon;
	}
}
