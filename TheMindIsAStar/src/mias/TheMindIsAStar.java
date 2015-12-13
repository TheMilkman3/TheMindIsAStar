package mias;

import java.io.File;

import com.jogamp.opengl.GLProfile;

import mias.entity.attributes.anatomy.BodyTemplate;
import mias.input.PlayerInputHandler;
import mias.material.Material;
import mias.render.RenderHandler;
import mias.render.TextureRegistry;
import mias.world.World;

public class TheMindIsAStar {

	public static final GLProfile GL_PROFILE = GLProfile.get(GLProfile.GL4);
	public static final Object START_LOCK = new Object();
	public static boolean quit = false;

	public static void main(String[] args) {
		loadGameData();
		RenderHandler renderHandler;
		new TextureRegistry();
		renderHandler = new RenderHandler();
		renderHandler.addKeyListener(new PlayerInputHandler());
		new World();
		renderHandler.start();
		while(!quit){
			World.instance().update();
		}
	}
	
	public static void loadGameData(){
		Material.loadMaterialsFromFile(new File("data\\materials.txt"));
		BodyTemplate.loadTemplates(new File("data\\body_templates.txt"));
	}
}
