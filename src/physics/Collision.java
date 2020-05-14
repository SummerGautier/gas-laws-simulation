package physics;

import models.Particle;

/**
 * About: This class handles all collision-centered logic
 */
public abstract class Collision {

    public static boolean isColliding(Particle particle1, Particle particle2){
        if(Vector2.distance(particle1.getPosVector(), particle2.getPosVector()) <= particle1.getRadius()+particle2.getRadius()){
            return true;
        }
        return false;
    }
    public static void resolveCollision(Particle particle1, Particle particle2){
        Vector2 tangentVector = new Vector2();
        //vector perpendicular to (x,y) is (-y, x)
        tangentVector.setY(-(particle2.getXPos() - particle1.getXPos()));
        tangentVector.setX(particle2.getYPos() - particle1.getYPos());
        //normalize tangent vector
        tangentVector.normalize();
        //calculate relative velocity
        Vector2 relativeVelocity = new Vector2(particle1.getXVelocity() - particle2.getXVelocity(), particle1.getYVelocity() - particle2.getYVelocity());
        //get length of the velocity component parallel to the tangent
        double length = Vector2.dotProduct(relativeVelocity, tangentVector);
        if(length > 0){
            return;
        }
        //multiply noramlized tangent vector by length (scalar) to get the vector componenet parallele ot the tangent
        Vector2 velocityComponentOnTangent = Vector2.multiply(tangentVector, length);
        //substracting parallel component veloicty from the relative velocity gives us the perpendicular component
        Vector2 velocityComponentPerpendicularToTangent = Vector2.subtract(relativeVelocity, velocityComponentOnTangent);
        //adjust particle velocities

        //particle 1
        particle1.setXVelocity(particle1.getXVelocity() - velocityComponentPerpendicularToTangent.getX());
        particle1.setYVelocity(particle1.getYVelocity() - velocityComponentPerpendicularToTangent.getY());
        //particle 2
        particle2.setXVelocity(particle2.getXVelocity() + velocityComponentPerpendicularToTangent.getX());
        particle2.setYVelocity(particle2.getYVelocity() + velocityComponentPerpendicularToTangent.getY());
    }

    /**
     * Determines if particle is colliding with "wall" in the up/down direction
     * @param particle, the particle to check
     * @param boundary, the position of the boundary in the y axis
     * @return
     */
    public static boolean isVerticalColliding(Particle particle, double boundary){
        if(particle.getYPos() + particle.getYVelocity() > boundary - particle.getRadius() || particle.getYPos() + particle.getYVelocity() < particle.getRadius()){
            return true;
        }
        return false;
    }

    /**
     * Determines if particle is colliding with "wall" in the left/right direction
     * @param particle
     * @param boundary
     * @return
     */
    public static boolean isHorizontalColliding(Particle particle, double boundary){
        if(particle.getXPos() + particle.getXVelocity() > boundary - particle.getRadius() || particle.getXPos() + particle.getXVelocity() < particle.getRadius()){
            return true;
        }
        return false;
    }
}
