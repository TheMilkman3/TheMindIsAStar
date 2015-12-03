package mias.util.pathfinding;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import mias.entity.PosEntity;
import mias.util.WorldCoord;

public class Pathfinder {
	
	private HashMap<WorldCoord, Node> nodes = new HashMap<WorldCoord, Node>(20);
	private LinkedList<Node> open = new LinkedList<Node>();
	private WorldCoord start;
	private WorldCoord end;
	private PosEntity owner;
	LinkedList<WorldCoord> path;
	
	public Pathfinder(WorldCoord start, WorldCoord end, PosEntity owner){
		this.start = start;
		this.end = end;
		this.owner = owner;
		Node startNode = new Node(this.start, 0d, hCost(this.start), nodes);
		nodes.put(startNode.coord, startNode);
		open.add(startNode);
	}
	
	public double hCost(WorldCoord n){
		return WorldCoord.distance(end, n);
	}
	
	public void addToOpen(Node n){
		open.add(n);
		Collections.sort(open);
	}
	
	public void addDirections(Node n){
		WorldCoord[] coords = new WorldCoord[4];
		coords[0] = WorldCoord.add(n.coord, WorldCoord.NORTH);
		coords[1] = WorldCoord.add(n.coord, WorldCoord.SOUTH);
		coords[2] = WorldCoord.add(n.coord, WorldCoord.WEST);
		coords[3] = WorldCoord.add(n.coord, WorldCoord.EAST);
		for(WorldCoord c : coords){
			Node d = nodes.get(c);
			if (d == null){
				d = new Node(c, n.gCost + 1d, hCost(c), nodes);
				if (d.fCost() == -1){
					d.closed = true;
				}
				else{
					d.previous = n;
					addToOpen(d);
				}
			}
			else if(!d.closed && d.gCost > n.gCost + 1d){
				d.gCost = n.gCost + 1d;
				d.previous = n;
				addToOpen(d);
			}
		}
	}
	
	public LinkedList<WorldCoord> pathfind(){
		if (path == null){
			boolean finished = false;
			Node current = null;
			while (!finished && !open.isEmpty()){
				current = open.pollFirst();
				if (!current.coord.equals(end)){
					current.closed = true;
					addDirections(current);
				}
				else{
					finished = true;
				}
			}
			if (finished){
				path = new LinkedList<WorldCoord>();
				while(current.previous != null){
					path.add(current.coord);
					current = current.previous;
				}
				path.pollFirst();
				return path;
			}
			else{
				return null;
			}
		}
		else{
			return path;
		}
	}
	
	public void resetPath(){
		path.clear();
	}
	
	private class Node implements Comparable<Node>{
		
		public final WorldCoord coord;
		public double gCost;
		public double hCost;
		public boolean closed = false;
		public Node previous;
		
		public Node(WorldCoord coord, double gCost, double hCost, HashMap<WorldCoord, Node> map){
			this.coord = coord;
			this.gCost = gCost;
			this.hCost = hCost;
			map.put(coord, this);
		}
		
		public double fCost(){
			if(owner.canPass(coord)){
				return hCost + gCost;
			}
			else{
				return -1;
			}
		}

		@Override
		public int compareTo(Node n) {
			double f1 = fCost();
			double f2 = n.fCost();
			if (f1 < f2){
				return -1;
			}
			else if(f1 > f2){
				return 1;
			}
			return 0;
		}
	}
}
