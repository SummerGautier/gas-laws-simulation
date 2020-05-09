package models;

public class Particle {
    private double weight;
    private int xPos;
    private int yPos;

    /**
     * instantiate a new 2D particle with weight (in amu), and starting position
     * @param weight, size of particle in amu
     * @param startXPos, starting x position
     * @param startYPos, starting y position
     */
    public Particle(double weight, int startXPos, int startYPos){
        this.weight = weight;
        this.xPos = startXPos;
        this.yPos = startYPos;
    }

    /**
     * instantiate a new particle with specified weight (in amu), default position (0,0)
     * @param weight, size of particle in amu
     */
    public Particle(double weight){
        this.weight = weight;
        this.xPos = 0;
        this.yPos = 0;
    }
    /**
     * instantiate a new particle with default weight (1 amu) and default position (0,0)
     */
    public Particle(){
        this.weight = 1;
        this.xPos = 0;
        this.yPos = 0;
    }

    /**
     * weight of particle
     * @return
     */
    public double getWeight() {
        return weight;
    }

    /**
     * current x position of particle
     * @return
     */
    public int getxPos() {
        return xPos;
    }

    /**
     * current y position of particle
     * @return
     */
    public int getyPos() {
        return yPos;
    }

    /**
     * set weight of particle
     * @param weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * set y position of particle
     * @param xPos
     */
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    /**
     * set y position of particle
     * @param yPos
     */
    public void setyPos(int yPos) {
        this.yPos = yPos;
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
