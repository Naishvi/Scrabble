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
	public static void main(String [] args) throws Exception{

		String IP = "localhost"; 
		int port = 5000; 
		DataInputStream input = null;
		DataOutputStream output = null;
		Socket clientSocket = new Socket(IP, port); 
		File board = new File("Board.java"); 
		
		while(Main.Quit != true) {
			try {
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)); 
				
				// Create a BufferedReader to read a line at a time.
				InputStream is = clientSocket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);

				// Read greeting from the server.
				String response = br.readLine();
				System.out.println(response);
				if (!response.startsWith("220")) {
					throw new Exception("220 reply not received from server.");
				}
				// Get a reference to the socket's output stream.
				OutputStream os = clientSocket.getOutputStream();

				System.out.println("Connected");
			}
			catch(Exception e){
			}
		}
		clientSocket.close(); 

	}
}
