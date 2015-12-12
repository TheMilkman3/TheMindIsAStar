package mias.entity.attributes.anatomy;

import java.util.LinkedList;

import mias.material.Material;
import mias.material.MaterialState;

public class BodyPart {
	
	private String name;
	//category that determines the parts function
	private PartCategory category;
	
	//which part of the body contains the part
	private BodyLocation location;
	
	//list is organized from shallow -> deep
	private LinkedList<BodyLayer> layers = new LinkedList<BodyLayer>();
	
	//adjacent body parts away from center
	private LinkedList<BodyPart> links = new LinkedList<BodyPart>();
	
	//adjacent body part towards center
	private BodyPart inwardLink;
	
	//contained body parts.  these parts act as if they have this part's layers
	//in addition to their own
	private LinkedList<BodyPart> internals = new LinkedList<BodyPart>();
	
	//which body part this is an internal of
	private BodyPart external;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//returns the lowest effectiveness of its layers
	public float getEffectiveness(){
		float lowest = 1f;
		for(BodyLayer layer : layers){
			lowest = Math.min(lowest, layer.getLayerEffectiveness());
		}
		return lowest;
	}
	
	public LinkedList<BodyLayer> getLayers() {
		return layers;
	}

	public LinkedList<BodyPart> getLinks() {
		return links;
	}
	
	public void addLink(BodyPart part){
		links.addFirst(part);
		part.setInwardLink(this);;
	}
	
	public void addInternal(BodyPart internal){
		internals.add(internal);
		internal.external = this;
	}
	
	public void removeInternal(BodyPart internal){
		internals.remove(internal);
		internal.external = null;
	}
	
	public LinkedList<BodyPart> getInternals() {
		return internals;
	}

	public PartCategory getCategory() {
		return category;
	}

	public void setCategory(PartCategory category) {
		this.category = category;
	}

	public BodyPart getExternal() {
		return external;
	}

	public BodyLocation getLocation() {
		return location;
	}

	public void setLocation(BodyLocation location) {
		this.location = location;
	}
	
	public static BodyPart defaultLung(){
		BodyPart lung = new BodyPart();
		lung.setName("Lung");
		lung.setCategory(PartCategory.LUNG);
		lung.setLocation(BodyLocation.UPPER_BODY);
		BodyLayer layer1 = new BodyLayer(Material.getMaterial("lung"), MaterialState.SOLID, 3f, 2f, 3102);
		lung.getLayers().add(layer1);
		return lung;
	}
	
	public static BodyPart defaultBrain(){
		BodyPart brain = new BodyPart();
		brain.setName("Brain");
		brain.setCategory(PartCategory.BRAIN);
		brain.setLocation(BodyLocation.UPPER_BODY);
		BodyLayer layer1 = new BodyLayer(Material.getMaterial("brain matter"), MaterialState.SOLID, 3f, 2f, 3102);
		brain.getLayers().add(layer1);
		return brain;
	}

	public BodyPart getInwardLink() {
		return inwardLink;
	}

	public void setInwardLink(BodyPart inwardLink) {
		this.inwardLink = inwardLink;
	}
	
	public BodyPart partialCopy(){
		BodyPart copy = new BodyPart();
		copy.name = this.name;
		copy.category = this.category;
		copy.location = this.location;
		for(BodyLayer layer : layers){
			copy.layers.add(layer.copy());
		}
		return copy;
	}
	
	public BodyPart fullCopy(){
		BodyPart copy = partialCopy();
		for(BodyPart part : internals){
			copy.addInternal(part.fullCopy());
		}
		for (BodyPart part : links){
			copy.addLink(part.fullCopy());
		}
		return copy;
	}
}
