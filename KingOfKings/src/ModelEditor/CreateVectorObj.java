package ModelEditor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
		
		newParts[newParts.length-2] = "ChangedModels";
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
	
	/*public static void main(String[] args) {
		
		new CreateVectorObj("bin/ModelEditor/stockpilexd", 3);
	}*/
	
	public static void copyFile(File sourceFile, File destFile) throws IOException {
	    if(!destFile.exists()) {
	    	System.out.println(destFile.getAbsolutePath());
	        destFile.createNewFile();
	    }

	    FileChannel source = null;
	    FileChannel destination = null;

	    try {
	        source = new FileInputStream(sourceFile).getChannel();
	        destination = new FileOutputStream(destFile).getChannel();
	        destination.transferFrom(source, 0, source.size());
	    }
	    finally {
	        if(source != null) {
	            source.close();
	        }
	        if(destination != null) {
	            destination.close();
	        }
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
		
		String[] objs = new String[]{"Archer","Archerxd","Archerxw","Archerxa","archerytower","archerytowerxd",
				"ballistictower","ballistictowerxd","castle","castlexd","farm","farmxd","Giant","Giantxd",
				"Giantxd","Giantxw","Giantxa","GiantLiar","GiantLiarxd","Hound","Houndxd","Houndxw","Houndxa",
				"HoundPit","HoundPitxd","mine","minexd","royalPalace","Spearman",
				"Spearmanxd","Spearmanxa","Spearmanxw","Spearyard","Spearyardxd","stockpile","stockpilexd",
				"SwordsMan","SwordsManxd","SwordsManxw","SwordsManxa","SwordsSmith","SwordsSmithxd","wall",
				"wallxd","wallTower","wallTowerxd","worker","workerxd","workerxa","workerxw","tree","gold",
				"rocks","site"};
		
		for(int f = 0; f < objs.length; f++){
			
			assert(new File("bin/ModelEditor/" + objs[f] + ".obj").exists());
			
		}
		
		for(int b = 0; b < objs.length; b++){
			
			if(objs[b].contains("x")){
				new CreateVectorObj("bin/ModelEditor/Models/" + objs[b],3);
			}else{
				new EditModel("bin/ModelEditor/Models/" + objs[b]);
			}
		}
		
		for(int m = 0; m < objs.length; m++){
			
			File src = new File("bin/ModelEditor/Models/" + objs[m].replace("x", "1") + ".mtl");
			File target = new File("bin/ModelEditor/Models/ChangedModels/" + objs[m].replace("x", "1") + ".mtl");
			copyFile(src,target);
			//Files.copy(src.toPath(), target.toPath(),StandardCopyOption.REPLACE_EXISTING);
		}
	}

}
