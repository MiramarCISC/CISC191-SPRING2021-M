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
        int row = start.getRow()-end.getRow();
        int column = start.getColumn()-end.getColumn();
        return ((end.getPiece().isWhite() != start.getPiece().isWhite()) || end.getPiece().toString() == "Blank")
                && (((row)* (column)==2 || (row)*(column)==-2));

    }


}