package IntermediateAI;

import java.util.ArrayList;

import Map.GameEngineCollisionMap;
import Util.Point;

public class WallCreator {
	
	private ArrayList<int[]> path;
	
	public WallCreator(ArrayList<Point> pts,int mapNo){
		
		path = new ArrayList<int[]>();
		
		for(int p = 1; p < pts.size(); p++){
			
			path.addAll(new Pathfinder(GameEngineCollisionMap.toArray(mapNo)).
					getPath(pts.get(p-1).x,pts.get(p-1).y, pts.get(p).x, pts.get(p).y));
		}
		
		path.addAll(new Pathfinder(GameEngineCollisionMap.toArray(mapNo)).
				getPath(pts.get(pts.size()-1).x,pts.get(pts.size()-1).y, pts.get(0).x, pts.get(0).y));
		
//		for(int p = 1; p < pts.size(); p++){
//			
//			int vectorX = pts.get(p).x - pts.get(p-1).x;
//			int vectorY = pts.get(p).y - pts.get(p-1).y;
//			
//			if(vectorX > vectorY){
//				
//				
//				
//			}else if(vectorX < vectorY){
//				
//				
//			}else{
//				
//				for(int f = pts.get(p-1).x; f < pts.get(p).x; f++){
//					
//					int nx = pts.get(p-1).x + (f * (vectorX / Math.abs(vectorX)));
//					int ny = pts.get(p-1).y + (f * (vectorY / Math.abs(vectorY)));
//					
//					if(GameEngineCollisionMap.getTile(nx, ny, mapNo) != 0){
//						
//						
//					}
//				}
//				
//			}
//			
//		}
	}
	
	public ArrayList<int[]> getWallRoute(){
		
		return path;
	}

}
