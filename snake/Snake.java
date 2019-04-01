package snake;

import javafx.scene.paint.Color;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Z on 12/10/2018.
 */
public class Snake extends SnakeBase {

    private final SnakeGame board;
    private Cell head;
    private Cell trailing;

    private Color color;

    private LinkedList<Cell> occupied;

    public Snake(SnakeGame board) {
        super();

        this.board = board;
        occupied = new LinkedList<>();
        color = board.getSnakeColor();

        updateSnake();
    }
    
    public boolean move(Direction dir) {
        switch (dir) {
            case UP: return up();
            case DOWN: return down();
            case LEFT: return left();
            case RIGHT: return right();
        }
        
        return false;
    }

    @Override
    public boolean up() {
        super.up();
        
        if (!isHeadValid()) return false;
    
        updateSnake();
        face(Direction.UP);
        return true;
    }

    @Override
    public boolean down() {
        super.down();
        
        if (!isHeadValid()) return false;
        
        updateSnake();
        face(Direction.DOWN);
        return true;
    }

    @Override
    public boolean left() {
        super.left();
        
        if (!isHeadValid()) return false;
        
        updateSnake();
        face(Direction.LEFT);
        return true;
    }

    @Override
    public boolean right() {
        super.right();
        
        if (!isHeadValid()) return false;
        
        updateSnake();
        face(Direction.RIGHT);
        return true;
    }
    
    private boolean isHeadValid() {
        int columnIndex = getHeadX();
        int rowIndex = getHeadY();
    
        if (rowIndex >= 0 && columnIndex >= 0 &&
                rowIndex < board.numRows() && columnIndex < board.numColumns()) {
            return !occupied.contains(board.getCell(rowIndex, columnIndex));
        }
        
        return false;
    }

    @Override
    public void longer() {
        super.longer();
        
        if (trailing != null) {
            trailing.setColor(color);
            trailing.setStroke(color);
            occupied.addLast(trailing);
            trailing = null;
        }
    }

    public SnakeGame getBoard() {
        return board;
    }
    
    protected List<Cell> getOccupied() {
        return Collections.unmodifiableList(occupied);
    }

    @Override
    public void eat(Edible e) {
        e.affect(this);
    }

    private void updateSnake() {
//        if (head != null && (head.getX() != getHeadX() || head.getY() != getHeadY()))
//            throw new InternalError("Snake Update Failure");

        Cell cell = board.getCell(getHeadY(), getHeadX());
    
        if (!occupied.isEmpty()) {
            Cell removed = occupied.removeLast();
            Color cellColor = board.getCellColor();
    
            removed.setColor(cellColor);
            removed.setStroke(cellColor);
    
            if (trailing == null || cell != head)
                trailing = removed;
        }
    
        head = cell;
        occupied.addFirst(head);
        head.setColor(color);
        head.setStroke(color);
        
        if (head.hasEdible()) {
            eat(head.getEdible());
            board.removeEdible(head);
            board.addEdible(new Food(board));
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        
        for (Cell c : occupied)
            c.setColor(color);
    }
}
