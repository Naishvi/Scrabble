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
		int port = 22222; 
		String IP = "localhost"; 
		DataInputStream input = null;
		DataOutputStream output = null;
		
		try {
			Socket socket = new Socket("72.93.95.216", 3000); 
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
}
