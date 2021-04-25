package edu.sdccd.cisc191.m.server;

public class Knight extends Pieces {
    public Knight(boolean isWhite, boolean moved) {
        super(isWhite, moved);

    }

    public String toString() {
        return "Knight";
    }

    @Override
    public boolean validateMove(Board board, Square start, Square end) {
        return end.getPiece().isWhite() != this.isWhite() && Math.abs((start.getRow()-end.getRow())* (start.getColumn()-end.getColumn()))==2;

    }


}
