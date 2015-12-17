package mias.material;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import mias.util.exceptions.MalformedDataException;

public class Material {
	
	private static HashMap<String, Material> materialRegistry = new HashMap<String, Material>();
	
	
	protected String name = null;
	protected float density = 1f;
	protected float crushResistance = 1f;
	protected float cutResistance = 1f;
	protected float pierceResistance = 1f;
	protected int freezingPoint = 2732;
	protected int boilingPoint = 3732;
	protected float thermalCon = 1f;
	protected float electricalCon = 1f;
	protected float oxygenStarvationRate = 0f;
	protected float rotRate = 0f;

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
	
	public static Material getMaterial(String name){
		return materialRegistry.get(name);
	}
	
	public static void loadMaterialsFromFile(File location){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(location));
			String line = " ";
			Material newMaterial = null;
			while(line != null){
				line = reader.readLine();
				if (line == null){
					break;
				}
				if (!line.startsWith("#") && !line.equals("")){
					if (newMaterial == null){
						if (line.equals("[MATERIAL]")){
							newMaterial = new Material();
						}
						else{
							reader.close();
							throw new MalformedDataException();
						}
					}
					else{
						String[] args = line.split("[\\[\\]]");
						switch(args[1]){
						case "NAME":
							newMaterial.name = args[2];
							break;
						case "DENSITY":
							newMaterial.density = Float.parseFloat(args[2]);
							break;
						case "CRUSHRES":
							newMaterial.crushResistance = Float.parseFloat(args[2]);
							break;
						case "CUTRES":
							newMaterial.cutResistance = Float.parseFloat(args[2]);
							break;
						case "PIERCERES":
							newMaterial.pierceResistance = Float.parseFloat(args[2]);
							break;
						case "FREEZING":
							newMaterial.freezingPoint = Integer.parseInt(args[2]);
							break;
						case "BOILING":
							newMaterial.boilingPoint = Integer.parseInt(args[2]);
							break;
						case "THERMALCON":
							newMaterial.thermalCon = Float.parseFloat(args[2]);
							break;
						case "ELECTRICALCON":
							newMaterial.electricalCon = Float.parseFloat(args[2]);
							break;
						case "OXYGENSTARV":
							newMaterial.oxygenStarvationRate = Float.parseFloat(args[2]);
							break;
						case "ROT":
							newMaterial.rotRate = Float.parseFloat(args[2]);
							break;
						case "END_MATERIAL":
							if(newMaterial.name == null){
								reader.close();
								throw new MalformedDataException();
							}
							else{
								Material.materialRegistry.put(newMaterial.name, newMaterial);
								newMaterial = null;
							}
							break;
						default:
							reader.close();
							throw new MalformedDataException();
						}
					}
				}
			}
			reader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MalformedDataException e) {
			e.printStackTrace();
		}
	}

	public float getCrushResistance() {
		return crushResistance;
	}

	public float getCutResistance() {
		return cutResistance;
	}

	public float getPierceResistance() {
		return pierceResistance;
	}
	
}
