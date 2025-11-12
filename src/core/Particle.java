package core;

import physics.Vector2D;
import javafx.scene.paint.Color;

public class Particle {
    public enum Type{
        RED,
        BLUE,
        GREEN,
        CYAN,
        PURPLE,
        YELLOW,
        ORANGE,
        LIME
    }
    //could later play with varied radii?
    public static final double RADIUS = 2;
    public static final double FRICTION = 0.85;
    public final Type type;
    public final Color color;
    public final double mass;
    public Vector2D position;
    public Vector2D velocity;
    public Vector2D acceleration;
    private Vector2D accumForce;

    public Particle(Vector2D inPos, Type inType){
        position = inPos;
        type = inType;
        color = switch(type){
            case RED -> Color.RED;
            case BLUE -> Color.BLUE;
            case GREEN -> Color.GREEN;
            case CYAN -> Color.CYAN;
            case PURPLE -> Color.PURPLE;
            case YELLOW -> Color.YELLOW;
            case ORANGE -> Color.ORANGE;
            case LIME -> Color.LIME;
        };
        //all mass of 1 for right now, might play with it later
        mass = switch(type){
            case RED -> 1;
            case BLUE -> 1;
            case GREEN -> 1;
            case CYAN -> 1;
            case PURPLE -> 1;
            case YELLOW -> 1;
            case ORANGE -> 1;
            case LIME -> 1;
        };
        velocity = new Vector2D(0,0);
        acceleration = new Vector2D(0,0);
        accumForce = new Vector2D(0,0);
    }

    public void applyForce(Vector2D force){
        accumForce = accumForce.add(force);
    }

    //Current implementation uses Euler's Method
    public void update(double dt){
        acceleration = accumForce.divide(mass);
        velocity = velocity.add(acceleration.multiply(dt));
        position = position.add(velocity.multiply(dt));
        //apply friction, is this the right spot?
        velocity = velocity.multiply(FRICTION);
        accumForce = Vector2D.ZERO;
        acceleration = Vector2D.ZERO;
    }

}
