package mias.render;

import mias.world.Chunk;
import mias.world.World;

public class WorldRenderer extends Renderer {

	public World world;
	
	public WorldRenderer(World world) {
		this.world = world;
	}
	
	@Override
	public void draw() {
		
	}
	
	public void drawChunk(Chunk c) {
		
	}

}
