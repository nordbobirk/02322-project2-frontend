package yukon.view;

public interface CardClickCallback {

    /**
     * A callback that is run when a click occurs on a button on a CardColumnView.
     *
     * @param column the column that was click
     * @param index  the index of the card that was clicked
     */
    void call(int column, int index);
}
