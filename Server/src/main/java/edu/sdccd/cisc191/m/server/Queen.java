package edu.sdccd.cisc191.m.server;

public class Queen extends Pieces {

    public Queen(boolean isWhite, boolean moved) {
        super(isWhite, moved);

    }

    public String toString() {
        return "Queen";
    }

    @Override
    public boolean validateMove(Board board, Square start, Square end) {

        Rook rook = new Rook(start.getPiece().isWhite(), start.getPiece().hasMoved());
        Bishop bishop = new Bishop(start.getPiece().isWhite(), start.getPiece().hasMoved());
        if (bishop.validateMove(board, start, end) == true || rook.validateMove(board, start, end) == true) {
            return true;
        }

        return false;

    }
}