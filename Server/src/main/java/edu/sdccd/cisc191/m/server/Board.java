package edu.sdccd.cisc191.m.server;


import java.util.LinkedList;

public class Board {

    private final Square[][] board;
    private final LinkedList<Square> boardPosition;

    public Board(){

        board = new Square[8][8];
        boardPosition = new LinkedList<Square>();

    }


    //set board with all the pieces
    public void resetBoard() {

        board[0][0] = new Square(0, 0, new Rook(true, false));
        board[0][1] = new Square(0, 1, new Knight(true, false));
        board[0][2] = new Square(0, 2, new Bishop(true, false));
        board[0][3] = new Square(0, 3, new King(true, false));
        board[0][4] = new Square(0, 4, new Queen(true, false));
        board[0][5] = new Square(0, 5, new Bishop(true, false));
        board[0][6] = new Square(0, 6, new Knight(true, false));
        board[0][7] = new Square(0, 7, new Rook(true, false));
        board[1][0] = new Square(1, 0, new Pawn(true,false));
        board[1][1] = new Square(1, 1, new Pawn(true,false));
        board[1][2] = new Square(1, 2, new Pawn(true,false));
        board[1][3] = new Square(1, 3, new Pawn(true,false));
        board[1][4] = new Square(1, 4, new Pawn(true,false));
        board[1][5] = new Square(1, 5, new Pawn(true,false));
        board[1][6] = new Square(1, 6, new Pawn(true,false));
        board[1][7] = new Square(1, 7, new Pawn(true,false));

        for (int r = 2; r < 6; r++) {
            for (int c = 0; c < 8; c++) {
                board[r][c] = new Square(r, c, new Blank());
            }
        }

        board[7][0] = new Square(7, 0, new Rook(false, false));
        board[7][1] = new Square(7, 1, new Knight(false, false));
        board[7][2] = new Square(7, 2, new Bishop(false, false));
        board[7][3] = new Square(7, 3, new King(false, false));
        board[7][4] = new Square(7, 4, new Queen(false, false));
        board[7][5] = new Square(7, 5, new Bishop(false, false));
        board[7][6] = new Square(7, 6, new Knight(false, false));
        board[7][7] = new Square(7, 7, new Rook(false, false));
        board[6][0] = new Square(6, 0, new Pawn(false,false));
        board[6][1] = new Square(6, 1, new Pawn(false,false));
        board[6][2] = new Square(6, 2, new Pawn(false,false));
        board[6][3] = new Square(6, 3, new Pawn(false,false));
        board[6][4] = new Square(6, 4, new Pawn(false,false));
        board[6][5] = new Square(6, 5, new Pawn(false,false));
        board[6][6] = new Square(6, 6, new Pawn(false,false));
        board[6][7] = new Square(6, 7, new Pawn(false,false));


    }

    //sets a position of the board to what was held in the Linked list. Used to swap Pieces.
    public void setBoard(LinkedList<Square>bp, int srow, int scol, int erow, int ecol, int spIndex, int epIndex){
        board[srow][scol] = bp.get(spIndex);
        board[erow][ecol]= bp.get(epIndex);

    }

    // a list that hold all the squares on the baord
    public void setList(){
        for(int r = 0; r < board.length;r++){
            for(int c = 0; c< board[r].length;c++){
                boardPosition.add(board[r][c]);
            }
        }
    }


    public LinkedList<Square> getList(){
        return boardPosition;
    }

    public Square getSquare(int row, int column) {
        return board[row][column];
    }

    public Square[][] getBoard() {
        return board;
    }


    //displays what the board looks like in the server
    public void displayBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < (board[0].length); j++) {
                System.out.printf("%-16s at %d, %d   " ,"This is a "+ board[i][j].getPiece(),(i),(j));

            }
            System.out.println();

        }
        System.out.println();
        System.out.println();

    }

    //Moves the piee to a specific square
    public void swapSquares(Square start, Square end){


        Square temp = start;
        board[start.getRow()][start.getColumn()] = new Square(start.getRow(),start.getColumn(),new Blank());
        board[end.getRow()][end.getColumn()].setPiece(temp.getPiece());



    }

    //sets a square to the another square inside the board.
    public void setSquare(Square change, Square newPiece){
        board[change.getRow()][change.getColumn()] = newPiece;
    }





}