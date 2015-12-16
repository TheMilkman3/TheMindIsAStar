package mias.render;

import java.util.HashMap;
import java.util.LinkedList;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.texture.Texture;

import mias.entity.PosEntity;
import mias.entity.RenderedEntity;
import mias.render.util.RenderCoord;
import mias.tile.Tile;
import mias.util.WorldCoord;
import mias.world.Chunk;
import mias.world.World;

public class GUIMap extends GUIWindow {

	protected World world;
	protected int cameraX = 0, cameraY = 0, cameraDepth = 0;
	protected int widthInTiles = 10, heightInTiles = 10;

	public GUIMap(float x, float y, float width, float height, int depth) {
		super(x, y, width, height, depth);
		world = World.instance();
		this.updateView();
	}

	@Override
	public void draw(GL4 gl4) {
		HashMap<Texture, TileRenderNode> nodeMap = new HashMap<Texture, TileRenderNode>();
		synchronized(this){
			for (int x = cameraX; x <= cameraX + widthInTiles; x++){
				for (int y = cameraY; y <= cameraY + heightInTiles; y++){
					short tileID = world.getTileID(x, cameraDepth, y);
					if (tileID != -1){
						String texString = Tile.getTexture(tileID);
						this.loadTexturesIntoNodeMap(nodeMap, texString, x, y, cameraDepth);
					}
					LinkedList<PosEntity> entities = world.getEntitiesAtPosition(new WorldCoord(x, cameraDepth, y));
					if (entities != null && !entities.isEmpty()){
						for (PosEntity e : entities){
							if(e.shouldRender()){
								String texString = ((RenderedEntity)e).getTexture();
								this.loadTexturesIntoNodeMap(nodeMap, texString, (int) e.getX(), (int) e.getZ(), 1f);
								break;
							}
						}
					}
				}
			}
			/*for (RenderedEntity e : world.getLoadedRenderableEntities().values()) {
				if(e.getY() == cameraDepth){
					String texString = e.getTexture();
					this.loadTexturesIntoNodeMap(nodeMap, texString, (int) e.getX(), (int) e.getZ(), 1f);
				}
			}*/
			for (TileRenderNode rn : nodeMap.values()) {
				Texture tex = rn.tex;
				tex.enable(gl4);
				tex.bind(gl4);
				for (RenderCoord rc : rn.coords) {
					if (this.inCameraView(rc)) {
						drawTile(gl4, rc.x, rc.y, rc.z);
					}
				}
			}
		}
	}

	public void drawTile(GL4 gl4, float x, float z, float depth) {
		synchronized(this){
			model.translate(x, z, this.depth + depth);
			RenderHandler.instance().drawTexturedRectangle(gl4, mvpMatrix());
			model.pop();
		}
	}

	public void setCameraCoord(int cameraX, int cameraY, int cameraDepth) {
		this.cameraX = cameraX;
		this.cameraY = cameraY;
		updateView();
	}

	public void setCameraDimensions(int cameraWidth, int cameraHeight) {
		this.widthInTiles = cameraWidth;
		this.heightInTiles = cameraHeight;
		updateView();
	}
	
	public void centerOn(int x, int y, int z){
		setCameraCoord(x - widthInTiles/2, z - heightInTiles/2, y);
	}
	
	public void centerOn(PosEntity e){
		centerOn((int)e.getX(), (int)e.getY(), (int)e.getZ());
	}
	
	@Override
	public void updateView() {
		synchronized(this){
			view.clear();
			view.translate(0f, 0f, -40f);
			view.ortho(cameraX, cameraX + widthInTiles, cameraY, cameraY + heightInTiles, 0.01f, 50f);
			view.scale(width, height, 1);
			view.translate((x * 2 - 1) + width, (y * 2 - 1) + height, 0f);
		}
	}

	public boolean inCameraView(RenderCoord rc) {
		return rc.x >= cameraX && rc.x <= cameraX + widthInTiles && rc.y >= cameraY
				&& rc.y <= cameraY + heightInTiles;
	}
	
	public boolean inCameraView(Chunk c){
		long x1 = c.getChunkX();
		long y1 = c.getChunkY();
		long z1 = c.getChunkZ();
		long x2 = x1 + Chunk.CHUNK_WIDTH;
		long y2 = y1 + Chunk.CHUNK_HEIGHT;
		long z2 = z1 + Chunk.CHUNK_DEPTH;
		return y1 <= cameraDepth && y2 >= cameraDepth &&
				x1 <= cameraX + widthInTiles && x2 >= cameraX &&
				z1 <= cameraY + heightInTiles && z2 >= cameraY;
				
	}
	
	private void loadTexturesIntoNodeMap(HashMap<Texture, TileRenderNode> nodeMap, String texString, int x, int z,
			float depth) {
		Texture tex = TextureRegistry.instance().getTexture(texString);
		if (tex != null) {
			if (!nodeMap.containsKey(tex)) {
				nodeMap.put(tex, new TileRenderNode(tex));
			}
			nodeMap.get(tex).addCoord(x, z, depth);
		}
	}
	
	@Override
	public void adjustForAspect() {
		/*TODO Make this work later
		float aspect = this.renderHandler.aspectRatio();
		if (aspect > 1f) {
			widthInTiles = (int) Math.ceil((int)((float)heightInTiles * aspect));
			updateView();
		}
		else if (aspect < 1f) {
			heightInTiles = (int) Math.ceil((int)((float)widthInTiles / aspect));
			updateView();
		}
		if (widthInTiles > MAX_WIDTH_IN_TILES){
			widthInTiles = MAX_WIDTH_IN_TILES;
			heightInTiles = (int) Math.ceil((int)((float)widthInTiles / aspect));
		}
		else if (heightInTiles > MAX_HEIGHT_IN_TILES){
			heightInTiles = MAX_HEIGHT_IN_TILES;
			widthInTiles = (int) Math.ceil((int)((float)heightInTiles * aspect));
		}*/
	}

	private class TileRenderNode {
		private Texture tex;
		private LinkedList<RenderCoord> coords = new LinkedList<RenderCoord>();

		public TileRenderNode(Texture tex) {
			this.tex = tex;
		}

		public void addCoord(float x, float y, float z) {
			coords.add(new RenderCoord(x, y, z));
		}
	}
}
