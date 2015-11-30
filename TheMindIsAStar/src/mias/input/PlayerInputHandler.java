package mias.input;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

import mias.TheMindIsAStar;
import mias.entity.RenderedEntity;
import mias.render.RenderHandler;
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
			player.offsetPos(0, 0, 1);
		}
		//Move south
		else if(e.getKeyCode() == KeyEvent.VK_S) {
			player.offsetPos(0, 0, -1);
		}
		//Move east
		else if(e.getKeyCode() == KeyEvent.VK_D) {
			player.offsetPos(1, 0, 0);
		}
		//Move west
		else if(e.getKeyCode() == KeyEvent.VK_A) {
			player.offsetPos(-1, 0, 0);
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

}
