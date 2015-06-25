package IntermediateAI;

import java.util.ArrayList;

public class PathRet {
	
	private ArrayList<int[]> path;
	private int score;
	
	public PathRet(ArrayList<int[]> path, int score){
		
		this.path = path;
		this.score = score;
	}
	
	public int getScore(){
		
		return score;
	}
	
	public ArrayList<int[]> getPath(){
		
		return path;
	}

}
