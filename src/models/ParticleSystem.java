package models;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;
import physics.Vector2;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.min;
import static java.lang.Math.*;

public abstract class ParticleSystem {
    protected double volume;
    protected double temperature;
    protected double pressure;
    protected double moles;

    protected Color color;

    protected ListIterator<Particle> particleIterator;
    protected ArrayList<Particle> particles;

    private final Random random;
    private final int MAX_PARTICLES = 500;



    /**
     * instantiates a Particle System with the max number of particles
     */
    public ParticleSystem(){
        this.particles = new ArrayList<Particle>();
        this.random = new Random();
        this.color = Color.RED;
        this.volume = 1;
        this.temperature = 1;
        this.pressure = 1;
    }

    /**
     * initialize particles in random positions
     */
    public void init(double xBounds, double yBounds){
        //init particle pos, speed, color etc.
        for(Particle particle : this.particles) {
            particle.setWeight(7);
            particle.setColor(this.color);

            particle.setVelocity(new Vector2(2,5));

            particle.setXPos(random.nextInt((int) (xBounds - 2 * particle.getRadius())) + particle.getRadius());
            particle.setYPos(random.nextInt((int) (yBounds - 2 * particle.getRadius())) + particle.getRadius());
        }
    }


    /* GET METHODS */
    /**
     * @return an array list of particles in the system
     */
    public ArrayList<Particle> getParticles() {
        return particles;
    }
    /**
     * system pressure (in atmospheres)
     * @return
     */
    public double getPressure() {
        return pressure;
    }
    /**
     * system tempreature (in degrees Kelvin)
     * @return
     */
    public double getTemperature() {
        return temperature;
    }
    /**
     * system volume (in Liters)
     * @return
     */
    public double getVolume() {
        return volume;
    }
    /**
     * total maximum allowed particles
     * @return
     */
    public int getMAX_PARTICLES() {
        return MAX_PARTICLES;
    }
    public double getMoles() {
        return moles;
    }


    /* UPDATE METHODS */
    /**
     * update the pressure of the system
     * @param pressure, in atmospheres
     */
    public void updatePressure(double pressure) {
        this.pressure = pressure;
    }
    /**
     * update the temperature of the system
     * @param temperature, in degrees Kelvin
     */
    public void updateTemperature(double temperature) {
        this.temperature = temperature;
    }
    /**
     * update the volume of the system
     * @param volume, in Liters
     */
    public void updateVolume(double volume) {
        this.volume = volume;
    }
    public void updateMoles(double moles) {
        this.moles = moles;
    }



    /* PARTICLE MOVEMENT METHODS */
    /**
     * update position of particles
     * @param xBounds, max x boundary of animation pane
     * @param yBounds, max y boundary of animation pane
     */
    public void updateParticlePositions(double xBounds, double yBounds){
        for(Particle particle : this.particles) {

            //detect particle collision
            detectParticleCollision(particle, this.particles);

            //update particle position
            particle.setXPos(particle.getXPos() + particle.getXVelocity());
            particle.setYPos(particle.getYPos() + particle.getYVelocity());

            //detect bounds collisions
            detectBoundsCollision(particle, xBounds, yBounds);

        }
    }

    /**
     * Detect if collision of particle at bounds
     * @param particle, the particle which will have it's position compared
     * @param xBounds, max x value of bounds
     * @param yBounds, max y value of bounds
     */
    private void detectBoundsCollision(Particle particle, double xBounds, double yBounds){
        //check X Bounds for collision
        if(particle.getXPos() + particle.getXVelocity() > xBounds - particle.getRadius() || particle.getXPos() + particle.getXVelocity() < particle.getRadius()){
            particle.setXVelocity(particle.getXVelocity() * -1);
        }
        //check Y Bounds for collision
        if(particle.getYPos() + particle.getYVelocity() > yBounds - particle.getRadius() || particle.getYPos() + particle.getYVelocity() < particle.getRadius()){
            particle.setYVelocity(particle.getYVelocity() * -1);
        }
    }

