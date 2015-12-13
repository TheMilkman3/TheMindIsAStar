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
	private HashMap<String, BodyPart> referencedParts = new HashMap<String, BodyPart>();
	
	public String getName(){
		return name;
	}
	
	public static BodyTemplate getTemplate(String templateName){
		return templateRegistry.get(templateName);
	}
	
	public static void loadTemplates(File location){
		String line = " ";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(location));
			BodyTemplate template = null;
			while(line != null){
				line = reader.readLine();
				if(line != null && !line.startsWith("#") && !line.trim().equals("")){
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
						case "NEEDS":
							template.body.neededParts.put(PartCategory.getCategory(args[3]), Integer.parseInt(args[4]));
							break;
						case "ADD_MATERIAL":
							template.referencedMaterials.put(args[3], Material.getMaterial(args[4]));
							break;
						case "BODY_PART":
							template.body.addPart(loadPart(template, reader));
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
						case "END_TEMPLATE":
							templateRegistry.put(template.name, template);
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
			System.err.println(line);
		}
	}
	
	private static BodyPart loadPart(BodyTemplate template, BufferedReader reader) throws IOException, MalformedDataException{
		String line = " ";
		BodyPart part = new BodyPart();
		while(line != null){
			line = reader.readLine();
			if (line != null){
				line = line.trim();
				String[] args = line.split("[\\[\\]]");
				switch(args[1]){
				case "NAME":
					part.setName(args[2]);
					break;
				case "CATEGORY":
					part.setCategory(PartCategory.getCategory(args[2].toUpperCase()));
					break;
				case "UPPER_BODY":
					part.setLocation(BodyLocation.UPPER_BODY);
					break;
				case "LOWER_BODY":
					part.setLocation(BodyLocation.LOWER_BODY);
					break;
				case "LAYER_BOTTOM":
					part.getLayers().addLast(loadLayer(template, reader));
					break;
				case "LAYER_TOP":
					part.getLayers().addFirst(loadLayer(template, reader));
					break;
				case "INWARD_LINK":
					template.referencedParts.get(args[2]).addLink(part);
					break;
				case "INTERNAL_OF":
					template.referencedParts.get(args[2]).addInternal(part);
					break;
				case "END_BODY_PART":
					if (part.getInwardLink() == null){
						if (template.body.getCenter() != null){
							System.err.println("Warning: Part: " + part.getName() + " is free-floating.  Inward link should be defined.");
						}
						else{
							template.body.setCenter(part);
						}
					}
					template.referencedParts.put(part.getName(), part);
					return part;
				default:
					reader.close();
					throw new MalformedDataException();
				}
			}
		}
		return null;
	}
	
	private static BodyLayer loadLayer(BodyTemplate template, BufferedReader reader) throws IOException, MalformedDataException{
		String line = " ";
		Material material = null;
		MaterialState state = MaterialState.SOLID;
		float volume = 0;
		float thickness = 0;
		int temperature = 0;
		float coverage = 1;
		boolean vascular = false;
		boolean nervous = false;

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
				case "COVERAGE":
					coverage = Float.parseFloat(args[2]);
					break;
				case "VASCULAR":
					vascular = true;
					break;
				case "NERVOUS":
					nervous = true;
					break;
				case "END_LAYER":
					BodyLayer layer = new BodyLayer(material, state, volume, thickness, temperature);
					layer.setVascular(vascular);
					layer.setNervous(nervous);
					layer.setCoverage(coverage);
					return layer;
				default:
					reader.close();
					throw new MalformedDataException();
				}
			}
		}
		return null;
	}
	
	private static MaterialInstance loadBlood(BodyTemplate template, BufferedReader reader) throws IOException, MalformedDataException{
		String line = " ";
		Material material = null;
		MaterialState state = MaterialState.LIQUID;
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
	
	public Body getBody(){
		return body.fullCopy();
	}
}
