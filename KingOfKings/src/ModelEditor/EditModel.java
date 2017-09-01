package ModelEditor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static java.nio.file.StandardCopyOption.*;
import java.util.ArrayList;

import ModelEditor.EditModel.Vertex;

public class EditModel {
	
	private ArrayList<Vertex> Vertices;
	
	public EditModel(String filename){
		
		//ArrayList<Vertex> Vertices = null;
		Vertices = null;
		ArrayList<String> OtherLines = new ArrayList<String>();
		
		System.out.println(filename);
		
		File file = new File(filename + ".obj");
		BufferedReader reader = null;
		try {
			 reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Vertices = GetVertices(reader,OtherLines);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		for(int v = 0; v < 10; v++){
//			
//			System.out.println(Vertices.get(v));
//		}
//		System.out.println("GAPPP");
//		
		Vertex center = getCenter(Vertices);
//		
//		System.out.println(center);
//		System.out.println("GAPPP");
		CenterModel(Vertices,center);
		GetUnitSize(Vertices,center);
		Vertex negVertex = getMostNegativeDirection(Vertices);
		//Vertex posVertex = getMostPositiveDirection(Vertices);
		//MoveModel(Vertices, -(posVertex.x + negVertex.x)/2.0f, (posVertex.y + negVertex.y)/2.0f, 
				//-negVertex.z);
		center = getCenter(Vertices);
		//MoveModel(Vertices,-center.x,-center.y,-center.z);
		RotateModelXAxis(Vertices,90.0f);
		//MoveModel(Vertices,center.x,center.y,center.z);
		//MultiplyUnitSize(Vertices,2.0);
		//MoveModel(Vertices,0.0f,0.0f,-negVertex.z);
		
		String toWrite = GetObjString(Vertices, OtherLines);
		
		String[] parts = filename.split("/");
		String[] newParts = new String[parts.length+1];
		
		for(int i = 0; i < parts.length-1; i++){
			
			newParts[i] = parts[i];
		}
		
		newParts[newParts.length-2] = "ChangedModels";
		newParts[newParts.length-1] = parts[parts.length-1];
		
		//allVertices = Vertices;
		
		File newFile = new File(String.join("/", newParts) + ".obj");
		
		System.out.println(newFile.getAbsolutePath());
		
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newFile)));
			writer.write(toWrite);
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		for(int v = 0; v < Vertices.size(); v++){
//			
//			System.out.println(Vertices.get(v));
//		}
		
		
		
	}
	
	private void RotateModelXAxis(ArrayList<Vertex> vertices,float angle){
		
		angle = (float) ((Math.PI* angle)/180);
		Vertex sameVertex = new Vertex();
		
		for(int v = 0; v < vertices.size(); v++){
			
			sameVertex.x = vertices.get(v).x;
			sameVertex.y = vertices.get(v).y;
			sameVertex.z = vertices.get(v).z;
			
			vertices.get(v).y = (float) (sameVertex.y*Math.cos(angle) - sameVertex.z*Math.sin(angle));
			vertices.get(v).z = (float) (sameVertex.y*Math.sin(angle) + sameVertex.z*Math.cos(angle));
		}
	}
	
	private void MoveModel(ArrayList<Vertex> Vertices,float x, float y, float z){
		
		for(int v = 0; v < Vertices.size(); v++){
			
			Vertices.get(v).offset(x,y,z);
		}
	}
	
	private Vertex getMostPositiveDirection(ArrayList<Vertex> Vertices){
		
		Vertex vertex = new Vertex();
		vertex.x = Float.MIN_VALUE;
		vertex.y = Float.MIN_VALUE;
		vertex.z = Float.MIN_VALUE;
		
		for(int v = 0; v < Vertices.size(); v++){
			
			vertex.x = Math.max(Vertices.get(v).x,vertex.x);
			vertex.y = Math.max(Vertices.get(v).y,vertex.y);
			vertex.z = Math.max(Vertices.get(v).z,vertex.z);
		}
		
		return vertex;
	}
	
