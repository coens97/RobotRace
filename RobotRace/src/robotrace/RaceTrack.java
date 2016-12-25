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
    private final static float stepSize = 0.0125f;
    
    
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
        for(int lane = 0; lane < 4; lane++)
        {
            //gl.glBegin(GL_LINE_STRIP);	
            gl.glBegin(GL_TRIANGLE_STRIP);	

            for(float i = 0; i <= 1.0f; i+= stepSize) {
                Vector vector = getLanePoint(lane, i);
                Vector tangent = getLaneTangent(lane, i);
                Vector normal = tangent.cross(Vector.Z).normalized();
                Vector vector1 = getLanePoint(lane, i).add(normal.scale(laneWidth/2));
                Vector vector2 = getLanePoint(lane, i).add(normal.scale(-laneWidth/2));
                gl.glVertex3d( vector2.x, vector2.y, vector2.z ); 
                gl.glVertex3d( vector1.x, vector1.y, vector1.z ); 

            }
            gl.glEnd();
        }
        gl.glPopMatrix();
        //render inner side
        gl.glPushMatrix();
        //gl.glBegin(GL_LINE_STRIP);	
        gl.glColor3d(1, 1, 0);
        gl.glBegin(GL_TRIANGLE_STRIP);	

        for(float i = 0; i <= 1.0f; i+= stepSize) {
            Vector vector = getLanePoint(0, i);
            Vector tangent = getLaneTangent(0, i);
            Vector normal = tangent.cross(Vector.Z).normalized();
            Vector vector1 = getLanePoint(0, i).add(normal.scale(-laneWidth/2));
            gl.glVertex3d( vector1.x, vector1.y, vector1.z ); 
            gl.glVertex3d( vector1.x, vector1.y, -1 ); 

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
            gl.glVertex3d( vector1.x, vector1.y, vector1.z ); 
            gl.glVertex3d( vector1.x, vector1.y, -1 ); 

        }
        gl.glEnd();
        gl.glPopMatrix();
    }
    
    /**
     * Returns the center of a lane at 0 <= t < 1.
     * Use this method to find the position of a robot on the track.
     */
    public Vector getLanePoint(int lane, double t){
        Vector tangent = getLaneTangent(lane, t);
        Vector normal = tangent.cross(Vector.Z).normalized();
        return getPoint(t).add(normal.scale(laneWidth * lane));

    }
    
    /**
     * Returns the tangent of a lane at 0 <= t < 1.
     * Use this method to find the orientation of a robot on the track.
     */
    public Vector getLaneTangent(int lane, double t){
        
        return getTangent(t);

    }
    
    
    
    // Returns a point on the test track at 0 <= t < 1.
    protected abstract Vector getPoint(double t);

    // Returns a tangent on the test track at 0 <= t < 1.
    protected abstract Vector getTangent(double t);
}
