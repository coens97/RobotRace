package robotrace;

import static javax.media.opengl.GL2.*;
import static robotrace.ShaderPrograms.*;
import static robotrace.Textures.*;

/**
 * Handles all of the RobotRace graphics functionality,
 * which should be extended per the assignment.
 * 
 * OpenGL functionality:
 * - Basic commands are called via the gl object;
 * - Utility commands are called via the glu and
 *   glut objects;
 * 
 * GlobalState:
 * The gs object contains the GlobalState as described
 * in the assignment:
 * - The camera viewpoint angles, phi and theta, are
 *   changed interactively by holding the left mouse
 *   button and dragging;
 * - The camera view width, vWidth, is changed
 *   interactively by holding the right mouse button
 *   and dragging upwards or downwards; (Not required in this assignment)
 * - The center point can be moved up and down by
 *   pressing the 'q' and 'z' keys, forwards and
 *   backwards with the 'w' and 's' keys, and
 *   left and right with the 'a' and 'd' keys;
 * - Other settings are changed via the menus
 *   at the top of the screen.
 * 
 * Textures:
 * Place your "track.jpg", "brick.jpg", "head.jpg",
 * and "torso.jpg" files in the folder textures. 
 * These will then be loaded as the texture
 * objects track, bricks, head, and torso respectively.
 * Be aware, these objects are already defined and
 * cannot be used for other purposes. The texture
 * objects can be used as follows:
 * 
 * gl.glColor3f(1f, 1f, 1f);
 * Textures.track.bind(gl);
 * gl.glBegin(GL_QUADS);
 * gl.glTexCoord2d(0, 0);
 * gl.glVertex3d(0, 0, 0);
 * gl.glTexCoord2d(1, 0);
 * gl.glVertex3d(1, 0, 0);
 * gl.glTexCoord2d(1, 1);
 * gl.glVertex3d(1, 1, 0);
 * gl.glTexCoord2d(0, 1);
 * gl.glVertex3d(0, 1, 0);
 * gl.glEnd(); 
 * 
 * Note that it is hard or impossible to texture
 * objects drawn with GLUT. Either define the
 * primitives of the object yourself (as seen
 * above) or add additional textured primitives
 * to the GLUT object.
 */
public class RobotRace extends Base {
    
    /** Array of the four robots. */
    private final Robot[] robots;
    
    /** Instance of the camera. */
    private final Camera camera;
    
    /** Instance of the race track. */
    private final RaceTrack[] raceTracks;
    
    /** Instance of the terrain. */
    private final Terrain terrain;
    private double speed = 0.0035;
        