	private Vertex getMostNegativeDirection(ArrayList<Vertex> Vertices){
		
		Vertex vertex = new Vertex();
		vertex.x = Float.MAX_VALUE;
		vertex.y = Float.MAX_VALUE;
		vertex.z = Float.MAX_VALUE;
		
		for(int v = 0; v < Vertices.size(); v++){
			
			vertex.x = Math.min(Vertices.get(v).x,vertex.x);
			vertex.y = Math.min(Vertices.get(v).y,vertex.y);
			vertex.z = Math.min(Vertices.get(v).z,vertex.z);
		}
		
		return vertex;
	}
	
	private String GetObjString(ArrayList<Vertex> Vertices, ArrayList<String> OtherLines){
		
		String file = "";
		int lineNo = 0;
		int vertexNo = 0;
		int OtherNo = 0;
		
		while(vertexNo < Vertices.size() || OtherNo < OtherLines.size()){
			
			if(vertexNo < Vertices.size() && Vertices.get(vertexNo).lineNo == lineNo){
				
				file += Vertices.get(vertexNo).ToLine();
				vertexNo++;
			}else if(OtherNo < OtherLines.size()){
				
				file += OtherLines.get(OtherNo) + "\n";
				OtherNo++;
			}
			
			lineNo++;
			
		}
		
		return file;
	}
	
	private void GetUnitSize(ArrayList<Vertex> vertices,Vertex center) {
		// TODO Auto-generated method stub
		Vertex furthestPoint = GetFurthestPoint(vertices,center);
		
		float mag = (float)Math.sqrt(Math.pow(furthestPoint.x, 2) +
				Math.pow(furthestPoint.y, 2) + Math.pow(furthestPoint.z, 2));
		
		for(int v = 0; v < vertices.size(); v++){
			
			vertices.get(v).divideBy(mag);
		}
	}
	
	private void MultiplyUnitSize(ArrayList<Vertex> vertices,double size){
		
		for(int v = 0; v < vertices.size(); v++){
			
			vertices.get(v).multipleBy(size);
		}
	}

	private Vertex GetFurthestPoint(ArrayList<Vertex> vertices,Vertex center) {
		// TODO Auto-generated method stub
		float furthest = Float.MIN_VALUE;
		Vertex furVertex = null;
		
		for(int f = 0; f < vertices.size(); f++){
			
			float dist;
			if((dist = vertices.get(f).distanceTo(center)) > furthest){
				
				furthest = dist;
				furVertex = vertices.get(f);
			}
		}
		
		return furVertex;
	}

	private void CenterModel(ArrayList<Vertex> vertices, Vertex center) {
		// TODO Auto-generated method stub
		for(int v = 0; v < vertices.size(); v++){
			
			vertices.get(v).MinusVertex(center);
		}
	}

	private Vertex getCenter(ArrayList<Vertex> vertices) {
		// TODO Auto-generated method stub
		Vertex avgVertex = new Vertex();
		
		for(int v = 0; v < vertices.size(); v++){
			
			avgVertex.x += vertices.get(v).x;
			avgVertex.y += vertices.get(v).y;
			avgVertex.z += vertices.get(v).z;
		}
		
		avgVertex.x = avgVertex.x / vertices.size();
		avgVertex.y = avgVertex.y / vertices.size();
		avgVertex.z = avgVertex.z / vertices.size();
		
		return avgVertex;
	}

	public ArrayList<Vertex> GetVertices(BufferedReader reader,
			ArrayList<String> OtherLines) throws IOException{
		
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		String line;
		int lineNo = 0;
		while((line = reader.readLine()) != null){

			if(line.substring(0, 2).equals("v ")){
				vertices.add(new Vertex(line,lineNo));
			}else{
				OtherLines.add(line);
			}
			lineNo++;
		}
		
		return vertices;
	}
	
	public ArrayList<Vertex> getVertices() {
		// TODO Auto-generated method stub
		return Vertices;
	}
	
	public class Vertex{
		
