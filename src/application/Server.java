package application;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server{
	int port; 
	Socket client; 
	ExecutorService pool; 
	int clientCount = 0; 
	String clientWord;
	ServerSocket server;

	public void startServer() throws IOException{
		server = new ServerSocket(5000);

		while(true) {
			Socket connectionSocket = server.accept(); 
			clientCount++;
			ServerThread runnable = new ServerThread(client, clientCount, this);
			pool.execute(runnable);
		}

	}
	/*public static void main(String [] args) throws Exception{

		Server serverObj = new Server(5000);
		serverObj.startServer(); 
		//while(true) {

		//BufferedReader fromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		//DataOutputStream out = new DataOutputStream(connectionSocket.getOutputStream()); 
		//Use client 1 word now to send it to client 2 word 

		//}

	}*/
	Server(int port){
		this.port = port; 
		pool = Executors.newFixedThreadPool(2); // Create 2 client connection
	}
}

class ServerThread implements Runnable{
	Server server = null; 
	Socket client; 
	BufferedReader in; 
	PrintStream out; 
	Scanner scan = new Scanner(System.in); 
	int id; 
	String s; 
	ServerThread(Socket client, int count, Server server) throws IOException{
		this.client = client; 
		this.server = server; 
		System.out.println("Connection with " + id + "established with client" + client);
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out = new PrintStream(client.getOutputStream()); 
		
	}

	@Override
	public void run() {
		int x = 1; 
		try {
			while(true){
				s=in.readLine();

				System. out.print("Client("+id+") :"+s+"\n");
				System.out.print("Server : ");
				//s=stdin.readLine();
				s=scan.nextLine();
				if (s.equalsIgnoreCase("bye"))
				{
					out.println("BYE");
					x=0;
					System.out.println("Connection ended by server");
					break;
				}
				out.println(s);
			}


			in.close();
			client.close();
			out.close();
			if(x==0) {
				System.out.println( "Server cleaning up." );
				System.exit(0);
			}
		}
		catch(IOException ex){
			System.out.println("Error : "+ex);
		}
	}
}