    /**
     * Constructs this robot race by initializing robots,
     * camera, track, and terrain.
     */
    public RobotRace() {
        
        // Create a new array of four robots
        robots = new Robot[4];
        
        // Initialize robot 0
        robots[0] = new Robot(Material.GOLD,
               0, 0, 0,
                0.3f, 0.3f
        );
        
        // Initialize robot 1
        robots[1] = new Robot(Material.SILVER,
              1, 0, 0,
                0.3f, 0.5f
        );
        
        // Initialize robot 2
        robots[2] = new Robot(Material.WOOD,
              2, 0, 0,
                0.4f, 0.2f
        );

        // Initialize robot 3
        robots[3] = new Robot(Material.ORANGE,
              3, 0, 0  ,
                0.2f, 0.2f
        );
        
        // Initialize the camera
        camera = new Camera();
        
        // Initialize the race tracks
        raceTracks = new RaceTrack[2];
        
        // Track 1
        raceTracks[0] = new ParametricTrack();
        
        // Track 2
        float g = 3.5f;
        raceTracks[1] = new BezierTrack(
                
            new Vector[] {
                new Vector(20,0,1),
                new Vector(20,5,1),
                new Vector(20,10,5),
                new Vector(20,15,5),
                
                new Vector(20,15,5),
                new Vector(20,20,5),
                new Vector(15,20,9),
                new Vector(10,20,9),
                
                new Vector(10,20,9),
                new Vector(5,20,9),
                new Vector(5,15,13),
                new Vector(5,10,13),
                
                new Vector(5,10,13),
                new Vector(5,5,13),
                new Vector(5,0,17),
                new Vector(0,-5,17),
                
                new Vector(0,-5,17),
                new Vector(-5,-10,17),
                new Vector(-7,-12,16.5),
                new Vector(-12,-12,16),
                
                new Vector(-12,-12,16),
                new Vector(-17,-12,15.5),
                new Vector(-19,-9,15),
                new Vector(-19,-4,14.5),
                
                new Vector(-19,-4,14.5),
                new Vector(-19,1,14),
                new Vector(-17,3,13.5),
                new Vector(-12,3,13),
                
                new Vector(-12,3,13),
                new Vector(-7,3,12.5),
                new Vector(-2,1,12),
                new Vector(-2, -4, 11.5),
                
                new Vector(-2,-4,11.5),
                new Vector(-2,-9,11),
                new Vector(-4,-11,10.5),
                new Vector(-9, -11, 10),
                
                new Vector(-9,-11,10),
                new Vector(-14,-11,10.5),
                new Vector(-16,-9,10),
                new Vector(-16, -4,9.5),
                
                new Vector(-16,-4,9.5),
                new Vector(-16, 1,9),
                new Vector(-14, 3,8.5),
                new Vector(-9, 3,8),
                
                new Vector(-9,3,8),
                new Vector(-4, 3,7.5),
                new Vector(-1, 3,3),
                new Vector(4, 3,3),
                
                new Vector(4,3,3),
                new Vector(9, 3,3),
                new Vector(11, 1,1),
                new Vector(11, -4,1),
                
                new Vector(11,-4,1),
                new Vector(11, -9,1),
                new Vector(20, -7,1),
                new Vector(20, 0,1),
            }
       
        );
        
        // Initialize the terrain
        terrain = new Terrain();
    }
    
    /**
     * Called upon the start of the application.
     * Primarily used to configure OpenGL.
     */
    @Override
    public void initialize() {
		
        // Enable blending.
        gl.glEnable(GL_BLEND);
        gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                
        // Enable depth testing.
        gl.glEnable(GL_DEPTH_TEST);
        gl.glDepthFunc(GL_LESS);
		
        // Enable face culling for improved performance
        // gl.glCullFace(GL_BACK);
        // gl.glEnable(GL_CULL_FACE);
        
	    // Normalize normals.
        gl.glEnable(GL_NORMALIZE);
        
        // Enable textures.
        gl.glEnable(GL_TEXTURE_2D);
        gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        
        
	// Try to load four textures, add more if you like in the Textures class         
        Textures.loadTextures();
        reportError("reading textures");
        
        // Try to load and set up shader programs
        ShaderPrograms.setupShaders(gl, glu);
        reportError("shaderProgram");
        
    }
   
    /**
     * Configures the viewing transform.
     */
    @Override
    public void setView() {
        // Select part of window.
        gl.glViewport(0, 0, gs.w, gs.h);
        
        // Set projection matrix.
        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();

        // Set the perspective.
        glu.gluPerspective(45, (float)gs.w / (float)gs.h, 0.1*gs.vDist, 10*gs.vDist);
        
        // Set camera.
        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();
        
        // Add light source
        gl.glLightfv(GL_LIGHT0, GL_POSITION, new float[]{0f,0f,1f,0f}, 0);
               
        // Update the view according to the camera mode and robot of interest.
        // For camera modes 1 to 4, determine which robot to focus on.
        int robotFocus = 0;
        for (int i = 1; i < 4; i++) {
            if (robots[robotFocus].extraDistance > robots[i].extraDistance) {
                robotFocus = i;
            }
        }
        camera.update(gs, robots[robotFocus]);
        glu.gluLookAt(camera.eye.x(),    camera.eye.y(),    camera.eye.z(),
                      camera.center.x(), camera.center.y(), camera.center.z(),
                      camera.up.x(),     camera.up.y(),     camera.up.z());
    }
    
