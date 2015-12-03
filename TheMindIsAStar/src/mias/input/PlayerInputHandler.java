package mias.input;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

import mias.TheMindIsAStar;
import mias.entity.EntityAttribute;
import mias.entity.RenderedEntity;
import mias.entity.action.Action;
import mias.entity.action.MoveAction;
import mias.entity.action.WaitAction;
import mias.entity.attributes.PlayerControl;
import mias.render.RenderHandler;
import mias.util.WorldCoord;
import mias.world.World;

public class PlayerInputHandler implements KeyListener, MouseListener {

	protected static PlayerInputHandler instance;

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
}
