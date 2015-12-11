package mias.material;

import java.util.HashMap;

public class Material {
	
	public static final Material brainMatter = new Material("brain matter", 1.36f/1.13f, 1f, 1f, 
			4000, 4500, 1f, 1f, 1.5f, 1.5f);
	
	private static HashMap<String, Material> materialRegistry = new HashMap<String, Material>();
	
	protected String name;
	protected float density;
	protected float hardness;
	protected float resilience;
	protected int freezingPoint;
	protected int boilingPoint;
	protected float thermalCon;
	protected float electricalCon;
	protected float oxygenStarvationRate = 0f;
	protected float rotRate = 0f;

	public Material(String name, float density, float hardness, float resilience, int freezingPoint,
			int boilingPoint, float thermalCon, float electricalCon, float rotRate, float oxygenStarvationRate) {
		super();
		this.name = name;
		this.density = density;
		this.hardness = hardness;
		this.resilience = resilience;
		this.freezingPoint = freezingPoint;
		this.boilingPoint = boilingPoint;
		this.thermalCon = thermalCon;
		this.electricalCon = electricalCon;
		this.rotRate = rotRate;
		this.oxygenStarvationRate = oxygenStarvationRate;
		materialRegistry.put(name, this);
	}

	public static HashMap<String, Material> getMaterialRegistry() {
		return materialRegistry;
	}


	public String getName() {
		return name;
	}

	/**Returns density of material in kg / l
	 * 
	 * @return
	 */
	public float getDensity() {
		return density;
	}


	public float getHardness() {
		return hardness;
	}


	public float getResilience() {
		return resilience;
	}


	public int getFreezingPoint() {
		return freezingPoint;
	}


	public int getBoilingPoint() {
		return boilingPoint;
	}


	public float getThermalCon() {
		return thermalCon;
	}


	public float getElectricalCon() {
		return electricalCon;
	}
	

	public float getOxygenStarvationRate() {
		return oxygenStarvationRate;
	}
	

	public float getRotRate() {
		return rotRate;
	}
	
}
