package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import java.util.Random;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import static javax.media.opengl.GL2.*;

/**
* Represents a Robot, to be implemented according to the Assignments.
*/
class Robot {
    
    /** The position of the robot. */
    public Vector position;
    
    /** The direction in which the robot is running. */
    public Vector direction = new Vector(1, 1, 0);
    private Vector defaultDirection = new Vector(1, 0, 0);

    /** The material from which this robot is built. */
    private final Material material;
    
    private float walkingSpeed = 6.0f;
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
    private int legsUpperKickAngle = 18;
    private float legsUpperHeight = legsHeight - legsKneeHeight - legsShoeHeight;
    
    private float robotHeight = headHeight + bodyHeight +legsHeight;
    
    private float armsRadius = 0.08f;
    private float armDistance = (bodyWidth + armsRadius) / 2;
    private int armUpperDefaultAngle = -5;
    private int armUpperSwingAngle = 15; 
    private float armUpperArmLenght = 0.2f;
    private float armLowerArmLenght = 0.2f;
    private float randomNumber;
    public float extraDistance = 0;
    private float sprintSpeed = 0.05f;

    /**
     * Constructs the robot with initial parameters.
     */
    public Robot(Material material,
       int x, int y, int z     
    ) {
        this.material = material;
        this.position = new Vector(x, y, z);
        Random r = new Random();
        randomNumber = 0.2f + r.nextFloat() * 0.5f;
    }
    
    private void addSprintSpeed(float tAnim)
    {
        if (Math.sin(tAnim * randomNumber) > 0.2)
        {
            extraDistance += sprintSpeed; // no use of time between frames
        }
    }

    /**
     * Draws this robot (as a {@code stickfigure} if specified).
     */
    public void draw(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        addSprintSpeed(tAnim);
        // from gameTime, to [0.0 .. 1.0] then [1.0 .. 0.0] and repeat
        tAnim *= walkingSpeed;
        tAnim = tAnim % 4.0f;
        tAnim -= 2;
        tAnim = Math.abs(tAnim);
        tAnim -= 1;
        
        gl.glPushMatrix();
            gl.glTranslated(-position.x, -position.y, -position.z + robotHeight/2);
            
            direction = direction.normalized();
            Vector up = defaultDirection.cross(direction);
            gl.glRotated(Math.acos(defaultDirection.dot(direction)) / Math.PI * 180, up.x, up.y, up.z);
            gl.glMaterialfv(GL_FRONT, GL_DIFFUSE, this.material.diffuse, 0);
            gl.glMaterialfv(GL_FRONT, GL_SPECULAR, this.material.specular, 0);
            gl.glMaterialf(GL_FRONT, GL_SHININESS, this.material.shininess);
            
            ShaderPrograms.robotShader.useProgram(gl);
            drawHead(gl, glu, glut, tAnim);
            drawBody(gl, glu, glut, tAnim);
            drawLegs(gl, glu, glut, tAnim);
            drawArms(gl, glu, glut, tAnim);
        gl.glPopMatrix();
    }
    
    public void Rotate(GL2 gl, GLU glu, GLUT glut) {
        
    }
    
    private void drawHead(GL2 gl, GLU glu, GLUT glut, float tAnim)
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
    
    private void drawBody(GL2 gl, GLU glu, GLUT glut, float tAnim)
    {
        gl.glPushMatrix();
        
        gl.glTranslatef(0, 0, legsHeight + bodyHeight/2);
        //draw body
        gl.glColor3d(0.5, 0.5, 0);
        gl.glScalef(bodyWidth, bodyDepth, bodyHeight);
        glut.glutSolidCube(1.0f);
        
        gl.glPopMatrix();
    }
    
    private void drawLegs(GL2 gl, GLU glu, GLUT glut, float tAnim)
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
        drawLeg(gl, glu, glut, -tAnim);
        gl.glPopMatrix();
    }
    
    private void drawLeg(GL2 gl, GLU glu, GLUT glut, float tAnim)
    {
        gl.glPushMatrix();

        gl.glTranslatef(0,0, legsHeight);
        gl.glRotatef(legsUpperDefaultAngle + legsUpperKickAngle * tAnim, 1, 0, 0);
        // draw butt 
        glut.glutSolidSphere(legsRadius, 8, 12);
        glut.glutSolidCylinder(legsRadius, -legsUpperHeight, 8, 12);
        gl.glTranslatef(0, 0, -legsUpperHeight);
        glut.glutSolidSphere(legsRadius, 8, 12);
        //draw knee
        glut.glutSolidSphere(legsRadius, 8, 12);
        //draw under leg
        gl.glRotatef(-2 * legsUpperDefaultAngle, 1, 0, 0);
        gl.glTranslatef(0, 0, -legsKneeHeight +legsShoeHeight);
        glut.glutSolidCylinder(legsRadius, legsKneeHeight - legsShoeHeight, 8, 12);
        glut.glutSolidSphere(legsRadius, 8, 12);
        // draw shoe
        gl.glRotatef(legsUpperDefaultAngle - legsUpperKickAngle * tAnim, 1, 0, 0);
        gl.glTranslatef(0, legsShoeDepth/2 - legsRadius, -legsShoeHeight/2);
        gl.glScalef(legsRadius * 2, legsShoeDepth, legsShoeHeight);
        glut.glutSolidCube(1.0f);
        
        gl.glPopMatrix();
    }
    
    private void drawArms(GL2 gl, GLU glu, GLUT glut, float tAnim)
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
        drawArm(gl, glu, glut, -tAnim);
        gl.glPopMatrix();
    }
    
    private void drawArm(GL2 gl, GLU glu, GLUT glut, float tAnim)
    {
        gl.glPushMatrix();

        gl.glTranslatef(0,0, legsHeight + bodyHeight - armsRadius);
        gl.glRotatef(armUpperDefaultAngle + armUpperSwingAngle * tAnim, 1, 0, 0);
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
