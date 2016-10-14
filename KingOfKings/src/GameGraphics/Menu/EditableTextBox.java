package GameGraphics.Menu;

import java.awt.Font;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;

import GameGraphics.GameScreenComposition.TextureRepo;

public class EditableTextBox implements IComChangeSelectionTextList {
	
	private float x;
	private float y;
	private float sizeX;
	private float sizeY;
	private boolean selected;
	private String text;
	private Rectangle box;
	private int animNo;
	private int ANIM_SPEED = 8;
	
	public EditableTextBox(float x, float y, float sizeX, float sizeY){
		
		this.x = x;
		this.y = y;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		text = "";
		box = new Rectangle(x, y, sizeX, sizeY, 0.0f, 0.0f, 0.0f,1,null);
	}
	
	public void DrawTextBox(GL2 draw,int ScreenWidth,int ScreenHeight,TextureRepo textures){
		
		box.Draw(draw, ScreenWidth, ScreenHeight, textures);
		
		String addition = "";
		
		if(selected && animNo != ANIM_SPEED){
			
			addition = "|";

		}
		
		if(animNo == ANIM_SPEED){
			
			animNo = 0;
		}
		
		TextDrawer.drawString(draw, x+0.01f, y+0.0085f,
				1.0f, 1.0f, 1.0f, new TextRenderer(new Font("Arial",Font.BOLD,18)), text + addition,
				ScreenWidth, ScreenHeight);
		animNo++;
	}
	
	public void addText(char addition){
		
		if(selected && isValidChar(addition)){
			
			text = text + addition;
		}
	}

	private boolean isValidChar(char addition) {
		// TODO Auto-generated method stub
		return (addition <= 90 && addition >= 65)
				|| (addition <= 123 && addition >= 97)
				|| (addition <= 57 && addition >= 48);
	}

	public boolean SelectedBox(float mx, float my, int screenWidth, int screenHeight) {
		// TODO Auto-generated method stub
		
		if(box.InMouse(mx, my)){
			
			selected = true;
		
		}else{
			
			UnSelect();
		}
		
		return selected;
	}
	
	public void UnSelect(){
		
		selected = false;
	}

	public void removeChar() {
		// TODO Auto-generated method stub
		if(text.length() > 0){
			text = text.substring(0,text.length()-1);
		}
	}

	@Override
	public void ChangeSelection(String selection) {
		// TODO Auto-generated method stub
		this.text = selection;
	}

	public String getText() {
		// TODO Auto-generated method stub
		return this.text;
	}
	

}
