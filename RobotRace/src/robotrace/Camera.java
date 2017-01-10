package robotrace;

/**
 * Implementation of a camera with a position and orientation. 
 */
class Camera {

    /** The position of the camera. */
    public Vector eye = new Vector(3f, 6f, 5f);

    /** The point to which the camera is looking. */
    public Vector center = Vector.O;

    /** The up vector. */
    public Vector up = Vector.Z;

    /**
     * Updates the camera viewpoint and direction based on the
     * selected camera mode.
     */
    public void update(GlobalState gs, Robot focus) {

        switch (gs.camMode) {
            
            // First person mode    
            case 1:
                setFirstPersonMode(gs, focus);
                break;
                
            // Default mode    
            default:
                setDefaultMode(gs);
        }
    }

    /**
     * Computes eye, center, and up, based on the camera's default mode.
     */
    private void setDefaultMode(GlobalState gs) {
        this.up = Vector.Z;
        this.center = gs.cnt;
        double cameraX = gs.vDist * Math.sin(gs.theta) * Math.cos(gs.phi); 
        double cameraY = gs.vDist * Math.cos(gs.theta) * Math.cos(gs.phi);       
        double cameraZ = gs.vDist * Math.sin(gs.phi);     

        this.eye = new Vector(cameraX , cameraY, cameraZ);
        this.eye.add(center);
        
    }

    /**
     * Computes eye, center, and up, based on the first person mode.
     * The camera should view from the perspective of the robot.
     */
    private void setFirstPersonMode(GlobalState gs, Robot focus) {
        gs.vDist = 4;
        this.up = Vector.Z;
        Vector dir = focus.direction.normalized().cross(Vector.O.subtract(Vector.Z));
        this.eye = Vector.O.subtract(focus.position).add(Vector.Z.scale(2.8)).add(dir.scale(0.5));
        
        this.center = this.eye.add(dir.scale(10));   
    }
}
