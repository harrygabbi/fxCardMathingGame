package ca.cmpt213.ans4_q2.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Card represents a single card in the Memory Matching Game.
 * It extends StackPane and contains the logic for displaying and updating the card's state.
 */
public class Card extends StackPane {
    private int row;
    private int col;
    private GameBoard gameBoard;
    private ImageView imageView;
    private Image BackImage = new Image(getClass().getResourceAsStream("/Images/BackPicture.png"));

    /**
     * Constructs a Card with the specified row, column, and reference to the GameBoard.
     *
     * @param row       the row position of the card
     * @param col       the column position of the card
     * @param gameBoard the reference to the game board that contains this card
     */
    public Card(int row, int col, GameBoard gameBoard) {
        this.row = row;
        this.col = col;
        this.gameBoard = gameBoard;

        // Initialize ImageView
        imageView = new ImageView();

        // Set ImageView properties
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);

        // Add the back image to the StackPane
        getChildren().add(imageView);

        // Add mouse click event
        setOnMouseClicked(e -> handleClick());

        // Set preferred size of the card
        setPrefSize(150, 150);

        // Apply a background color and style to the card
        setStyle("-fx-background-color: white;"
                + "-fx-background-radius: 10;"
                + "-fx-border-radius: 10;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 2);");
    }

    /**
     * Handles the click event on the card and notifies the game board.
     */
    private void handleClick() {
        gameBoard.cardClicked(row, col);
    }

    /**
     * Updates the card's image based on its current state in the game logic.
     * If the card is revealed, it shows the corresponding image; otherwise, it shows the back image.
     */
    public void update() {
        Image image = gameBoard.getGameLogic().getImageForCard(row, col);
        if (image != null) {
            imageView.setImage(image);
        } else {
            imageView.setImage(BackImage);
        }
    }
}
