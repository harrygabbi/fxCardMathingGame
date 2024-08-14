package ca.cmpt213.ans4_q2.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * MemoryGame is the main class for the Memory Matching Game application.
 * It sets up the primary stage and initializes the game board.
 */
public class MemoryGame extends Application {

    /**
     * The main entry point for JavaFX applications.
     * This method is called when the application is launched.
     *
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Memory Matching Game");

        BorderPane root = new BorderPane();
        GameBoard gameBoard = new GameBoard();
        root.setCenter(gameBoard);

        // Load the background image
        Image backgroundImage = new Image(getClass().getResourceAsStream("/Images/wallpaper.jpg"));
        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));

        // Set the background of the BorderPane
        root.setBackground(new Background(background));

        Scene scene = new Scene(root, 700, 750);
        primaryStage.setScene(scene);
        primaryStage.show();

        gameBoard.startNewGame();
    }

    /**
     * The main method is the entry point of the application.
     * It launches the JavaFX application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        launch(args);
    }
}
