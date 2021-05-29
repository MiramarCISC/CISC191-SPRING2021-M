package edu.sdccd.cisc191.m.server;

/**
 * Author(s): Aiden Wise, Austin Nguyen
 * Description: This is the abstract class that is the main structure for all the pieces
 */

public abstract class Pieces {
    protected boolean white;
    protected boolean moved;

    public Pieces(boolean white, boolean moved) {
        this.white = white;
        this.moved = moved;
    }

    public Pieces() {

    }

    public abstract boolean validateMove(Board board, Square start, Square end);

    public abstract String toString();


    public boolean hasMoved() {
        return moved;

    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public boolean isWhite() {
        return white;
    }

}