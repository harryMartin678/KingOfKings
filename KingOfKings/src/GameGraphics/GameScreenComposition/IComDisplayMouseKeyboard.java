package GameGraphics.GameScreenComposition;

public interface IComDisplayMouseKeyboard extends IComDisplay {

//	if(square[1] < (FRAME_Y_SIZE+frameY) && square[1] > (FRAME_Y_SIZE-6+frameY)){
//		
//		if(square[1] < (FRAME_Y_SIZE+frameY) && square[1] > (FRAME_Y_SIZE-3+frameY)){	
//			if(frameY < map.getHeight()-FRAME_Y_SIZE){
//				frameY+=2;
//			}
//		}else{
//			
//			if(frameY < map.getHeight()-FRAME_Y_SIZE){
//				frameY++;
//			}
//		}
//	
//	}
//	
//	if(square[1] >= frameY && square[1] < frameY + 6){
//		
//		if((square[1] >= frameY && square[1] < frameY + 3)){
//			if(frameY > 1){
//				frameY-=2;
//			}
//		}else{
//			
//			if(frameY > 0){
//				frameY--;
//			}
//		}
//	}
//	
//	if(square[0] < (FRAME_X_SIZE+frameX) && square[0] > (FRAME_X_SIZE-6+frameX)){
//		
//		if(square[0] < (FRAME_X_SIZE+frameX) && square[0] > (FRAME_X_SIZE-3+frameX)){
//			if(frameX < map.getWidth()-FRAME_X_SIZE){
//				frameX+=2;
//			}
//		}else{
//			
//			if(frameX < map.getWidth()-FRAME_X_SIZE){
//				frameX++;
//			}
//		}
//	
//	}
//	
//	if(square[0] >= frameX && square[0] < frameX + 6){
//		
//		if(square[0] >= frameX && square[0] < frameX + 3){
//			if(frameX > 1){
//				frameX-=2;
//			}
//		}else{
//			
//			if(frameX > 0){
//				frameX--;
//			}
//		}
//	}
	public void moveMap(int[] square);
	//return new int[]{x+frameX,y+frameY};
	public int[] getFrameAdjustedPos(int[] pos);
	/*
	 * if(selectedUnits.size() > 0){
			
			moveUnit(squareX,squareY);
		
		}else{
		
			frameX = squareX;
			frameY = squareY;
		}
	 */
	public void handleMiniMapSelection(int squareX,int squareY);
	//public void CreateBuildingIconButtons();
	//public void CreateUnitIcons();
	public int getFrameSizeY();
	public int getFrameSizeX();
	//public void CreateHoverPanel(String type,int index,double cornerX, double cornerY);
	public void setFrameX(int frameX);
	public void setFrameY(int frameY);
	
}
