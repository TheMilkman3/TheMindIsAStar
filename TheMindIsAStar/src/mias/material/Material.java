package mias.material;

import java.util.HashMap;

public class Material {
	private static HashMap<String, Material> materialRegistry = new HashMap<String, Material>();
	
	protected String name;
	protected float density;
	protected float hardness;
	protected float resilience;
	protected float freezingPoint;
	protected float boilingPoint;
	protected float thermalCon;
	protected float electricalCon;
	
	
	public Material(String name) {
		this.name = name;
		materialRegistry.put(name, this);
	}


	public static HashMap<String, Material> getMaterialRegistry() {
		return materialRegistry;
	}


	public String getName() {
		return name;
	}


	public float getDensity() {
		return density;
	}


	public float getHardness() {
		return hardness;
	}


	public float getResilience() {
		return resilience;
	}


	public float getFreezingPoint() {
		return freezingPoint;
	}


	public float getBoilingPoint() {
		return boilingPoint;
	}


	public float getThermalCon() {
		return thermalCon;
	}


	public float getElectricalCon() {
		return electricalCon;
	}
	
}
