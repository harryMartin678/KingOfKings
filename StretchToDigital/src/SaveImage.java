import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SaveImage {
	
	public SaveImage(String filePath,int[][] pixels) throws IOException{
		
		Image image = getImageFromArray(pixels);
		
		File saveTo = new File(filePath);
		ImageIO.write((RenderedImage) image, "jpeg", saveTo);
		
	}
	
	public Image getImageFromArray(int[][] pixels) {
		
		int width = pixels.length;
		int height = pixels[0].length;
		
        BufferedImage image = new BufferedImage(width, height,
        		BufferedImage.TYPE_INT_RGB);
        
        for(int x = 0; x < width; x++){
        	for(int y = 0; y < height; y++){
        		
        		image.setRGB(x, y, pixels[x][y]);
        	}
        }
        
        com.jhlabs.image.CircleFilter filter = new com.jhlabs.image.CircleFilter();
		

        return filter.filter(image, 
        		new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB));
    }

}
