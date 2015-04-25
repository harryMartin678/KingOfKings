package GameGraphics;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class GameScreen implements GLEventListener {

	@Override
	public void display(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		GL2 draw = drawable.getGL().getGL2();
		
		draw.glPushMatrix();
		draw.glScalef(0.1f, 0.1f, 0.1f);
		draw.glBegin(GL2.GL_QUADS);
			draw.glColor3f(0.0f, 1.0f, 0.0f);
			draw.glVertex3f(1.0f, 1.0f, -1.0f);//back face
			draw.glVertex3f(-1.0f, 1.0f, -1.0f);
			draw.glVertex3f(-1.0f, -1.0f, -1.0f);
			draw.glVertex3f(1.0f, -1.0f, -1.0f);
		draw.glEnd();
		draw.glPopMatrix();
			
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
			int arg4) {
		// TODO Auto-generated method stub
		
	}

}
