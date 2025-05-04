package yukon.model;

public class Card {

    private final Suit suit;
    private final int number;
    private boolean shown;
    private Card next;

    public Card(Suit suit, int number, boolean shown, Card next) {
        this.suit = suit;
        this.number = number;
        this.shown = shown;
        this.next = next;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getNumber() {
        return number;
    }

    public boolean isShown() {
        return shown;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
    }

    public Card getNext() {
        return next;
    }

    public void setNext(Card next) {
        this.next = next;
    }

    public static Card getTail(Card head) {
        Card current = head;
        while (current.next != null) {
            current = current.next;
        }
        return current;
    }

}
