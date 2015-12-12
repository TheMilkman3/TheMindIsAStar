package mias.entity.attributes.anatomy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import mias.material.Material;
import mias.material.MaterialInstance;
import mias.material.MaterialState;
import mias.util.exceptions.MalformedDataException;

public class BodyTemplate {
	
	private static HashMap<String, BodyTemplate> templateRegistry = new HashMap<String, BodyTemplate>();
	
	private String name;
	private Body body = new Body();
	private HashMap<String, Material> referencedMaterials = new HashMap<String, Material>();
	
	public String getName(){
		return name;
	}
	
	public static BodyTemplate getTemplate(String templateName){
		return templateRegistry.get(templateName);
	}
	
	public static void loadTemplates(File location){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(location));
			String line = " ";
			BodyTemplate template = null;
			while(line != null){
				line = reader.readLine();
				if(line != null && !line.startsWith("#") && !line.equals("")){
					line = line.trim();
					String[] args = line.split("[\\[\\]]");
					if (template == null){
						if (line.equals("[TEMPLATE]")){
							template = new BodyTemplate();
						}
						else{
							reader.close();
							throw new MalformedDataException();
						}
					}
					else{
						switch(args[1]){
						case "TEMPLATE_NAME":
							template.name = args[2];
							break;
						case "ADD_MATERIAL":
							template.referencedMaterials.put(args[2], Material.getMaterial(args[3]));
							break;
						case "BODY_PART":
							template.body.addPart(loadPart(reader));
							break;
						case "NORMAL_BLOOD_VOLUME":
							template.body.setNormalBloodVolume(Float.parseFloat(args[2]));
							break;
						case "WALK_SPEED":
							template.body.setWalkSpeed(Float.parseFloat(args[2]));
							break;
						case "CRAWL_SPEED":
							template.body.setCrawlSpeed(Float.parseFloat(args[2]));
							break;
						case "BLOOD":
							template.body.setBlood(loadBlood(template, reader));
							break;
						}
						
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MalformedDataException e) {
			e.printStackTrace();
		}
	}
	
	private static BodyPart loadPart(BufferedReader reader) throws IOException{
		String line = " ";
		BodyPart part = new BodyPart();
		while(line != null){
			line = reader.readLine();
			if (line != null){
				line = line.trim();
				String[] args = line.split("[\\[\\]]");
				
			}
		}
		return part;
	}
	
	private static MaterialInstance loadBlood(BodyTemplate template, BufferedReader reader) throws IOException, MalformedDataException{
		String line = " ";
		Material material = null;
		MaterialState state = null;
		float volume = 0;
		float thickness = 0;
		int temperature = 0;
		while(line != null){
			line = reader.readLine();
			if (line != null){
				line = line.trim();
				String[] args = line.split("[\\[\\]]");
				switch(args[1]){
				case "MATERIAL":
					material = template.referencedMaterials.get(args[2]);
					break;
				case "SOLID":
					state = MaterialState.SOLID;
					break;
				case "LIQUID":
					state = MaterialState.LIQUID;
					break;
				case "GAS":
					state = MaterialState.GAS;
					break;
				case "VOLUME":
					volume = Float.parseFloat(args[2]);
					break;
				case "THICKNESS":
					thickness = Float.parseFloat(args[2]);
					break;
				case "TEMPERATURE":
					temperature = Integer.parseInt(args[2]);
					break;
				case "END_BLOOD":
					return new MaterialInstance(material, state, volume, thickness, temperature);
				default:
					reader.close();
					throw new MalformedDataException();
				}
			}
		}
		return null;
	}
	
}
