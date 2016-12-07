package Util;

import java.util.ArrayList;

public class Vector {

	private Point start;
	private Point direction;
	private double maxT;
	
	public Vector(Point one, Point two){
		
		this.start = one;
		direction = minusPoints(two, one);
		direction.normalize();
		
		if(direction.x == 0){
			
			maxT = minusPoints(one,two).y / direction.y;
			
		}else{
			
			maxT = minusPoints(one,two).x / direction.x;
		}	
		
	}
	
	private Point minusPoints(Point one, Point two){
		
		return new Point(one.x - two.x,one.y - two.y);
	}
	
	public double intersects(Vector vec){
		
		double xT = (vec.getStartX() - start.x) / (double)direction.x;
		double yT = (vec.getStartY() - start.y) / (double)direction.y;
		
		if(xT == yT && xT >= 0 && xT <= maxT){
			
			return xT;
		
		}else{
			
			return -1.0;
		}
	}
	
	public int[] getValueAtTInt(double t){
		
		return new int[]{
			(int)(start.x + direction.x * t),
			(int)(start.y + direction.y * t)
		};
	}
	
	private int sign(double value){
		
		if(value < 0){
			
			return 1;
		
		}else{
			
			return -1;
		}
	}
	
	public ArrayList<int[]> getStages(){
		
		ArrayList<int[]> points = new ArrayList<int[]>();
		for(int t = 0; Math.abs(t) <= Math.abs(maxT); t+= sign(maxT)){
			
			points.add(getValueAtTInt(t));
		}
		
		return points;
	}

	private double getStartY() {
		// TODO Auto-generated method stub
		return start.y;
	}

	private double getStartX() {
		// TODO Auto-generated method stub
		return start.x;
	}

	public Point getLocation(double t) {
		// TODO Auto-generated method stub
		return new Point((int)(start.x + direction.x*t),
				(int)(start.y + direction.y*t));
	}

}
