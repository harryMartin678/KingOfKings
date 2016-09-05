package Util;

public class Vector3{
	
	
	public Vector3(){
		
		
	}
	
	public Vector3(float x, float y, float z){
		
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float x;
	public float y;
	public float z;
	
	public Vector3 Add(Vector3 a){
		
		return new Vector3(a.x + this.x, a.y + this.y, a.z + this.z);
	}
	
	public Vector3 Subtract(Vector3 s){
		
		return new Vector3(s.x - this.x, s.y - this.y,s.z - this.z);
	}
	
	public Vector3 Multiply(float m){
		
		return new Vector3(this.x * m, this.y * m, this.z * m);
	}
	
	public float Dot(Vector3 b){
		
		return this.x * b.x + this.y * b.y + this.z * b.z;
	}
	
	public void SetValue(Vector3 vec){
		
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
	}
	
	public double[][] getMatrix(){
		
		return new double[][]{{x,y,z}};
	}
	
	public Matrix toMatrix(){
		
		return new Matrix(this.getMatrix());
	}
	
	public void Normalize(){
		
		float length = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
		
		x = x / length;
		y = y / length;
		z = z / length;
	}
	
	public void Print(String name){
		
		System.out.println(name + " x:" + x + " y:" + y + " z:" + z + " MousePicker");
	}
}
