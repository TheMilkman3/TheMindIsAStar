package mias.entity.attributes.equipment;

import mias.material.MaterialInstance;

public class Blade extends StrikingSurface {
	
	public static final float BASE_SHARPNESS_MULTIPLIER = 1f;
	
	private float sharpness;
	
	public Blade(MaterialInstance material, float sharpness) {
		super(material);
		this.sharpness = sharpness;
	}

	@Override
	public float getDamageMultiplier() {
		return sharpness;
	}

	@Override
	public StrikeType getStrikeType() {
		return StrikeType.CUTTING;
	}

}
