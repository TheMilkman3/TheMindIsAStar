package mias.entity.attributes.anatomy;

import java.util.LinkedList;

public class BodyPart {
	
	//category that determines the parts function
	private PartCategory category;
	
	//which part of the body contains the part
	private BodyLocation location;
	
	//list is organized from shallow -> deep
	private LinkedList<BodyLayer> layers = new LinkedList<BodyLayer>();
	
	//adjacent body parts
	private LinkedList<BodyPart> links = new LinkedList<BodyPart>();
	
	//contained body parts.  these parts act as if they have this part's layers
	//in addition to their own
	private LinkedList<BodyPart> internals = new LinkedList<BodyPart>();
	
	//which body part this is an internal of
	private BodyPart external;
	
	public LinkedList<BodyLayer> getLayers() {
		return layers;
	}

	public LinkedList<BodyPart> getLinks() {
		return links;
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
}
