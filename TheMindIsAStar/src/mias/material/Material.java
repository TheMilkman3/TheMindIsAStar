package mias.material;

import java.util.HashMap;

public class Material {
	private static HashMap<String, Material> materialRegistry = new HashMap<String, Material>();
	
	protected String name;
	protected float density;
	protected float hardness;
	protected float freezingPoint;
	protected float boilingPoint;
	protected float thermalConductivity;
	
	
	public Material(String name) {
		this.name = name;
		materialRegistry.put(name, this);
	}
}
