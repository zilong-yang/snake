package snake;

import javafx.scene.input.KeyCode;

/**
 * Created by Z on 12/17/2018.
 */
public enum Direction {
	
	UP,
	DOWN,
	LEFT,
	RIGHT,
	ZERO
	;
	
	public Direction opposite() {
		switch (this) {
			case UP:
				return DOWN;
			case DOWN:
				return UP;
			case LEFT:
				return RIGHT;
			default:
				return LEFT;
		}
	}
	
	public static Direction linearDirection
			(int fromX, int fromY, int toX, int toY) {
		if (fromX != toX && fromY != toY)
			throw new IllegalArgumentException("Not Inline");
		else if (fromX == toX && fromY == toY)
			return ZERO;
		
		if (fromX == toX)   return fromY > toY ? UP : DOWN;
		else                return fromX > toX ? LEFT : RIGHT;
	}
	
	public static Direction convert(KeyCode key) {
		switch (key) {
			case UP: return UP;
			case DOWN: return DOWN;
			case LEFT: return LEFT;
			case RIGHT: return RIGHT;
		}
		
		return ZERO;
	}
}
