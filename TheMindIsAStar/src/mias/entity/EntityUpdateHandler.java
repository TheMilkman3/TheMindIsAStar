package mias.entity;

import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;

import mias.entity.attributes.Updateable;
import mias.world.World;

public class EntityUpdateHandler {
	
	private static EntityUpdateHandler instance;
	
	private LinkedList<Updateable> updateList = new LinkedList<Updateable>();
	private Updateable player;
	
	private EntityUpdateHandler(){
	}
	
	public static EntityUpdateHandler instance(){
		return instance;
	}
	
	public static EntityUpdateHandler instantiate(){
		if (instance == null){
			instance = new EntityUpdateHandler();
		}
		return instance;
	}
	
	public void addToUpdateList(Updateable ue){
		if (ue.Owner() == World.instance().getPlayer()){
			this.player = ue;
		}
		else{
			updateList.addFirst(ue);
			Collections.sort(updateList);
		}
	}
	
	/*Try not to use this, probably isn't efficient*/
	public void removeFromUpdateList(Updateable ue){
		updateList.remove(ue);
	}
	
	public void updateEntites(){
		int leastTicks;
		if (!updateList.isEmpty()){
			leastTicks = updateList.getFirst().GetTicksUntilUpdate();
		}
		else if(player != null){
			leastTicks = player.GetTicksUntilUpdate();
		}
		else{
			return;
		}
		
		if (player.GetTicksUntilUpdate() < leastTicks){
			leastTicks = player.GetTicksUntilUpdate();
		}
		
		if(leastTicks > 0){
			if (!player.isPaused()){
				player.DecrementTicks(leastTicks);
			}

			for(Updateable up : updateList){
				if (!up.isPaused()){
					up.DecrementTicks(leastTicks);
				}
			}
		}
		
		if (player.readyToUpdate()){
			player.owner.update();
		}
		
		if (leastTicks > 0){
			ListIterator<Updateable> iterator = updateList.listIterator();
			LinkedList<Updateable> readd = new LinkedList<Updateable>();
			while(iterator.hasNext()){
				Updateable ue = iterator.next();
				if (!ue.isPaused()){
					if (ue.readyToUpdate()){
						ue.Owner().update();
						readd.add(ue);
						updateList.pollFirst();
					}
					else{
						break;
					}
				}
			}
			for(Updateable up : readd){
				this.addToUpdateList(up);
			}
		}
		
	}
	
	public void setPlayer(Updateable player) {
		this.player = player;
	}
	
}
