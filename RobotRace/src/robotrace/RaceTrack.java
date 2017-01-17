package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import static javax.media.opengl.GL2.*;

/**
 * Implementation of a race track that is made from Bezier segments.
 */
abstract class RaceTrack {
    
    /** The width of one lane. The total width of the track is 4 * laneWidth. */
    private final static float laneWidth = 1.22f;
    private final static float stepSize = 0.005f;
    
    
    /**
     * Constructor for the default track.
     */
    public RaceTrack() {
    }


    
    /**
     * Draws this track, based on the control points.
     */
    public void draw(GL2 gl, GLU glu, GLUT glut) {
        // render top part
        gl.glPushMatrix();
        ShaderPrograms.trackShader.useProgram(gl);
        Textures.track.enable(gl);
        Textures.track.bind(gl);
        for(int lane = 0; lane < 4; lane++)
        {
            //gl.glBegin(GL_LINE_STRIP);	
            gl.glBegin(GL_TRIANGLE_STRIP);	
            boolean tex = false;
            for(float i = 0; i <= 1.0f; i+= stepSize) {
                Vector vector = getLanePoint(lane, i);
                Vector tangent = getLaneTangent(lane, i);
                Vector normal = tangent.cross(Vector.Z).normalized();
                Vector vector1 = getLanePoint(lane, i).add(normal.scale(laneWidth/2));
                Vector vector2 = getLanePoint(lane, i).add(normal.scale(-laneWidth/2));
                gl.glTexCoord2d(0, tex?1:0);
                gl.glVertex3d( vector2.x, vector2.y, vector2.z ); 
                gl.glTexCoord2d(1, tex?1:0);
                gl.glVertex3d( vector1.x, vector1.y, vector1.z ); 
                tex = !tex;
            }
            gl.glEnd();
        }
        Textures.track.disable(gl);
        gl.glPopMatrix();
        //render inner side
        //gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        //gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        Textures.brick.enable(gl);
        Textures.brick.bind(gl);
        gl.glPushMatrix();
        //gl.glBegin(GL_LINE_STRIP);	
        gl.glColor3d(1, 1, 0);
        gl.glBegin(GL_TRIANGLE_STRIP);	

        for(float i = 0; i <= 1.0f; i+= stepSize) {
            Vector vector = getLanePoint(0, i);
            Vector tangent = getLaneTangent(0, i);
            Vector normal = tangent.cross(Vector.Z).normalized();
            Vector vector1 = getLanePoint(0, i).add(normal.scale(-laneWidth/2));
            gl.glTexCoord2f(i * 20 % 1, 0);
            gl.glVertex3d( vector1.x, vector1.y, vector1.z ); 
            gl.glTexCoord2f(i * 20 % 1, 1);
            gl.glVertex3d( vector1.x, vector1.y, vector1.z-2 ); 

        }
        gl.glEnd();
        gl.glPopMatrix();
        //render outside 
        //render inner side
        gl.glPushMatrix();
        //gl.glBegin(GL_LINE_STRIP);	
        gl.glColor3d(0, 1, 0);
        gl.glBegin(GL_TRIANGLE_STRIP);	

        for(float i = 0; i <= 1.0f; i+= stepSize) {
            Vector vector = getLanePoint(3, i);
            Vector tangent = getLaneTangent(3, i);
            Vector normal = tangent.cross(Vector.Z).normalized();
            Vector vector1 = getLanePoint(3, i).add(normal.scale(laneWidth/2));
            gl.glTexCoord2f(i * 20 % 1, 0);
            gl.glVertex3d( vector1.x, vector1.y, vector1.z ); 
            gl.glTexCoord2f(i * 20 % 1, 1);
            gl.glVertex3d( vector1.x, vector1.y,vector1.z -2); 

        }
        gl.glEnd();
        
        gl.glPopMatrix();
        Textures.brick.disable(gl);
    }
    
    /**
     * Returns the center of a lane at 0 <= t < 1.
     * Use this method to find the position of a robot on the track.
     */
    public Vector getLanePoint(int lane, double t){
        Vector tangent = getLaneTangent(0, t);
        Vector normal = tangent.cross(Vector.Z).normalized();
        return getPoint(t).add(normal.scale(laneWidth * lane));

    }
    
    /**
     * Returns the tangent of a lane at 0 <= t < 1.
     * Use this method to find the orientation of a robot on the track.
     */
    public Vector getLaneTangent(int lane, double t){
        if (lane == 0){ 
            return getTangent(t);
        }
        else {// on other lanes tangent is changed
            return getLanePoint(lane, t + 0.001).subtract(getLanePoint(lane, t));
        }
    }
    
    
    
    // Returns a point on the test track at 0 <= t < 1.
    protected abstract Vector getPoint(double t);

    // Returns a tangent on the test track at 0 <= t < 1.
    protected abstract Vector getTangent(double t);
}
