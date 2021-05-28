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

        if(run == 0){
            System.out.println("Rise: " + rise + " End Row: " + end.getRow() + " - Start Row: " + start.getRow());
            System.out.println("Run: " + run + " End Column: " + end.getColumn() + " - Start Column: " + start.getColumn());
            System.out.println("Slope break: " + slope);
            return false;
        }else if(rise%run ==0){
            slope = rise/run;
        }


        if (slope == -1) { //top right and bottom left check
            if (start.getRow() > end.getRow() && start.getColumn() < end.getColumn()) { //top right check
                int column = start.getColumn() + 1;
                for (int i = start.getRow() -1 ; i > end.getRow(); i--) { //top right check
                    Square temp = board.getSquare(i, column);
                    if ("Blank" != temp.getPiece().toString()) {

                        System.out.println("Chose a Piece At " + end.getRow() + "," + end.getColumn()
                                + " Breaks(Top Right) at " + temp.getRow() + "," + temp.getColumn());
                        return false;

                    }
                    column++;
                }
            } else if (start.getRow() < end.getRow() && start.getColumn() > end.getColumn()) { //bottom left check
                int column = start.getColumn() - 1;
                for (int i = start.getRow() + 1; i < end.getRow(); i++) {
                    Square temp = board.getSquare(i, column);
                    if ("Blank" != temp.getPiece().toString()) {
                        System.out.println("Chose a Piece At " + end.getRow() + "," + end.getColumn()
                                + " Breaks(bottom left) at " + temp.getRow() + "," + temp.getColumn());
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
                        System.out.println("Chose a Piece At " + end.getRow() + "," + end.getColumn()
                                + " Breaks(Top Left) at " + temp.getRow() + "," + temp.getColumn());
                        return false;
                    }
                    column--;
                }
            } else if (start.getRow() < end.getRow() && start.getColumn() < end.getColumn()) { //Bottom Right check
                int column = start.getColumn() + 1;
                for (int i = start.getRow() + 1; i < end.getRow(); i++) {
                    Square temp = board.getSquare(i, column);
                    if ("Blank" != temp.getPiece().toString()) {
                        System.out.println("Chose a Piece At " + end.getRow() + "," + end.getColumn()
                                + " Breaks(Bottom Right) at " + temp.getRow() + "," + temp.getColumn());
                        return false;
                    }
                    column++;
                }
            }
            if (start.getPiece().isWhite() == end.getPiece().isWhite() && end.getPiece().toString()!="Blank") {
                System.out.println("Break: Allied Piece");
                System.out.println(slope);
                System.out.println("Start:" + start.getPiece().isWhite() + start.getPiece().toString());
                System.out.println("End:" + end.getPiece().isWhite() + end.getPiece().toString());
                return false;
            }

        }
        if (slope == 1 || slope == -1) {
            System.out.println("Attack (Bishop)");
            System.out.println("Rise: " + rise + " End Row: " + end.getRow() + " - Start Row: " + start.getRow());
            System.out.println("Run: " + run + " End Column: " + end.getColumn() + " - Start Column: " + start.getColumn());
            System.out.println("Slope: " + slope);
            return true;
        }
        System.out.println("BROKE");
        System.out.println("Rise: " + rise + " End Row: " + end.getRow() + " - Start Row: " + start.getRow());
        System.out.println("Run: " + run + " End Column: " + end.getColumn() + " - Start Column: " + start.getColumn());
        System.out.println("Slope: " + slope);
        return false;
    }


}