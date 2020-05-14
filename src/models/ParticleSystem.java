package models;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import physics.Collision;
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
    protected Color strokeColor;

    protected ListIterator<Particle> particleIterator;
    protected ArrayList<Particle> particles;

    private final Random random;
    private final int MAX_PARTICLES = 300;



    /**
     * instantiates a Particle System with the max number of particles
     */
    public ParticleSystem(){
        this.particles = new ArrayList<Particle>();
        this.random = new Random();
        this.color = Color.RED;
        this.strokeColor = Color.BLACK;
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
            particle.setWeight(10);
            particle.setColor(this.color);
            particle.setStroke(this.strokeColor);
            particle.setVelocity(new Vector2(1,1));
            //Coool shadow effect! (gives the particles depth)
            DropShadow particleShadow = new DropShadow();
            particleShadow.setRadius(particle.getRadius());
            particleShadow.setOffsetX(1);
            particleShadow.setOffsetY(1);
            particle.setEffect(particleShadow);
            particle.setCache(true);

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
        if(Collision.isHorizontalColliding(particle, xBounds)){
            particle.negateXVelocity();
        }
        //check Y Bounds for collision
        if(Collision.isVerticalColliding(particle, yBounds)){
            particle.negateYVelocity();
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
            if(Collision.isColliding(particle, otherParticle)){
                Collision.resolveCollision(particle, otherParticle);
            }
        }
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
     * Sets the size of the particles[] array to the specified number
     * @param numberOfParticles, total number of particles that should be in the system
     */
    public void setNumberOfParticles(int numberOfParticles){
        //if greater than max size, do nothing
        if(numberOfParticles > MAX_PARTICLES){ return; }
        //if less than size of particles[], subtract difference from particles
        if(numberOfParticles < this.particles.size()){
            this.removeParticles(this.particles.size() - numberOfParticles);
        }
        //if greater than size of particles[]
        if(numberOfParticles > this.particles.size()){
            this.add(numberOfParticles - this.particles.size());
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
