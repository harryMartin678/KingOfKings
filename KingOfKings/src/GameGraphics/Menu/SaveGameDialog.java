package GameGraphics.Menu;

import com.jogamp.opengl.GL2;

public class SaveGameDialog {

	private TextList savedGames;
	
	public SaveGameDialog(){
		
		savedGames = new TextList(0.1625f, 0.3125f, 0.675f, 0.525f);
		savedGames.AddText("one");
		savedGames.AddText("two");
		savedGames.AddText("three");
		savedGames.AddText("four");
		savedGames.AddText("five");
		savedGames.AddText("six");
		savedGames.AddText("seven");
		savedGames.AddText("eight");
		savedGames.AddText("nine");
		savedGames.AddText("ten");
		savedGames.AddText("eleven");
		savedGames.AddText("twelve");
		savedGames.AddText("thriteen");
		savedGames.AddText("fourteen");
		savedGames.AddText("fifteen");
		
	}
	
	public void DrawSaveGameDialog(GL2 draw,int ScreenWidth,int ScreenHeight){
		
		new Rectangle().DrawWithoutCreation(draw, ScreenWidth, ScreenHeight,
				0.1f,0.2f, 0.8f, 0.75f, 1.0f,1.0f,1.0f, true);
		new Rectangle().DrawWithoutCreation(draw, ScreenWidth, ScreenHeight,
				0.15f,0.3f, 0.7f, 0.55f, 1.0f,1.0f,0.0f, true);
		
		savedGames.DrawTextList(draw, ScreenWidth, ScreenHeight);
	}
	
	public boolean RegulateSGDMouse(float x, float y,int ScreenWidth,int ScreenHeight){
		
		return savedGames.SelectTextList(x, y, ScreenWidth, ScreenHeight);
	}
	
}
