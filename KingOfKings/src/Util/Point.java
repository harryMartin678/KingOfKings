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

	public void printPoint(String place) {
		// TODO Auto-generated method stub
		System.out.println(x + " " + y + " " + place);
	}

	public int[] ToArray() {
		// TODO Auto-generated method stub
		return new int[]{x,y};
	}

	public static Point Center(Point one, Point two) {
		// TODO Auto-generated method stub
		return new Point((one.x + two.x)/2, (one.y + two.y)/2);
	}
	
	public static int GetUniqueNo(int[] pos){
		
		return (int)((0.5 * (pos[0] + pos[1]) * (pos[0] + pos[1] + 1)) + pos[1]);
	}
	
	public static void main(String[] args) {
		
		System.out.println(Point.GetUniqueNo(new int[]{116,120}) +
				" " + Point.GetUniqueNo(new int[]{120,116}));
 	}

}
