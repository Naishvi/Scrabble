package application;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	public static void main(String [] args) throws Exception{
		try {
		ServerSocket server = new ServerSocket(5000); 
		Socket s = server.accept(); 
		
		System.out.println("Connected");
		
		}
		catch (Exception e){
			
		}
	}
}
