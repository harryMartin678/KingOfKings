package GameGraphics.GameScreenComposition;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLException;
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
	private ClientWrapper cmsg;
	private int myPlayerNumber;
	private ProcessFrameThread processFrame;
	
	private boolean start;

	
	public GraphicsEngine(final ClientWrapper cmsg) throws IOException{
		
		this.cmsg = cmsg;
		start = false;

		final ArrayList<String> load = Load();
		map = new MapComp(load.get(1));
		
		display = new Display();
		mouseKeyboard = new MouseKeyboard();
		units = new UnitList();
		buildings = new BuildingList();
		
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
				map, myPlayerNumber, cmsg);

		
		
		
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
		animation.start();
	}
	
	public void Start(){
		
		start = true;
	}
	
	public void display(GLAutoDrawable drawable,GLU glu, GLUT glut){
		
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
	Thread animation = new Thread(new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			while(true){
				
				long startTime = System.currentTimeMillis();
				
				units.begin();
			    //buildings.begin();
				//deal with mouse input 
				mouseKeyboard.regulateMouse(display.getFrameX(), display.getFrameY(),
						display.getFrameXSize(), display.getFrameYSize(), map.getViewedMap());
				//buildings.end();
				for(int i = 0; i < units.getUnitListSize(); i++){
					
//					if(i >= units.getUnitListSize()){
//						
//						try {
//							Thread.sleep(1);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
					
					units.changeCurrentFrame(i);
				}
				units.end();

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
						
						System.out.println("No Time Animation GraphicsEngine");
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
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				mouseKeyboard.handleReleasedMouse(e);
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
