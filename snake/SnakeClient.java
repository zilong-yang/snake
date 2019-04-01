package snake;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * Created by Z on 12/14/2018.
 */
public class SnakeClient extends Application {

    @Override
    public void start(Stage primaryStage) {
        SnakeGame game = new SnakeGame(20, 30);
        game.getControl().setLatency(150);
//        board.getControl().start();
    
        StackPane stackPane = new StackPane(game.gameBoard());
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setPadding(new Insets(5));
        
        Button btStart = new Button("Start");
        stackPane.getChildren().add(btStart);
    
        Button btFaster = new Button("+ Speed");
        Button btSlower = new Button("- Speed");
    
        HBox controlBox = new HBox(5);
        controlBox.setPadding(new Insets(5));
        controlBox.setAlignment(Pos.CENTER);
        controlBox.getChildren().addAll(btFaster, btSlower);
        
        VBox base = new VBox(5);
        base.setAlignment(Pos.CENTER);
        base.setPadding(new Insets(5));
        base.getChildren().addAll(stackPane, controlBox);

        Scene scene = new Scene(base);
        primaryStage.setTitle("Snake");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(closeEvent -> game.getControl().stop());
        
        btStart.setOnAction(event -> {
            stackPane.getChildren().remove(btStart);
            game.getControl().start();
            game.gameBoard().requestFocus();
        });
        
        btFaster.setOnAction(event -> {
            if (game.getControl().isRunning()) {
                long latency = game.getControl().getLatency();
                
                if (latency > 50)
                    game.getControl().setLatency(latency - 50);
                
            }
    
            game.gameBoard().requestFocus();
        });
        
        btSlower.setOnAction(event -> {
            if (game.getControl().isRunning()) {
                long latency = game.getControl().getLatency();
                
                if (latency < 500)
                    game.getControl().setLatency(latency + 50);
                
            }
            
            game.gameBoard().requestFocus();
        });
        
        game.endedProperty().addListener(listener -> {
            if (game.ended()) {
                Text text = new Text("You lost!");
                text.setTextAlignment(TextAlignment.CENTER);
                
                Button btOk = new Button("Oh, bet.");
    
                VBox vBox = new VBox(10);
                vBox.setAlignment(Pos.CENTER);
                vBox.setPadding(new Insets(5));
                vBox.getChildren().addAll(text, btOk);
    
                Stage endGameStage = new Stage();
                endGameStage.setScene(new Scene(vBox, 200, 100));
                endGameStage.setTitle("Game Ended");
                endGameStage.show();
                
                btOk.setOnAction(event -> endGameStage.close());
            }
        });
    }
}
