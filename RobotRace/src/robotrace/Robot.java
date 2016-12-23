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
    private float headNose = 0.05f;
    
    private float bodyHeight = 0.7f;
    private float bodyWidth = 0.5f;
    private float bodyDepth = 0.3f;
    
    private float legsHeight = 0.8f;
    private float legsRadius = 0.1f;
    private float legsDistance = 0.3f/2;
    private float legsShoeHeight = 0.1f;
    private float legsShoeDepth = 0.3f;
    private float legsKneeHeight = 0.4f;
    private int legsUpperDefaultAngle = 10;
    private float legsUpperHeight = legsHeight - legsKneeHeight;
    
    private float robotHeight = headHeight + bodyHeight +legsHeight;
    
    private float armsRadius = 0.08f;
    private float armDistance = (bodyWidth + armsRadius) / 2;
    private int armUpperDefaultAngle = -5;
    private float armUpperArmLenght = 0.2f;
    private float armLowerArmLenght = 0.2f;

    /**
     * Constructs the robot with initial parameters.
     */
    public Robot(Material material,
       int x, int y, int z     
    ) {
        this.material = material;
        this.position = new Vector(x, y, z);
        
    }

    /**
     * Draws this robot (as a {@code stickfigure} if specified).
     */
    public void draw(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        gl.glPushMatrix();
            gl.glTranslated(-position.x, -position.y, -position.z - robotHeight/2);
            drawHead(gl, glu, glut, tAnim);
            drawBody(gl, glu, glut, tAnim);
            drawLegs(gl, glu, glut, tAnim);
            drawArms(gl, glu, glut, tAnim);
        gl.glPopMatrix();
    }
    
    public void drawHead(GL2 gl, GLU glu, GLUT glut, float tAnim)
    {
        gl.glPushMatrix();
        
        gl.glTranslatef(0, 0, legsHeight + bodyHeight);
        //draw neck
        gl.glColor3d(1, 0, 0);
        glut.glutSolidCylinder(headNeckWidth, headNeckHeight, 10, 8);
        //draw head
        gl.glColor3d(1, 1, 0);
        float radius = headHeight - headNeckHeight;
        gl.glTranslatef(0, 0, headNeckHeight + radius);
        glut.glutSolidSphere(radius, 12, 8);
        //draw nose
        gl.glTranslatef(0, radius, 0);
        gl.glRotated(10, 1, 0, 0);
        gl.glColor3d(0, 1, 1);
        glut.glutSolidCone(headNose/2, headNose, 10, 8);
        
        gl.glPopMatrix();
    }
    
    public void drawBody(GL2 gl, GLU glu, GLUT glut, float tAnim)
    {
        gl.glPushMatrix();
        
        gl.glTranslatef(0, 0, legsHeight + bodyHeight/2);
        //draw body
        gl.glColor3d(0.5, 0.5, 0);
        gl.glScalef(bodyWidth, bodyDepth, bodyHeight);
        glut.glutSolidCube(1.0f);
        
        gl.glPopMatrix();
    }
    
    public void drawLegs(GL2 gl, GLU glu, GLUT glut, float tAnim)
    {
        gl.glColor3d(0.5, 0.5, 0.5);
        //draw left leg
        gl.glPushMatrix();
        gl.glTranslatef(-legsDistance, 0, 0);
        drawLeg(gl, glu, glut, tAnim);
        gl.glPopMatrix();
        //draw right leg
        gl.glPushMatrix();
        gl.glTranslatef(legsDistance, 0, 0);
        drawLeg(gl, glu, glut, tAnim);
        gl.glPopMatrix();
    }
    
    public void drawLeg(GL2 gl, GLU glu, GLUT glut, float tAnim)
    {
        gl.glPushMatrix();

        gl.glTranslatef(0,0, legsHeight);
        gl.glRotatef(legsUpperDefaultAngle, 1, 0, 0);
        // draw butt 
        glut.glutSolidSphere(legsRadius, 8, 12);
        glut.glutSolidCylinder(legsRadius, -legsUpperHeight, 8, 12);
        gl.glTranslatef(0, 0, -legsUpperHeight);
        glut.glutSolidSphere(legsRadius, 8, 12);
        //draw knee
        glut.glutSolidSphere(legsRadius, 8, 12);
        //draw under leg
        gl.glRotatef(-2 * legsUpperDefaultAngle, 1, 0, 0);
        gl.glTranslatef(0, 0, -legsKneeHeight);
        glut.glutSolidCylinder(legsRadius, legsKneeHeight, 8, 12);
        glut.glutSolidSphere(legsRadius, 8, 12);
        // draw shoe
        gl.glRotatef(legsUpperDefaultAngle, 1, 0, 0);
        gl.glTranslatef(0, legsShoeDepth/2 - legsRadius, -legsShoeHeight/2);
        gl.glScalef(legsRadius * 2, legsShoeDepth, legsShoeHeight);
        glut.glutSolidCube(1.0f);
        
        gl.glPopMatrix();
    }
    
    public void drawArms(GL2 gl, GLU glu, GLUT glut, float tAnim)
    {
        gl.glColor3d(0, 0.5, 0.5);
        //draw left leg
        gl.glPushMatrix();
        gl.glTranslatef(-armDistance, 0, 0);
        drawArm(gl, glu, glut, tAnim);
        gl.glPopMatrix();
        //draw right leg
        gl.glPushMatrix();
        gl.glTranslatef(armDistance, 0, 0);
        drawArm(gl, glu, glut, tAnim);
        gl.glPopMatrix();
    }
    
    public void drawArm(GL2 gl, GLU glu, GLUT glut, float tAnim)
    {
        gl.glPushMatrix();

        gl.glTranslatef(0,0, legsHeight + bodyHeight - armsRadius);
        gl.glRotatef(armUpperDefaultAngle, 1, 0, 0);
        // draw butt 
        glut.glutSolidSphere(armsRadius, 8, 12);
        glut.glutSolidCylinder(armsRadius, -armUpperArmLenght, 8, 12);
        //draw elbow
        gl.glTranslatef(0, 0, -armUpperArmLenght);
        gl.glRotatef(-2 * armUpperDefaultAngle, 1, 0, 0);
        glut.glutSolidSphere(armsRadius, 8, 12);
        glut.glutSolidCylinder(armsRadius, -armLowerArmLenght, 8, 12);
        
        gl.glPopMatrix();
    }
}
