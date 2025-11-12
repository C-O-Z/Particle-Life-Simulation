import core.Particle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import physics.Vector2D;
import ui.ParticlePanel;
import ui.RelationshipVisualizer;

import java.util.ArrayList;
import java.util.Random;

public class App extends Application {
    private final int WIDTH = 1000;
    private final int HEIGHT = 750;
    private final int OPTIONS_WIDTH = 300;
    private ParticlePanel simPanel;

    @Override
    public void start(Stage primaryStage) {
        //create and fill initial particle list
        ArrayList<Particle> particles = new ArrayList<>();
        particles = randomParticles(1000);
        //create simulation panel
        simPanel = new ParticlePanel(WIDTH-OPTIONS_WIDTH,HEIGHT,particles);

        BorderPane mainPanel = new BorderPane();

        VBox optionsBox = new VBox();
        optionsBox.setPrefSize(OPTIONS_WIDTH,HEIGHT);

        RelationshipVisualizer relationships = new RelationshipVisualizer(simPanel.getMatrix(),OPTIONS_WIDTH,300);
        optionsBox.getChildren().add(relationships);

        mainPanel.setLeft(optionsBox);
        mainPanel.setCenter(simPanel);

        Scene scene = new Scene(mainPanel, WIDTH, HEIGHT);

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
            //int rndKey = rnd.nextInt(0, 3);
            particles.add(new Particle(rndPos,Particle.Type.values()[rndKey]));
        }
        return particles;
    }

    public static void main(String[] args) {
        launch(args);
    }
}