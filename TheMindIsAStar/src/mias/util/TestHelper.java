package mias.util;

import mias.tile.Tile;
import mias.world.World;

public class TestHelper {
	
	public static void setTileCube(short tileID, long x1, long y1, long z1, long x2, long y2, long z2){
		for (long i = x1; i <= x2; i++){
			for (long j = y1; j <= y2; j++){
				for (long k = z1; k <= z2; k++){
					World.instance().setTileID(Tile.wallTile, i, j, k);
				}
			}
		}
	}
	
}
