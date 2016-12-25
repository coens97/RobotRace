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
    private final static float stepSize = 0.002f;
    
    
    /**
     * Constructor for the default track.
     */
    public RaceTrack() {
    }


    
    /**
     * Draws this track, based on the control points.
     */
    public void draw(GL2 gl, GLU glu, GLUT glut) {
        //Vector lastVector1, lastVector2;
        gl.glBegin(GL_TRIANGLE_STRIP);	
        for(float i = 0; i <= 1.0f; i+= stepSize) {
            Vector vector = getLanePoint(0, i);
            Vector vector1 = getLanePoint(0, i).add(new Vector(-0.5 * laneWidth, 0, 0));
            Vector vector2 = getLanePoint(0, i).add(new Vector( 0.5 * laneWidth, 0, 0));
            gl.glVertex3d( vector1.x, vector1.y, vector1.z ); 
            gl.glVertex3d( vector2.x, vector2.y, vector2.z ); 
            
        }
        gl.glEnd();
    }
    
    /**
     * Returns the center of a lane at 0 <= t < 1.
     * Use this method to find the position of a robot on the track.
     */
    public Vector getLanePoint(int lane, double t){
        return getPoint(t);

    }
    
    /**
     * Returns the tangent of a lane at 0 <= t < 1.
     * Use this method to find the orientation of a robot on the track.
     */
    public Vector getLaneTangent(int lane, double t){
        
        return Vector.O;

    }
    
    
    
    // Returns a point on the test track at 0 <= t < 1.
    protected abstract Vector getPoint(double t);

    // Returns a tangent on the test track at 0 <= t < 1.
    protected abstract Vector getTangent(double t);
}
