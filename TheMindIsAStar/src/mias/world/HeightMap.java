package mias.world;

import mias.util.RNG;

public class HeightMap {
	
	protected float[][] map;
	protected int width;
	protected int depth;
	
	public HeightMap(int width, int depth){
		this.width = width;
		this.depth = depth;
		map = new float[width] [depth];
	}
	
	public void randomizeHeights(float min, float max){
		float range = max - min;
		for (int x = 0; x <= width - 1; x++){
			for (int y = 0; y <= depth - 1; y++){
				float height = RNG.getFloat() * range + min;
				map[x][y] = height;
			}
		}
	}
}
