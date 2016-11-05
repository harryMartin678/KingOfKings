package GameGraphics.GameScreenComposition;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.util.ArrayList;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import GameGraphics.BuildingList;
import GameGraphics.Unit;
import GameGraphics.UnitList;
import GameGraphics.Menu.Menu;
import Map.GraphicsCollisionMap;

public class GraphicsEngine implements IComGameEngineFrameProcess {
	
	private MapComp map;
	private Display display;
	private MouseKeyboard mouseKeyboard;
	private UnitList units;
	private BuildingList buildings;
	private ClientWrapper cmsg;
	private int myPlayerNumber;
	private ProcessFrameThread processFrame;
	private Menu menu;
	
	private boolean start;

	
	public GraphicsEngine(final ClientWrapper cmsg) throws IOException{
		
		
		GraphicsCollisionMap.fogOfWar = true;
		
		this.cmsg = cmsg;
		start = false;

		final ArrayList<String> load = Load();
		map = new MapComp(load.get(1));
		
		menu = new Menu();
		display = new Display(menu);
		mouseKeyboard = new MouseKeyboard(menu);
		units = new UnitList();
		buildings = new BuildingList();
		
		//System.out.println(new Integer(load.get(0)).intValue() + " GraphicsEngine");
		this.setMyPlayerNumber(new Integer(load.get(0)).intValue());
		
		map.SetDisplay((IComMapUpdateDisplayFrame)display);
		
		
		buildings.setClientMessager(cmsg);

		processFrame = new ProcessFrameThread(cmsg,(IComFrameProcessMap) map,
				(IComFrameProcessDisplay) display,(IComGameEngineFrameProcess) this,
				(IComUnitListFrameProcess) units,(IComBuildingListFrameProcess) buildings,
				(IComMouseFrameProcess) mouseKeyboard);
		

		display.setUpDisplay((IComUnitListDisplay) units, (IComBuildingListDisplay) buildings,
				(IComMouseKeyboard) mouseKeyboard, map,myPlayerNumber);

		mouseKeyboard.setUpMouseKeyboard((IComUnitListMouseKeyboard)units,
				(IComBuildingListMouseKeyboard)buildings,(IComDisplayMouseKeyboard) display, 
				map, myPlayerNumber, cmsg,display.getUnitBBoxes(),display.getBuildingBBoxes());
		buildings.SetUpBuildingList(mouseKeyboard);

		//GraphicsCollisionMap.RefreshCollisionMap(this.map.getMap().toArray());
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				processFrame.load(load);
				cmsg.addMessage("READY");
				
				while(!start){
					
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				System.out.println("entered game");
				//System.exit(0);
				try {
					AllReadyStart();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			

		}).start();

			
	}
	
	public void AllReadyStart() throws IOException{
		
		processFrame.start();
		//animation.start();
	}
	
	public void Start(){
		
		start = true;
	}
	
	public void display(GLAutoDrawable drawable,GLU glu, GLUT glut){
		
		mouseKeyboard.moveMap();
		display.display(drawable, glu, glut);

	}
	
	public void init(GLAutoDrawable drawable,GLU glu){
		
		try {
			display.init(drawable.getGL().getGL2(),glu);
		} catch (GLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void SetWidth(int width){
		
		mouseKeyboard.SetWidth(width);
		display.setScreenWidth(width);
	}
	
	public void SetHeight(int height){
		
		mouseKeyboard.SetHeight(height);
		display.setScreenHeight(height);
	}
	
	private ArrayList<String> Load(){
		
		cmsg.requestFrame();
		ArrayList<String> load = new ArrayList<String>();
		
		while(true){
			
			String msg;
			
			if((msg = cmsg.getFrameMessage()) != "null"){
				
				if(msg.equals("END_LOAD")){
					
					break;
				}
				
				load.add(msg);
				
			}
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return load;
	}

	@Override
	public void setMyPlayerNumber(int playerNumber) {
		// TODO Auto-generated method stub
		this.myPlayerNumber = playerNumber;
		units.setMyPlayerNumber(playerNumber);
	}

	//deals with animations 
//	Thread animation = new Thread(new Runnable(){
//
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			//int beat = 0;
//			
//			while(true){
//				
//				long startTime = System.currentTimeMillis();
//				
//				units.begin();
//			    //buildings.begin();
//				//deal with mouse input 
////				if(beat == 5){
////					mouseKeyboard.regulateMouse(display.getFrameX(), display.getFrameY(),
////							display.getFrameXSize(), display.getFrameYSize(), map.getViewedMap());
////					beat = 0;
////				}
//				
//				mouseKeyboard.moveMap();
//				//buildings.end();
////				for(int i = 0; i < units.getUnitListSize(); i++){
////					
//////					if(i >= units.getUnitListSize()){
//////						
//////						try {
//////							Thread.sleep(1);
//////						} catch (InterruptedException e) {
//////							// TODO Auto-generated catch block
//////							e.printStackTrace();
//////						}
//////					}
////					
////					units.changeCurrentFrame(i);
////				}
//				Unit.AnimateUnits();
//				units.end();
//
//				/*
//				treeTime ++;
//				
//				if(treeTime > 4){
//					
//					treeUn.changeCurrentFrame();
//					
//					if(treeTime == 8){
//						
//						treeTime = 0;
//					}
//				}*/
//				
//				try {
//					
//					long sleepTime = 200 - (System.currentTimeMillis() - startTime);
//					
//					if(sleepTime < 5){
//						
//						System.out.println("No Time Animation GraphicsEngine");
//						sleepTime = 20;
//					}
//					
//					Thread.sleep(sleepTime);
//					
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//
//		}
//
//	});
			
	public MouseMotionListener getMouseMotionListener(){
		
		return new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
				mouseKeyboard.handleMouseDragged(e,display.getFrameX(),display.getFrameY(),
						display.getFrameSizeX(),display.getFrameSizeY(),map.getViewedMap());
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				//System.out.println(e.getX() + " " + e.getY() + " GraphicsEngine");
				mouseKeyboard.handleHover(e);
						//,display.getFrameX(),display.getFrameY(),
						//display.getFrameSizeX(),display.getFrameSizeY(),map.getViewedMap());
			}
			
			
		};
	}
	
	public MouseWheelListener getMouseWheelListener(){
		
		return new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				// TODO Auto-generated method stub
				mouseKeyboard.handleMouseWheel(e);
			}
		};
		
	}
	
	public MouseListener getMouseListener(){
		
		return new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				mouseKeyboard.handleMouseClicked(e,display.getFrameX(),display.getFrameY(),
						display.getFrameSizeX(),display.getFrameSizeY(),map.getViewedMap());
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}


			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				mouseKeyboard.handleReleasedMouse(e,display.getFrameX(),display.getFrameY(),
						display.getFrameSizeX(),display.getFrameSizeY(),map.getViewedMap());
			}
			
			
		};
	}
	
	public KeyListener getKeyboardListener(){
		return new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				mouseKeyboard.handleKeyboardPressed(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

				mouseKeyboard.handleKeyboardReleased(e);
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}
			
			
		};
	}

	@Override
	public int getPlayerNumber() {
		// TODO Auto-generated method stub
		return this.myPlayerNumber;
	}

	public boolean getStart() {
		// TODO Auto-generated method stub
		return start;
	}	
	
	
	

}
