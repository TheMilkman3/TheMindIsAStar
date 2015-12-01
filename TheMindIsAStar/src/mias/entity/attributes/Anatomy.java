package mias.entity.attributes;

import java.util.LinkedList;

import mias.entity.Entity;
import mias.entity.EntityAttribute;

public class Anatomy extends EntityAttribute {

	private LinkedList<AnatomyPart> parts = new LinkedList<AnatomyPart>();
	
	public Anatomy addPart(AnatomyPart part){
		parts.add(part);
		for(AnatomyPart child : part.childConnections){
			addPart(child);
		}
		return this;
	}
	
	public Anatomy removePart(AnatomyPart part){
		parts.remove(part);
		for(AnatomyPart child : part.childConnections){
			removePart(child);
		}
		return this;
	}
	
	@Override
	public String attributeType() {
		return EntityAttribute.ANATOMY;
	}

	@Override
	public void onGive() {

	}

	@Override
	public void onRemove() {

	}
	
	public class AnatomyPart{
		private String name;
		private Anatomy anatomy;
		private AnatomyPart parentConnection;
		private LinkedList<AnatomyPart> childConnections = new LinkedList<AnatomyPart>();
		
		public Entity Separate(){
			if(parentConnection != null){
				parentConnection.RemoveChildConnection(this);
				parentConnection = null;
				anatomy.removePart(this);
				return new Entity(name).giveAttribute(new Anatomy().addPart(this));
			}
			return null;
		}
		
		public void setParentConnection(AnatomyPart part){
			parentConnection = part;
		}
		
		public void addChildConnection(AnatomyPart part){
			childConnections.add(part);
		}
		
		public void RemoveChildConnection(AnatomyPart part){
			childConnections.remove(part);
		}
		
	}
}

