package ui;

import core.Particle;
import core.SimulationEngine;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import physics.RelationshipMatrix;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class ParticlePanel extends Pane{
    private final Canvas canvas;
    private final AnimationTimer timer;
    private final GraphicsContext gc;
    private final SimulationEngine simEngine;
    private final long frameInterval = 28_000_000;
    private long lastTime = 0;


    public ParticlePanel(int width, int height, ArrayList<Particle>inParticles){
        this.setPrefSize(width,height);
        canvas = new Canvas(width,height);
        this.getChildren().add(canvas);
        //setup vars
        RelationshipMatrix matrix = new RelationshipMatrix();
        simEngine = new SimulationEngine(width,height,inParticles,matrix);
        gc = canvas.getGraphicsContext2D();
        //Start rendering loop
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(now - lastTime >= frameInterval){
                    simEngine.tick(frameInterval / 1e9);
                    render();
                    lastTime = now;
                }
//                if(lastTime == 0){
//                    lastTime = now;
//                    return;
//                }
//                //Potential to refactor to fixed timestep if needed for accuracy/performance
//                double  dt = (now - lastTime) / 1e9;
//                simEngine.tick(dt);
//                render();
//                lastTime = now;
            }
        };
    }

    public void start(){
        timer.start();
    }
    public void stop(){
        timer.stop();
    }

    private void render(){
        //reset frame to black
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
        //draw each particle
        for(Particle curParticle : simEngine.getParticles()){
            //draw current particle
            gc.setFill(curParticle.color);
            gc.fillOval(curParticle.position.x - Particle.RADIUS, curParticle.position.y - Particle.RADIUS,Particle.RADIUS * 2, Particle.RADIUS * 2);
        }
    }

    public RelationshipMatrix getMatrix(){
        return simEngine.getMatrix();
    }
}
