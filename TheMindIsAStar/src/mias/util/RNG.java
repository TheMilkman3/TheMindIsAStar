package mias.util;

import java.util.Random;

public class RNG {
	
	private static RNG instance = new RNG();
	
	private Random r;
	
	private RNG(){
		r = new Random();
	}
	
	public static float getFloat(){
		return instance.r.nextFloat();
	}
}
