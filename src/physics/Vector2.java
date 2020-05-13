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
    public void invert(){
        double temp = this.x;
        this.x  = this.y;
        this.y = this.x;
    }
    public void negate(){
        this.x *= -1;
        this.y *= -1;
    }

    /* STATIC METHODS */
    public static double distance(Vector2 a, Vector2 b){
        double v1 = b.getX() - a.getX();
        double v2 = b.getY() - a.getY();
        return Math.sqrt(v1*v1 + v2*v2);
    }
    public static double dotProduct(Vector2 a, Vector2 b){
        return (a.getX() * b.getX()) + (a.getY() * b.getY());
    }
    public static Vector2 multiply(Vector2 vector, Double scalar){
        return new Vector2(vector.getX() * scalar, vector.getY() * scalar);
    }
    public static Vector2 subtract(Vector2 a, Vector2 b){
        return new Vector2(a.getX() - b.getX(), a.getY() - b.getY());
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


    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
