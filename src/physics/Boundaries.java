package physics;

import core.Particle;

public class Boundaries {
    public static void enforceReflectiveBoundary(Particle particle, int minX, int maxX, int minY, int maxY){
        //find bounds for x and y accounting for radius of particle
        double effectiveMinX = minX + Particle.RADIUS;
        double effectiveMinY = minY + Particle.RADIUS;
        double effectiveMaxX = maxX - Particle.RADIUS;
        double effectiveMaxY = maxY - Particle.RADIUS;

        if(particle.position.x < effectiveMinX || particle.position.x > effectiveMaxX){
            particle.velocity = new Vector2D(-particle.velocity.x, particle.velocity.y);
        }
        if(particle.position.y < effectiveMinY || particle.position.y > effectiveMaxY){
            particle.velocity = new Vector2D(particle.velocity.x, -particle.velocity.y);
        }
        //Maybe look here if reflecting has issues
        double newXPos = Math.max(effectiveMinX, Math.min(particle.position.x, effectiveMaxX));
        double newYPos = Math.max(effectiveMinY, Math.min(particle.position.y, effectiveMaxY));
        particle.position.set(newXPos, newYPos);
    }

    public static void enforceWrapBoundary(Particle particle, int width, int height){
        double newX = (particle.position.x + width) % width;
        double newY = (particle.position.y + height) % height;
        particle.position.set(newX, newY);
    }
}