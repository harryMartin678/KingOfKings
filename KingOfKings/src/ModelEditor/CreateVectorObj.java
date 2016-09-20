package ModelEditor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import ModelEditor.EditModel.Vertex;

public class CreateVectorObj {
	
	public CreateVectorObj(String filename,int fileNo){
		
		ArrayList<ArrayList<Vertex>> keyFrame = new ArrayList<ArrayList<Vertex>>();
		for(int f = 1; f <= fileNo; f++){
			
			keyFrame.add(new EditModel(
					filename.replace('x', new Integer(f).toString().charAt(0))).getVertices());
		}
		
		ArrayList<Vector> vectors = new ArrayList<Vector>();
		
		for(int k = 0; k < keyFrame.size()-1; k++){
			
			for(int f = 0; f < keyFrame.get(k).size(); f++){
				
				if(vectors.size() <= f){
					
					vectors.add(new Vector());
					vectors.get(vectors.size()-1).lineNo = f;
				}
				
				vectors.get(f).addVector(keyFrame.get(k).get(f).x, 
						keyFrame.get(k+1).get(f).x,keyFrame.get(k).get(f).y, 
						keyFrame.get(k+1).get(f).y,keyFrame.get(k).get(f).z, 
						keyFrame.get(k+1).get(f).z);
			}
		}
		
		String toWrite = "";
		
		for(int w = 0; w < vectors.size(); w++){
			
			toWrite += vectors.get(w).ToLine();
		}
		
		//System.out.println(toWrite);
		
		String[] parts = filename.split("/");
		String[] newParts = new String[parts.length+1];
		
		for(int i = 0; i < parts.length-1; i++){
			
			newParts[i] = parts[i];
		}
		
		newParts[newParts.length-2] = "converted";
		newParts[newParts.length-1] = parts[parts.length-1];
		
		File file = new File(String.join("/", newParts) + ".objvec");
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(toWrite);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}
	
	public class Vector{
		
		public ArrayList<Double> xVec;
		public ArrayList<Double> yVec;
		public ArrayList<Double> zVec;
		public int lineNo;
		
		public Vector(){
			
			xVec = new ArrayList<Double>();
			yVec = new ArrayList<Double>();
			zVec = new ArrayList<Double>();
		}
		
		public String ToLine() {
			// TODO Auto-generated method stub
			String line = lineNo + " ";
			for(int vec = 0; vec < xVec.size(); vec++){
				
				line += xVec.get(vec) + "," + yVec.get(vec) + "," + zVec.get(vec) + "/";
			}
			
			return line + "\n";
		}

		public void addVector(double x1,double x2,double y1, double y2, double z1,double z2){
			
			if(!(x2 - x1 == 0 && y2-y1 == 0 && z2 - z1 == 0)){

				xVec.add(x2 - x1);
				yVec.add(y2 - y1);
				zVec.add(z2 - z1);
			}
			
		}
		
		public void printVector(){
			
			for(int v = 0; v < xVec.size(); v++){
				
				System.out.println(xVec.get(v) + " " + yVec.get(v) + " "
						+ zVec.get(v));
			}
		}
	}
	
	public static void main(String[] args) {
		
		new CreateVectorObj("bin/ModelEditor/wallTowerxd", 2);
	}

}
