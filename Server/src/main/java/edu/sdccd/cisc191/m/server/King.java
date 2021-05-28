package edu.sdccd.cisc191.m.server;

import edu.sdccd.cisc191.m.Player;

public class King extends Pieces {
    public King(boolean isWhite, boolean moved) {
        super(isWhite, moved);

    }

    public String toString() {
        return "King";
    }

    @Override
    public boolean validateMove(Board board, Square start, Square end) {
        Game logic = new Game();
        Player color = new Player();

        if(isWhite()) {
            color.setWhite(false);
        }else{
            color.setWhite(true);
        }

        if (end.getPiece().isWhite() == start.getPiece().isWhite() && end.getPiece().toString()!="Blank") { //checks if there is a piece of the same color in end pos
            System.out.println("break1");
            return false;
        }
        //citation
        int x = Math.abs(start.getRow() - end.getRow());
        int y = Math.abs(start.getColumn() - end.getColumn());

        //check for queen side castling
        //check if the king and the rook on right has moved
        if((moved==false)&&(board.getSquare(start.getRow(),start.getColumn()+4).getPiece().toString()=="Rook")&&
                (board.getSquare(start.getRow(),start.getColumn()+4).getPiece().hasMoved()==false)&&start.getRow()==end.getRow()&&
                start.getColumn()+2== end.getColumn()){
            Square temp;
            for(int i = start.getColumn()+1; i < 7 ; i++){
                temp = new Square(start.getRow(),i,board.getSquare(start.getRow(),i).getPiece());
                if(temp.getPiece().toString() != "Blank" || logic.isChecked(board, color, start, board.getSquare(start.getRow(),start.getColumn()+1))==true){
                    return false;
                }

            }

        }
        //check for king side castling
        //check if king and the rook on left has moved
        else if((moved==false)&&(board.getSquare(start.getRow(),start.getColumn()-3).getPiece().toString()=="Rook")&&
                (board.getSquare(start.getRow(),start.getColumn()-3).getPiece().hasMoved()==false)&&start.getRow()==end.getRow()&&
                start.getColumn()-2== end.getColumn()){
            Square temp;
            for(int i = start.getColumn()-1; i >0 ; i--){
                temp = new Square(start.getRow(),i,board.getSquare(start.getRow(),i).getPiece());
                if(temp.getPiece().toString() != "Blank"|| logic.isChecked(board, color, start, board.getSquare(start.getRow(),start.getColumn()-1))==true){
                    return false;
                }

            }

        }


        else if(start.getColumn()!=end.getColumn()&&(start.getRow()!=end.getRow())){
            if(x + y !=2){
                System.out.println("break2");
                return false;
            }
        }else if (x + y != 1) {
            System.out.println("break3");
            return false;

        }






        return true;
    }


}