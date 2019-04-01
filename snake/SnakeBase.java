package snake;

/**
 * Created by Z on 12/10/2018
 */
public abstract class SnakeBase {
	
	private int length;
	
	private int headX;
	private int headY;
	
	private Direction towards;
	
	public SnakeBase() {
		this(1, 0, 0);
	}
	
	public SnakeBase(int length, int headX, int headY) {
		this.length = length;
		this.headX = headX;
		this.headY = headY;
		this.towards = Direction.RIGHT;
	}
	
	public boolean up() {
		headY--;
		return true;
	}
	
	public boolean down() {
		headY++;
		return true;
	}
	
	public boolean left() {
		headX--;
		return true;
	}
	
	public boolean right() {
		headX++;
		return true;
	}
	
	public void longer() {
		length++;
	}
	
	public void shorter() {
		if (length > 0)
			length--;
	}
	
	public int getLength() {
		return length;
	}
	
	public void setLength(int length) {
		this.length = length;
	}
	
	public int getHeadX() {
		return headX;
	}
	
	public void setHeadX(int headX) {
		this.headX = headX;
	}
	
	public int getHeadY() {
		return headY;
	}
	
	public void setHeadY(int headY) {
		this.headY = headY;
	}
	
	public Direction facing() {
		return towards;
	}
	
	public void face(Direction towards) {
		this.towards = towards;
	}
	
	public abstract void eat(Edible e);
}
