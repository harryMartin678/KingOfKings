package Util;

public class Point {
	
	public int x;
	public int y;
	
	public Point(int x, int y){
		
		this.x = x;
		this.y = y;
	}
	
	public Point(int[] pt){
		
		this.x = pt[0];
		this.y = pt[1];
	}

}
