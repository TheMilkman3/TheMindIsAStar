package mias.material;

public class MaterialInstance {
	
	private Material material;
	private MaterialState state;
	private float volume;
	private float thickness;
	private int temperature;
	
	public MaterialInstance(Material material, MaterialState state, float volume, float thickness, int temperature) {
		this.material = material;
		this.state = state;
		this.volume = volume;
		this.thickness = thickness;
		this.temperature = temperature;
	}
	
	public float getMass(){
		return volume * material.getDensity();
	}
	
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	public MaterialState getState() {
		return state;
	}
	public void setState(MaterialState state) {
		this.state = state;
	}
	public float getVolume() {
		return volume;
	}
	public void setVolume(float volume) {
		this.volume = volume;
	}
	public float getThickness() {
		return thickness;
	}
	public void setThickness(float thickness) {
		this.thickness = thickness;
	}
	public int getTemperature() {
		return temperature;
	}
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public MaterialInstance copy(){
		MaterialInstance copy = new MaterialInstance(material, state, volume, thickness, temperature);
		return copy;
	}
}
