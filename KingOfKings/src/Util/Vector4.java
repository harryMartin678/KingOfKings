package Util;

public class Vector4{
	
	public Vector3 xyz;
	public float h;
	
	public Vector4(){
		
		xyz = new Vector3();
	}
	
	public Vector4(float x, float y, float z, float h){
		
		xyz = new Vector3(x, y, z);
		this.h = h;
	}
	
	public double[][] getMatrixVertical(){
		
		return new double[][]{{xyz.x},{xyz.y},{xyz.z},{h}};
	}
	
}
