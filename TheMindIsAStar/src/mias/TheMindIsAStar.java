package mias;

import com.jogamp.opengl.GLProfile;

import mias.render.RenderHandler;
import mias.render.TextureRegistry;

public class TheMindIsAStar {
	
	public static final GLProfile GL_PROFILE = GLProfile.get(GLProfile.GL4);
	
	private static final TextureRegistry textureRegistry = new TextureRegistry();
	
	public static void main(String[] args){
		textureRegistry.registerTextures();
		textureRegistry.loadRegisteredTextures();
		RenderHandler rh = new RenderHandler();
		rh.start();
	}
	
	public static TextureRegistry getTextureRegistry() {
		return textureRegistry;
	}

}
