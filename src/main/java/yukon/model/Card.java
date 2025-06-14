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

    public static char numberToChar(int number) {
        return switch (number) {
            case 1 -> 'A';
            case 10 -> 'T';
            case 11 -> 'J';
            case 12 -> 'Q';
            case 13 -> 'K';
            default -> ("" + number).charAt(0);
        };
    }

    public static int getColumnSize(Card head) {
        if (head == null) {
            return 0;
        }

        int size = 1;
        Card current = head;
        while (current.getNext() != null) {
            size++;
            current = current.getNext();
        }
        return size;
    }

    public static Card getNthCard(Card columnHead, int n) {
        if (n <= 0) {
            return columnHead;
        }

        int index = 0;
        Card current = columnHead;
        while (current.getNext() != null) {
            current = current.getNext();
            index++;
            if (index == n) return current;
        }

        return null;
    }

    public Card getTail() {
        Card current = this;
        while (current.next != null) {
            current = current.next;
        }
        return current;
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

    @Override
    public String toString() {
        return String.format("%c%c%d", Card.numberToChar(number), Suit.toChar(suit), shown ? 1 : 0);
    }

}
