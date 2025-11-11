package physics;

public final class Vector2D {

    public double x,y;
    public static final Vector2D ZERO = new Vector2D(0,0);

    //Constructors
    public Vector2D(){
        this(Vector2D.ZERO);
    }

    public Vector2D(double inX, double inY){
        x = inX;
        y = inY;
    }

    public Vector2D(Vector2D v){
        x = v.x;
        y = v.y;
    }

    //Setter Methods
    public void set(double inX, double inY){
        x = inX;
        y = inY;
    }
    public void set(Vector2D inVec){
        x = inVec.x;
        y = inVec.y;
    }

    //Vector Operation Methods
    public Vector2D add(Vector2D inVec){
        return new Vector2D(x + inVec.x,y + inVec.y);
    }
    public Vector2D subtract(Vector2D inVec){
        return new Vector2D(x - inVec.x,y - inVec.y);
    }
    public Vector2D multiply(double scale){
        return new Vector2D(x * scale,y * scale);
    }
    public Vector2D divide(double scale){
        return new Vector2D(x / scale,y / scale);
    }
    public Vector2D negate(){
        return new Vector2D(-x,-y);
    }
    public double dot(Vector2D inVec){
        return (x * inVec.x) + (y * inVec.y);
    }
    public double cross(Vector2D inVec){
        return (x * inVec.y) - (y * inVec.x);
    }
    public double magnitude(){
        return Math.sqrt((x*x) + (y*y));
    }
    public Vector2D normalize(){
        double magnitude = this.magnitude();
        //check for 0 or near 0 magnitudes in order to avoid arithmetic exceptions
        double newX = 0;
        double newY = 0;
        if(magnitude > 1e-9){
            newX = x / magnitude;
            newY = y / magnitude;
        }
        return new Vector2D(newX,newY);
    }

    //Copy Method
    public Vector2D copy(){
        return new Vector2D(x,y);
    }

    @Override
    public String toString(){
        //Truncate to three decimal places for readability
        return String.format("(%.3f, %.3f)", x, y);
    }
}
