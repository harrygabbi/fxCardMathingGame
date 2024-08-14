package ca.cmpt213.ans4_q2.game;

import javafx.scene.image.Image;

/**
 * CardLogic represents the logic and state of a single card in the Memory Matching Game.
 * It manages the card's image, revealed state, matched state, and associated sound file.
 */
public class CardLogic {
    private Image image;
    private boolean revealed;
    private boolean matched;
    private String soundFile;

    /**
     * Constructs a CardLogic instance with the specified image and sound file.
     *
     * @param image     the image of the card
     * @param soundFile the sound file associated with the card
     */
    public CardLogic(Image image, String soundFile) {
        this.image = image;
        this.revealed = false;
        this.matched = false;
        this.soundFile = soundFile;
    }

    /**
     * Reveals the card by setting its revealed state to true.
     */
    public void reveal() {
        this.revealed = true;
    }

    /**
     * Hides the card by setting its revealed state to false.
     */
    public void hide() {
        this.revealed = false;
    }

    /**
     * Matches the card by setting its matched state to true.
     */
    public void match() {
        this.matched = true;
    }

    /**
     * Checks if the card is revealed.
     *
     * @return true if the card is revealed, false otherwise
     */
    public boolean isRevealed() {
        return revealed;
    }

    /**
     * Checks if the card is matched.
     *
     * @return true if the card is matched, false otherwise
     */
    public boolean isMatched() {
        return matched;
    }

    /**
     * Retrieves the image of the card.
     *
     * @return the image of the card
     */
    public Image getImage() {
        return image;
    }

    /**
     * Checks if this card matches another card based on their images.
     *
     * @param other the other card to compare with
     * @return true if the cards have the same image, false otherwise
     */
    public boolean matches(CardLogic other) {
        return this.image.equals(other.image);
    }

    /**
     * Retrieves the sound file associated with the card.
     *
     * @return the sound file associated with the card
     */
    public String getSoundFile() {
        return soundFile;
    }
}
