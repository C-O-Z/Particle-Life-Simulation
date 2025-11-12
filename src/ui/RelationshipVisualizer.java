package ui;

import core.Particle;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import physics.RelationshipMatrix;

import java.util.Stack;

public class RelationshipVisualizer extends GridPane {
    private final RelationshipMatrix matrix;
    private final int width,height;

    public RelationshipVisualizer(RelationshipMatrix inMatrix, int w, int h){
        matrix = inMatrix;
        width = w;
        height = h;
        this.setPrefSize(width,height);
        //populate rows and columns with colored squares representing force values between pairs
        for(int i = 0; i < Particle.Type.values().length; i++){
            this.add(makeLabelBox(Particle.Type.values()[i]),0,i);
            for(int j = 0; j < Particle.Type.values().length;j++){
                if(j == 0){
                    this.add(makeLabelBox(Particle.Type.values()[i]),i,j);
                    continue;
                }
                this.add(makeRelationShipBox(Particle.Type.values()[i],Particle.Type.values()[j]),i,j);
            }
        }
    }

    private StackPane makeRelationShipBox(Particle.Type t1, Particle.Type t2){
        StackPane rBox = new StackPane();
        //rBox.setPrefSize(width,height);
        Rectangle rect = new Rectangle((double)width/Particle.Type.values().length+1,(double)height/Particle.Type.values().length+1);
        double transparencyValue = map(matrix.getForceBetween(t1,t2),-1,1,0,1);
        Color relationshipColor;
        if(matrix.getForceBetween(t1,t2) < 0){
            relationshipColor = new Color(1,0,0,transparencyValue);
        }else if(matrix.getForceBetween(t1,t2) > 0){
            relationshipColor = new Color(0,1,0,transparencyValue);
        }else{
            relationshipColor = new Color(0,0,0,0);
        }
        rect.setFill(relationshipColor);
        Label rValue = new Label(String.format("%.2f",matrix.getForceBetween(t1,t2)));
        rBox.getChildren().addAll(rect,rValue);
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
