package mias.render;

import java.util.HashMap;
import java.util.LinkedList;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.texture.Texture;

import mias.render.util.RenderCoord;
import mias.tile.Tile;
import mias.world.Chunk;
import mias.world.World;

public class GUIMap extends GUIWindow {
	
	protected World world;
	protected int cameraX = 0,  cameraY = 0;
	protected int widthInTiles = 10, heightInTiles = 10;
	
	public GUIMap(float x, float y, float width, float height, int depth) {
		super(x, y, width, height, depth);
		world = World.instance();
		this.updateView();
	}
	
	@Override
	public void draw(GL4 gl4) {
		HashMap<Texture, TileRenderNode> nodeMap = new HashMap<Texture, TileRenderNode>();
		HashMap<Integer, Chunk> loadedChunks = world.getLoadedChunks();
		for(Chunk chunk : loadedChunks.values()){
			for (int i = 0; i <= Chunk.CHUNK_WIDTH - 1; i++){
				for (int j = 0; j <= Chunk.CHUNK_DEPTH - 1; j++){
					String texString = Tile.getTexture(chunk.getTileID(i, 0, j));
					Texture tex = TextureRegistry.instance().getTexture(texString);
					if(tex != null){
						if(!nodeMap.containsKey(tex)){
							nodeMap.put(tex, new TileRenderNode(tex));
						}
						nodeMap.get(tex).addCoord(i + chunk.getChunkX(), j + chunk.getChunkZ());
					}
				}
			}
		}
		for(TileRenderNode rn : nodeMap.values()){
			Texture tex = rn.tex;
			tex.enable(gl4);
			tex.bind(gl4);
			for (RenderCoord rc : rn.coords){
				if (this.inCameraView(rc)){
					drawTile(gl4, rc.x, rc.y);
				}
			}
		}
	}
	
	public void drawTile(GL4 gl4, float x, float z) {
		model.translate(x, z, 0f);
		RenderHandler.instance().drawTexturedRectangle(gl4, mvpMatrix());
		model.pop();
	}

	public void setCameraCoord(int cameraX, int cameraY) {
		this.cameraX = cameraX;
		this.cameraY = cameraY;
		updateView();
	}
	
	@Override
	public void updateView() {
		view.clear();
		view.translate(0f, 0f, -1f);
		view.ortho(cameraX, cameraX + widthInTiles, cameraY, cameraY + heightInTiles, 0.01f, 1f);
		view.scale(width, height, 1);
		view.translate(x * 2 - width, y * 2 - height, 0f);
	}
	
	private class TileRenderNode{
		private Texture tex;
		private LinkedList<RenderCoord> coords = new LinkedList<RenderCoord>();
		
		public TileRenderNode(Texture tex){
			this.tex = tex;
		}
		
		public void addCoord(float x, float y){
			coords.add(new RenderCoord(x, y));
		}
	}
	
	public boolean inCameraView(RenderCoord rc){
		return rc.x >= cameraX && rc.x <= cameraX + widthInTiles - 1
				&& rc.y >= cameraY && rc.y <= cameraY + heightInTiles - 1;
	}
}
