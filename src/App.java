import core.Particle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import physics.Vector2D;
import ui.ParticlePanel;

import java.util.ArrayList;
import java.util.Random;

public class App extends Application {
    private final int WIDTH = 800;
    private final int HEIGHT = 1000;
    private ParticlePanel simPanel;

    @Override
    public void start(Stage primaryStage) {
        //create and fill initial particle list
        ArrayList<Particle> particles = new ArrayList<>();
//        particles = randomParticles(500);
        particles.add(new Particle(new Vector2D(WIDTH*1.2/4.0,HEIGHT/2.0),Particle.Type.RED));
        particles.add(new Particle(new Vector2D(WIDTH*1.25/4.0,HEIGHT/2.0),Particle.Type.BLUE));
        particles.add(new Particle(new Vector2D(WIDTH*1.3/4.0,HEIGHT/2.0),Particle.Type.GREEN));
        simPanel = new ParticlePanel(WIDTH,HEIGHT,particles);

        Scene scene = new Scene(simPanel, WIDTH, HEIGHT);

        primaryStage.setTitle("Particle Life Simulation");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        simPanel.start();
    }

    @Override
    public void stop(){
        simPanel.stop();
    }

    private ArrayList<Particle> randomParticles(int n){
        Random rnd = new Random();
        ArrayList<Particle> particles = new ArrayList<>();
        double xyMin = Particle.RADIUS;
        for(int i = 0; i < n; i++){
            //find random position within bounds accounting for radius of each particle
            double randomX = xyMin + (WIDTH - Particle.RADIUS - xyMin ) * rnd.nextDouble();
            double randomY = xyMin + (HEIGHT - Particle.RADIUS - xyMin ) * rnd.nextDouble();
            Vector2D rndPos = new Vector2D(randomX,randomY);
            //grab random type for particle
            int rndKey = rnd.nextInt(0, Particle.Type.values().length);
            particles.add(new Particle(rndPos,Particle.Type.values()[rndKey]));
        }
        return particles;
    }

    public static void main(String[] args) {
        launch(args);
    }
}