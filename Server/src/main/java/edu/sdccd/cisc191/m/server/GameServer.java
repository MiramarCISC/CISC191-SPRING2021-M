package edu.sdccd.cisc191.m.server;


import edu.sdccd.cisc191.m.*;

import java.net.*;
import java.io.*;

import java.util.LinkedList;


/**
 * This program is a server that takes connection requests on
 * the port specified by the constant LISTENING_PORT.  When a
 * connection is opened, the program sends the current time to
 * the connected socket.  The program will continue to receive
 * and process connections until it is killed (by a CONTROL-C,
 * for example).  Note that this server processes each connection
 * as it is received, rather than creating a separate thread
 * to process the connection.
 */


public class GameServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Socket clientSocket2;
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader in2;
    private LinkedList <Board> boardStates = new LinkedList<Board>();
    private LinkedList<Square> boardPosition  = new LinkedList<Square>();
    private int numPlayers;


    public GameServer(){
        numPlayers = 0;

    }

    public void start(int port) throws Exception {
        serverSocket = new ServerSocket(port);



        System.out.println("Waiting for connections...");

        /*

        while(numPlayers < 2){
            clientSocket = serverSocket.accept();
            numPlayers++;
            System.out.println("Player #" + numPlayers + " has connected.");

        }
         */

        clientSocket = serverSocket.accept();
        clientSocket2 = serverSocket.accept();


        System.out.println("We now have 2 players. No longer accepting connections.");


        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


        String inputLine;

        boolean legal;
        boolean promotePossible = false;
        boolean checked;
        boolean checkmated;
        Board board = new Board(800,800);
        board.resetBoard();
        board.displayBoard();
        board.setList();
        boardPosition = board.getList();
        Game logic = new Game();
        int srow, scol, erow, ecol;
        int turns = 0;
        String turn = "white";


        Player player1 = new Player(true);
        Player player2 = new Player(false);


        Player hold = player1;



        while (true) {

            inputLine = in.readLine();


            System.out.println(inputLine);

            MoveRequest request = MoveRequest.fromJSON(inputLine);




            srow = request.getSrow();
            scol = request.getScol();
            erow = request.getErow();
            ecol = request.getEcol();

            System.out.println(srow);
            System.out.println(scol);
            System.out.println(erow);
            System.out.println(ecol);




            boolean isWhite;

            if(turn.equals("white")){
                isWhite = true;
            }else{
                isWhite = false;
            }

            System.out.println("This is hold white: " + hold.isWhite());
            System.out.println("This is variable white: " + isWhite);

            if(((srow<8 && srow>-1) && (scol<8 && scol>-1) && (erow<8 && erow>-1) && (ecol<8 && ecol>-1))
                    &&(board.getSquare(srow,scol).getPiece().isWhite()==isWhite)) {

                System.out.println("Selected Piece: " + board.getSquare(srow, scol).getPiece().toString());


                //(logic.isChecked(board,temp, board.getSquare(srow,scol), board.getSquare(erow, ecol))!=true)
                if (board.getSquare(srow, scol).getPiece().validateMove(board, board.getSquare(srow, scol), board.getSquare(erow, ecol))
                        &&(logic.isChecked(board,hold, board.getSquare(srow,scol), board.getSquare(erow, ecol))!=true)) { //checks if move is valid
                    int spIndex = 0;
                    int epIndex = 0;
                    legal = true;


                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();


                    if(turns%2==0){//sets next turn

                        turn = "black";


                        turns+=1;

                    } else {

                        turn = "white";

                        turns+=1;

                    }


                    if ((board.getSquare(srow, scol).getPiece().toString() == "King") && (board.getSquare(srow, scol).getPiece().hasMoved() == false) && (scol + 2 == ecol)) {
                        //Movement for castling queen side. Swaps 2 pieces at once

                        int spIndexRook = 0;
                        int epIndexRook = 0;

                        for (int i = 0; i < boardPosition.size(); i++) { //swaps pieces
                            if (boardPosition.get(i).getRow() == srow && boardPosition.get(i).getColumn() == scol) { //gets index of Starting Piece
                                spIndex = i;
                            }
                            if (boardPosition.get(i).getRow() == erow && boardPosition.get(i).getColumn() == ecol) { //gets index of Ending Piece
                                epIndex = i;
                            }

                        }

                        for (int i = 0; i < boardPosition.size(); i++) { //swaps pieces for rook
                            if (boardPosition.get(i).getRow() == srow && boardPosition.get(i).getColumn() == (scol + 4)) { //gets index of Starting Piece
                                spIndexRook = i;
                            }
                            if (boardPosition.get(i).getRow() == srow && boardPosition.get(i).getColumn() == (scol + 1)) { //gets index of Ending Piece
                                epIndexRook = i;
                            }

                        }


                        board.getSquare(srow, scol).getPiece().setMoved(true);//King

                        board.getSquare(srow, (scol + 4)).getPiece().setMoved(true);//Rook

                        //King
                        board.getSquare(srow, scol).setRow(erow);
                        board.getSquare(srow, scol).setColumn(ecol);
                        board.getSquare(erow, ecol).setRow(srow);
                        board.getSquare(erow, ecol).setColumn(scol);

                        //Rook
                        board.getSquare(srow, (scol + 4)).setColumn((scol + 1));
                        board.getSquare(erow, (scol + 1)).setRow((scol + 4));

                        //King
                        boardPosition.get(spIndex).getPiece().setMoved(true);
                        boardPosition.set(epIndex, boardPosition.get(spIndex)); //sets Ending Square to Starting square
                        boardPosition.get(epIndex).setRow(erow);
                        boardPosition.get(epIndex).setColumn(ecol);
                        boardPosition.set(spIndex, new Square(srow, scol, new Blank())); // sets Starting Square to blank square
                        board.setBoard(boardPosition, srow, scol, erow, ecol, spIndex, epIndex); //sets the position of the Pieces on the 2D Board using the LinkedList

                        //Rook
                        boardPosition.get(spIndexRook).getPiece().setMoved(true);
                        boardPosition.set(epIndexRook, boardPosition.get(spIndexRook)); //sets Ending Square to Starting square
                        boardPosition.get(epIndexRook).setRow(erow);
                        boardPosition.get(epIndexRook).setColumn(scol+1);
                        boardPosition.set(spIndexRook, new Square(srow, (scol + 4), new Blank())); // sets Starting Square to blank square

                        board.setBoard(boardPosition, srow, (scol + 4), erow, (scol + 1), spIndexRook, epIndexRook); //sets the position of the Pieces on the 2D Board using the LinkedList


                        boardStates.add(board); //saves the current board state
                        board.displayBoard();


                    } else if ((board.getSquare(srow, scol).getPiece().toString() == "King") && (board.getSquare(srow, scol).getPiece().hasMoved() == false) && (scol - 2 == ecol)) {
                        //Movement for castling King side. Swaps 2 pieces at once

                        int spIndexRook = 0;
                        int epIndexRook = 0;

                        for (int i = 0; i < boardPosition.size(); i++) { //swaps pieces
                            if (boardPosition.get(i).getRow() == srow && boardPosition.get(i).getColumn() == scol) { //gets index of Starting Piece
                                spIndex = i;
                            }
                            if (boardPosition.get(i).getRow() == erow && boardPosition.get(i).getColumn() == ecol) { //gets index of Ending Piece
                                epIndex = i;
                            }

                        }

                        for (int i = 0; i < boardPosition.size(); i++) { //swaps pieces for rook
                            if (boardPosition.get(i).getRow() == srow && boardPosition.get(i).getColumn() == (scol - 3)) { //gets index of Starting Piece
                                spIndexRook = i;
                            }
                            if (boardPosition.get(i).getRow() == srow && boardPosition.get(i).getColumn() == (scol - 1)) { //gets index of Ending Piece
                                epIndexRook = i;
                            }

                        }


                        board.getSquare(srow, scol).getPiece().setMoved(true);//King

                        board.getSquare(srow, (scol - 3)).getPiece().setMoved(true);//Rook

                        //King
                        board.getSquare(srow, scol).setRow(erow);
                        board.getSquare(srow, scol).setColumn(ecol);
                        board.getSquare(erow, ecol).setRow(srow);
                        board.getSquare(erow, ecol).setColumn(scol);

                        //Rook
                        board.getSquare(srow, (scol - 3)).setColumn((scol - 1));
                        board.getSquare(erow, (scol - 1)).setRow((scol - 3));

                        //King
                        boardPosition.get(spIndex).getPiece().setMoved(true);
                        boardPosition.set(epIndex, boardPosition.get(spIndex)); //sets Ending Square to Starting square
                        boardPosition.get(epIndex).setRow(erow);
                        boardPosition.get(epIndex).setColumn(ecol);
                        boardPosition.set(spIndex, new Square(srow, scol, new Blank())); // sets Starting Square to blank square
                        board.setBoard(boardPosition, srow, scol, erow, ecol, spIndex, epIndex); //sets the position of the Pieces on the 2D Board using the LinkedList

                        //Rook
                        boardPosition.get(spIndexRook).getPiece().setMoved(true);
                        boardPosition.set(epIndexRook, boardPosition.get(spIndexRook)); //sets Ending Square to Starting square
                        boardPosition.get(epIndexRook).setRow(erow);
                        boardPosition.get(epIndexRook).setColumn(scol-1);
                        boardPosition.set(spIndexRook, new Square(srow, (scol - 3), new Blank())); // sets Starting Square to blank square

                        board.setBoard(boardPosition, srow, (scol - 3), erow, (scol - 1), spIndexRook, epIndexRook); //sets the position of the Pieces on the 2D Board using the LinkedList


                        boardStates.add(board); //saves the current board state
                        board.displayBoard();


                    } else {



                        for (int i = 0; i < boardPosition.size(); i++) { //swaps pieces
                            if (boardPosition.get(i).getRow() == srow && boardPosition.get(i).getColumn() == scol) { //gets index of Starting Piece
                                spIndex = i;
                            }
                            if (boardPosition.get(i).getRow() == erow && boardPosition.get(i).getColumn() == ecol) { //gets index of Ending Piece
                                epIndex = i;
                            }

                        }

                        board.getSquare(srow, scol).getPiece().setMoved(true);

                        board.getSquare(srow, scol).setRow(erow);
                        board.getSquare(srow, scol).setColumn(ecol);
                        board.getSquare(erow, ecol).setRow(srow);
                        board.getSquare(erow, ecol).setColumn(scol);
                        boardPosition.get(spIndex).getPiece().setMoved(true);


                        if((erow == 0 || erow == 7)&& boardPosition.get(spIndex).getPiece().toString().equals("Pawn")){ //promotion
                            out.println("What piece do you want to promote to? Queen, Rook, Bishop, or Knight?");


                            boardPosition.set(epIndex, new Square(erow, ecol, new Queen(boardPosition.get(spIndex).getPiece().isWhite(),true)));
                            boardPosition.get(epIndex).setRow(erow);
                            boardPosition.get(epIndex).setColumn(ecol);
                            // sets Ending Square's piece to square with a Queen
                        }else{
                            boardPosition.set(epIndex, boardPosition.get(spIndex)); //sets Ending Square to Starting square
                            boardPosition.get(epIndex).setRow(erow);
                            boardPosition.get(epIndex).setColumn(ecol);
                        }
                        boardPosition.set(spIndex, new Square(srow, scol, new Blank())); //sets Starting Square to Blank Square

                        board.setBoard(boardPosition, srow, scol, erow, ecol, spIndex, epIndex); //sets the position of the Pieces on the 2D Board using the LinkedList
                        boardStates.add(board); //saves the current board state
                        board.displayBoard();
                    }

                } else {
                    legal = false;
                    board.displayBoard();
                }
            } else {

                legal = false;
                board.displayBoard();
            }
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println("Legal Before: " + legal);

            MoveResponse response = new MoveResponse(request,legal);


            out.println(MoveResponse.toJSON(response));






            if(legal && logic.stalemate(board,hold)){
                if(turn.equals("black")) {
                    System.out.println("Black is Stalemated");
                }else{
                    System.out.println("White is Stalemated");
                }
            }


            if(legal && logic.isCheckmated(board,hold)){
                if(turn.equals("black")) {
                    System.out.println("Black is Checkmated");
                }else{
                    System.out.println("White is Checkmated");
                }
            }


            if(legal){
                logic.clear();
            }

            System.out.println("this is the next turn " + turn);




            if(turn.equals("white") && legal){

                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


                hold = player2;


            } else if(turn.equals("black") && legal){

                out = new PrintWriter(clientSocket2.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket2.getInputStream()));

                hold = player1;

            }




        }
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }




    public static void main(String[] args) {
        GameServer server = new GameServer();
        try {
            server.start(4444);
            //server.stop();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
} //end class Server