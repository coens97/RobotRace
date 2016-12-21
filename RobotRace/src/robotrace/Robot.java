package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import static javax.media.opengl.GL2.*;

/**
* Represents a Robot, to be implemented according to the Assignments.
*/
class Robot {
    
    /** The position of the robot. */
    public Vector position = new Vector(1, 0, 0);
    
    /** The direction in which the robot is running. */
    public Vector direction = new Vector(1, 0, 0);

    /** The material from which this robot is built. */
    private final Material material;
    
    private float headHeight = 0.3f;
    private float headNeckHeight = 0.05f;
    private float headNeckWidth = 0.10f;
    
    private float bodyHeight = 0.7f;
    
    private float legsHeight = 0.8f;
    private float legsButtHeight = 0.2f;
    private float legsShoeHeight = 0.1f;
    
    private float robotHeight = headHeight + bodyHeight +legsHeight;

    /**
     * Constructs the robot with initial parameters.
     */
    public Robot(Material material
            
    ) {
        this.material = material;

        
    }

    /**
     * Draws this robot (as a {@code stickfigure} if specified).
     */
    public void draw(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        gl.glPushMatrix();
            gl.glTranslated(-position.x, -position.y, -position.z - robotHeight/2);
            drawHead(gl, glu, glut, tAnim);
            drawBody(gl, glu, glut, tAnim);
            drawLeg(gl, glu, glut, tAnim);
            drawArm(gl, glu, glut, tAnim);
        gl.glPopMatrix();
    }
    
    public void drawHead(GL2 gl, GLU glu, GLUT glut, float tAnim)
    {
        gl.glPushMatrix();
        
        gl.glTranslatef(0, 0, legsHeight + bodyHeight);
        //draw neck
        gl.glColor3d(255, 0, 0);
        glut.glutSolidCylinder(headNeckWidth, headNeckHeight, 10, 8);
        //draw head
        gl.glColor3d(255, 255, 0);
        float radius = headHeight - headNeckHeight;
        gl.glTranslatef(0, 0, headNeckHeight + radius);
        glut.glutSolidSphere(radius, 12, 8);
        
        gl.glPopMatrix();
    }
    
    public void drawBody(GL2 gl, GLU glu, GLUT glut, float tAnim)
    {
        
    }
    
    public void drawLeg(GL2 gl, GLU glu, GLUT glut, float tAnim)
    {
        
    }
    
    public void drawArm(GL2 gl, GLU glu, GLUT glut, float tAnim)
    {
        
    }
}
