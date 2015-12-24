package GameGraphics.GameScreenComposition;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;

import GameClient.ClientMessages;
import GameGraphics.BuildingList;
import GameGraphics.UnitList;

public class GraphicsEngine implements IComGameEngineFrameProcess {
	
	private MapComp map;
	private Display display;
	private MouseKeyboard mouseKeyboard;
	private UnitList units;
	private BuildingList buildings;
	private ClientMessages cmsg;
	private int myPlayerNumber;
	private ProcessFrameThread processFrame;

	
	public GraphicsEngine(ClientMessages cmsg) throws IOException{
		
		this.cmsg = cmsg;
		
		ArrayList<String> load = Load();
		map = new MapComp(load.get(0));
		
		display = new Display();
		mouseKeyboard = new MouseKeyboard();
		units = new UnitList(myPlayerNumber);
		buildings = new BuildingList();
		buildings.setClientMessager(cmsg);
		
		display.setUpDisplay((IComUnitListDisplay) units, (IComBuildingListDisplay) buildings,
				(IComMouseKeyboard) mouseKeyboard, map,myPlayerNumber);
		
		mouseKeyboard.setUpMouseKeyboard((IComUnitListMouseKeyboard)units,
				(IComBuildingListMouseKeyboard)buildings,(IComDisplayMouseKeyboard) display, 
				map, myPlayerNumber, cmsg);
		
		processFrame = new ProcessFrameThread(cmsg,(IComFrameProcessMap) map,
				(IComFrameProcessDisplay) display,(IComGameEngineFrameProcess) this,
				(IComUnitListFrameProcess) units,(IComBuildingListFrameProcess) buildings);
		processFrame.start(load);
		animation.start();
		
	}
	
	public void display(GLAutoDrawable drawable,GLU glu, GLUT glut){
		
		display.display(drawable, glu, glut);

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
		
		ArrayList<String> load = new ArrayList<String>();
		
		while(true){
			
			String msg;
			
			if((msg = cmsg.getMessage()) != null){
				
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
	}

	//deals with animations 
	Thread animation = new Thread(new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			while(true){
				
				long startTime = System.currentTimeMillis();
				
				//deal with mouse input 
				mouseKeyboard.regulateMouse(display.getFrameX(), display.getFrameY(),
						display.getFrameXSize(), display.getFrameYSize(), map.getViewedMap());
				
				for(int i = 0; i < units.getUnitListSize(); i++){
					
					if(i >= units.getUnitListSize()){
						
						try {
							Thread.sleep(2);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					units.changeCurrentFrame(i);
				}

				/*
				treeTime ++;
				
				if(treeTime > 4){
					
					treeUn.changeCurrentFrame();
					
					if(treeTime == 8){
						
						treeTime = 0;
					}
				}*/
				
				try {
					
					long sleepTime = 200 - (System.currentTimeMillis() - startTime);
					
					if(sleepTime < 5){
						
						sleepTime = 5;
					}
					
					Thread.sleep(sleepTime);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	});
			
	public MouseMotionListener getMouseMotionListener(){
		
		return new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
				mouseKeyboard.handleMouseDragged(e);
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub

			}
			
			
		};
	}
	
	public MouseListener getMouseListener(){
		
		return new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

				mouseKeyboard.handleMouseClicked(e);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
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
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
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
	
	
	

}
