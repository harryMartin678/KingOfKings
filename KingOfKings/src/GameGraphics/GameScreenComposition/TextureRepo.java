package GameGraphics.GameScreenComposition;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class TextureRepo {

	private HashMap<String,Integer> textures;
	
	public TextureRepo(){
		
		textures = new HashMap<String,Integer>();
	}
	
	public void LoadTextures(String[] textureFiles){
		
		for(int t = 0; t < textureFiles.length; t++){
			
			try{
				File file = new File("ImageBank/"+textureFiles[t]);

				Texture tex = TextureIO.newTexture(file,true);
				textures.put(textureFiles[t],tex.getTextureObject());
			}catch(IOException e){
				
				e.printStackTrace();
				
			}
			
		}
	}
	
	public int getTexture(String textureName){
		return textures.get(textureName);
	}
}
