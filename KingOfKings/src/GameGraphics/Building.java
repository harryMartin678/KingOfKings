package GameGraphics;

import java.util.ArrayList;

public class Building {
	
	private float x;
	private float y;
	private String name;
	
	
	public Building(float x, float y, String name){
		
		this.x = x;
		this.y = y;
		
		this.name = name;
	}
	
	public void setXY(float x, float y){
		
		this.y = y;
		this.x = x;
	}
	
	public float getX(){
		
		return x;
	}
	
	public float getY(){
		
		return y;
	}
	
	public String getName(){
		
		return name;
	}


}