    /**
     * Draws the entire scene.
     */
    @Override
    public void drawScene() {
        gl.glUseProgram(defaultShader.getProgramID());
        reportError("program");
        
        // Background color.
        gl.glClearColor(1f, 1f, 1f, 0f);
        
        // Clear background.
        gl.glClear(GL_COLOR_BUFFER_BIT);
        
        // Clear depth buffer.
        gl.glClear(GL_DEPTH_BUFFER_BIT);
        
        // Set color to black.
        gl.glColor3f(0f, 0f, 0f);
        
        gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        

    // Draw hierarchy example.
        //drawHierarchy();
        
        // Draw the axis frame.
        if (gs.showAxes) {
            drawAxisFrame();
        }
        
        // Draw the (first) robot.
        gl.glUseProgram(robotShader.getProgramID()); 
        
        for (int i = 0; i < robots.length; i++) {
            float robotAnim = this.gs.tAnim + robots[i].extraDistance;
            robots[i].position = Vector.Z.subtract(raceTracks[gs.trackNr].getLanePoint(i, robotAnim/40));
            robots[i].direction = raceTracks[gs.trackNr].getLaneTangent(i, robotAnim/40).cross(Vector.Z);
            robots[i].draw(gl, glu, glut, robotAnim);
        }

        // Draw the race track.
        gl.glUseProgram(trackShader.getProgramID());

        raceTracks[gs.trackNr].draw(gl, glu, glut);
        
        // Draw the terrain.
        gl.glUseProgram(terrainShader.getProgramID());
        terrain.draw(gl, glu, glut, this.gs.tAnim);
        reportError("terrain:");
        
        
        // draw trees
        /*gl.glPushMatrix(); 
            gl.glRotatef(-90, 0.0f, 1.0f, 0.0f);
            drawTree();
        gl.glPopMatrix();
        gl.glPushMatrix(); 
            gl.glRotatef(-90, 0.0f, 1.0f, 0.0f);
            //gl.glTranslated(0, 5, 4);
            gl.glScaled(1.3,1.3,1.3);
            gl.glColor3d(165, 42, 42);
            drawTree();
        gl.glPopMatrix();
        gl.glPushMatrix(); 
            gl.glRotatef(-90, 0.0f, 1.0f, 0.0f);
            gl.glTranslated(0, -5, -4);
            gl.glScaled(0.7,0.7,0.7);
            gl.glColor3d(165, 42, 42);
            drawTree();
        gl.glPopMatrix();*/
        drawTree(0, 5, 4, 1.3, 1.0);
        drawTree(0, -5, -4, 1.3, 0.8);
        drawTree(0, 15, 11, 1.6,0.9);
        drawTree(0, -15, -11, 1.3, 0.8);
        drawTree(0, -14, 13, 1.4, 0.8);
        drawTree(0, 14, -13, 1.4, 0.7);
        drawTree(0, -17, 9, 1.1, 0.9);
        drawTree(0, 17, -9, 1.6, 0.8);
        drawTree(0, -8, 17, 1.2, 0.5);
        drawTree(0, 8, -17, 1.7, 0.9);
        
        
    }
    
