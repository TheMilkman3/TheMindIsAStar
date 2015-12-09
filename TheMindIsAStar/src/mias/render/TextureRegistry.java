package mias.render;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class TextureRegistry {

	private HashMap<String, Texture> registry = new HashMap<String, Texture>(50);

	private static TextureRegistry instance;

	public TextureRegistry() {
		instance = this;
	}

	public void register(String texture) {
		registry.put(texture, null);
	}

	public void registerTextures() {
		register("ascii");
		register("tile_grass");
		register("entity_player");
		register("entity_test_npc");
		register("tile_wall");
	}

	public void loadRegisteredTextures() {
		for (String texture : registry.keySet()) {
			loadTexture(texture);
		}
	}

	public void loadTexture(String texture) {
		File fileLocation = new File("assets\\textures\\" + texture + ".png");
		try {
			Texture texData = TextureIO.newTexture(fileLocation, false);
			registry.put(texture, texData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Texture getTexture(String texture) {
		return registry.get(texture);
	}

	public static TextureRegistry instance() {
		return instance;
	}
	

	public Texture getAsciiTileset() {
		return getTexture("ascii");
	}
		


}
