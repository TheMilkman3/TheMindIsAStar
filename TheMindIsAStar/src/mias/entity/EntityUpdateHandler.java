package mias.entity;

import java.util.Collections;
import java.util.LinkedList;

import mias.entity.attributes.Updateable;
import mias.entity.attributes.anatomy.Body;
import mias.world.World;

public class EntityUpdateHandler {
	
	public static final int BODY_UPDATE_FREQ = 10;
	
	private static EntityUpdateHandler instance;
	
	private LinkedList<Updateable> updateList = new LinkedList<Updateable>();
	private LinkedList<Body> bodyList = new LinkedList<Body>();
	private Updateable player;
	private int currentTick = 0;
	private int nextBodyUpdate = BODY_UPDATE_FREQ;
	
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
	
	public void addBody(Body b){
		bodyList.add(b);
	}
	
	public void removeBody(Body b){
		bodyList.remove(b);
	}
	
	public void addToUpdateList(Updateable ue){
		if (ue.Owner() == World.instance().getPlayer()){
			setPlayer(player);
		}
		else{
			ue.setActivationTick(ue.GetTicksUntilUpdate() + currentTick);
			updateList.addFirst(ue);
			Collections.sort(updateList);
		}
	}
	
	/*Try not to use this, probably isn't efficient*/
	public void removeFromUpdateList(Updateable ue){
		updateList.remove(ue);
	}
	
	public void updateEntites(){
		if(readyToUpdate(player)){
			player.owner.update();
			addToUpdateList(player);
		}
		else{
			if (nextBodyUpdate == currentTick){
				for(Body b : bodyList){
					b.update();
				}
				nextBodyUpdate = currentTick + BODY_UPDATE_FREQ;
			}
			while(readyToUpdate(updateList.getFirst())){
				Updateable u = updateList.removeFirst();
				u.owner.update();
				addToUpdateList(u);
			}
			currentTick++;
		}
	}
	
	public void setPlayer(Updateable player) {
		this.player = player;
		player.setActivationTick(player.GetTicksUntilUpdate() + currentTick);
	}
	
	private boolean readyToUpdate(Updateable u){
		return u.getActivationTick() == currentTick;
	}
	
}
