package models;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Random;
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


    /* CONSTRUCTORS */

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

    /* INIT METHODS */
    /**
     * initialize particles in random positions
     */
    public void init(double xBounds, double yBounds){
        //init particle pos, speed, color etc.
        for(Particle particle : this.particles) {
            particle.setColor(this.color);
            particle.setXPos(random.nextInt((int)(xBounds - particle.getWidth())));
            particle.setYPos((int) random.nextInt((int)(yBounds - particle.getHeight())));
            particle.setXVelocity((int)random.nextInt(15)-5);
            particle.setYVelocity((int)random.nextInt(15)-5);
            particle.setWeight(10);
        }
    }
    /**
     * intialize particles at specified starting position
     * @param x, x position
     * @param y, y position
     */
    public void init(double xBounds, double yBounds, double x, double y){
        x = (x > xBounds || x < 0)? xBounds/2 : x;
        y = (y > yBounds || y < 0)? yBounds/2 : y;
        //init particle pos, speed, color etc.
        for(Particle particle : this.particles) {
            particle.setColor(this.color);
            particle.setXPos(x);
            particle.setYPos(y);
            particle.setXVelocity(1);
            particle.setYVelocity(1);

        }
    }
    /**
     * initliaze each particle at specified corresponding position in a collection of position(s)
     * @param positions, a collection of particle positions
     */
    public void init(HashMap<Integer, HashMap<Double,Double>> positions){
        particleIterator = this.getParticles().listIterator();
        while(particleIterator.hasNext()) {
            //get particle position from collection
            HashMap<Double,Double> position = positions.get(particleIterator.nextIndex());
            double x = position.get(0);
            double y = position.get(1);

            //set particle data
            Particle particle = particleIterator.next();
            particle.setColor(this.color);
            particle.setXPos(x);
            particle.setYPos(y);
            particle.setXVelocity(1);
            particle.setYVelocity(1);

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
    /**
     * update position of particles
     * @param xBounds, max x boundary of animation pane
     * @param yBounds, max y boundary of animation pane
     */
    public void updateParticlePositions(double xBounds, double yBounds){
        for(Particle particle : this.particles) {

            //detect particle collision
            //detectParticleCollision(particle, this.particles);

            /*
                TODO: Combine intra-particle collisions with bounds
             */
            //particle collision detection
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

        //if already out of bounds, set to negative and
        //check X Bounds for collision
        if((particle.getXPos() > xBounds - particle.getWidth()) || (particle.getXPos() <= 0)){
            particle.setXVelocity(particle.getXVelocity() * -1);
        }
        //check Y Bounds for collision
        if((particle.getYPos() > yBounds - particle.getHeight()) || (particle.getYPos() <= 0)){
            particle.setYVelocity(particle.getYVelocity() * -1);
        }
    }
    private void detectParticleCollision(Particle particle, ArrayList<Particle> particles){
        particleIterator = particles.listIterator();
        while(particleIterator.hasNext()){
            Particle otherParticle = particleIterator.next();
            if(Math.pow((otherParticle.getCenterX()-particle.getCenterX()),2) + Math.pow((particle.getCenterY()-otherParticle.getCenterY()),2) <= Math.pow(particle.getRadius()+otherParticle.getRadius(),2)){
                adjustVelocityPostCollision(particle, otherParticle);
            }
        }
    }

    private void adjustVelocityPostCollision(Particle particle1, Particle particle2){
        /*
            EQUATION REFERENCE: https://williamecraver.wixsite.com/elastic-equations
            ALSO SEE: https://en.wikipedia.org/wiki/Elastic_collision
         */
        /*
            TODO: calculate PHI and incorporate multi-point collision
         */
        //mass
        double m1 = particle1.getWeight();
        double m2 = particle2.getWeight();
        //velocity
        double v1 = particle1.getVelocity();
        double v2 = particle2.getVelocity();
        //angles
        double theta1 = Math.acos(particle1.getXVelocity()/particle1.getVelocity());
        double theta2 = Math.acos(particle2.getXVelocity()/particle2.getVelocity());
        double phi = -0.5;
        //rotated velocities
        double v1Xr = v1*cos(theta1-phi);
        double v1Yr = v1*sin(theta1-phi);
        double v2Xr = v2*cos(theta2-phi);
        double v2Yr = v2*sin(theta2-phi);
        //solved ROTATED x components of velocity in collision equation
        double v1FXr = ((v1Xr)*(m1-m2)+(2*m2*v2Xr))/(m1+m2);
        double v2FXr = ((v2Xr)*(m2-m1)+(2*m1*v1Xr))/(m1+m2);
        //final UN-ROTATED x and y components of velocity in collision equation
        double v1FX = (v1FXr*cos(phi)) + (v1Yr*cos(phi+(PI/2)));
        double v1FY = (v1FXr*sin(phi)) + (v1Yr*sin(phi+(PI/2)));
        double v2FX = (v2FXr*cos(phi)) + (v2Yr*cos(phi+(PI/2)));
        double v2FY = (v2FXr*sin(phi)) + (v2Yr*sin(phi+(PI/2)));

        //set new x and y velocity of particles
        particle1.setXVelocity(v1FX);
        particle1.setYVelocity(v1FY);
        particle2.setXVelocity(v2FX);
        particle2.setYVelocity(v2FY);
    }


    /* BUSINESS METHODS */
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

    /* ABSTRACT METHODS */
    public abstract void calculateParticleVelocities();

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
}
