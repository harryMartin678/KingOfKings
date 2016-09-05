package GameGraphics.Menu;

import java.awt.Font;
import java.util.ArrayList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;

import GameGraphics.GameScreenComposition.TextureRepo;

public class TextList {
	
	private float Left;
	private float Bottom;
	private float SizeX;
	private float SizeY;
	private ArrayList<String> Items;
	private TextRenderer font;
	private int ScrollIndexStart;
	private Rectangle UpArrow;
	private Rectangle DownArrow;
	private float textLeft;
	private float textTop;
	private int SelectedText;
	
	public TextList(float Left,float Bottom,float SizeX,float SizeY){
		
		this.Left = Left;
		this.Bottom = Bottom;
		this.SizeX = SizeX;
		this.SizeY = SizeY;
		this.textLeft = Left + 0.025f;
		this.textTop = Bottom + (SizeY - 0.025f);
		SelectedText = -1;
		
		UpArrow = new Rectangle(Left + SizeX - 0.025f, Bottom + (SizeY/2),
				0.025f, 0.05f, 0.0f, 1.0f, 0.0f, 0,null);
		DownArrow = new Rectangle(Left + SizeX - 0.025f, Bottom + ((SizeY/2) - 0.1f),
				0.025f, 0.05f, 0.0f, 1.0f, 0.0f, 0,null);
		
		ScrollIndexStart = 0;
		
		Items = new ArrayList<String>();
		
		font = new TextRenderer(new Font("Arial",Font.BOLD,18));
	}
	
	public void AddText(String text){
		
		Items.add(text);
	}
	
	public void DrawTextList(GL2 draw, int ScreenWidth, int ScreenHeight,TextureRepo textures){
		
		new Rectangle().DrawWithoutCreation(draw, ScreenWidth, ScreenHeight,
				Left, Bottom, SizeX, SizeY,0.75f,0.75f,1.0f, true,textures);
		
		
//		System.out.println(ScrollIndexStart + " " +
//		Math.min(Items.size(),(int)(SizeY / 0.05f)) + " " + Items.set, element) " TextList");
		
		for(int t = ScrollIndexStart; t < getLimit(); t++){
			
			if(t == SelectedText){
				
				new Rectangle().DrawWithoutCreation(draw, ScreenWidth, ScreenHeight,
						textLeft - 0.025f,(textTop - 0.025f) - ((t-ScrollIndexStart) * 0.05f),
						SizeX,0.05f,0.0f,0.0f,1.0f, false,textures);
			}
			
			DrawText(draw,ScreenWidth,ScreenHeight,Items.get(t),
					textLeft,textTop - ((t-ScrollIndexStart) * 0.05f));
		}
		
		UpArrow.Draw(draw, ScreenWidth, ScreenHeight,textures);
		DownArrow.Draw(draw, ScreenWidth, ScreenHeight,textures);
	}

	private void DrawText(GL2 draw, int ScreenWidth, int ScreenHeight, String text,
			float x, float y) {
		// TODO Auto-generated method stub
		TextDrawer.drawString(draw, x, y, 0.0f, 0.0f, 0.0f, font,
				text, ScreenWidth, ScreenHeight);
	}
	
	public boolean SelectTextList(float x,float y,int ScreenWidth,int ScreenHeight){
		
		
		if(UpArrow.InMouse(x, y)){
			
			if(ScrollIndexStart > 0){
				
				ScrollIndexStart--;
			}

			return true;
		
		}else if(DownArrow.InMouse(x, y)){
			
			if(ScrollIndexStart < Items.size() - (SizeY / 0.05f)){
				
				ScrollIndexStart++;
			}

			return true;
		}
		
	    SelectedText = -1;
		
		for(int t = ScrollIndexStart; t < getLimit(); t++){
			
			Rectangle inT = new Rectangle(textLeft - 0.025f,(textTop - 0.025f) - ((t-ScrollIndexStart) * 0.05f),
					SizeX,0.05f,0.0f,0.0f,1.0f,0,null);
			
			if(inT.InMouse(x, y)){
				
				SelectedText = t;
				return true;
			}
		}
		
		
		
		return false;
	}

	private int getLimit(){
		
		int limit;
		int pageLimit = (int)(SizeY / 0.05f);
		
		if(Items.size() < pageLimit){
			
			limit = Items.size();
		
		}else{
			
			limit = pageLimit + ScrollIndexStart;
		}
		
		return limit;
	}
	

}
