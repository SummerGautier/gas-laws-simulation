package models;

import javafx.scene.shape.Circle;

public class Particle extends Circle {
    private double weight;
    private int xPos;
    private int yPos;

    /**
     * instantiate a new 2D particle with weight (in amu), and starting position
     * @param weight, size of particle in amu
     * @param startXPos, starting x position
     * @param startYPos, starting y position
     */
    public Particle(int startXPos, int startYPos, double weight){
        super(startXPos, startYPos, weight);
        this.weight = this.getRadius();
    }

    /**
     * instantiate a new particle with specified weight (in amu), default position (0,0)
     * @param weight, size of particle in amu
     */
    public Particle(double weight){
        super( 0 + 2 * weight, 0 + 2 * weight, weight);
        this.weight = this.getRadius();
    }
    /**
     * instantiate a new particle with default weight (1 amu) and default position (0,0)
     */
    public Particle(){
        super(10, 10, 5);
        this.weight = this.getRadius();
    }

    /**
     * JSON of info about the particle
     * @return
     */
    @Override
    public String toString() {
        return "'Particle'{" +
                "'weight':" + weight +
                ", 'xPos':" + xPos +
                ", 'yPos':" + yPos +
                '}';
    }
}
