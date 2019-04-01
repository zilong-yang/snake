package snake;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by Z on 12/10/2018.
 */
public class Cell extends StackPane {

    private final int x;
    private final int y;

    private Rectangle background;

    public Cell(int x, int y, int size) {
        this.x = x;
        this.y = y;

        background = new Rectangle(size, size);
        background.setFill(Color.WHITE);
        background.setStroke(Color.BLACK);

        getChildren().add(background);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setColor(Color c) {
        background.setFill(c);
        background.setStroke(
                c.darker());
    }

    public Color getColor() {
        return ((Color) background.getFill());
    }

    public void setStroke(Color c) {
        background.setStroke(c);
    }

    public Color getStroke() {
        return ((Color) background.getStroke());
    }
    
    @Override
    public String toString() {
        return String.format("Cell[%d, %d]", x, y);
    }
    
    //////////////////////
    // Edible Component //
    //////////////////////
    
    private Edible edible;
    
    public Edible getEdible() {
        return edible;
    }
    
    public boolean hasEdible() {
        return edible != null;
    }
    
    public <T extends Node & Edible> void setEdible(T edible) {
        if (this.edible == null) {
            boolean add = getChildren().add(edible);
        } else {
            getChildren().set(1, edible);
        }
        
        this.edible = edible;
    }
    
    public void removeEdible() {
        getChildren().remove((Node) edible);
        edible = null;
    }
}
