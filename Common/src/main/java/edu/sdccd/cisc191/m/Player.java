package edu.sdccd.cisc191.m;

import java.net.*;
import java.io.*;

public class Player {
    boolean isWhite;
    private ClientSideConnection csc;

    public void makeMove() {
    }
    public void connectToServer(){
        csc = new ClientSideConnection();
    }
    private class ClientSideConnection{
        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;
        public ClientSideConnection(){
            System.out.println("Client");
            try{
                socket = new Socket("localhost", 33333);
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
            }catch (IOException ex){
                System.out.println("IO Exception from CSC constructor");
            }
        }
    }
    public static void main(String[] args){

    }
}
