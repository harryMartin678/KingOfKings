package GameGraphics;

import java.util.ArrayList;

public class Keyframes {
	
	private ArrayList<float[]> keyframes;
	private int index;
	
	public Keyframes(String keyFrameLine){
		
		 
		String[] frames = keyFrameLine.split("/");
		index = Integer.parseInt(frames[0].split(" ")[0]);
		frames[0] = frames[0].split(" ")[1];
		keyframes = new ArrayList<float[]>();
		
		for(int f = 0; f < frames.length; f++){
			
			String[] vertex = frames[f].split(",");
			keyframes.add(ToFloatArray(vertex));
		}
	}
	
	private float[] ToFloatArray(String[] strArray) {
		// TODO Auto-generated method stub
		float[] array = new float[strArray.length];
		
		for(int f = 0; f < array.length; f++){
			
			array[f] = Float.parseFloat(strArray[f]);
		}
		
		return array;
	}

	public void PrintKeyframes(){
		
		for(int f = 0; f < keyframes.size(); f++){
			
//			System.out.println(keyframes.get(f)[0] + " " + keyframes.get(f)[1]
//					+ " " + keyframes.get(f)[2]);
			for(int v = 0; v < keyframes.get(f).length; v++){
				
				System.out.print(keyframes.get(f)[v] + " ");
			}
			
			System.out.println();
		}
	}
	
	public float[] GetOffSet(int animNo,String filename){
		
		int segSize = 100 / keyframes.size();
		
		int chosenKf = (int)(((float)animNo / 100.0) * keyframes.size());
		int offset = animNo - (chosenKf*segSize); 
		float per = (float)offset / (float)segSize;
		
		if(chosenKf >= keyframes.size()){
			
			System.out.println(filename + " Keyframes");
			System.out.println(animNo + " Keyframe");
		}
		
		if(keyframes.get(chosenKf).length < 3){
			
			System.out.println(filename + " Keyframes"); 
			System.out.println(keyframes.get(chosenKf).length + " " + chosenKf + " Keyframes");
			PrintKeyframes();
		}
		 
		return new float[]{keyframes.get(chosenKf)[0] * per, 
				keyframes.get(chosenKf)[1] * per,
				keyframes.get(chosenKf)[2] * per};
	}

	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}
	
//	public static void main(String[] args) {
//		
//		Keyframes frames = new Keyframes("9 -0.019194722175598145,0.010722935199737549,"
//				+"-0.0016759932041168213/-0.01783524453639984,0.0681624710559845,"
//				+ "8.789300918579102E-4/");
//		
//		frames.PrintKeyframes();
//		
//		float[] offset = frames.GetOffSet(49);
//		
//		System.out.println("/////////////////////////");
//		System.out.println(offset[0] + " " + offset[1] + " " + offset[2]);
//	}
	

}
