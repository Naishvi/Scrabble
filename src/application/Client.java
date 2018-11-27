package application;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.net.*;
public class Client {
	public static int col; 
	public static int row; 
	public static void main(String [] args) throws Exception{
		LetterBag letterBag = new LetterBag();
		String IP = "localhost"; 
		int port = 5000; 
		Socket clientSocket = new Socket(IP, port);
		String word = "Letter";
		String modifiedSentence = "T 4,2|O 5,2|E 6,2"; 
		String update = null; 
		while(Main.Quit != true) {
			try {
	
				DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream()); 
				BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
				
				
				
				//Encode
				char[] clientWord = word.toCharArray(); 
				for(int i = 0; i < clientWord.length; i++) {
					int X; //get X postion 
					int Y; //get Y postion 
					String position = "[i],X,Y|"; 
					update = update + position; 
				}
				out.writeBytes(word + '\n');
				modifiedSentence = fromServer.readLine(); 
				
				
				
				
				
				//Update the board from the ServerInput 
				//Decode
				String[] letters = modifiedSentence.split("|"); 
				//[("T,4,2"), ("O,5,2"), ("E,6,2")]
				for(int i = 0; i< letters.length; i++) {
					String[] positionOfLetter = letters[i].split(",");
					 letterBag.letterImage.get(positionOfLetter[0]);  
					 col = Integer.parseInt(positionOfLetter[1]); 
					 row = Integer.parseInt(positionOfLetter[2]); 
				}
				
				System.out.println("Connected");
			}
			catch(Exception e){
			}
		}
		clientSocket.close(); 

	}
}