    /**
     * Detect if collision between particles
     * @param particle, particle to compare
     * @param particles, list of possible colliding particles
     */
    private void detectParticleCollision(Particle particle, ArrayList<Particle> particles){
        particleIterator = particles.listIterator();
        while(particleIterator.hasNext()){
            Particle otherParticle = particleIterator.next();
            if(Math.pow((otherParticle.getCenterX()-particle.getCenterX()),2) + Math.pow((otherParticle.getCenterY()-particle.getCenterY()),2) <= Math.pow(particle.getRadius()+otherParticle.getRadius(),2)){
                resolveCollision(particle, otherParticle);
            }
        }
    }
    private void resolveCollision(Particle particle1, Particle particle2){
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
        //multiply noramlized tangent vector by length (scalar) to get the vector componenet parallele ot the tangent
        Vector2 velocityComponentOnTangent = Vector2.multiply(tangentVector, length);
        //substracting parallel component veloicty from the relative velocity gives us the perpendicular component
        Vector2 velocityComponentPerpendicularToTangent = Vector2.subtract(relativeVelocity, velocityComponentOnTangent);
        //adjust particle velocities

        //particle 1
        particle1.setXVelocity(particle1.getXVelocity() - velocityComponentPerpendicularToTangent.getX());
        particle1.setYVelocity(particle1.getYVelocity() - velocityComponentPerpendicularToTangent.getY());
        //partilce 2
        particle2.setXVelocity(particle2.getXVelocity() + velocityComponentPerpendicularToTangent.getX());
        particle2.setYVelocity(particle2.getYVelocity() + velocityComponentPerpendicularToTangent.getY());

    }


    /* COLLECTION METHODS */
    /**
     * adds a new particle to the particle system
     * @param particle, the particle object to add to this system
     */
    public void add(Particle particle){
        if(this.particles.size() < MAX_PARTICLES){
            particles.add(particle);
        }
    }
    /**
     * Adds the minimum of: the max allowed particles OR the number of particles requested
     * All Particles start at the specified x and y position
     * @param numberOfParticles, the number of system particles requested
     */
    public void add(int numberOfParticles){
        this.particles = new ArrayList<Particle>(MAX_PARTICLES);
        //add the minimum of the maximum allows particles OR the number of particles requested.
        for (int i = 0; i < min(numberOfParticles, MAX_PARTICLES); i++) {
            this.particles.add(new Particle());
        }
    }
    /**
     * adds a collection of particles to the particle system
     * @param newParticles
     */
    public void addAll(ArrayList<Particle> newParticles){
        if(this.particles.size() + newParticles.size() <= MAX_PARTICLES){
            particles.addAll(newParticles);
        }
    }

    /**
     * removes the specified number of particles
     * @param numberOfParticlesToRemove, total number of particles to be removed
     */
    public void removeParticles(int numberOfParticlesToRemove){
        int totalRemoved = 0;
        ListIterator<Particle> particleIterator = this.particles.listIterator();
        while(particleIterator.hasNext() && totalRemoved <= numberOfParticlesToRemove){
            particleIterator.remove();
            totalRemoved++;
        }
    }


    /* STRING METHODS */
    /**
     * JSON of all particles in the system and their data
     * @return
     */
    public String stringifyParticles(){
        return "'ParticleData'{\n\t" +
                particles.stream()
                        .map(Particle::toString)
                        .collect(Collectors.joining(",\n\t"))
                + "\n}";
    }
    /**
     * a JSON over general Particle System Info
     * @return
     */
    @Override
    public String toString() {
        return "'ParticleSystem'{" +
                "\n\t'totalParticles':" + particles.size() +
                ",\n\t'volume':" + volume +
                ",\n\t'temperature':" + temperature +
                ",\n\t'pressure':" + pressure +
                ",\n\t'MAX_PARTICLES':" + MAX_PARTICLES+
                "\n}";
    }



    /* ABSTRACT METHODS */
    public abstract void calculateParticleVelocities();
}
