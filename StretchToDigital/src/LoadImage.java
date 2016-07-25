import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class LoadImage {
	
	private String filePath;

	public LoadImage(String filePath){
		
		this.filePath = filePath;
	}
	
	public int[][] GetImage() throws IOException{
		
		BufferedImage bufferImg = ImageIO.read(new File(filePath));
		
		int[][] pixels = new int[bufferImg.getWidth()][bufferImg.getHeight()];
		
		for(int x = 0; x < bufferImg.getWidth(); x++){
			for(int y = 0; y < bufferImg.getHeight(); y++){
				
				pixels[x][y] = bufferImg.getRGB(x, y);
			}
		}
		
		return pixels;
		
	}
	
	
}
