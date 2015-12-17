package mias.input;

import java.util.Iterator;
import java.util.LinkedList;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

import mias.TheMindIsAStar;
import mias.entity.EntityAttribute;
import mias.entity.PosEntity;
import mias.entity.RenderedEntity;
import mias.entity.action.Action;
import mias.entity.action.DropAction;
import mias.entity.action.MoveAction;
import mias.entity.action.PickUpAction;
import mias.entity.action.WaitAction;
import mias.entity.attributes.PlayerControl;
import mias.entity.attributes.anatomy.Body;
import mias.render.GUIMap;
import mias.render.GUIMenu;
import mias.render.GUIMenu.MenuFunction;
import mias.render.RenderHandler;
import mias.util.MessageType;
import mias.util.WorldCoord;
import mias.world.World;

public class PlayerInputHandler implements KeyListener, MouseListener {

	protected static PlayerInputHandler instance;

	protected GUIMenu menu;
	protected GUIMap map;

	public PlayerInputHandler() {
		instance = this;
	}

	public static PlayerInputHandler instance() {
		return instance;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		World world = World.instance();
		RenderedEntity player = world.getPlayer();
		if(map.inFocus()){
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				RenderHandler.instance().stop();
				TheMindIsAStar.quit = true;
			}
			//Move north
			else if(e.getKeyCode() == KeyEvent.VK_W) {
				setPlayerAction(new MoveAction(player, WorldCoord.NORTH));
			}
			//Move south
			else if(e.getKeyCode() == KeyEvent.VK_S) {
				setPlayerAction(new MoveAction(player, WorldCoord.SOUTH));
			}
			//Move east
			else if(e.getKeyCode() == KeyEvent.VK_D) {
				setPlayerAction(new MoveAction(player, WorldCoord.EAST));
			}
			//Move west
			else if(e.getKeyCode() == KeyEvent.VK_A) {
				setPlayerAction(new MoveAction(player, WorldCoord.WEST));
			}
			else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				setPlayerAction(new WaitAction(player, 10));
			}
			else if(e.getKeyCode() == KeyEvent.VK_M) {
				World.instance().sendMessage(Long.toString(System.currentTimeMillis()), MessageType.SPEECH);
			}
			else if(e.getKeyCode() == KeyEvent.VK_P){
				menu.setFunction(MenuFunction.PICK_UP);
				menu.setHeader("Pick up:");
				int i = 0;
				for(PosEntity entity : world.getEntitiesAtPosition(world.getPlayer().getPos())){
					if (entity != world.getPlayer()){
						menu.addMenuItem(Integer.toString(i).charAt(0), entity.getName());
					}
				}
				menu.focus();
			}
			else if (e.getKeyCode() == KeyEvent.VK_L){
				int i = 0;
				Body body = (Body)(world.getPlayer().getAttribute(EntityAttribute.BODY));
				if (body != null){
					menu.setFunction(MenuFunction.DROP);
					menu.setHeader("Drop:");
					for(PosEntity entity : body.getHeldEntities()){
						if (entity != world.getPlayer()){
							menu.addMenuItem(Integer.toString(i).charAt(0), entity.getName());
						}
					}
					menu.focus();
				}
			}
		}
		else if (menu.inFocus()){
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
				resetMenu();
			}
			else{
				String result = menu.getItemFromInput(e.getKeyChar());
				if (result != null){
					switch(menu.getFunction()){
					case PICK_UP:
						setPlayerAction(new PickUpAction(player, getTargetFromMenu(e.getKeyChar(), world.getEntitiesAtPosition(world.getPlayer().getPos()))));
						resetMenu();
						break;
					case DROP:
						Body body = (Body)(world.getPlayer().getAttribute(EntityAttribute.BODY));
						setPlayerAction(new DropAction(player, getTargetFromMenu(e.getKeyChar(), body.getHeldEntities())));
						resetMenu();
						break;
					case NONE:
						break;
					}
				}
			}
		}
	}
	
	public PosEntity getTargetFromMenu(char selection, LinkedList<PosEntity> entityList){
		int index = Integer.parseInt(String.valueOf(selection));
		Iterator<PosEntity> iter = entityList.listIterator(index);
		PosEntity target = iter.next();
		if (target == World.instance().getPlayer()){
			target = iter.next();
		}
		return target;
	}
	
	public void resetMenu(){
		menu.clearMenuItems();
		menu.setFunction(MenuFunction.NONE);
		menu.setHeader(null);
		map.focus();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseWheelMoved(MouseEvent e) {
	}

	public void setPlayerAction(Action a){
		PlayerControl pc = (PlayerControl)(World.instance().getPlayer().getAttribute(EntityAttribute.PLAYER_CONTROL));
		if (pc != null){
			synchronized(pc){
				pc.setAction(a);
			}
		}
	}

	public GUIMenu getMenu() {
		return menu;
	}

	public void setMenu(GUIMenu menu) {
		this.menu = menu;
	}

	public GUIMap getMap() {
		return map;
	}

	public void setMap(GUIMap map) {
		this.map = map;
	}

}
