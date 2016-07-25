import java.awt.Color;
import java.io.IOException;

public class StretchToDigital {

	private final String FilePath = "C:\\Users\\harry\\Documents\\Non-code material\\"+
			"King Of Kings Docs\\Texture\\ScannedWoodTexture";
	
	
	public StretchToDigital() throws IOException{
		
		LoadImage file = new LoadImage(FilePath+".jpeg");
		int[][] pixels = SetBackground(file.GetImage(),new Color(139, 69,19),new Color(130, 82, 1)
				,new Color(101, 67, 33));
		
		//pixels = GaussianFilter(pixels);
		
		//ApplyGFilter(pixels, 4);
		
		//System.out.println(pixels.length + " " + pixels[0].length);
		//PrintPixels(file.GetImage(),false);
		
		
		SaveImage saveFile = new SaveImage(FilePath + "Processed.jpeg", 
				pixels);
	}
	
	private int[][] SetBackground(int[][] pixels,Color backColor,Color fromColor, Color toColor){
		
		double minThreshold = 2500000; 
		
		for(int x = 0; x < pixels.length; x++){
			for(int y = 0; y < pixels[x].length; y++){
				
				//System.out.println(Math.abs(pixels[x][y]));
				if(Math.abs(pixels[x][y]) < minThreshold){
					
					pixels[x][y] = backColor.getRGB();
				
				}else{
					
					double percentage =  (Math.abs(pixels[x][y]) - minThreshold)/14277216.0;

					//System.out.println(percentage);
					Color pxColor = new Color(
							(int)(((toColor.getRed() - fromColor.getRed()) +
									fromColor.getRed()) * percentage),
							(int)(((toColor.getGreen() - fromColor.getGreen()) 
									+ fromColor.getGreen()) * percentage),
							(int)(((toColor.getBlue() - fromColor.getBlue()) 
									+ fromColor.getBlue()) * percentage));
					
					pixels[x][y] = pxColor.getRGB();
				}
			}
		}
		
		return pixels;
		
	}
	
	private int[][] SmoothImage(int[][] pixels){
		
		int[][] smooth = new int[pixels.length][pixels[0].length];
		
		for(int x = 0; x < pixels.length; x++){
			for(int y = 0; y < pixels[x].length; y++){
				
				smooth[x][y] = NearestNeighbour(pixels,x,y);
			}
		}
		
		return smooth;
		
	}
	
	private void ApplyGFilter(int[][] pixels,int Radius){
		
		for(int x = 0; x < pixels.length; x += Radius){
			for(int y = 0; y < pixels.length; y += Radius){
				
				GaussianFilter(pixels,x,y,Radius,Radius);
			}
		}
		
	}
	
	private int[][] CopyPixels(int[][] pixels){
		
		int[][] copy = new int[pixels.length][pixels[0].length];
		
		for(int x = 0; x < copy.length; x++){
			for(int y = 0; y < copy[x].length; y++){
				
				copy[x][y] = pixels[x][y];
			}
		}
		
		return copy;
	}
	
	private void GaussianFilter(int[][] pixels,int x, int y, int SizeX,int SizeY){
		
		double cutoff = 50;
	    double dc_level = 255;
	    
        int xcenter = (SizeX/2)+1;
        int ycenter = (SizeY/2)+1;
        
        for (int cy = x - SizeY; cy < x + SizeY; cy++){
            for (int cx = y - SizeY; cx < y + SizeX; cx++){
            	
            	if(cx < 0 || cx >= pixels.length || cy < 0 || cy >= pixels[0].length){
    				
    				continue;
    			}
            	
                double distance = Math.sqrt(Math.abs(x-xcenter)*Math.abs(x-xcenter)
                		+Math.abs(y-ycenter)*Math.abs(y-ycenter));
                pixels[cx][cy] = (int) (dc_level*Math.exp((-1*distance*distance)/
                		(1.442695*cutoff*cutoff)));

            }
        }

	}
	
	private int NearestNeighbour(int[][] pixels,int x, int y){
		
		int[] lc = new int[]{1,0,0,1,1,1,-1,1,1,-1,-1,-1};
		int closest = Integer.MIN_VALUE;
		int closestIndex = 0;
		
		for(int a = 0; a < lc.length; a+=2){
			
			int cx = lc[a] + x;
			int cy = lc[a+1] + y;
			
			if(cx < 0 || cx >= pixels.length || cy < 0 || cy >= pixels[0].length){
				
				continue;
			}
			
			int distance = distance(pixels[x][y],pixels[cx][cy]);
			
			if(distance > closest){
				
				closest = distance;
				closestIndex = a;
			}
		}
		
		int neigh = pixels[lc[closestIndex]+x][lc[closestIndex+1] + y];
		
		return Interpolate(neigh,pixels[x][y]);
		
		
	}
	
	private int distance(int color1I,int color2I){
		
		Color colorOne = new Color(color1I);
		Color colorTwo = new Color(color2I);
		
		return (int)Math.sqrt(Math.pow(colorOne.getRed() - colorTwo.getRed(),2) 
				+ Math.pow(colorOne.getGreen() - colorTwo.getGreen(), 2) +
				Math.pow(colorOne.getBlue() - colorTwo.getBlue(), 2));
	}
	
	private int Interpolate(int colorOneI,int colorTwoI){
		
		Color colorOne = new Color(colorOneI);
		Color colorTwo = new Color(colorTwoI);
		
		return new Color((colorOne.getRed() + colorTwo.getRed())/2,
				(colorOne.getGreen() + colorTwo.getGreen())/2,
				(colorOne.getBlue() + colorTwo.getBlue())/2).getRGB();
	}
	
	private int AveragePixel(int[][] pixels,int x, int y){
		
		double avg = 0;
		
		int[] lc = new int[]{1,0,0,1,1,1,-1,1,1,-1,-1,-1};
		
		int[][] pairs = new int[lc.length/2][2];
		
		int total = 0;
		
		for(int a = 0; a < lc.length; a+=2){
			
			int cx = lc[a] + x;
			int cy = lc[a+1] + y;
			
			if(cx < 0 || cx >= pixels.length || cy < 0 || cy >= pixels[0].length){
				
				continue;
			}
			int dist = Math.abs(pixels[x][y] - pixels[cx][cy]);
			pairs[a/2] = new int[]{pixels[cx][cy],dist};
			total += dist;
			//avg += pixels[cx][cy] * (1.0/(pixels[x][y] - pixels[cx][cy]));
		}
		
		for(int p = 0; p < pairs.length; p++){
			
			avg += pairs[p][0] * (total - pairs[p][1]);
		}
	
		if(total == 0){
			
			return pixels[x][y];
		}
		
		return (int) (avg + (pixels[x][y] * total)) / (2 * total);
	}
	
	
	private void PrintPixels(int[][] pixels,boolean hex){
		
		for(int x = 0; x < pixels.length; x++){
			for(int y = 0; y < pixels[x].length; y++){
				
				if(hex){
					System.out.print(Integer.toHexString(pixels[x][y]) + " ");
				}else{
					System.out.print(pixels[x][y]);
				}
				
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		
		try {
			new StretchToDigital();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
