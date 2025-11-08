package physics;

import core.Particle;

public class Boundaries {
    public static void enforceReflectiveBoundary(Particle particle, int width, int height){
        if(particle.position.x < 0 || particle.position.x > width){
            particle.velocity = new Vector2D(-particle.velocity.x, particle.velocity.y);
        }
        if(particle.position.y < 0 || particle.position.y > height){
            particle.velocity = new Vector2D(particle.velocity.x, -particle.velocity.y);
        }
        //Maybe look here if reflecting has issues
        double newXPos = Math.max(0, Math.min(particle.position.x, width));
        double newYPos = Math.max(0, Math.min(particle.position.y, height));
        particle.position = new Vector2D(newXPos, newYPos);
    }
}