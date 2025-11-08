package core;

import physics.Vector2D;
import javafx.scene.paint.Color;

public class Particle {
    //May refactor to include more particle types, 3 is a good start
    public enum Type{
        RED,
        BLUE,
        GREEN
    }
    public Type type;
    public Vector2D position;
    public Vector2D velocity;
    public Vector2D acceleration;
    public Color color;

    public Particle(Vector2D inPos, Type inType, Color inColor){
        position = inPos;
        type = inType;
        color = inColor;
        velocity = new Vector2D(0,0);
        acceleration = new Vector2D(0,0);
    }

    public void applyForce(Vector2D force){
        //might cause issues?
        acceleration = acceleration.add(force);
    }

    public void update(double dt){
        velocity = velocity.add(acceleration.scale(dt));
        position = position.add(velocity.scale(dt));
        //is this correct?
        acceleration = Vector2D.ZERO;
    }

}
