package mias.entity.attributes;

import mias.entity.EntityAttribute;
import mias.entity.EntityUpdateHandler;

public class Updateable extends EntityAttribute implements Comparable<Updateable> {
	
	private int ticksUntilUpdate = 0;
	private boolean paused = false;
	
	public Updateable(int ticksUntilUpdate){
		this.ticksUntilUpdate = ticksUntilUpdate;
	}
	
	public Updateable(){
		this(0);
	}
	
	public boolean readyToUpdate(){
		return this.ticksUntilUpdate <= 0;
	}
	
	public int GetTicksUntilUpdate(){
		return this.ticksUntilUpdate;
	}
	
	public Updateable SetTicksUntilUpdate(int ticks){
		this.ticksUntilUpdate = ticks;
		if(this.ticksUntilUpdate < 0){
			this.ticksUntilUpdate = 0;
		}
		return this;
	}
	
	public Updateable DecrementTicks(int ticks){
		this.SetTicksUntilUpdate(ticksUntilUpdate - ticks);
		return this;
	}
	
	public Updateable DecrementTicks(){
		return this.DecrementTicks(1);
	}
	
	public boolean isPaused(){
		return paused;
	}
	
	public Updateable setPause(boolean paused){
		this.paused = paused;
		return this;
	}
	
	@Override
	public String attributeType() {
		return EntityAttribute.UPDATEABLE;
	}

	@Override
	public void onGive() {
		if (owner.hasAttribute(PLAYER_CONTROL)){
			EntityUpdateHandler.instance().setPlayer(this);
		}
		else{
			EntityUpdateHandler.instance().addToUpdateList(this);
		}
	}

	@Override
	public void onRemove() {
		EntityUpdateHandler.instance().removeFromUpdateList(this);
	}

	@Override
	public int compareTo(Updateable arg0) {
		if (this.ticksUntilUpdate == arg0.ticksUntilUpdate){
			return 0;
		}
		else if (this.ticksUntilUpdate > arg0.ticksUntilUpdate){
			return 1;
		}
		else{
			return -1;
		}
	}
}
