package mias.entity.attributes.weapon;

public abstract class StrikingSurface {
	
	protected StrikeType strikeType;
	
	public enum StrikeType{
		CRUSHING, SLICING, PIERCING
	}
}
