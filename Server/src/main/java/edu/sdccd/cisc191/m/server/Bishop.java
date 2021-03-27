package edu.sdccd.cisc191.m.server;

public class Bishop extends Pieces {
    public Bishop(boolean isWhite, boolean moved) {
        super(isWhite, moved);

    }

    public String toString() {
        return "Bishop";
    }

    @Override
    public boolean getLegalMoves(Board board, Square start, Square end) {
        return false;
    }


}
