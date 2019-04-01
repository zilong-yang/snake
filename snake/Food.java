package snake;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by Z on 12/17/2018.
 */
public class Food extends Circle implements Edible {
	
	public Food(SnakeGame board) {
		setRadius(SnakeGame.CELL_SIZE / 2.0);
		setFill(Color.GREEN);
		setStroke(getFill());
	}
	
	@Override
	public void affect(SnakeBase snake) {
		snake.longer();
	}
}
