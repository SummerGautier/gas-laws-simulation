package physics;

public class Vector2 {
    private double x;
    private double y;

    /* CONSTRUCTORS */
    public Vector2(){
        this.x = 0.0;
        this.y = 0.0;
    }
    /**
     * Instance of Vector2
     * @param x, x component of vector
     * @param y, y component of vector
     */
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /* INSTANCE METHODS */
    public boolean equals(Vector2 other){
        return (this.x == other.x && this.y == other.y);
    }
    public void normalize(){
        double length = Math.sqrt(x*x + y*y);
        if(length != 0.0){
            double s = 1/length;
            this.x *= s;
            this.y *= s;
        }
    }

    /* STATIC METHODS */
    public static double distance(Vector2 a, Vector2 b){
        double v1 = b.getX() - a.getX();
        double v2 = b.getY() - a.getY();
        return Math.sqrt(v1*v1 + v2*v2);
    }

    /* GET METHODS */
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    /* SET METHODS */
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
}
