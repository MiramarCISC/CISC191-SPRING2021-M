package edu.sdccd.cisc191.m.server;

public class Rook extends Pieces {
    public Rook(boolean isWhite, boolean moved) {
        super(isWhite, moved);

    }

    public String toString() {
        return "Rook";
    }

    //
    @Override
    public boolean validateMove(Board board, Square start, Square end) {

        if (end.getPiece().toString().equals("Blank")) {

            if (Math.abs(start.getRow() - end.getRow()) > 0) { //Check vertically
                Square temp = new Square(-1, -1, new Blank());
                if (end.getColumn() != start.getColumn()) {
                    return false;
                } else if (end.getRow() > start.getRow()) { //down check
                    for (int i = start.getRow() + 1; i < end.getRow(); i++) {
                        temp = board.getSquare(i, start.getColumn());
                        if (!"Blank".equals(temp.getPiece().toString())) {
                            return false;
                        }
                    }
                } else if (end.getRow() < start.getRow()) { //up check
                    for (int i = end.getRow() + 1; i < start.getRow(); i++) {
                        temp = board.getSquare(i, start.getColumn());
                        if (temp.getPiece().toString() != "Blank") {
                            return false;
                        }
                    }
                }
            }
            if (Math.abs(start.getColumn() - end.getColumn()) > 0) { //checks horizontally
                Square temp = new Square(-1, -1, null);
                if (end.getRow() != start.getRow()) {
                    return false;
                } else if (end.getColumn() > start.getColumn()) { //right check
                    for (int i = start.getColumn() + 1; i < end.getColumn(); i++) {
                        temp = board.getSquare(start.getRow(), i);
                        if (temp.getPiece().toString() != "Blank") {
                            return false;
                        }
                    }
                } else if (end.getColumn() < start.getColumn()) { //left check
                    for (int i = end.getColumn() + 1; i < start.getColumn(); i++) {
                        temp = board.getSquare(start.getRow(), i);
                        if (temp.getPiece().toString() != "Blank") {
                            return false;
                        }
                    }
                }
            }


            return true;
        } else {
            if (Math.abs(start.getRow() - end.getRow()) > 0) { //Check vertically
                Square temp = new Square(-1, -1, new Blank());
                if (end.getColumn() != start.getColumn()) {

                    return false;
                } else if (end.getRow() > start.getRow()) { //down check
                    for (int i = start.getRow() + 1; i < end.getRow(); i++) {
                        temp = board.getSquare(i, start.getColumn());
                        if (!"Blank".equals(temp.getPiece().toString())) {
                            return false;
                        }
                    }
                } else if (end.getRow() < start.getRow()) { //up check
                    for (int i = end.getRow() + 1; i < start.getRow(); i++) {
                        temp = board.getSquare(i, start.getColumn());
                        if (temp.getPiece().toString() != "Blank") {
                            return false;
                        }
                    }
                }
            }
            if (Math.abs(start.getColumn() - end.getColumn()) > 0) { //checks horizontally
                Square temp = new Square(-1, -1, null);
                if (end.getRow() != start.getRow()) {
                    return false;
                } else if (end.getColumn() > start.getColumn()) { //right check
                    for (int i = start.getColumn() + 1; i < end.getColumn(); i++) {
                        temp = board.getSquare(start.getRow(), i);
                        if (temp.getPiece().toString() != "Blank") {
                            return false;
                        }
                    }
                } else if (end.getColumn() < start.getColumn()) { //left check
                    for (int i = end.getColumn() + 1; i < start.getColumn(); i++) {
                        temp = board.getSquare(start.getRow(), i);
                        if (temp.getPiece().toString() != "Blank") {

                            return false;
                        }
                    }
                }
            }
            if(end.getPiece().isWhite()==start.getPiece().isWhite()){

                return false;
            }else

                return true;
        }
    }





}