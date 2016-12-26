
package robotrace;

/**
 * Implementation of RaceTrack, creating a track from control points for a 
 * cubic Bezier curve
 */
public class BezierTrack extends RaceTrack {
    
    private Vector[] controlPoints;

    
    BezierTrack(Vector[] controlPoints) {
        this.controlPoints = controlPoints;
        


    }
    
    @Override
    protected Vector getPoint(double t) {
        t %= 1; // only allow input 0..1
        int nrFragments = controlPoints.length / 4;
        int fragment = (int)Math.floor(t * nrFragments)*4;
        t *= nrFragments;
        t %= 1;
        return getCubicBezierPnt(t, controlPoints[fragment], controlPoints[fragment + 1], controlPoints[fragment + 2], controlPoints[fragment + 3]);
    }

    @Override
    protected Vector getTangent(double t) {
        t %= 1; // only allow input 0..1
        int nrFragments = controlPoints.length / 4;
        int fragment = (int)Math.floor(t * nrFragments)*4;
        t *= nrFragments;
        t %= 1;
        return getCubicBezierTng(t, controlPoints[fragment], controlPoints[fragment + 1], controlPoints[fragment + 2], controlPoints[fragment + 3]);

    }
    
    public Vector getCubicBezierPnt(double t, Vector P0, Vector P1, Vector P2, Vector P3) {
        double it = 1 - t;
        return P0.scale(it*it*it)
                .add(P1.scale(3*t*it*it))
                .add(P2.scale(3*t*t*it))
                .add(P3.scale(t*t*t));
    }
    
    public Vector getCubicBezierTng(double t, Vector P0, Vector P1, Vector P2, Vector P3) {
        double it = 1 - t;
        return P0.scale(-3*it*it)
                .add(P1.scale(3*(t-1)*(3*t-1)))
                .add(P2.scale(6*t - 9*t*t))
                .add(P3.scale(3*t*t)).normalized();
    }
}
