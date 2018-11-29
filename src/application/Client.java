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

import javafx.stage.Stage;

import java.net.*;

public class Client {

	private static double x;
	private static double y;
	private static Scanner scanner = new Scanner(System.in);

public static void main(String[] args) throws Exception {
		
		System.out.println("Hello user. Enter your name: ");
		String name = scanner.nextLine(); 
		dos.writeUTF(name);
		
		String serverMessage = dis.readUTF(); 
		System.out.println(serverMessage);
		
		String tmpReader = dis.readUTF(); 
		System.out.println(tmpReader);
		
		boolean tmpReader2 = dis.readBoolean();
		
		if(tmpReader2) {
			client.start(new Stage());
		}
		else {
			//Then there was an error
			return; 
		}
		while(true) {
			Thread.sleep(50);
		}
	}

	public String toStringEncoded(LinkedList<Position> enforcedPos, Board board) {
		String encodeString = "";
		// LinkedList of positions enforcedPositions (Main.java) always happens
		// to have a duplicate entry for (7, 7) at the beginning. It should be removed
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