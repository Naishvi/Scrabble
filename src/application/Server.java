package application;

 

import java.io.DataInputStream;

import java.io.DataOutputStream;

import java.io.IOException;

import java.net.ServerSocket;

import java.net.Socket;

 

public class Server {

 

      public static final int PORT_NUMBER = 3246;

     

      public static void main(String[] args) throws IOException {

             ServerSocket serverSocket = new ServerSocket(PORT_NUMBER, 2);

             System.out.println("Server running...");

             System.out.println("Waiting for first player.");

            

             Socket socket1 = serverSocket.accept();

             DataOutputStream dos1 = new DataOutputStream(socket1.getOutputStream());

             DataInputStream dis1 = new DataInputStream(socket1.getInputStream());

     

             String player1Name = dis1.readUTF();

             dos1.writeUTF("Hello " + player1Name + ". Player 2 is offline, please wait.");

            

             System.out.println("Server accepted " + player1Name);

             System.out.println("Waiting for opponent");

            

             Socket socket2 = serverSocket.accept();

             DataOutputStream dos2 = new DataOutputStream(socket2.getOutputStream());

             DataInputStream dis2 = new DataInputStream(socket2.getInputStream());

            

             String player2Name = dis2.readUTF();

             dos2.writeUTF("Hello " + player2Name + ". Player 1 is online.");

     

             System.out.println("Server accepted " + player2Name);

            

             System.out.println("Telling players that game can begin...");

             dos1.writeUTF(player1Name + ", you can begin to play. Enjoy your Scrabble game!");

             dos2.writeUTF(player2Name + ", you can begin to play. Enjoy your Scrabble game!");
             
             while(true) {
             String encodedString = dis1.readUTF(); 
             System.out.println(encodedString);
             dos2.writeUTF(encodedString);
             String encodedString2 = dis2.readUTF(); 
             dos1.writeUTF(encodedString2);
             
             }
             
             
            // dos1.writeBoolean(true);

            // dos2.writeBoolean(true);

      }

 

}