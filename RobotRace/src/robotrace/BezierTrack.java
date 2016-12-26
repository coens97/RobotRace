
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
        t = t % 1;
        return getCubicBezierPnt(t, controlPoints[0], controlPoints[1], controlPoints[2], controlPoints[3]);

    }

    @Override
    protected Vector getTangent(double t) {
        t = t % 1;
        return getCubicBezierTng(t, controlPoints[0], controlPoints[1], controlPoints[2], controlPoints[3]);

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
        return P1.subtract(P0)
                .scale(3*it*it)
                .add(
                        P2.subtract(P1)
                                .scale(6*it*t))
                .add(
                        P3.subtract(P2)
                                .scale(3*t*t));
    }
}
