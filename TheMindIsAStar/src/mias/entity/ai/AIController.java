package mias.entity.ai;

import java.util.LinkedList;

import mias.entity.IUpdatable;
import mias.entity.ai.need.AINeed;

public class AIController {
	
	private IUpdatable owner;
	private LinkedList<AINeed> needs = new LinkedList<AINeed>();
	
	public LinkedList<AINeed> Needs() {
		return needs;
	}
	
	public IUpdatable Owner(){
		return owner;
	}
}
