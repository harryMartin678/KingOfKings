package IntermediateAI;

import java.util.ArrayList;

public class PathNode {
	
	ArrayList<int[]> path;
	int tNo;
	
	public PathNode(ArrayList<int[]> path,int tNo){
		
		this.path = path;
		this.tNo = tNo;
	}
	
	public ArrayList<int[]> getPath(){
		
		return path;
	}
	
	public int getTNo(){
		
		return tNo;
	}

}
