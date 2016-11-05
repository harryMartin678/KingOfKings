package IntermediateAI;

import java.util.ArrayList;
import java.util.HashMap;

import Map.GameEngineCollisionMap;
import Util.Point;

public class WallCreator {
	
	private ArrayList<int[]> path;
	private HashMap<Integer,Integer> wallCorners;
	
	
	public WallCreator(Point centerPoint,int size,int[][] map){
		
		path = new ArrayList<int[]>();
		wallCorners = new HashMap<Integer, Integer>();
		
//		int mx = pts.get(0).x;
//		int my = pts.get(0).y;
		Point nw = getCorner(new Point(centerPoint.x - size, centerPoint.y + size), map);
		Point ne = getCorner(new Point(centerPoint.x + size, centerPoint.y + size), map);
		Point sw = getCorner(new Point(centerPoint.x - size, centerPoint.y - size), map);
		Point se = getCorner(new Point(centerPoint.x + size, centerPoint.y - size), map);
		
		wallCorners.put(Point.GetUniqueNo(new int[]{nw.x,nw.y}),calculateSectionAngle(nw,ne,centerPoint));
		wallCorners.put(Point.GetUniqueNo(new int[]{ne.x,ne.y}),calculateSectionAngle(ne,se,centerPoint));
		wallCorners.put(Point.GetUniqueNo(new int[]{se.x,se.y}),calculateSectionAngle(se,sw,centerPoint));
		wallCorners.put(Point.GetUniqueNo(new int[]{sw.x,sw.y}),calculateSectionAngle(sw,nw,centerPoint));
		
		Point[] pts = new Point[]{nw,ne,se,sw};
		
		for(int p = 0; p < pts.length-1; p++){
			
			path.addAll(new Pathfinder(map,-1).
					getPath(pts[p].x,pts[p].y, pts[p+1].x, pts[p+1].y));
			path.remove(path.size()-1);
		}
		
		path.addAll(new Pathfinder(map,-1).
				getPath(pts[pts.length-1].x,pts[pts.length-1].y, pts[0].x, pts[0].y));
		path.remove(path.size()-1);
		
//		int pathNo = 2;
//		
//		for(int sx = mx - 20; sx < mx + 20;sx++){
//			for(int sy = my - 20; sy < my + 20; sy++){
//				
//				Point pt;
//				if((pt = isPoint(sx,sy,pts)) != null){
//					//System.out.print(pt.x + "|" + pt.y+ "|" + map[x][y] + " ");
//					System.out.print("Y ");
//					pathNo++;
//				}else if(isOnList(sx,sy,path)){
//					System.out.print("X ");
//				}else{
//					System.out.print(map[sx][sy] + " ");
//				}
//			}
//			
//			System.out.println();
//		}
		
	}
	
	public int isCorner(int x, int y){
		
		if(!wallCorners.containsKey(Point.GetUniqueNo(new int[]{x,y}))){
			
			return -1;
		
		}else{
			
			//System.out.println("Is Corner: " + wallCorners.get(Point.GetUniqueNo(new int[]{x,y})));
			return wallCorners.get(Point.GetUniqueNo(new int[]{x,y}));
		}
	}
	
	public boolean isWallCorner(int x, int y){
		
		return wallCorners.containsKey(new int[]{x,y});
	}
	
	public static Point getCorner(Point point,int[][] map){
		
		Point newPt = null;
		int[] lc = new int[]{-1,-1,-1,1,1,-1,0,1,1,0,1,1};
		
		for(int i = 0; i < 6 && newPt == null; i++){
			
			for(int l = 0; l < lc.length; l+=2){
				
				int nx = point.x + (lc[l]*i);
				int ny = point.y + (lc[l+1]*i);
				if(nx >= 0 && nx < map.length && ny >= 0 
						&& ny < map[0].length && map[nx][ny] == 0){
					newPt = new Point(new int[]{nx,ny});
				}
				
				if(i == 0){
					
					break;
				}
			}
		}
		
		return newPt;
	}
	
	public ArrayList<int[]> getWallRoute(){
		
		return path;
	}
	
	public static boolean isOnList(int x, int y,ArrayList<int[]> list){
	
		for(int l = 0; l < list.size(); l++){
			
			if(list.get(l)[0] == x && list.get(l)[1] == y){
				
				return true;
			}
		}
		
		return false;
	}
	
	public static Point isPoint(int x, int y, ArrayList<Point> pts) {
		// TODO Auto-generated method stub
		for(int l = 0; l < pts.size(); l++){
			
			if(pts.get(l).x == x && pts.get(l).y == y){
				
				return pts.get(l);
			}
		}
		
		return null;
	}
	
	private int calculateSectionAngle(Point one, Point two,Point center){
		
		Point avg = Point.Center(one,two);
		
		float dx = avg.x - center.x;
		float dy = avg.y - center.y;
		
		return (int)(Math.acos(dx / Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2))) 
				* (180.0f/Math.PI));	
	}
	
	
	public static void main(String[] args) {

		int[][] map = new int[][]{{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
		   
		 Point center = new Point(7,7);
		 int size = 3;
		 WallCreator creator = new WallCreator(center,size, map);
		 ArrayList<int[]> list = creator.getWallRoute();
		 
		 int pathNo = 2;
		 
		System.out.println(list.size() + " WallCreator");
		for(int x = 0; x < map.length; x++){
			for(int y = 0; y < map[0].length; y++){
			
//				Point pt;
//				if((pt = isPoint(x,y,pts)) != null){
//					//System.out.print(pt.x + "|" + pt.y+ "|" + map[x][y] + " ");
//					System.out.print("Y ");
//					pathNo++;
//				}else 
				
				if(Math.abs(center.x - x)  == size 
						&& Math.abs(center.y - y) == size){
					
					System.out.print("Y ");
					
				}else if(isOnList(x,y,list)){
					System.out.print("X ");
				}else{
					System.out.print(map[x][y] + " ");
				}
			}
			System.out.println();
		}
	}

	

}
