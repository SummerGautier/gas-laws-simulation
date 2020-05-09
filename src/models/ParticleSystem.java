package models;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.stream.Collectors;

import static java.lang.Integer.min;

public class ParticleSystem {
    private ArrayList<Particle> particles;
    private double volume;
    private double temperature;
    private double pressure;
    private double avgKineticSpeed;

    private final int MAX_PARTICLES = 500;

    /**
     * instantiates a Particle system with
     * The minimum of: the max allowed particles OR the number of particles requested
     * All Particles start at the specified x and y position
     * @param numberOfParticles, the number of system particles requested
     * @param particleWeight, the size/weight of reach particle (in amu)
     * @param xPos, the starting x position of the particle system
     * @param yPos, the starting y positio of the particle system
     */
    public ParticleSystem(int numberOfParticles, int particleWeight, int xPos, int yPos){
        this.particles = new ArrayList<Particle>(MAX_PARTICLES);
        //add the minimum of the maximum allows particles OR the number of particles requested.
        for (int i = 0; i < min(numberOfParticles, MAX_PARTICLES); i++) {
            this.particles.add(new Particle(particleWeight, xPos, yPos));
        }
    }
    /**
     * instantiates a Particle system with
     * The minimum of: the max allowed particles OR the number of particles requested
     * @param numberOfParticles, the number of system particles requested
     * @param particleWeight, the size/weight of each particle (in amu)
     * @return a particle system with the
     */
    public ParticleSystem(int numberOfParticles, int particleWeight){
        this.particles = new ArrayList<Particle>(MAX_PARTICLES);
        //add the minimum of the maximum allows particles OR the number of particles requested.
        for (int i = 0; i < min(numberOfParticles, MAX_PARTICLES); i++) {
            this.particles.add(new Particle(particleWeight));
        }
    }

    /**
     * instantiate a particle system with
     * the minimum of: the max allowed particles OR the number of particles requested
     * @param numberOfParticles, the number of system particles requested
     */
    public ParticleSystem(int numberOfParticles){
        this.particles = new ArrayList<Particle>(MAX_PARTICLES);
        //add the minimum of the maximum allows particles OR the number of particles requested.
        for (int i = 0; i < min(numberOfParticles, MAX_PARTICLES); i++) {
            this.particles.add(new Particle());
        }
    }

    /**
     * instantiates a Particle System with the max number of particles
     */
    public ParticleSystem(){
        this.particles = new ArrayList<Particle>(MAX_PARTICLES);
    }

    /**
     * array list of particles in the system
     * @return
     */
    public ArrayList<Particle> getParticles() {
        return particles;
    }

    /**
     * average kinetic speed of particles in the system
     * @return
     */
    public double getAvgKineticSpeed() {
        return avgKineticSpeed;
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

    /**
     * adds a new particle to the particle system
     * @param particle, the particle object to add to this system
     */
    public void addParticle(Particle particle){
        this.particles.add(particle);
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
                ",\n\t'avgKineticSpeed':" + avgKineticSpeed +
                ",\n\t'MAX_PARTICLES':" + MAX_PARTICLES+
                "\n}";
    }
}