package LWJGL;


//You might notice a lot of imports here.
//You are probably wondering why I didn't just import org.lwjgl.opengl.GL11.*
//Well, I did it as a hint to you.
//OpenGL has a lot of commands, and it can be kind of intimidating.
//This is a list of all the commands I used when I implemented my project.
//Therefore, if a command appears in this list, you probably need it.
//If it doesn't appear in this list, you probably don't.
//Of course, your milage may vary. Don't feel restricted by this list of imports.

import java.util.Iterator;

import org.lwjgl.input.Keyboard;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3d;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.util.glu.GLU.gluPerspective;

/**
 *
 * @author Brennan Smith
 */
public class StudentLWJGLController implements CS355LWJGLController 
{
  static int viewportW = 640;
  static int viewportH = 480;
  float fovy = 85;
  float aspect = viewportW/viewportH;
  float zNear = 0.1f;
  float zFar = 500;
  
  //This is a model of a house.
  //It has a single method that returns an iterator full of Line3Ds.
  //A "Line3D" is a wrapper class around two Point2Ds.
  //It should all be fairly intuitive if you look at those classes.
  //If not, I apologize.
  private WireFrame model = new HouseModel();
  
  private VirtualCamera cam = new VirtualCamera();

  //This method is called to "resize" the viewport to match the screen.
  //When you first start, have it be in perspective mode.
  @Override
  public void resizeGL() 
  {
	  //set up the viewport parameters
	  glViewport(0, 0, viewportW, viewportH);

      glMatrixMode(GL_PROJECTION);
      glLoadIdentity();
      gluPerspective(fovy, aspect, zNear, zFar);
  }

    @Override
    public void update() 
    {
        
    }

    //This is called every frame, and should be responsible for keyboard updates.
    //An example keyboard event is captured below.
    //The "Keyboard" static class should contain everything you need to finish this up.
    @Override
    public void updateKeyboard() 
    {
        if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
            cam.left();
        } else if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
            cam.right();
        } else if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
        	if(cam.z > -zFar)
        		cam.forward();
        } else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
        	if(cam.z < zFar)
        		cam.backward();
        } else if(Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            cam.turnL();
        } else if(Keyboard.isKeyDown(Keyboard.KEY_E)) {
        	cam.turnR();
        } else if(Keyboard.isKeyDown(Keyboard.KEY_R)) {
            cam.up();
        } else if(Keyboard.isKeyDown(Keyboard.KEY_F)) {
            cam.down();
        } else if(Keyboard.isKeyDown(Keyboard.KEY_O)) {
            // select projection matrix (controls view on screen)
            double left = -viewportW/30;
            double right = viewportW/30;
            double bottom = -viewportH/30;
            double top = viewportH/30;
            double znear = -1;
            
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(left, right, bottom, top, znear, zFar+5);
        } else if(Keyboard.isKeyDown(Keyboard.KEY_P)) {
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            gluPerspective(fovy, aspect, zNear, zFar);
        }
    }

    //This method is the one that actually draws to the screen.
    @Override
    public void render() 
    {
    	// This clears the screen.
        glClear(GL_COLOR_BUFFER_BIT);

        // Do your drawing here.
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        //world-to-camera transformation
        //transform the model according to where the camera currently is and where it's facing.
        glRotatef(-cam.rotation, 0, 1, 0);
        glTranslatef(-cam.x, -cam.y, -cam.z);

        glBegin(GL_LINES);
        glColor3f(0, 0, 1); //make the model blue
        draw_house();
      
        glEnd();
    }
    
    public void draw_house() {
    	Iterator<Line3D> iter = model.getLines();
      	while(iter.hasNext()) {
	      	Line3D line = iter.next();
	      	glVertex3d(line.start.x, line.start.y, line.start.z);
	      	glVertex3d(line.end.x, line.end.y, line.end.z);
	    }   
    }
    
    class VirtualCamera {
        float x, y, z, rotation;
        VirtualCamera() {
        	x = 0;
        	y = 5;
        	z = 20;
        	rotation = 0;
        }
        void down() { y--; }
        void up() { y++; }
        void turnL() { rotation++; }
        void turnR() { rotation--; }
        void left() {
        	x -= Math.cos(rotation * Math.PI / 180);
        	z += Math.sin(rotation * Math.PI / 180);
        }
        void right() {
        	x += Math.cos(rotation * Math.PI / 180);
        	z -= Math.sin(rotation * Math.PI / 180);
        }
        void backward() {
        	z += Math.cos(rotation * Math.PI / 180);
        	x += Math.sin(rotation * Math.PI / 180);
        }
        void forward() {
        	z -= Math.cos(rotation * Math.PI / 180);
        	x -= Math.sin(rotation * Math.PI / 180);
        }
    }
}
