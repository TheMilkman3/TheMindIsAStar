package mias.entity.attributes.anatomy;

import java.util.HashMap;

public class PartCategory {
	private static HashMap<String, PartCategory> categoryRegistry = new HashMap<String, PartCategory>();
	public static final PartCategory
	//internal organs
	HEART = new PartCategory("HEART"), BRAIN = new PartCategory("BRAIN"), LUNG = new PartCategory("LUNG"), 
	STOMACH = new PartCategory("STOMACH"), KIDNEY = new PartCategory("KIDNEY"), SPLEEN = new PartCategory("SPLEEN"), 
	LIVER = new PartCategory("LIVER"),
	//sensory organs
	EYE = new PartCategory("EYE"), EAR = new PartCategory("EAR"), NOSE = new PartCategory("NOSE"), TONGUE = new PartCategory("TONGUE"),
	//appendages
	LEG = new PartCategory("LEG"), ARM = new PartCategory("ARM"), HAND = new PartCategory("HAND"), FINGER = new PartCategory("FINGER"),
	FOOT = new PartCategory("FOOT"), TOE = new PartCategory("TOE"), TAIL = new PartCategory("TAIL"), TENTACLE = new PartCategory("TENTACLE"), 
	TOOTH = new PartCategory("TOOTH"), PROTRUSION = new PartCategory("PROTRUSION"),
	//nervous
	SPINE_UPPER = new PartCategory("SPINE_UPPER"), SPINE_LOWER = new PartCategory("SPINE_LOWER");
	
	private String name;
	
	private PartCategory(String name){
		this.name = name;
		categoryRegistry.put(name, this);
	}

	public String getName() {
		return name;
	}
	
	public static PartCategory getCategory(String name){
		return categoryRegistry.get(name);
	}
}
