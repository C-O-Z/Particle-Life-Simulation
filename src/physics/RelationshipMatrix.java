package physics;

import core.Particle;

import java.util.*;

public class RelationshipMatrix {

    private final double minForce = 0.3;
    private final double maxForce = 1.0;
    private final double minDistance = 30;
    private final double maxDistance = 50;
    private final double minRadius = 80;
    private final double maxRadius = 150;
    private final EnumMap<Particle.Type, EnumMap<Particle.Type,Double>> attractionForces;
    private final EnumMap<Particle.Type,EnumMap<Particle.Type,Double>> minDistances;
    private final EnumMap<Particle.Type,EnumMap<Particle.Type,Double>> radii;

    public RelationshipMatrix(){
        attractionForces = this.generateRandomForces();
        minDistances = this.generateRandomMinDistances();
        radii = this.generateRandomRadii();
    }

    //methods to randomly populate relationship matrices
    //holy shit this better work im barely awake
    private EnumMap<Particle.Type,EnumMap<Particle.Type,Double>> generateRandomForces(){
        Random rnd = new Random();
        EnumMap<Particle.Type,EnumMap<Particle.Type,Double>> forces = new EnumMap<>(Particle.Type.class);
        for(Particle.Type curType : Particle.Type.values()){
            EnumMap<Particle.Type,Double> neighborForces = new EnumMap<>(Particle.Type.class);
//            ArrayList<Particle.Type> otherTypes = new ArrayList<>(Arrays.asList(Particle.Type.values()));
//            otherTypes.remove(curType);
            for(Particle.Type nextType : Particle.Type.values()){
                double randomForce = minForce + (maxForce - minForce) * rnd.nextDouble();
                if(rnd.nextInt(0,101) <= 50){
                    randomForce = -randomForce;
                }
                neighborForces.put(nextType,randomForce);
            }
            forces.put(curType,neighborForces);
        }
        return forces;

    }
    private EnumMap<Particle.Type,EnumMap<Particle.Type,Double>> generateRandomMinDistances(){
        Random rnd = new Random();
        EnumMap<Particle.Type,EnumMap<Particle.Type,Double>> distances = new EnumMap<>(Particle.Type.class);
        for(Particle.Type curType : Particle.Type.values()){
            EnumMap<Particle.Type,Double> neighborDistances = new EnumMap<>(Particle.Type.class);
//            ArrayList<Particle.Type> otherTypes = new ArrayList<>(Arrays.asList(Particle.Type.values()));
//            otherTypes.remove(curType);
            for(Particle.Type nextType : Particle.Type.values()){
                double randomDistance = minDistance + (maxDistance - minDistance) * rnd.nextDouble();
                neighborDistances.put(nextType,randomDistance);
            }
            distances.put(curType,neighborDistances);
        }
        return distances;
    }
    private EnumMap<Particle.Type,EnumMap<Particle.Type,Double>> generateRandomRadii(){
        Random rnd = new Random();
        EnumMap<Particle.Type,EnumMap<Particle.Type,Double>> newRadii = new EnumMap<>(Particle.Type.class);
        for(Particle.Type curType : Particle.Type.values()){
            EnumMap<Particle.Type,Double> neighborRadii = new EnumMap<>(Particle.Type.class);
//            ArrayList<Particle.Type> otherTypes = new ArrayList<>(Arrays.asList(Particle.Type.values()));
//            otherTypes.remove(curType);
            for(Particle.Type nextType : Particle.Type.values()){
                double randomRadius = minRadius + (maxRadius - minRadius) * rnd.nextDouble();
                neighborRadii.put(nextType,randomRadius);
            }
            newRadii.put(curType,neighborRadii);
        }
        return newRadii;
    }

    //methods to easily retrieve relationship values
    public double getForceBetween(Particle p1, Particle p2){
        return attractionForces.get(p1.type).get(p2.type);
    }
    public double getMinDistanceFor(Particle p1, Particle p2){
        return minDistances.get(p1.type).get(p2.type);
    }
    public double getRadiusFor(Particle p1, Particle p2){
        return radii.get(p1.type).get(p2.type);
    }
    public EnumMap<Particle.Type, EnumMap<Particle.Type,Double>> getForces(){
        return attractionForces;
    }
    public EnumMap<Particle.Type, EnumMap<Particle.Type,Double>> getMinDistances(){
        return minDistances;
    }
    public EnumMap<Particle.Type, EnumMap<Particle.Type,Double>> getRadii(){
        return radii;
    }
}
