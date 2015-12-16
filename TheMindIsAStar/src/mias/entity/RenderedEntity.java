package mias.entity;

import mias.entity.attributes.equipment.Equippable;
import mias.world.World;

public class RenderedEntity extends PosEntity {

	private String texture;

	public RenderedEntity(String name, long x, long y, long z) {
		super(name, x, y, z);
	}

	public String getTexture() {
		return texture;
	}

	public RenderedEntity setTexture(String texture) {
		this.texture = texture;
		return this;
	}

	@Override
	public boolean shouldRender() {
		if (isHeld()){
			return false;
		}
		else if(hasAttribute(EntityAttribute.EQUIPPABLE)){
			if (((Equippable)getAttribute(EntityAttribute.EQUIPPABLE)).isEquipped()){
				return false;
			}
		}
		return true;
	}

	@Override
	public void loadEntity(World w) {
		super.loadEntity(w);
		w.getLoadedRenderableEntities().put(entityID, this);
	}

}
