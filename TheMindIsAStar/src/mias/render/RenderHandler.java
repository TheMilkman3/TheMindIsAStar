package mias.render;

import java.util.LinkedList;

import com.jogamp.newt.Display;
import com.jogamp.newt.Screen;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;

import mias.entity.PosEntity;
import mias.world.World;

public class RenderHandler implements Runnable {
	
	private static RenderHandler instance;
	
	private double framesPerSecond = 60.0;
	
	private LinkedList<Renderer> renderers = new LinkedList<Renderer>();
	private long playerX, playerY, playerZ;
	
	private Display display;
	private Screen screen;
	private GLProfile glProfile;
	private GLCapabilities glCapabilities;
	private GLWindow glWindow;
	
	private RenderHandler() {
		this.addRenderer(new ChunkRenderer());
	}
	
	public static RenderHandler instance() {
		if (instance == null) {
			instance = new RenderHandler();
		}
		return instance;
	}
	
	public void Initiate() {
	}
	
	public void addRenderer(Renderer r) {
		renderers.add(r);
	}

	@Override
	public void run() {
		for(Renderer renderer : renderers) {
			renderer.draw();
		}
		try {
			Thread.sleep((long)(1000.0/framesPerSecond));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void setFramesPerSecond(double f) {
		framesPerSecond = f;
	}
	
	public void setPlayerPos(){
		synchronized(World.instance().getPlayer()) {
			PosEntity player = World.instance().getPlayer();
			playerX = player.getX();
			playerY = player.getY();
			playerZ = player.getZ();
		}
	}

	public long getPlayerX() {
		return playerX;
	}

	public long getPlayerY() {
		return playerY;
	}

	public long getPlayerZ() {
		return playerZ;
	}
	
}
