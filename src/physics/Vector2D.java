package physics;

public final class Vector2D {

    public double x,y;
    public static final Vector2D ZERO = new Vector2D(0,0);
    //Constructors
    public Vector2D(){
        this(0,0);
    }

    public Vector2D(double inX, double inY){
        x = inX;
        y = inY;
    }

    //Setter Methods
    public Vector2D set(double inX, double inY){
        x = inX;
        y = inY;
        return this;
    }
    public Vector2D set(Vector2D inVec){
        x = inVec.x;
        y = inVec.y;
        return this;
    }

    //Vector Operation Methods
    public Vector2D add(Vector2D inVec){
        x += inVec.x;
        y += inVec.y;
        return this;
    }
    public Vector2D subtract(Vector2D inVec){
        x -= inVec.x;
        y -= inVec.y;
        return this;
    }
    public Vector2D scale(double scale){
        x *= scale;
        y *= scale;
        return this;
    }
    public Vector2D negate(){
        x = -x;
        y = -y;
        return this;
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
        if(magnitude > 1e-9){
            x /= magnitude;
            y /= magnitude;
        }
        return this;
    }

    //Copy Method
    public Vector2D copy(){
        return new Vector2D(x,y);
    }

    @Override
    public String toString(){
        //Truncate to three decimal places for readability
        return String.format("(%.3f, %.3f", x, y);
    }
}
