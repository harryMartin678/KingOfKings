package IntermediateAI;

public class Node {
	
	private int h;
	private int g;
	private int x;
	private int y;
	private Node parent;
	private boolean foundHigher = false;
	
	public Node(int x, int y){
		
		this.x = x;
		this.y = y;
		g = 0;
	}
	
	public Node(int x, int y, Node parent){
		
		this.x = x;
		this.y = y;
		this.parent = parent;
	}
	
	public void calculateG(){
		
		g = parent.getG() + 1;
	}
	
	public void calculateH(int targetX, int targetY){
		
		//h = (int) Math.sqrt(Math.pow((double) (targetX - x),2 ) + Math.pow((double) (targetY - y), 2));
		h = Math.abs(targetX - x) + Math.abs(targetY - y);
	}
	
	public int getG(){
		
		return g;
	}
	
	public int getH(){
		
		return h;
	}
	
	public int getF(){
		
		return g + h;
	}
	
	public int getX(){
		
		return x;
	}
	
	public int getY(){
		
		return y;
	}
	
	public void foundHigher(){
		
		foundHigher = true;
	}
	
	public boolean wasFindHigher(){
		
		return foundHigher;
	}
	
	
	public Node getParent(){
		
		return parent;
	}
	
	
	public void changeParent(Node parent){
		
		this.parent = parent;
		this.calculateG();
	}
	
	

}
