package yukon.model;

/**
 * The possible suits of a card.
 */
public enum Suit {
    CLUBS, HEARTS, SPADES, DIAMONDS;

    public static char toChar(Suit suit) {
        return switch (suit) {
            case CLUBS -> 'C';
            case HEARTS -> 'H';
            case SPADES -> 'S';
            case DIAMONDS -> 'D';
        };
    }
}
