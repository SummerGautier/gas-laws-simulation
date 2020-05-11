package models;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Random;
import java.util.stream.Collectors;

import static java.lang.Integer.min;

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
            particle.setXPos(random.nextInt((int)xBounds));
            particle.setYPos((int) random.nextInt((int)yBounds));
            particle.setVelocity(1);

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
            particle.setVelocity(1);

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
            particle.setVelocity(1);

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
            //bounds detection
            if((particle.getXPos() > xBounds - particle.getWidth()) || (particle.getXPos() <= 0)){
                particle.setXVelocity(particle.getXVelocity() * -1);
            }
            if((particle.getYPos() > yBounds - particle.getHeight()) || (particle.getYPos() <= 0)){
                particle.setYVelocity(particle.getYVelocity() * -1);
            }

            particle.setXPos(particle.getXPos() + particle.getXVelocity());
            particle.setYPos(particle.getYPos() + particle.getYVelocity());
        }
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
