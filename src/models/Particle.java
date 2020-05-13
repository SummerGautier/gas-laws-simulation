package models;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import physics.Vector2;

public class Particle extends Circle {
    private Vector2 velocity;

    /* CONSTRUCTORS */
    /**
     * instantiate a new 2D particle with weight (in amu), and starting position
     * @param weight, size of particle in amu
     * @param startXPos, starting x position
     * @param startYPos, starting y position
     */
    public Particle(int startXPos, int startYPos, double weight){
        super(startXPos, startYPos, weight);
    }
    /**
     * instantiate a new particle with specified weight (in amu), default position (0,0)
     * @param weight, size of particle in amu
     */
    public Particle(double weight){
        super( 0 + 2 * weight, 0 + 2 * weight, weight);
    }
    /**
     * instantiate a new particle with default weight (1 amu) and default position (0,0)
     */
    public Particle(){
        super(50,50,5);
    }


    /* GET METHODS */
    //position
    public double getXPos(){
        return this.getCenterX();
    }
    public double getYPos(){
        return this.getCenterY();
    }
    public Vector2 getPosVector(){return new Vector2(this.getXPos(), this.getYPos());}
    //velocity
    public double getXVelocity(){
        return this.velocity.getX();
    }
    public double getYVelocity(){
        return this.velocity.getY();
    }
    public Vector2 getVelocity(){
        return this.velocity;
    }
    //weight
    public double getWeight() {
        return this.getRadius();
    }
    //color
    public Paint getColor(){
        return this.getFill();
    }


    /* SET METHODS */
    //position
    public void setXPos(double xPos){
        this.setCenterX(xPos);
    }
    public void setYPos(double yPos){
        this.setCenterY(yPos);
    }
    public void setPosition(Vector2 position){ this.setCenterX(position.getX()); this.setCenterY(position.getY());}
    //velocity
    public void setXVelocity(double velocity){
        this.velocity.setX(velocity);
    }
    public void setYVelocity(double velocity){
        this.velocity.setY(velocity);
    }
    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }
    //weight
    public void setWeight(double weight) {
        this.setRadius(weight);
    }
    //color
    public void setColor(Color color){
        this.setFill(color);
    }

    /* Other */
    public void negatePos(){
        this.setCenterX(this.getCenterX() * -1);
        this.setCenterY(this.getCenterY() * -1);
    }
    public void invertVelocity(){
        this.velocity.invert();
    }
    public void negateVelocity(){
        this.velocity.negate();
    }
    public void negateXVelocity(){this.velocity.setX(this.getXVelocity() *-1);}
    public void negateYVelocity(){this.velocity.setY(this.getYVelocity() *-1);}


    /**
     * JSON of info about the particle
     * @return
     */
    @Override
    public String toString() {
        return "'Particle'{" +
                "'weight':" + this.getRadius() +
                ", 'xPos':" + getYPos() +
                ", 'yPos':" + getXPos() +
                '}';
    }
}
