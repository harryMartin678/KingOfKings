package GameGraphics;

import java.util.ArrayList;

public class Building {
	
	private float x;
	private float y;
	private String name;
	private int buildingNo;
	
	
	public Building(float x, float y, String name,int buildingNo){
		
		this.x = x;
		this.y = y;
		this.buildingNo = buildingNo;
		
		this.name = name;
	}
	
	public int getBuildingNo(){
		
		return buildingNo;
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
