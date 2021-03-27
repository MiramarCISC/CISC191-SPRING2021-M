package edu.sdccd.cisc191.m.server;

public class Pawn extends Pieces {

    public Pawn(boolean isWhite, boolean moved) {
        super(isWhite, moved);

    }

    public String toString() {
        return "Pawn";
    }

    @Override
    public boolean getLegalMoves(Board board, Square start, Square end) {
        if (end.getPiece().isWhite() == isWhite()) { //checks if there is a piece of the same color in end pos
            return false;
        }
        //citation
        int x = Math.abs(start.getRow() - end.getRow());
        int y = Math.abs(start.getColumn() - end.getColumn());
        if (x + y == 1) {
            return true;

        }
        return true;
    }

    public void promote() {

    }


}
