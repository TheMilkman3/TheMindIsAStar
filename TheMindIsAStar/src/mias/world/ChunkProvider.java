package mias.world;

import mias.tile.Tile;

public class ChunkProvider {

	public static void setDefaultChunk(Chunk c) {
		for (int x = 0; x <= Chunk.CHUNK_WIDTH - 1; x++) {
			for (int z = 0; z <= Chunk.CHUNK_DEPTH - 1; z++) {
				c.setTileID(Tile.grassTile, x, 0, z);
			}
		}
		for (int x = 0; x <= Chunk.CHUNK_WIDTH - 1; x++) {
			for (int z = 0; z <= Chunk.CHUNK_DEPTH - 1; z++) {
				for (int y = 1; y <= Chunk.CHUNK_HEIGHT - 1; y++) {
					c.setTileID(Tile.airTile, x, y, z);
				}
			}
		}
	}

}
