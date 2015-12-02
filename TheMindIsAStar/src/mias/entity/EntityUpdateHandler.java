package mias.entity;

import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;

import mias.entity.attributes.Updateable;
import mias.world.World;

public class EntityUpdateHandler {
	
	private static EntityUpdateHandler instance;
	private boolean playerDone = false;
	
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
		
		//Player updates have priority and are handled first
		if (player.GetTicksUntilUpdate() < leastTicks){
			leastTicks = player.GetTicksUntilUpdate();
		}
		if (!player.isPaused()){
			player.DecrementTicks(leastTicks);
			if (player.readyToUpdate()){
				player.Owner().update();
			}
		}
		if (playerDone){
			ListIterator<Updateable> iterator = updateList.listIterator();
			updateList = new LinkedList<Updateable>();
			while(iterator.hasNext()){
				Updateable ue = iterator.next();
				if (!ue.isPaused()){
					Entity e = ue.Owner();
					ue.DecrementTicks(leastTicks);
					if (ue.readyToUpdate()){
						e.update();
						addToUpdateList(ue);
					}
				}
			}
		}
	}

	public boolean isPlayerDone() {
		return playerDone;
	}

	public void setPlayerDone(boolean playerDone) {
		this.playerDone = playerDone;
	}
	

	public void setPlayer(Updateable player) {
		this.player = player;
	}
	
}
