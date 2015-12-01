package mias.material;

public class MaterialLayer {
	private Material material;
	private float thickness;
	public int temperature;
	
	public Material getMaterial() {
		return material;
	}
	public MaterialLayer setMaterial(Material material) {
		this.material = material;
		return this;
	}
	public float getThickness() {
		return thickness;
	}
	public MaterialLayer setThickness(float thickness) {
		this.thickness = thickness;
		return this;
	}
	public int getTemperature() {
		return temperature;
	}
	public MaterialLayer setTemperature(int temperature) {
		this.temperature = temperature;
		return this;
	}
}
