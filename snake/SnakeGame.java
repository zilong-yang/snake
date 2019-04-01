package snake;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.*;


/**
 * Created by Z on 12/10/2018.
 */
public class SnakeGame {

    private int numRows;
    private int numColumns;
    
    private final GridPane board;
    
    public SnakeGame() {
        this(30, 50);
    }

    public SnakeGame(int numRows, int numColumns) {
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.cells = new Cell[numRows][numColumns];
        this.board = new GridPane();

        init();

        this.snake = new Snake(this);
        addEdible(new Food(this));

        board.setBorder(new Border(new BorderStroke(
                Color.BLACK, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(1)
        )));
    }

    private void init() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                Cell cell = new Cell(i, j, CELL_SIZE);
                cell.setColor(cellColor);
                cell.setStroke(cellColor);

                cells[i][j] = cell;
                board.add(cell, j, i);
            }
        }
    
        board.setOnKeyPressed(event -> {
            System.out.println("test");
            Direction facing = snake.facing();
            KeyCode key = event.getCode();
    
            if (key.isArrowKey()) {
                Direction keyDir = Direction.convert(key);
    
                if (facing != keyDir && facing.opposite() != keyDir) {
                    if (!snake.move(keyDir))
                        end();
                    else if (getControl().isRunning())
                        getControl().restart();
                }
            }
        });
    }

    public int numRows() {
        return numRows;
    }

    public int numColumns() {
        return numColumns;
    }
    
    public GridPane gameBoard() {
        return board;
    }
    
    //////////////
    // End Game //
    //////////////
    
    private BooleanProperty ended = new SimpleBooleanProperty(false);
    
    public void end() {
        getControl().stop();
        ended.set(true);
        board.setDisable(true);
    }
    
    public boolean ended() {
        return ended.get();
    }
    
    public BooleanProperty endedProperty() {
        return ended;
    }
    
    /////////////////////
    // Cell Properties //
    /////////////////////
    
    private Cell[][] cells;
    public static int CELL_SIZE = 10;
    
    public Cell getCell(int row, int col) {
        // if row or col is out of bounds
        if ((row < 0 || col < 0) || (row >= numRows || col >= numColumns))
            return null;
        
        return cells[row][col];
    }

    private Color cellColor = Color.WHITE;
    
    public Color getCellColor() {
        return cellColor;
    }

    public void setCellColor(Color cellColor) {
        this.cellColor = cellColor;

        for (Cell[] cellRow : cells) {
            for (Cell c : cellRow) {
                c.setColor(cellColor);
            }
        }

        setSnakeColor(snakeColor);
    }
    
    //////////////////////
    // Snake Properties //
    //////////////////////
    
    private Snake snake;
    
    public Snake getSnake() {
        return snake;
    }

    private Color snakeColor = Color.BLACK;

    public Color getSnakeColor() {
        return snakeColor;
    }

    public void setSnakeColor(Color snakeColor) {
        this.snakeColor = snakeColor;
        snake.setColor(snakeColor);
    }

    ////////////////////
    // Snake Controls //
    ////////////////////

    private final SnakeControl CONTROL = new SnakeControl();

    public SnakeControl getControl() {
        return CONTROL;
    }

    /**
     * A class for controlling the snake using arrow keys.
     *
     * This class can only be instantiated through the SnakeGame class.
     * However, the user can use public methods in this class to
     * control the snake's overall movement.
     */
    public class SnakeControl {

        private Thread thread;
        private final LongProperty latency;
    
        private SnakeControl() {
            this(1000);
        }
    
        private SnakeControl(long latency) {
            this.latency = new SimpleLongProperty(latency);
            thread = new Thread(getControl());
        }
    
        private Runnable getControl() {
            return () -> {
                try {
                    //noinspection InfiniteLoopStatement
                    while (true) {
                        Thread.sleep(latency.get());

                        Platform.runLater(() -> {
                            if (!snake.move(snake.facing())) SnakeGame.this.end();
                        });
                    }
                } catch (InterruptedException ignored) { }
            };
        }
    
        public void start() {
            thread.start();
        }
    
        public boolean isRunning() {
            return thread.isAlive();
        }
    
        public void stop() {
            thread.interrupt();
            thread = new Thread(getControl());
        }
    
        public void restart() {
            if (isRunning()) stop();
            start();
        }

        public long getLatency() {
            return latency.get();
        }

        public void setLatency(long millis) {
            latency.set(millis);
            
            if (isRunning()) restart();
        }

        public LongProperty latencyProperty() {
            return latency;
        }
    }
    
    /////////////////////
    // Food Properties //
    /////////////////////
    
    private Set<Edible> edibles = new HashSet<>();
    
    public <T extends Node & Edible> boolean addEdible(T edible) {
        Cell free = randomUnoccupiedCell();
        free.setEdible(edible);
        
        return edibles.add(edible);
    }
    
    public void removeEdible(Cell cell) {
        cell.removeEdible();
    }
    
    private Cell randomUnoccupiedCell() {
        ArrayList<Cell> cellList = new ArrayList<>();
        for (Cell[] row : cells) cellList.addAll(Arrays.asList(row));
        cellList.removeAll(snake.getOccupied());
        
        return cellList.get(new Random().nextInt(cellList.size()));
    }
}
