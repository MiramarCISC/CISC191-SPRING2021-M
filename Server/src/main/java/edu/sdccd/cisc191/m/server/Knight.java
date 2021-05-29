package edu.sdccd.cisc191.m.server;

/**
 * Author(s): Aiden Wise, Austin Nguyen
 * Description: The code the makes up the Knight object.
 * Is primarily used to calculate a valid move of the piece
 */

public class Knight extends Pieces {
    public Knight(boolean isWhite, boolean moved) {
        super(isWhite, moved);

    }

    public String toString() {
        return "Knight";
    }

    @Override

    public boolean validateMove(Board board, Square start, Square end) {
        int row = start.getRow() - end.getRow();
        int column = start.getColumn() - end.getColumn();
        return ((end.getPiece().isWhite() != start.getPiece().isWhite()) || end.getPiece().toString() == "Blank")
                && (((row) * (column) == 2 || (row) * (column) == -2));

    }


}