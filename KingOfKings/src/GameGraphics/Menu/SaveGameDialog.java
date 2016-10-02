package GameGraphics.Menu;

import java.awt.event.KeyEvent;

import com.jogamp.opengl.GL2;

import GameGraphics.GameScreenComposition.TextureRepo;

public class SaveGameDialog {

	private TextList savedGames;
	private EditableTextBox box;
	
	public SaveGameDialog(){
		
		savedGames = new TextList(0.1625f, 0.3125f, 0.675f, 0.525f);
		box = new EditableTextBox(0.25f, 0.875f, 0.5f, 0.025f);
		
		savedGames.AddText("One");
	}
	
	public void DrawSaveGameDialog(GL2 draw,int ScreenWidth,int ScreenHeight,TextureRepo textures){
		
		new Rectangle().DrawWithoutCreation(draw, ScreenWidth, ScreenHeight,
				0.1f,0.2f, 0.8f, 0.75f, 1.0f,1.0f,1.0f, true,textures);
		new Rectangle().DrawWithoutCreation(draw, ScreenWidth, ScreenHeight,
				0.15f,0.3f, 0.7f, 0.55f, 1.0f,1.0f,0.0f, true,textures);
		
		savedGames.DrawTextList(draw, ScreenWidth, ScreenHeight,textures);
		box.DrawTextBox(draw, ScreenWidth, ScreenHeight, textures);
	}
	
	public boolean RegulateSGDMouse(float x, float y,int ScreenWidth,int ScreenHeight){
		
		boolean text =  savedGames.SelectTextList(x, y, ScreenWidth, ScreenHeight);
		
		if(text){
			
			box.UnSelect();
			return true;
		
		}else{
			
			return box.SelectedBox(x,y,ScreenWidth,ScreenHeight);
		}
	}

	public void RegisterKeyStroke(char key) {
		// TODO Auto-generated method stub
		if(key == KeyEvent.VK_BACK_SPACE){
			
			box.removeChar();
			
		}else{
			
			box.addText(key);
			
		}
		
	}
	
}
