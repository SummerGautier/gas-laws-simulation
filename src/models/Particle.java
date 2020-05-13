package models;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Particle extends Circle {
    private double weight;
    private double xVelocity;
    private double yVelocity;
    private final double MIN_WEIGHT = 2;


    /* CONSTRUCTORS */
    /**
     * instantiate a new 2D particle with weight (in amu), and starting position
     * @param weight, size of particle in amu
     * @param startXPos, starting x position
     * @param startYPos, starting y position
     */
    public Particle(int startXPos, int startYPos, double weight){
        super(startXPos, startYPos, weight);
        this.weight = this.getRadius();
        this.setXYVelocity(1);
    }

    /**
     * instantiate a new particle with specified weight (in amu), default position (0,0)
     * @param weight, size of particle in amu
     */
    public Particle(double weight){
        super( 0 + 2 * weight, 0 + 2 * weight, weight);
        this.weight = this.getRadius();
        this.setXYVelocity(1);
    }
    /**
     * instantiate a new particle with default weight (1 amu) and default position (0,0)
     */
    public Particle(){
        super(10,10,5);
        this.weight = this.getRadius();
        this.setXYVelocity(1);
    }


    /* GET METHODS */
    public double getWeight() {
        return weight;
    }
    public double getXPos(){
        return (this.getCenterX() - this.getRadius());
    }
    public double getYPos(){
        return (this.getCenterY() - this.getRadius());
    }
    public double getWidth(){
        return (2 * this.getRadius());
    }
    public double getHeight(){
        return (2 * this.getRadius());
    }
    public double getXVelocity(){
        return this.xVelocity;
    }
    public double getYVelocity(){
        return this.yVelocity;
    }
    public double getVelocity(){
        return Math.sqrt(Math.pow(this.getXVelocity(), 2) + Math.pow(this.getYVelocity(),2));
    }
    public Paint getColor(){
        return this.getFill();
    }


    /* SET METHODS */
    public void setWeight(double weight) {
        this.weight = weight;
        this.setRadius(weight);
    }
    public void setXYVelocity(double velocity) {
        this.xVelocity = this.yVelocity = velocity;
    }
    public void setXVelocity(double velocity){
        this.xVelocity = velocity;
    }
    public void setYVelocity(double velocity){
        this.yVelocity = velocity;
    }
    public void setXPos(double xPos){
        this.setCenterX(xPos + this.getRadius());
    }
    public void setYPos(double yPos){
        this.setCenterY(yPos + this.getRadius());
    }
    public void setColor(Color color){
        this.setFill(color);
    }
    /**
     * JSON of info about the particle
     * @return
     */
    @Override
    public String toString() {
        return "'Particle'{" +
                "'weight':" + weight +
                ", 'xPos':" + getYPos() +
                ", 'yPos':" + getXPos() +
                '}';
    }
}
