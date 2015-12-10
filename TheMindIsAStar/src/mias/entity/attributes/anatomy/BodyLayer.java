package mias.entity.attributes.anatomy;

import mias.material.Material;
import mias.material.MaterialInstance;
import mias.material.MaterialState;

public class BodyLayer extends MaterialInstance {

	//how completely the layer covers the one below it
	protected float coverage = 1f;
	
	//does the layer have blood veins
	protected boolean vascular = true;
	
	//does the layer act like a nerve
	protected boolean nervous = false;
	
	// how deprived the layer is of oxygen.  Once this
	//hits 1, layer will begin to necrotize
	protected float oxygenDeprivation = 0f;
	//how much the layer has begun to rot
	protected float necrosis = 0f;
	
	public BodyLayer(Material material, MaterialState state, float volume, float thickness, int temperature) {
		super(material, state, volume, thickness, temperature);
	}
}
