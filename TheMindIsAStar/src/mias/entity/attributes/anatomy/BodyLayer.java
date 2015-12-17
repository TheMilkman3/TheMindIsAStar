package mias.entity.attributes.anatomy;

import mias.entity.attributes.equipment.StrikingSurface.StrikeType;
import mias.material.Material;
import mias.material.MaterialInstance;
import mias.material.MaterialState;

public class BodyLayer extends MaterialInstance {

	public static final float CRUSHED_BLEED_RATE = 0.2f,
			CUT_BLEED_RATE = 0.4f,
			PIERCE_BLEED_RATE = 0.4f,
			CRUSH_DAMAGE_LEFTOVER_RATE = 1.75f;
	
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
	//how deep of a cut the layer has
	protected float cut = 0f;
	//how pulverized the layer is
	protected float crushed = 0f;
	//how pierced the layer is
	protected float pierced = 0f;
	
	public BodyLayer(Material material, MaterialState state, float volume, float thickness, int temperature) {
		super(material, state, volume, thickness, temperature);
	}


	/**How effective the layer is.  What this means varies depending on the body part.
	 * 
	 * @return float from 0 - 1, 1 meaning operating normally, 0 meaning non-functional
	 */
	
	public float getLayerEffectiveness(){
		return Math.max(0f, 1f - oxygenDeprivation + necrosis + cut + crushed);
	}
	
	public float getBleedRate(){
		if (isVascular()){
			return 0f;
		}
		else{
			return (crushed * CRUSHED_BLEED_RATE + cut * CUT_BLEED_RATE + pierced * PIERCE_BLEED_RATE) * getVolume();
		}
	}
	
	public float applyDamage(float damage, StrikeType strikeType){
		float actualDamage = damage;
		float remainingDamage = 0;
		if (strikeType == StrikeType.CUTTING){
			actualDamage *= getMaterial().getCutResistance();
			remainingDamage = Math.max(0, actualDamage - (1f - getCut()));
			setCut(getCut() - actualDamage);
		}
		else if (strikeType == StrikeType.CRUSHING){
			actualDamage *= getMaterial().getCrushResistance();
			remainingDamage = Math.max(0, actualDamage * CRUSH_DAMAGE_LEFTOVER_RATE - (1f - getCrushed()));
			setCrushed(getCrushed() - actualDamage);
		}
		else if (strikeType == StrikeType.PIERCING){
			actualDamage *= getMaterial().getPierceResistance();
			remainingDamage = Math.max(0, actualDamage - (1f - getPierced()));
			setPierced(getPierced() - actualDamage);
		}
		return remainingDamage;
	}
	
	public float getCoverage() {
		return coverage;
	}

	public void setCoverage(float coverage) {
		this.coverage = Math.max(0, Math.min(1f, coverage));;
	}

	public boolean isVascular() {
		return vascular;
	}

	public void setVascular(boolean vascular) {
		this.vascular = vascular;
	}

	public boolean isNervous() {
		return nervous;
	}

	public void setNervous(boolean nervous) {
		this.nervous = nervous;
	}

	public float getOxygenDeprivation() {
		return oxygenDeprivation;
	}

	public void setOxygenDeprivation(float oxygenDeprivation) {
		this.oxygenDeprivation = Math.min(1f, Math.max(0f, oxygenDeprivation));
	}
	
	public void adjustOxygenDeprivation(float oxygenDeprivation){
		this.setOxygenDeprivation(this.oxygenDeprivation + oxygenDeprivation);
	}

	public float getNecrosis() {
		return necrosis;
	}

	public void setNecrosis(float necrosis) {
		this.necrosis = Math.min(1f, Math.max(0f, necrosis));
	}
	
	public void adjustNecrosis(float necrosis) {
		this.setNecrosis(this.necrosis + necrosis);
	}

	public float getCut() {
		return cut;
	}

	public void setCut(float cut) {
		this.cut = Math.max(0f, Math.min(1f, cut));
	}

	public float getCrushed() {
		return crushed;
	}

	public void setCrushed(float crushed) {
		this.crushed = Math.max(0f, Math.min(1f, crushed));
	}
	


	public float getPierced() {
		return pierced;
	}
	


	public void setPierced(float pierced) {
		this.pierced = Math.max(0f, Math.min(1f, pierced));
	}

	
	public BodyLayer copy() {
		BodyLayer copy = new BodyLayer(getMaterial(), getState(), getVolume(), getThickness(), getTemperature());
		copy.coverage = this.coverage;
		copy.vascular = this.vascular;
		copy.nervous = this.nervous;
		copy.oxygenDeprivation = this.oxygenDeprivation;
		copy.necrosis = this.necrosis;
		copy.crushed = this.crushed;
		copy.cut = this.cut;
		copy.pierced = this.pierced;
		return copy;
	}
	

}
