package GameGraphics.Menu;

import java.awt.event.KeyEvent;
import java.io.File;

import com.jogamp.opengl.GL2;

import GameGraphics.GameScreenComposition.TextureRepo;
import Util.FileFinder;

public class SaveGameDialog {

	private TextList savedGames;
	private EditableTextBox box;
	private Rectangle exitButton;
	private Rectangle saveGameButton;
	
	public enum SaveGameMouseReturn{
		
		NOTSELECTED,
		SELECTED,
		DESTROY,
		SAVEGAME
	}
	
	public SaveGameDialog(){
		
		savedGames = new TextList(0.1625f, 0.3125f, 0.675f, 0.525f);
		box = new EditableTextBox(0.25f, 0.875f, 0.5f, 0.025f);
		savedGames.SetChangeSelection(box);
		
		exitButton = new Rectangle(0.875f, 0.9f, 0.0175f, 0.035f, 1.0f, 0.0f, 0.0f, 1, null);
		saveGameButton = new Rectangle(0.45f,0.225f,0.1f,0.06f,0.0f,1.0f,0.0f,1,null);
		
		String[] files = FileFinder.RemoveExtension(FileFinder.CutDirectoryOut(
				FileFinder.FindFiles(FileFinder.SAVEDIRECTORY),FileFinder.SAVEDIRECTORY));
		
		for(int f = 0; f < files.length; f++){
			
			savedGames.AddText(files[f]);
		}
	}
	
	public void DrawSaveGameDialog(GL2 draw,int ScreenWidth,int ScreenHeight,TextureRepo textures){
		
		new Rectangle().DrawWithoutCreation(draw, ScreenWidth, ScreenHeight,
				0.1f,0.2f, 0.8f, 0.75f, 1.0f,1.0f,1.0f, true,textures);
		new Rectangle().DrawWithoutCreation(draw, ScreenWidth, ScreenHeight,
				0.15f,0.3f, 0.7f, 0.55f, 1.0f,1.0f,0.0f, true,textures);
		
		savedGames.DrawTextList(draw, ScreenWidth, ScreenHeight,textures);
		box.DrawTextBox(draw, ScreenWidth, ScreenHeight, textures);
		
		exitButton.Draw(draw, ScreenWidth, ScreenHeight, textures);
		saveGameButton.Draw(draw, ScreenWidth, ScreenHeight, textures);
	}
	
	public SaveGameMouseReturn RegulateSGDMouse(float x, float y,int ScreenWidth,int ScreenHeight){
		
		if(exitButton.InMouse(x, y)){
			
			return SaveGameMouseReturn.DESTROY;
		}
		
		if(saveGameButton.InMouse(x, y)){
			
			return SaveGameMouseReturn.SAVEGAME;
		}
		
		boolean text = savedGames.SelectTextList(x, y, ScreenWidth, ScreenHeight);
		
		if(text){
			
			box.UnSelect();
			return SaveGameMouseReturn.SELECTED;
		
		}else{
			
			if(box.SelectedBox(x,y,ScreenWidth,ScreenHeight)){
				
				return SaveGameMouseReturn.SELECTED;
				
			}else{
				
				return SaveGameMouseReturn.NOTSELECTED;
			}
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

	public String getText() {
		// TODO Auto-generated method stub
		return box.getText();
	}
	
}
