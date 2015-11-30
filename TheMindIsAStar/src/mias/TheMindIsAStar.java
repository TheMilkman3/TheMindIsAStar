package mias;

import com.jogamp.opengl.GLProfile;

import mias.input.PlayerInputHandler;
import mias.render.RenderHandler;
import mias.render.TextureRegistry;
import mias.world.World;

public class TheMindIsAStar {

	public static final GLProfile GL_PROFILE = GLProfile.get(GLProfile.GL4);
	public static final Object START_LOCK = new Object();
	public static boolean quit = false;

	public static void main(String[] args) {
		RenderHandler renderHandler;
		new TextureRegistry();
		renderHandler = new RenderHandler();
		renderHandler.addKeyListener(new PlayerInputHandler());
		new World();
		renderHandler.start();
	}
}
