package GameGraphics.Menu;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import GameGraphics.GameScreenComposition.Display;
import GameGraphics.GameScreenComposition.IComMapDisplay;
import GameGraphics.GameScreenComposition.IComMapMouseKeyboard;

public class MapDiagram {
	
	private float Left;
	private float Bottom;
	private float Size;
	private float Radius;
	
	private float MapCircleRadius = 0.005f;
	
	public MapDiagram(float Left,float Bottom,float Size,float Radius){
		
		this.Left = Left;
		this.Bottom = Bottom;
		this.Size = Size;
		this.Radius = Radius;
	}
	
	public float GetCircleY(int mapNo,double centerY,double radius,
			float NoOfMaps){
		
		float anglePerMap = 360.0f / NoOfMaps;
		//System.out.println(anglePerMap + " DisplayMapDiagram");
		return (float)(centerY + (radius * Math.sin(anglePerMap*(mapNo))));
	}
	
	public float GetCircleX(int mapNo,double centerX, double radius,
			float NoOfMaps){
		
		float anglePerMap = 360.0f / NoOfMaps;
		
		return (float)(centerX + (radius * Math.cos(anglePerMap * (mapNo))));
		
	}
	
	public void DrawMapDiagram(GL2 draw,IComMapDisplay map,int ScreenWidth,int ScreenHeight){
		
		float CenterX = Left + (Size/2);
		float CenterY = Bottom + (Size/2);
		
		new Circle().DrawWithoutCreation(draw, ScreenWidth, ScreenHeight,
				CenterX, CenterY, Size, 0.0f, 0.0f, 0.0f, true);

		DrawLines(draw, map, ScreenWidth, ScreenHeight, CenterX, CenterY, Radius);
		
		new Circle().DrawWithoutCreation(draw, ScreenWidth, ScreenHeight,
				GetCircleX(map.getViewedMap(),CenterX,Radius,map.getMapListSize()),
				GetCircleY(map.getViewedMap(),CenterY,Radius,map.getMapListSize()),
				MapCircleRadius + 0.001f, 1.0f, 1.0f, 1.0f, false);
		
		for(int m = 0; m < map.getMapListSize(); m++){

			FloatBuffer buffer = Display.getPlayerColour(map.getMapPlayer(m));
			new Circle().DrawWithoutCreation(draw, ScreenWidth, ScreenHeight,
					GetCircleX(m,CenterX,Radius,map.getMapListSize()),
					GetCircleY(m,CenterY,Radius,map.getMapListSize()),
					MapCircleRadius, buffer.get(0),buffer.get(1),buffer.get(2), true);
		}
		
	}
	
	private void DrawLines(GL2 draw,IComMapDisplay map,int ScreenWidth,int ScreenHeight,
			float CenterX,float CenterY,float Radius){
		
		ArrayList<int[]> trans = map.GetTransitions();
		
		for(int t = 0; t < trans.size(); t++){
			
			int map1 = trans.get(t)[0];
			int map2 = trans.get(t)[1];
			
			float x1 = GetCircleX(map1,CenterX,Radius,map.getMapListSize());
			float y1 = GetCircleY(map1,CenterY,Radius,map.getMapListSize());
			float x2 = GetCircleX(map2,CenterX,Radius,map.getMapListSize());
			float y2 = GetCircleY(map2,CenterY,Radius,map.getMapListSize());
			
			new Line(x1,x2,y1,y2).DrawLine(draw, ScreenWidth, ScreenHeight);
		}
	}
	
	public boolean SelectMapDiagram(double mx, double my,IComMapMouseKeyboard map,
			IComMouseKeyboardMenu command){
		
		for(int m = 0; m < map.getMapListSize(); m++){
			
			if(InMapCircle(mx,my,map.getMapListSize(),m)){
				
				command.SetViewedMap(m);
				return true;
			}
		}
		
		return false;
	}
	
	public int HoverMapDiagram(double mx,double my,IComMapMouseKeyboard map){
		
		for(int m = 0; m < map.getMapListSize(); m++){
			
			if(InMapCircle(mx, my, map.getMapListSize(), m)){
				
				return m;
			}
		}
		
		return -1;
	}
	
	private boolean InMapCircle(double mx, double my,int NoofMaps,int mapNo){
		
		float CenterX = GetCircleX(mapNo, Left + (Size/2), Radius, NoofMaps);
		float CenterY = GetCircleY(mapNo, Bottom + (Size/2), Radius, NoofMaps);
		
		return Math.abs(CenterX - mx) < MapCircleRadius && 
				Math.abs(CenterY - my) < MapCircleRadius;
	}

	public float[] getSelectedMapPos(int selectedMap,int NoOfMaps) {
		// TODO Auto-generated method stub
		return new float[]{
				GetCircleX(selectedMap, Left, Radius, NoOfMaps) 
				+ ((Size/2) + MapCircleRadius),
				GetCircleY(selectedMap, Bottom, Radius, NoOfMaps) 
				- ((Size/2) + MapCircleRadius)
		};
	}
	

}
