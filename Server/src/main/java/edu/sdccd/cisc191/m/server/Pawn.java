package edu.sdccd.cisc191.m.server;

/**
 * Author(s): Aiden Wise, Austin Nguyen
 * Description: The code the makes up the Pawn object.
 * Is primarily used to calculate a valid move of the piece
 */

public class Pawn extends Pieces {

    public Pawn(boolean isWhite, boolean moved) {
        super(isWhite, moved);

    }

    public String toString() {
        return "Pawn";
    }

    @Override
    public boolean validateMove(Board board, Square start, Square end) {
        if (end.getPiece().isWhite() == start.getPiece().isWhite() && end.getPiece().toString() != "Blank") { //checks if there is a piece of the same color in end pos
            return false;
        }

        int x = Math.abs(start.getRow() - end.getRow());
        int y = Math.abs(start.getColumn() - end.getColumn());

        if (start.getPiece().isWhite()) { //white pawns can only move to higher rows
            if (start.getRow() >= end.getRow()) {
                return false;

            }
        } else //black pawns can only move to lower rows
            if (start.getRow() <= end.getRow()) {
                return false;
            }


        if (end.getPiece().toString() != "Blank" && (Math.abs(x) == 1) && (Math.abs(y) == 1)) {
            return true;
        }

        if (moved == false) {
            if ((x > 2) || y != 0) {
                return false;
            }
        } else if ((x != 1 || y != 0)) {
            return false;

        }


        if (end.getPiece().toString() != "Blank" && ((Math.abs(x) == 1) && (Math.abs(y) == 0))) {
            return false;

        }


        return true;
    }


}