		public float x;
		public float y;
		public float z;
		public int lineNo;
		
		public Vertex(String line,int lineNo){
			
			ParseLine(line);
			this.lineNo = lineNo;
		}
		
		public void multipleBy(double size) {
			// TODO Auto-generated method stub
			this.x *= size;
			this.y *= size;
			this.z *= size;
		}

		public void offset(float dx, float dy, float dz) {
			// TODO Auto-generated method stub
			//werid stuff here
			this.x += dx;
			this.y += dy;
			this.z += dz;
		}

		public void divideBy(float mag) {
			// TODO Auto-generated method stub
			this.x /= mag;
			this.y /= mag;
			this.z /= mag;
		}

		public Vertex() {
			// TODO Auto-generated constructor stub
		}

		public void ParseLine(String line){
			
			String[] lineSplit = line.split(" ");
			
			x = Float.parseFloat(lineSplit[1]);
			y = Float.parseFloat(lineSplit[2]);
			z = Float.parseFloat(lineSplit[3]);
		}
		
		public String ToLine(){
			
			return "v " + x + " " + y + " " + z + "\n";
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "{ "+x + " " + y + " " + z + " }";
		}
		
		public void MinusVertex(Vertex offset){
			
			this.x -= offset.x;
			this.y -= offset.y;
			this.z -= offset.z;
		}
		
		public float distanceTo(Vertex vertex){
			
			return (float)Math.sqrt(Math.pow(vertex.x - this.x, 2)
					+ Math.pow(vertex.y - this.y, 2) 
					+ Math.pow(vertex.z - this.z, 2));
		}
		
	}

	
	
	public static void main(String[] args) throws IOException {
		
		/*String[] objs = new String[]{"worker","worker1a","worker1d","Archer1","Archer1a"
				,"Archer1d","Archer1w","archerytower","archerytower1d","ballistictower",
				"ballistictower1d","farm","mine","Giant","Giant1a","Giant1d","Giant1w"
				,"GiantLiar","GiantLiar1d","Hound","Hound1a","Hound1d","Hound1w",
				"HoundPit","HoundPit1d","royalPalace","Spearman","Spearman1a","Spearman1d",
				"Spearman1w","Spearyard","Spearyard1d","stockpile","stockpile1d","SwordsMan",
				"SwordsMana1","SwordsMand1","SwordsManw1","SwordsSmith","SwordsSmith1d"};*/
		
		String[] objs = new String[]{"Archer","Archer1d","Archer1w","Archer1a","archerytower","archerytower1d",
				"ballistictower","ballistictower1d","castle","castle1d","farm","farm1d","Giant","Giant1d",
				"Giant1d","Giant1w","Giant1a","GiantLiar","GiantLiar1d","Hound","Hound1d","Hound1w","Hound1a",
				"HoundPit","HoundPit1d","HoundPit1w","HoundPit1a","mine","mine1d","royalPalace","Spearman",
				"Spearman1d","Spearman1a","Spearman1w","Spearyard","Spearyard1d","stockpile","stockpile1d",
				"SwordsMan","SwordsMan1d","SwordsMan1w","SwordsMan1a","SwordsSmith","SwordsSmith1d","wall",
				"wall1d","wallTower","wallTower1d","worker","worker1d","worker1a","worker1w"};
		
		for(int f = 0; f < objs.length; f++){
			
			assert(new File("bin/ModelEditor/" + objs[f] + ".obj").exists());
			
		}
		
		for(int b = 0; b < objs.length; b++){
			
			new EditModel("bin/ModelEditor/Models/" + objs[b] + ".obj");
		}
		
		for(int m = 0; m < objs.length; m++){
			
			File src = new File("bin/ModelEditor/Models/" + objs[m] + ".mtl");
			File target = new File("bin/ModelEditor/ChangedModels/" + objs[m] + ".mtl");
			Files.copy(src.toPath(), target.toPath(),StandardCopyOption.REPLACE_EXISTING);
		}
		//new EditModel("bin/ModelEditor/wallTower");
	}

}
