package ca.cmpt213.ans4_q2.game;

import javafx.scene.image.Image;

/**
 * Card represents a single card in the Memory Matching Game.
 * It contains the card's image and the associated sound file.
 */
public class Card {
    private Image image;
    private String soundFile;

    /**
     * Constructs a Card instance with the specified image and sound file.
     *
     * @param image     the image of the card
     * @param soundFile the sound file associated with the card
     */
    public Card(Image image, String soundFile) {
        this.image = image;
        this.soundFile = soundFile;
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
     * Retrieves the sound file associated with the card.
     *
     * @return the sound file associated with the card
     */
    public String getSoundFile() {
        return soundFile;
    }
}
