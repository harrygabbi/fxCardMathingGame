package ca.cmpt213.ans4_q2.ui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import ca.cmpt213.ans4_q2.game.MemoryGameLogic;

/**
 * GameBoard represents the playing field for the Memory Matching Game.
 * It extends GridPane and contains the logic for initializing and updating the game board.
 */
public class GameBoard extends GridPane {
    private MemoryGameLogic gameLogic;
    private Card[][] cards;
    private boolean isProcessing;
    private Label moveCountLabel;

    /**
     * Constructs a GameBoard and initializes its components.
     */
    public GameBoard() {
        this.gameLogic = new MemoryGameLogic();
        this.cards = new Card[4][4];
        initializeBoard();

        gameLogic.setOnBoardUpdated(this::updateBoard);
        gameLogic.setOnProcessingStart(() -> setProcessing(true));
        gameLogic.setOnProcessingEnd(() -> setProcessing(false));
        gameLogic.setOnMoveCountChanged(this::updateMoveCount);
        gameLogic.setOnGameWon(this::showGameWonDialog);
    }

    /**
     * Initializes the game board layout and components.
     */
    private void initializeBoard() {
        setPadding(new Insets(10));
        setHgap(10);
        setVgap(10);
        setAlignment(Pos.CENTER);

        // Apply a background color directly to the game board
        setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);"
                + "-fx-background-radius: 10;"
                + "-fx-border-radius: 10;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                Card card = new Card(row, col, this);
                cards[row][col] = card;
                add(card, col, row);
            }
        }

        Button newGameButton = new Button("New Game");
        newGameButton.setOnAction(e -> startNewGame());
        newGameButton.setStyle("-fx-font-size: 16px; "
                + "-fx-background-color: #ff9800; "
                + "-fx-text-fill: white; "
                + "-fx-background-radius: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-padding: 10 20;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 2);");
        add(newGameButton, 1, 5, 2, 1);

        moveCountLabel = new Label("Moves: 0");
        moveCountLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        add(moveCountLabel, 0, 5, 1, 1);
    }

    /**
     * Starts a new game by resetting the game logic and updating the board.
     */
    public void startNewGame() {
        isProcessing = false;
        gameLogic.resetGame();
    }

    /**
     * Handles the logic when a card is clicked.
     *
     * @param row the row of the clicked card
     * @param col the column of the clicked card
     */
    public void cardClicked(int row, int col) {
        if (!isProcessing && gameLogic.canRevealCard(row, col)) {
            gameLogic.revealCard(row, col);

            if (gameLogic.isGameWon()) {
                // Handle game won scenario (e.g., display a message)
            }
        }
    }

    /**
     * Updates the game board to reflect the current game state.
     */
    private void updateBoard() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                cards[row][col].update();
            }
        }
    }

    /**
     * Sets the processing state of the game.
     *
     * @param isProcessing true if the game is processing, false otherwise
     */
    private void setProcessing(boolean isProcessing) {
        this.isProcessing = isProcessing;
    }

    /**
     * Updates the move count label with the current number of moves.
     */
    private void updateMoveCount() {
        moveCountLabel.setText("Moves: " + gameLogic.getMoveCount());
    }

    /**
     * Shows a dialog when the game is won.
     */
    private void showGameWonDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Congratulations");
            alert.setHeaderText(null);
            alert.setContentText("You won the game in " + gameLogic.getMoveCount() + " moves!");

            alert.showAndWait();
        });
    }

    /**
     * Returns the game logic instance.
     *
     * @return the game logic instance
     */
    public MemoryGameLogic getGameLogic() {
        return gameLogic;
    }
}
