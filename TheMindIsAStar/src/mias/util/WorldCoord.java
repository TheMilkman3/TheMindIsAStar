package mias.util;

public class WorldCoord {
	
	public static final WorldCoord NORTH = new WorldCoord(0, 0, 1),
			SOUTH = new WorldCoord(0, 0, -1),
			WEST = new WorldCoord(-1, 0, 0),
			EAST = new WorldCoord(1, 0, 0),
			UP = new WorldCoord(0, 1, 0),
			DOWN = new WorldCoord(0, -1, 0);
	
	public long x , y, z;
	
	public WorldCoord(long x, long y, long z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public WorldCoord set(long x, long y, long z){
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	public static WorldCoord add(WorldCoord c1, WorldCoord c2){
		return new WorldCoord(c1.x + c2.x, c1.y + c2.y, c1.z + c2.z);
	}
	
	public static WorldCoord subtract(WorldCoord c1, WorldCoord c2){
		return new WorldCoord(c1.x - c2.x, c1.y - c2.y, c1.z - c2.z);
	}
	
	public static double distance(WorldCoord c1, WorldCoord c2){
		long a = c1.x - c2.x;
		long b = c1.y - c2.y;
		long c = c1.z - c2.z;
		return Math.sqrt(Math.pow(a, 2.0) + Math.pow(b, 2.0) + Math.pow(c, 2.0));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || this.getClass() != obj.getClass()){
			return false;
		}
		return x == ((WorldCoord)obj).x && y == ((WorldCoord)obj).y && z == ((WorldCoord)obj).z;
	}

	@Override
	public int hashCode() {
		int hash = 11;
		int result = 3;
		
		result = hash * result + (int)x;
		result = hash * result + (int)y;
		result = hash * result + (int)z;
		
		return result;
	}
}