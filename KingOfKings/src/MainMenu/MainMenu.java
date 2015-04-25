package MainMenu;

import java.awt.Container;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class MainMenu {
	
	private Container pane;
	private MainMenuGUI mm;
	private JFrame frame;
	
	public MainMenu(){
		
		GraphicsDevice graphics = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
		
		frame = new JFrame();
		frame.setTitle("King Of Kings");
		frame.setSize(500, 500);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//sets to full screen 
		graphics.setFullScreenWindow(frame);
		
		mm = new MainMenuGUI();
		
		//closes the main menu if a button is pressed
		Thread mainMenu = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub

				while(true){
					
					String check = mm.getButtonPressed();
					
					//Checks whether the new game button has been pressed 
					if(!check.equals("None") && check.equals("newGame")){
						
						pane.removeAll();
						frame.repaint();
						break;
					}
					
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			
		});
		
		mainMenu.start();
		
		pane = frame.getContentPane();
		pane.add(mm);
		
		 
		
		frame.setVisible(true);
	}
	
	private void startNewGame(){
		
		
	}
	
	public static void main(String[] args) {
		
		new MainMenu();
	}

}
