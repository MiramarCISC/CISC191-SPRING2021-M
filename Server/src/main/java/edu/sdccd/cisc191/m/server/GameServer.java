package edu.sdccd.cisc191.m.server;


import edu.sdccd.cisc191.m.*;

import java.net.*;
import java.io.*;

import java.util.ArrayList;
import java.util.LinkedList;


/**
 * Author(s): Aiden Wise, Austin Nguyen
 * Description: This program is a server that processes the move requests from 2 clients and determines
 * whether the move is legal or not according to the game logic of the game.
 */


public class GameServer {
    //sockets
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Socket clientSocket2;

    //send out to client
    private PrintWriter out;

    //read input from client
    private BufferedReader in;

    //game states in arrays
    private ArrayList<Board> boardStates = new ArrayList<>();
    private ArrayList<Square> boardPosition = new ArrayList<>();

    //gameover
    private static GameServer server;

    //constructor
    public GameServer() {

    }

    //start server given port
    public void start(int port) throws Exception {

        //setting up server socket
        serverSocket = new ServerSocket(port);


        System.out.println("Waiting for connections...");

        //accepting client1 and client2
        clientSocket = serverSocket.accept();
        clientSocket2 = serverSocket.accept();


        System.out.println("We now have 2 players. No longer accepting connections.");

        //**** CLIENT 1 ****
        //outputting into client
        out = new PrintWriter(clientSocket.getOutputStream(), true);

        //inputting to client
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        //variables
        String inputLine;
        boolean legal;
        Board board = new Board();
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
        // change turns
        Player hold = player1;

        while (true) {

            //read info from input
            inputLine = in.readLine();
            System.out.println(inputLine);

            //requested move
            MoveRequest request = MoveRequest.fromJSON(inputLine);

            //store moves
            srow = request.getSrow();
            scol = request.getScol();
            erow = request.getErow();
            ecol = request.getEcol();

            System.out.println(srow);
            System.out.println(scol);
            System.out.println(erow);
            System.out.println(ecol);

            //if white change booleans
            boolean isWhite;

            if (turn.equals("white")) {
                isWhite = true;
            } else {
                isWhite = false;
            }


            //check if the move is within the boundaries of the board and if the piece is the correct color
            if (((srow < 8 && srow > -1) && (scol < 8 && scol > -1) && (erow < 8 && erow > -1) && (ecol < 8 && ecol > -1))
                    && (board.getSquare(srow, scol).getPiece().isWhite() == isWhite)) {

                System.out.println("Selected Piece: " + board.getSquare(srow, scol).getPiece().toString());

                if (board.getSquare(srow, scol).getPiece().validateMove(board, board.getSquare(srow, scol), board.getSquare(erow, ecol))
                        && (logic.isChecked(board, hold, board.getSquare(srow, scol), board.getSquare(erow, ecol)) != true)) { //checks if move is valid
                    int spIndex = 0;
                    int epIndex = 0;
                    legal = true;


                    System.out.println();


                    if (turns % 2 == 0) {//sets next turn

                        turn = "black";


                        turns += 1;

                    } else {

                        turn = "white";

                        turns += 1;

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
                        boardPosition.get(epIndexRook).setColumn(scol + 1);
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
                        boardPosition.get(epIndexRook).setColumn(scol - 1);
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


                        if ((erow == 0 || erow == 7) && boardPosition.get(spIndex).getPiece().toString().equals("Pawn")) { //promotion

                            boardPosition.set(epIndex, new Square(erow, ecol, new Queen(boardPosition.get(spIndex).getPiece().isWhite(), true)));
                            boardPosition.get(epIndex).setRow(erow);
                            boardPosition.get(epIndex).setColumn(ecol);
                            // sets Ending Square's piece to square with a Queen

                        } else {

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

            //response to the move
            MoveResponse response = new MoveResponse(request, legal, turn);
            //Send move to client 1
            out.println(MoveResponse.toJSON(response));


            //checks for white end game
            if (legal && logic.stalemate(board, hold)) {
                if (turn.equals("black")) {
                    System.out.println("Black is Stalemated");

                    try {
                        server.stop();
                    } catch (Exception e) {

                    }


                } else {
                    System.out.println("White is Stalemated");
                    try {
                        server.stop();
                    } catch (Exception e) {

                    }

                }
            }


            if (legal && logic.isCheckmated(board, hold)) {
                if (turn.equals("black")) {
                    System.out.println("Black is Checkmated");

                    try {
                        server.stop();
                    } catch (Exception e) {

                    }

                } else {
                    System.out.println("White is Checkmated");

                    try {
                        server.stop();
                    } catch (Exception e) {

                    }
                }
            }

            //next players turn
            System.out.println(turn + " has the next turn");

            //if next turn is white change the out and in to read from client1
            if (turn.equals("white") && legal) {

                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


                hold = player2;

            //if next turn is black change the out and in to read from client2
            } else if (turn.equals("black") && legal) {

                out = new PrintWriter(clientSocket2.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket2.getInputStream()));

                hold = player1;

            }


        }
    }
    // exceptions
    public void stop() throws IOException {

        in.close();
        out.close();
        clientSocket.close();
        clientSocket2.close();
        serverSocket.close();


    }


    public static void main(String[] args) {
        server = new GameServer();
        try {
            server.start(4444);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}