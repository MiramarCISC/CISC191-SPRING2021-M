package edu.sdccd.cisc191.m.server;


import edu.sdccd.cisc191.m.MoveRequest;
import edu.sdccd.cisc191.m.MoveResponse;
import edu.sdccd.cisc191.m.Player;

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
    private PrintWriter out;
    private BufferedReader in;
    private LinkedList <Board> boardStates = new LinkedList<Board>();
    private LinkedList<Square> boardPosition  = new LinkedList<Square>();

    public void start(int port) throws Exception {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        boolean legal;
        Board board = new Board(800,800);
        board.resetBoard();
        board.displayBoard();
        board.setList();
        boardPosition = board.getList();
        Game logic = new Game();
        int srow, scol, erow, ecol;

        while (null!=(inputLine = in.readLine())) {
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

            System.out.println("Selected Piece: " + board.getSquare(srow, scol).getPiece().toString());

            Player temp = new Player(true);
            if(board.getSquare(srow,scol).getPiece().isWhite() == true){
                temp = logic.getPlayer1();
            }else{
                temp = logic.getPlayer2();
            }
            if(board.getSquare(srow,scol).getPiece().validateMove(board,board.getSquare(srow,scol),board.getSquare(erow,ecol))
                   ){ //checks if move is valid
                int spIndex = 0;
                int epIndex = 0;
                legal = true;

                for(int i = 0; i < boardPosition.size(); i++){ //swaps pieces
                    if(boardPosition.get(i).getRow()==srow&&boardPosition.get(i).getColumn()==scol){ //gets index of Starting Piece
                       spIndex = i;
                    }
                    if(boardPosition.get(i).getRow()==erow&&boardPosition.get(i).getColumn()==ecol){ //gets index of Ending Piece
                        epIndex = i;
                    }

                }
                board.getSquare(srow,scol).setRow(erow);
                board.getSquare(srow,scol).setColumn(ecol);
                board.getSquare(erow,ecol).setRow(srow);
                board.getSquare(erow,ecol).setRow(scol);
                boardPosition.set(epIndex,boardPosition.get(spIndex)); //sets Ending Square to Starting square
                boardPosition.set(spIndex, new Square(srow,scol, new Blank())); // sets Starting Square to blank square
                board.setBoard(boardPosition, srow, scol, erow, ecol, spIndex, epIndex); //sets the position of the Pieces on the 2D Board using the LinkedList
                boardStates.add(board); //saves the current board state
                board.displayBoard();

            }else{
                legal = false;
                board.displayBoard();
            }


            MoveResponse response = new MoveResponse(request,legal);
            out.println(MoveResponse.toJSON(response));
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
