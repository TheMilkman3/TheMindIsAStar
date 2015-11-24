package mias;

import com.jogamp.opengl.GLProfile;

import mias.render.RenderHandler;
import mias.render.TextureRegistry;
import mias.world.World;

public class TheMindIsAStar {
	
	public static final GLProfile GL_PROFILE = GLProfile.get(GLProfile.GL4);
	
	public static void main(String[] args){
		RenderHandler rh = new RenderHandler();
		new TextureRegistry();
		new World();
		rh.start();
	}
}
