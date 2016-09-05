package GameGraphics.Menu;

import java.nio.FloatBuffer;

import com.jogamp.opengl.GL2;

import GameGraphics.GameScreenComposition.Display;
import GameGraphics.GameScreenComposition.IComBuildingListDisplay;
import GameGraphics.GameScreenComposition.IComUnitListDisplay;
import GameGraphics.GameScreenComposition.TextureRepo;

public class Minimap {
	
	private float CenterX;
	private float CenterY;
	private float SizeX;
	private float SizeY;
	
	public Minimap(float CenterX, float CenterY,float SizeX,float SizeY){
		
		this.CenterX = CenterX;
		this.CenterY = CenterY;
		this.SizeX = SizeX;
		this.SizeY = SizeY;
	}
	
	public void DrawMinimap(GL2 draw,int ScreenWidth,int ScreenHeight,
			IComBuildingListDisplay buildings,IComUnitListDisplay units
			,int mapWidth,int mapHeight,int myPlayer,int FRAME_X_SIZE,int FRAME_Y_SIZE,
			int FrameX,int FrameY,TextureRepo textures){

		//System.out.println(FRAME_X_SIZE + " " + FRAME_Y_SIZE + " MiniMap");
		new Rectangle().DrawWithoutCreation(draw, ScreenWidth, ScreenHeight, 
				CenterX, CenterY, SizeX, SizeY, 0.5f, 0.5f, 0.5f,true,textures);
		
		units.begin();
		for(int u = 0; u < units.size(); u++){
			
			FloatBuffer buffer = Display.getPlayerColour(myPlayer);
			//System.out.println((units.getUnitY(u)/(float)mapHeight) + " " + mapUnitX + " Minimap");
			new Circle().DrawWithoutCreation(draw, ScreenWidth, ScreenHeight, 
					CenterX  + ((units.getUnitX(u)/(float)mapWidth) * SizeX),
					CenterY + ((units.getUnitY(u)/(float)mapHeight) * SizeY),
					0.001f, buffer.get(0), buffer.get(1), buffer.get(2), true);
			
		}
		units.end();
		
		buildings.begin();
		for(int b = 0; b < buildings.size(); b++){
			
			FloatBuffer buffer = Display.getPlayerColour(myPlayer);
			new Rectangle().DrawWithoutCreation(draw, ScreenWidth, ScreenHeight,
					CenterX + ((buildings.getBuildingX(b)/(float)mapWidth) * SizeX), 
					CenterY + ((buildings.getBuildingY(b)/(float)mapHeight) * SizeY), 
					0.004f, 0.004f, buffer.get(0), buffer.get(1),buffer.get(2),true,textures);
		}
		buildings.end();
		//System.out.println(FRAME_X_SIZE + " " + mapWidth + " MiniMap");
		new Rectangle().DrawWithoutCreation(draw, ScreenWidth, ScreenHeight, 
				CenterX + ((FrameX/(float)mapWidth) * SizeX),
				CenterY + ((FrameY/(float)mapHeight) * SizeY),
				((float)FRAME_X_SIZE/(float)mapWidth)*0.1f,
				((float)FRAME_Y_SIZE/(float)mapHeight)*0.1f, 0.0f, 0.0f, 0.0f, false,textures);
		
	}
	
	public int[] SelectMinimap(double mx, double my,int mapWidth,int mapHeight){
		
		float offsetX = (float) (mx - CenterX);
		float offsetY = (float) (my - CenterY);
		
		if(offsetX < 0 || offsetX > SizeX || offsetY < 0 || offsetY > SizeY){
			
			return new int[]{-1,-1};
		}
		
		return new int[]{(int)((offsetX/SizeX)*mapWidth),(int)((offsetY/SizeY)* mapHeight)};
	}
	

}
