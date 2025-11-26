package ui;

import core.Particle;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import physics.RelationshipMatrix;

public class RelationshipVisualizer extends GridPane {
    private final RelationshipMatrix matrix;
    private final int width,height;

    public RelationshipVisualizer(RelationshipMatrix inMatrix, int w, int h){
        matrix = inMatrix;
        width = w;
        height = h;
        this.setPrefSize(width,height);
        //populate rows and columns with colored squares representing force values between pairs
        Rectangle rect = new Rectangle((double)width/Particle.Type.values().length+1,(double)height/Particle.Type.values().length+1);
        Color boxColor = Color.BLACK;
        rect.setFill(boxColor);
        this.add(rect,0,0);
        for(int c = 0; c < Particle.Type.values().length; c++){
            this.add(makeLabelBox(Particle.Type.values()[c]),c+1,0);
            this.add(makeLabelBox(Particle.Type.values()[c]),0,c+1);
            for(int r = 0; r < Particle.Type.values().length;r++){
                this.add(makeRelationShipBox(Particle.Type.values()[c],Particle.Type.values()[r]),c+1,r+1);
            }
        }
    }

    private StackPane makeRelationShipBox(Particle.Type t1, Particle.Type t2){
        StackPane rBox = new StackPane();
        Rectangle bRect = new Rectangle((double)width/Particle.Type.values().length+1,(double)height/Particle.Type.values().length+1);
        bRect.setFill(Color.BLACK);
        Rectangle rect = new Rectangle((double)width/Particle.Type.values().length+1,(double)height/Particle.Type.values().length+1);
        double transparencyValue = 0;
        double transparencyMin = 0.3;
        if(matrix.getForceBetween(t1,t2) < 0){
            transparencyValue = map(matrix.getForceBetween(t1,t2),-1,-0.3, 1,transparencyMin);
        }else{
            transparencyValue = map(matrix.getForceBetween(t1,t2),0.3,1,transparencyMin, 1);
        }
        Color relationshipColor;
        if(matrix.getForceBetween(t1,t2) < -0.3){
            relationshipColor = new Color(1,0,0,transparencyValue);
        }else if(matrix.getForceBetween(t1,t2) > 0.3){
            relationshipColor = new Color(0,1,0,transparencyValue);
        }else{
            relationshipColor = new Color(1,1,1,0);
        }
        rect.setFill(relationshipColor);
        Label rValue = new Label(String.format("%.2f",matrix.getForceBetween(t1,t2)));
        rBox.getChildren().addAll(bRect,rect,rValue);
        return rBox;
    }
    private StackPane makeLabelBox(Particle.Type t){
        StackPane box = new StackPane();
        Rectangle rect = new Rectangle((double)width/Particle.Type.values().length+1,(double)height/Particle.Type.values().length+1);
        Color boxColor = switch(t){
            case RED -> Color.RED;
            case BLUE -> Color.BLUE;
            case GREEN -> Color.GREEN;
            case CYAN -> Color.CYAN;
            case PURPLE -> Color.PURPLE;
            case YELLOW -> Color.YELLOW;
            case ORANGE -> Color.ORANGE;
            case LIME -> Color.LIME;
        };
        rect.setFill(boxColor);
        Label cValue = new Label(t.toString());
        box.getChildren().addAll(rect,cValue);
        return box;
    }


    private double map(double x, double iMin, double iMax, double jMin, double jMax){
        return (x - iMin) / (iMax - iMin) * (jMax - jMin) + jMin;
    }

}
