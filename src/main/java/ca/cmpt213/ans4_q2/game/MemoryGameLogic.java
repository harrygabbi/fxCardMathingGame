package ca.cmpt213.ans4_q2.game;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * MemoryGameLogic handles the core logic for the Memory Matching Game.
 * It manages the game state, processes user actions, and updates the game board.
 */
public class MemoryGameLogic {
    private CardLogic[][] board;
    private List<Card> cards;
    private boolean firstCardRevealed;
    private CardLogic firstCard;
    private CardLogic secondCard;
    private Runnable onBoardUpdated;
    private Runnable onProcessingStart;
    private Runnable onProcessingEnd;
    private Runnable onGameWon;
    private int moveCount;
    private Runnable onMoveCountChanged;

    /**
     * Constructs a MemoryGameLogic instance and initializes the game board.
     */
    public MemoryGameLogic() {
        this.board = new CardLogic[4][4];
        this.cards = new ArrayList<>();  // Initialize cards list
        loadCards();
        resetGame();
    }

    /**
     * Loads the card images and sounds into the cards list.
     */
    private void loadCards() {
        for (int i = 1; i <= 8; i++) {
            Image image = new Image(getClass().getResourceAsStream("/Images/" + i + ".png"));
            String soundFile = getClass().getResource("/Sounds/" + i + ".mp3").toString();
            cards.add(new Card(image, soundFile));
            cards.add(new Card(image, soundFile)); // Add each card twice
        }
    }

    /**
     * Sets the callback to be invoked when the board is updated.
     *
     * @param onBoardUpdated the callback to set
     */
    public void setOnBoardUpdated(Runnable onBoardUpdated) {
        this.onBoardUpdated = onBoardUpdated;
    }

    /**
     * Sets the callback to be invoked when processing starts.
     *
     * @param onProcessingStart the callback to set
     */
    public void setOnProcessingStart(Runnable onProcessingStart) {
        this.onProcessingStart = onProcessingStart;
    }

    /**
     * Sets the callback to be invoked when processing ends.
     *
     * @param onProcessingEnd the callback to set
     */
    public void setOnProcessingEnd(Runnable onProcessingEnd) {
        this.onProcessingEnd = onProcessingEnd;
    }

    /**
     * Sets the callback to be invoked when the game is won.
     *
     * @param onGameWon the callback to set
     */
    public void setOnGameWon(Runnable onGameWon) {
        this.onGameWon = onGameWon;
    }

    /**
     * Sets the callback to be invoked when the move count changes.
     *
     * @param onMoveCountChanged the callback to set
     */
    public void setOnMoveCountChanged(Runnable onMoveCountChanged) {
        this.onMoveCountChanged = onMoveCountChanged;
    }

    /**
     * Resets the game to its initial state.
     */
    public void resetGame() {
        firstCardRevealed = false;
        firstCard = null;
        secondCard = null;
        moveCount = 0;
        shuffleCards();
        initializeBoard();
        notifyBoardUpdated();
        notifyMoveCountChanged();
    }

    /**
     * Shuffles the list of cards to randomize their positions.
     */
    private void shuffleCards() {
        Collections.shuffle(cards);
    }

    /**
     * Initializes the game board with shuffled cards.
     */
    private void initializeBoard() {
        int cardIndex = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                Card card = cards.get(cardIndex);
                board[row][col] = new CardLogic(card.getImage(), card.getSoundFile());
                cardIndex++;
            }
        }
    }

    /**
     * Checks if a card at the specified position can be revealed.
     *
     * @param row the row of the card
     * @param col the column of the card
     * @return true if the card can be revealed, false otherwise
     */
    public boolean canRevealCard(int row, int col) {
        return !board[row][col].isMatched() && !board[row][col].isRevealed();
    }

    /**
     * Reveals a card at the specified position and processes game logic.
     *
     * @param row the row of the card
     * @param col the column of the card
     */
    public void revealCard(int row, int col) {
        CardLogic card = board[row][col];
        card.reveal();
        notifyBoardUpdated();

        if (!firstCardRevealed) {
            firstCardRevealed = true;
            firstCard = card;
        } else {
            secondCard = card;
            notifyProcessingStart();
            moveCount++;
            notifyMoveCountChanged();
            if (!firstCard.matches(secondCard)) {
                // If the cards don't match, hide them again after a short delay
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(event -> {
                    Platform.runLater(() -> {
                        firstCard.hide();
                        secondCard.hide();
                        firstCardRevealed = false;
                        notifyBoardUpdated();
                        notifyProcessingEnd();
                    });
                });
                pause.play();
            } else {
                firstCard.match();
                secondCard.match();
                playSound(firstCard.getSoundFile());
                firstCardRevealed = false;
                notifyBoardUpdated();
                notifyProcessingEnd();

                if (isGameWon()) {
                    notifyGameWon();
                }
            }
        }
    }

    /**
     * Checks if the game is won by verifying that all cards are matched.
     *
     * @return true if the game is won, false otherwise
     */
    public boolean isGameWon() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (!board[row][col].isMatched()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Retrieves the image for a card at the specified position.
     *
     * @param row the row of the card
     * @param col the column of the card
     * @return the image of the card if it is revealed or matched, null otherwise
     */
    public Image getImageForCard(int row, int col) {
        return board[row][col].isRevealed() || board[row][col].isMatched() ? board[row][col].getImage() : null;
    }

    /**
     * Notifies the callback that the board has been updated.
     */
    private void notifyBoardUpdated() {
        if (onBoardUpdated != null) {
            onBoardUpdated.run();
        }
    }

    /**
     * Notifies the callback that processing has started.
     */
    private void notifyProcessingStart() {
        if (onProcessingStart != null) {
            onProcessingStart.run();
        }
    }

    /**
     * Notifies the callback that processing has ended.
     */
    private void notifyProcessingEnd() {
        if (onProcessingEnd != null) {
            onProcessingEnd.run();
        }
    }

    /**
     * Notifies the callback that the game has been won.
     */
    private void notifyGameWon() {
        if (onGameWon != null) {
            onGameWon.run();
        }
    }

    /**
     * Notifies the callback that the move count has changed.
     */
    private void notifyMoveCountChanged() {
        if (onMoveCountChanged != null) {
            onMoveCountChanged.run();
        }
    }

    /**
     * Plays the sound associated with a card.
     *
     * @param soundFile the sound file to play
     */
    private void playSound(String soundFile) {
        Media media = new Media(soundFile);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    /**
     * Retrieves the current move count.
     *
     * @return the current move count
     */
    public int getMoveCount() {
        return moveCount;
    }
}
