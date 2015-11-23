package mias.render;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

import mias.TheMindIsAStar;

public class TextureRegistry {
	
	private HashMap<String, TextureData> registry = new HashMap<String, TextureData>(50);
	
	public void register(String texture) {
		registry.put(texture, null);
	}
	
	public void registerTextures() {
		register("tile_air");
		register("tile_green");
	}
	
	public void loadRegisteredTextures() {
		for (String texture : registry.keySet()) {
			loadTexture(texture);
		}
	}
	
	public void loadTexture(String texture) {
		File fileLocation = new File("assets\\textures\\" + texture + ".png");
		try {
			TextureData texData = TextureIO.newTextureData(TheMindIsAStar.GL_PROFILE, fileLocation, true, ".png");
			registry.put(texture, texData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public TextureData getTexture(String texture) {
		return registry.get(texture);
	}
}
