package GameGraphics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class LoadVector {
	
	private HashMap<Integer,Keyframes> vertexOffsets;
	
	public LoadVector(String vecFile) throws IOException{
		
		vertexOffsets = new HashMap<Integer,Keyframes>();
		File vec = new File(vecFile);
		BufferedReader reader = new BufferedReader(new FileReader(vec));
		
		String line;
		
		while((line = reader.readLine()) != null){
			
			Keyframes frame = new Keyframes(line);
			
			vertexOffsets.put(frame.getIndex(), frame);
		}
	}
	
	public HashMap<Integer,Keyframes> getKeyFrames(){
		
		return vertexOffsets;
	}

}