    /**
     * Draws the x-axis (red), y-axis (green), z-axis (blue),
     * and origin (yellow).
     */
    public void drawAxisFrame() {
        // draw yellow cube
        float cubeSize = 0.1f;
        gl.glPushMatrix(); 
        gl.glTranslated(this.camera.center.x, this.camera.center.y, this.camera.center.z);
        gl.glPushMatrix(); 
            gl.glColor3d(255, 255, 0);
            glut.glutSolidSphere(cubeSize,8 , 12);
        gl.glPopMatrix();
        // draw arrow x
        gl.glPushMatrix(); 
        gl.glColor3d(255, 0, 0);
        drawArrow(cubeSize);
        gl.glPopMatrix();
        // draw arrow z
        gl.glPushMatrix(); 
        gl.glColor3d(0, 0, 255);
        gl.glRotatef(90, 0.0f, 1.0f, 0.0f);
        drawArrow(cubeSize);
        gl.glPopMatrix();
        // draw arrow y
        gl.glPushMatrix(); 
        gl.glColor3d(0, 255, 0);
        gl.glRotatef(-90, 0.0f, 0.0f, 1.0f);
        drawArrow(cubeSize);
        gl.glPopMatrix();
        gl.glPopMatrix();
    }
    
    /**
     * Draws a single arrow
     */
    public void drawArrow(float cubeSize) {
        gl.glPushMatrix(); 
        float coneLength = 0.2f;
        float lineLength = 1.0f - (cubeSize + coneLength);
        float lineWith = 0.06f;
        
        gl.glTranslated(-1 * (cubeSize/2 + lineLength/2), 0, 0);
        gl.glScaled(lineLength, lineWith, lineWith);
        
        glut.glutSolidCube(1.0f);
        gl.glPopMatrix();
        
        gl.glPushMatrix(); 
        gl.glTranslated(-1 * (cubeSize/2 + lineLength), 0, 0);
        gl.glRotatef(-90, 0.0f, 1.0f, 0.0f);
        glut.glutSolidCone(lineWith * 2, coneLength, 10, 4);
        gl.glPopMatrix();
    }
    
    public void drawTree(int tx, int ty, int tz, double scale, double w) {
        gl.glPushMatrix();
            gl.glRotatef(-90, 0.0f, 1.0f, 0.0f);
            gl.glTranslated(tx, ty, tz);
            gl.glScaled(scale,scale,scale);
        
            float cubeSize = 1f;
        
            gl.glPushMatrix(); 
        
                //draw base of the tree
                float coneLength = 1.5f;
                float lineLength = 1.0f - (cubeSize + coneLength + coneLength);
                float lineWith = 0.5f;

                gl.glScaled(lineLength, lineWith, lineWith);
                //gl.glRotatef(0, 0.0f, 1.0f, 0.0f);
                gl.glTranslated(-0.5f, 0, -0.3f);
                gl.glColor3d(0.5f, 0.35f, 0.05f);
                //gl.glRotatef(90, 0.0f, 1.0f, 0.0f);
                glut.glutSolidCylinder(0.6f, 0.5f, 10, 8);
            gl.glPopMatrix();
        
            gl.glPushMatrix();
                //draw top of the tree
                gl.glColor3d(0, 255, 0);
                gl.glTranslated(-1 * (cubeSize + lineLength), 0, 0);
                gl.glRotatef(90, 0.0f, 1.0f, 0.0f);
                glut.glutSolidCone(lineWith * 2, coneLength, 10, 4);
            gl.glPopMatrix();
            
            gl.glPushMatrix();
                //draw top of the tree
                gl.glColor3d(0, 255, 0);
                gl.glTranslated(-1 * (cubeSize*2 + lineLength), 0, 0);
                gl.glRotatef(90, 0.0f, 1.0f, 0.0f);
                glut.glutSolidCone(lineWith * 2, coneLength, 10, 4);
            gl.glPopMatrix();
        
            gl.glPushMatrix();
                gl.glTranslated(-1.5 * (cubeSize + lineLength), 0, 0);
                gl.glRotatef(90, 0.0f, 1.0f, 0.0f);
                glut.glutSolidCone(lineWith * 2 * w, coneLength, 10, 4);
            gl.glPopMatrix();
        gl.glPopMatrix();
    }
    
    /**
     * Main program execution body, delegates to an instance of
     * the RobotRace implementation.
     */
    public static void main(String args[]) {
        RobotRace robotRace = new RobotRace();
        robotRace.FPS = 15; // 15ms
        robotRace.run();
    }
}
