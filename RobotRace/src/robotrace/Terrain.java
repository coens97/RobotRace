package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import static javax.media.opengl.GL.GL_TRIANGLE_STRIP;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

/**
 * Represents the terrain, to be implemented according to the Assignments.
 */
class Terrain {
    private float start = -20;
    private float end = 20;
    private float step = 0.5f;
    
    public Terrain() {
        
    }

    /**
     * Draws the terrain.
     */
    public void draw(GL2 gl, GLU glu, GLUT glut) {
        gl.glPushMatrix();
        ShaderPrograms.terrainShader.useProgram(gl);
        for(float x = start; x < end; x += step)
        {
            //gl.glBegin(GL_LINE_STRIP);	
            gl.glBegin(GL_TRIANGLE_STRIP);	
            for(float y = start; y <= end; y += step) {
                gl.glVertex3f(x, y, 0 ); 
                gl.glVertex3f(x + step, y, 0); 
            }
            gl.glEnd();
        }
        gl.glPopMatrix();
        // draw water
        gl.glPushMatrix();
        ShaderPrograms.defaultShader.useProgram(gl);
        gl.glColor4d(0.5, 0.5, 0.5,0.5);
        
        gl.glBegin(GL_TRIANGLE_STRIP);	
            gl.glVertex3f(start, start, 0 ); 
            gl.glVertex3f(start, end, 0); 
            gl.glVertex3f(end, start, 0 ); 
            gl.glVertex3f(end, end, 0); 
        gl.glEnd();
        
        gl.glPopMatrix();
    }
    
}