package core;

import physics.Boundaries;
import physics.RelationshipMatrix;
import physics.Vector2D;

import java.util.ArrayList;

public class SimulationEngine {
    private final double k = 3;
    private final double maxRadius = 0.1;
    private final ArrayList<Particle> particles;
    private final int width,height;
    private final RelationshipMatrix relationshipMatrix;
    private final int minXPos = 0;
    private final int minYPos = 0;

    public SimulationEngine(int w, int h, ArrayList<Particle> inParticles, RelationshipMatrix matrix){
        particles = inParticles;
        width = w;
        height = h;
        relationshipMatrix = matrix;
    }

    public void tick(double dt){
        for(Particle curParticle : particles){
            //shitty and slow implementation to get the gist of how to apply the forces
            ArrayList<Particle> neighbors = new ArrayList<>(particles);
            neighbors.remove(curParticle);
            Vector2D directionToNeighbor = Vector2D.ZERO;
            Vector2D totalForce = Vector2D.ZERO;
            double distanceToNeighbor = 0.0;
            for(Particle neighbor : neighbors){
                //logic and math for all this is lifted and refactored from here: https://www.youtube.com/watch?v=xiUpAeos168
                //calculate force to be added by neighbor
                directionToNeighbor = neighbor.position.copy();
                directionToNeighbor = directionToNeighbor.subtract(curParticle.position);
                //handle force application for wrapping boundaries
                if(directionToNeighbor.x > 0.5 * width){
                    directionToNeighbor.x -= width;
                }
                if(directionToNeighbor.x < -0.5 * width){
                    directionToNeighbor.x += width;
                }
                if(directionToNeighbor.y > 0.5 * height){
                    directionToNeighbor.y -= height;
                }
                if(directionToNeighbor.y < -0.5 * height){
                    directionToNeighbor.y += height;
                }
                distanceToNeighbor = directionToNeighbor.magnitude();
                directionToNeighbor = directionToNeighbor.normalize();
                //repulsive force calc
                if(distanceToNeighbor < relationshipMatrix.getMinDistanceFor(curParticle,neighbor)){
                    Vector2D repulsiveForce = new Vector2D(directionToNeighbor);
                    repulsiveForce = repulsiveForce.multiply(Math.abs(relationshipMatrix.getForceBetween(curParticle,neighbor)) * -3);
                    double mappedScalar = map(distanceToNeighbor,0, relationshipMatrix.getMinDistanceFor(curParticle,neighbor),1,0);
                    repulsiveForce = repulsiveForce.multiply(mappedScalar);
                    repulsiveForce = repulsiveForce.multiply(k);
                    totalForce = totalForce.add(repulsiveForce);
                }
                //attractive force calc
//                if(distanceToNeighbor < relationshipMatrix.getRadiusFor(curParticle,neighbor)){
//                    Vector2D addedForce = new Vector2D(directionToNeighbor);
//                    addedForce = addedForce.multiply(relationshipMatrix.getForceBetween(curParticle,neighbor));
//                    double mappedScalar = map(distanceToNeighbor,0, relationshipMatrix.getRadiusFor(curParticle,neighbor),0,1);
//                    addedForce = addedForce.multiply(mappedScalar);
//                    addedForce = addedForce.multiply(k);
//                    totalForce = totalForce.add(addedForce);
//                }
            }
            //totalForce = totalForce.multiply(k);
            curParticle.applyForce(totalForce);
            //update particle position
            curParticle.update(dt);
            //Boundaries.enforceReflectiveBoundary(curParticle,minXPos,width,minYPos,height);
            Boundaries.enforceWrapBoundary(curParticle,width,height);
        }
    }
    //map method lifted from here: https://stackoverflow.com/questions/17134839/how-does-the-map-function-in-processing-work
    private double map(double x, double iMin, double iMax, double jMin, double jMax){
        return jMin + (jMax - jMin) * ((x - iMin) / (iMax - iMin));
    }
    public ArrayList<Particle> getParticles(){
        return particles;
    }
    private Vector2D calcForce(Particle p1, Particle p2){
        Vector2D direction = p2.position.subtract(p1.position);
        Vector2D generatedForce = Vector2D.ZERO;
        double distance = direction.magnitude();
        if(distance > 0 && distance < relationshipMatrix.getRadiusFor(p1,p2)){
            double force = forceFunction(distance / maxRadius, relationshipMatrix.getForceBetween(p1,p2));
            generatedForce = direction.divide(distance);
            generatedForce = generatedForce.multiply(force);
        }
        return generatedForce;

    }

    private double forceFunction(double distance, double attractiveForce){
        //value is arbitrary
        double beta = 0.3;
        if(distance < beta){
            return distance / beta - 1;
        }else if(beta < distance && distance < 1){
            return attractiveForce * (1 - Math.abs(2 * distance -1 - beta) / (1 - beta));
        }else{
            return 0;
        }
    }
}
