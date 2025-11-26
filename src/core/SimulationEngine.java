package core;

import physics.Boundaries;
import physics.RelationshipMatrix;
import physics.Vector2D;

import java.util.ArrayList;
import java.util.Vector;

public class SimulationEngine {
    private final double universalScalar = 40;
    private final ArrayList<Particle> particles;
    private final int width,height;
    private RelationshipMatrix relationshipMatrix;

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
                Vector2D directionUnitVector = directionToNeighbor.normalize();
                //repulsive force calc
                if(distanceToNeighbor < relationshipMatrix.getMinDistanceFor(curParticle,neighbor)){
                    Vector2D repulsiveForce = new Vector2D(directionUnitVector);
                    repulsiveForce = repulsiveForce.multiply(Math.abs(relationshipMatrix.getForceBetween(curParticle,neighbor)) * -3);
                    double mappedScalar = map(distanceToNeighbor,0, relationshipMatrix.getMinDistanceFor(curParticle,neighbor),1,0);
                    repulsiveForce = repulsiveForce.multiply(mappedScalar);
                    totalForce = totalForce.add(repulsiveForce);
                }
                //attractive force calc
                if(distanceToNeighbor < relationshipMatrix.getRadiusFor(curParticle,neighbor) && distanceToNeighbor > relationshipMatrix.getMinDistanceFor(curParticle,neighbor)){
                    Vector2D addedForce = new Vector2D(directionUnitVector);
                    addedForce = addedForce.multiply(relationshipMatrix.getForceBetween(curParticle,neighbor));
                    double mappedScalar = map(distanceToNeighbor,0, relationshipMatrix.getRadiusFor(curParticle,neighbor),1,0);
                    addedForce = addedForce.multiply(mappedScalar);
                    totalForce = totalForce.add(addedForce);
                }
            }
            totalForce = totalForce.multiply(universalScalar);
            curParticle.applyForce(totalForce);
            //update particle position
            curParticle.update(dt);
            //Boundaries.enforceReflectiveBoundary(curParticle,minXPos,width,minYPos,height);
            Boundaries.enforceWrapBoundary(curParticle,width,height);
        }
    }

    //map method lifted from here: https://stackoverflow.com/questions/17134839/how-does-the-map-function-in-processing-work
    private double map(double x, double iMin, double iMax, double jMin, double jMax){
        return (x - iMin) / (iMax - iMin) * (jMax - jMin) + jMin;
    }
    public ArrayList<Particle> getParticles(){
        return particles;
    }

    public RelationshipMatrix getMatrix (){
        return relationshipMatrix;
    }
    public void setRelationshipMatrix(RelationshipMatrix newMatrix){
        relationshipMatrix = newMatrix;
    }

}
