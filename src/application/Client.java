package application;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.net.*;
public class Client {
	public static void main(String [] args) throws Exception{
		
		String IP = "localhost"; 
		DataInputStream input = null;
		DataOutputStream output = null;
		Socket socket = new Socket(IP, 5000); 
		while(Main.quit != true) {
			try {
				// Create a BufferedReader to read a line at a time.
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);

				// Read greeting from the server.
				String response = br.readLine();
				System.out.println(response);
				if (!response.startsWith("220")) {
					throw new Exception("220 reply not received from server.");
				}
				// Get a reference to the socket's output stream.
				OutputStream os = socket.getOutputStream();

				System.out.println("Connected");
			}
			catch(Exception e){
				
			}
		}
		socket.close(); 

	}
	
	public String toStringEncoded(LinkedList<Position> enforcedPos, Board board) {
		String encodeString = "";
		// LinkedList of positions enforcedPositions (Main.java) always happens
		// to have a duplicate entry at the beginning. It should be removed
		enforcedPos.remove(new Position(7, 7));
		for (int i = 0; i < enforcedPos.getLength(); i++) {
			Position aPos = enforcedPos.getEntry(i);
			int posRow = aPos.getRow();
			int posCol = aPos.getCol();
			Character charAtPos = board.getSquares()[posRow][posCol].getLetter();
			String toConcatenate = charAtPos + "," + posRow + "," + posCol;
			encodeString += toConcatenate + (i < enforcedPos.getLength() - 1 ? "|" : "");
		}
		return encodeString;
	}
}