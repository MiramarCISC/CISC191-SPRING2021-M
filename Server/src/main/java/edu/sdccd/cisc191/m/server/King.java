package edu.sdccd.cisc191.m.server;

public class King extends Pieces {
    public King(boolean isWhite, boolean moved) {
        super(isWhite, moved);

    }

    public String toString() {
        return "King";
    }

    @Override
    public boolean getLegalMoves(Board board, Square start, Square end) {
        return false;
    }


}
