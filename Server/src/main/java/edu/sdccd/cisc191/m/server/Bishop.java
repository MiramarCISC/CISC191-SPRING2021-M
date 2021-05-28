package edu.sdccd.cisc191.m.server;

public class Bishop extends Pieces {
    public Bishop(boolean isWhite, boolean moved) {
        super(isWhite, moved);

    }

    public String toString() {
        return "Bishop";
    }

    @Override
    public boolean validateMove(Board board, Square start, Square end) {

        int slope = 0;
        int rise = end.getRow() - start.getRow();
        int run = end.getColumn() - start.getColumn();

        if (run == 0) {

            return false;

        } else if (rise % run == 0) {
            slope = rise / run;
        }


        if (slope == -1) { //top right and bottom left check
            if (start.getRow() > end.getRow() && start.getColumn() < end.getColumn()) { //top right check
                int column = start.getColumn() + 1;
                for (int i = start.getRow() - 1; i > end.getRow(); i--) { //top right check
                    Square temp = board.getSquare(i, column);
                    if ("Blank" != temp.getPiece().toString()) {

                        return false;

                    }
                    column++;
                }
            } else if (start.getRow() < end.getRow() && start.getColumn() > end.getColumn()) { //bottom left check
                int column = start.getColumn() - 1;
                for (int i = start.getRow() + 1; i < end.getRow(); i++) {
                    Square temp = board.getSquare(i, column);
                    if ("Blank" != temp.getPiece().toString()) {

                        return false;
                    }
                    column--;
                }
            }

        } else if (slope == 1) { //Top Left and Bottom Right Check
            if (start.getRow() > end.getRow() && start.getColumn() > end.getColumn()) { //Top left check
                int column = start.getColumn() - 1;
                for (int i = start.getRow() - 1; i > end.getRow(); i--) {
                    Square temp = board.getSquare(i, column);
                    if ("Blank" != temp.getPiece().toString()) {

                        return false;
                    }
                    column--;
                }
            } else if (start.getRow() < end.getRow() && start.getColumn() < end.getColumn()) { //Bottom Right check
                int column = start.getColumn() + 1;
                for (int i = start.getRow() + 1; i < end.getRow(); i++) {
                    Square temp = board.getSquare(i, column);
                    if ("Blank" != temp.getPiece().toString()) {

                        return false;
                    }
                    column++;
                }
            }

            //if its the same piece color the move not valid
            if (start.getPiece().isWhite() == end.getPiece().isWhite() && end.getPiece().toString() != "Blank") {
                return false;
            }

        }

        //finally if the slope is equal to one and goes through every other statement without going false, the move is valid.
        if (slope == 1 || slope == -1) {

            return true;
        }

        return false;
    }


}