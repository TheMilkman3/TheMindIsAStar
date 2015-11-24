package mias.render;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.texture.Texture;

import mias.tile.Tile;
import mias.world.World;

public class GUIMap extends GUIWindow {
	
	protected World world;
	protected int cameraX = 0, cameraZ = 0, cameraY = 0;
	
	public GUIMap(int x, int y, int width, int height, int depth) {
		super(x, y, width, height, depth);
		world = World.instance();
	}
	
	@Override
	public void draw(GL4 gl4) {
	}
	
	public void drawTile(GL4 gl4, short tileID, int x, int z) {
		Texture tex = TextureRegistry.instance().getTexture(Tile.getTexture(tileID));
		tex.enable(gl4);
		tex.bind(gl4);
		model.translate(x, x, z);
		RenderHandler.instance().drawTexturedRectangle(gl4, mvpMatrix());
		model.pop();
	}

	public void setCameraX(int cameraX) {
		this.cameraX = cameraX;
	}

	public void setCameraZ(int cameraZ) {
		this.cameraZ = cameraZ;
	}

	public void setCameraY(int cameraY) {
		this.cameraY = cameraY;
	}
	
	public void setCameraCoord(int cameraX, int cameraY, int cameraZ) {
		this.cameraX = cameraX;
		this.cameraY = cameraY;
		this.cameraZ = cameraZ;
	}
}
