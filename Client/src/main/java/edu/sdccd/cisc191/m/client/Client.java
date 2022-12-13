package edu.sdccd.cisc191.m.client;

import edu.sdccd.cisc191.m.*;

import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * Author(s): Aiden Wise, Austin Nguyen
 * Description: Requests a move from a user which is then sent to the Server to be proccessed.
 * The server then responds telling the user if the move was valid or not.
 * Make sure you allow multiple instances of the client to run to be able to test two players!
 * Only two clients can be running at the same time
 */

public class Client {
    //client socket
    private Socket clientSocket;
    // sends output
    private static PrintWriter out;
    // reads input
    private static BufferedReader in;
    //reads input from client
    private static Scanner scnr;
    //gui
    private static GameView gameView;

    //input (r,c) for which peice to select
    private static int srow;
    private static int scol;

    //input (r,c) for where to move that piece
    private static int erow;
    private static int ecol;

    //response from server
    private static MoveResponse respond;


    // constructor for client
    public Client() {
        scnr = new Scanner(System.in);
    }



    // starts connection with server
    public void startConnection(String ip, int port) throws IOException {

        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    //sends a request for a move returns if response is valid
    public static MoveResponse sendRequest() throws Exception {

        System.out.println("Enter Starting Row:");
        srow = scnr.nextInt();
        System.out.println("Enter Starting Column:");
        scol = scnr.nextInt();
        System.out.println("Enter Ending Row:");
        erow = scnr.nextInt();
        System.out.println("Enter Ending Column:");
        ecol = scnr.nextInt();

        // send encrypted moves to server
        out.println(MoveRequest.toJSON(new MoveRequest(srow, scol, erow, ecol)));

        //recieve decrpyted response from server
        respond = MoveResponse.fromJSON(in.readLine());

        //checking for valid move


        // w (2,5) (3,6) --->  w (2,5) (3,6)
        return respond;
    }

    /*
    public static MoveResponse getResponse() {
        gets server response

        #if resposne is valid
            turn clinet to listen
        #if response is update board
            update our gui board for other move
        #else failed
            return respond;
    }
     */

    //helper method -- get respond field
    public static MoveResponse getResponse() {



        return respond;
    }

    //closes connection
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }


    public static void main(String[] args) {
        //creates client
        Client client = new Client();

        //try starting connection
        try {
            client.startConnection("127.0.0.1", 4444);

            //creates game view
            gameView = new GameView();

            //game loop
            //##### problem while true -- and working with alternating information between clients
            while (true) {

                //send --> move we want to make

                //listen --> for if valid
                // update gui to show our move

                //listen --> listen for other move
                //update gui to show their move


                //send
                client.sendRequest();

                System.out.println(respond.toString());

                //getting input for move
                int srow = client.getResponse().getRequest().getSrow();
                int scol = client.getResponse().getRequest().getScol();

                int erow = client.getResponse().getRequest().getErow();
                int ecol = client.getResponse().getRequest().getEcol();

                //check if leagl move if king castle update gui if queen castle update gui else update gui
                if (respond.toString().contains("is legal")) {
                    assert gameView.getPiece(scol, srow) != null;
                    if (gameView.getPiece(scol, srow).getName().equalsIgnoreCase("king")
                            && gameView.getPiece(scol, srow).hasMoved() == false
                            && gameView.getPiece(scol - 3, srow).getName().equalsIgnoreCase("rook")
                            && ecol == scol - 2) { //Paint for King side castling
                        gameView.getPiece(scol, srow).move(ecol, erow); //moves king on gui
                        gameView.getPiece(scol - 3, srow).move(scol - 1, srow); //moves rook on gui
                        gameView.getPiece(ecol, erow).setMoved(true);
                        gameView.getPiece(scol - 1, srow).setMoved(true);

                        gameView.paint();

                    } else if (gameView.getPiece(scol, srow).getName().equalsIgnoreCase("king")
                            && gameView.getPiece(scol, srow).hasMoved() == false && gameView.getPiece(scol + 4, srow).getName().equalsIgnoreCase("rook")
                            && ecol == scol + 2) { //paint for Queen side castling
                        gameView.getPiece(scol, srow).move(ecol, erow); //moves king on gui
                        gameView.getPiece(scol + 4, srow).move(scol + 1, srow); //moves rook on gui
                        gameView.getPiece(ecol, erow).setMoved(true);
                        gameView.getPiece(scol + 1, srow).setMoved(true);

                        gameView.paint();
                    } else { //paint for every other valid move
                        gameView.getPiece(scol, srow).move(ecol, erow); //moves piece on gui
                        gameView.getPiece(ecol, erow).setMoved(true);
                        gameView.paint();
                    }


                }



            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}