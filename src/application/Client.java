package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import java.util.Scanner;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;



public class Client extends Main{
	static Main main = new Main(); 
	private static double x;

	private static double y;

	public static Scanner scanner = new Scanner(System.in);

	public static  String word = "";
	
	public LinkedList<Character> letterList;
	
	public LinkedList<Integer> rowList;
	
	public LinkedList<Integer> colList;
	
	
	public LinkedList<Character> getLetterList() {
		return letterList;
	}
	
	public LinkedList<Integer> getRowList() {
		return rowList;
	}
	
	public LinkedList<Integer> getColList() {
		return colList;
	}


	public static void main(String[] args) throws IOException{
		int x = 5; 
		Socket socket = new Socket("localhost", 3246);
		InputStream inputStream = socket.getInputStream();
		OutputStream outStream = socket.getOutputStream(); 
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		//DataOutputStream stringWord = new DataOutputStream(socket.getOutputStream());
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		//DataInputStream readWord = new DataInputStream(socket.getInputStream());
		
	/*	String clientWord = new String(dis.readUTF());
		 OutputStreamWriter osw = new OutputStreamWriter(outStream);
		 BufferedWriter bw = new BufferedWriter(osw);*/
		 

		System.out.println("Hello user. Enter your name: ");

		String name = scanner.nextLine();

		dos.writeUTF(name);



		String serverMessage = dis.readUTF();

		System.out.println(serverMessage);



		String tmpReader = dis.readUTF();

		System.out.println(tmpReader);
		launch(args);
		
			DataOutputStream outgoing = new DataOutputStream(socket.getOutputStream());
			DataInputStream incoming = new DataInputStream(socket.getInputStream());
			String send = encode();
			System.out.println(send); 
			outgoing.writeUTF(send);


			word = incoming.readUTF(); 	
			System.out.println(word);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			
			
	
		}
		
			
		}
	
	
		
		/*while(Quit != true) {
			// 
			main.send.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					String w = main.encode(); 
					bw.append(w); 
					dos.writeUTF(w);
					
				}
			});
		
			
			main.update.addActionListener(l);
		if(main.send()) {
		String send = encode(); 
		dos.writeUTF(send);


		word = tmpReader; 	
		System.out.println(word);
		}
		}*/
		//socket.close();

	//	boolean tmpReader2 = dis.readBoolean();
/*
		if(tmpReader2) {

			while(quit != true) {

			String send = encode(); 
			dos.writeUTF(send);


			word = tmpReader; 	
			System.out.println(word);
			}
			socket.close();
		}

		else {

			//Then there was an error

			return;

		}*/
/*
		while(true) {

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}*/
/*	public static String encode() {
		String encodeString = "";

		// LinkedList of positions enforcedPositions (Main.java) always happens
		// to have a duplicate entry at the beginning. It should be removed
		enforcedPositions.remove(new Position(7, 7));
		for (int i = 0; i < enforcedPositions.getLength(); i++) {
			Position aPos = enforcedPositions.getEntry(i);
			int posRow = aPos.getRow();
			int posCol = aPos.getCol();
			Character charAtPos = board.getSquares()[posRow][posCol].getLetter();
			String toConcatenate = charAtPos + "," + posRow + "," + posCol;
			encodeString += toConcatenate + (i < enforcedPositions.getLength() - 1 ? "|" : "");
		}

		return encodeString;

	}
	public static void decode(String word) {
		String[] letters = word.split("\\|"); 
		//[("T,4,2"), ("O,5,2"), ("E,6,2")]
		for(int i = 0; i< letters.length; i++) {	
			String[] positionOfLetter = letters[i].split(",");
			linkedLetters.add(positionOfLetter[0].charAt(0));  
			linkedRow.add(Integer.parseInt(positionOfLetter[1]));
			linkedCol.add(Integer.parseInt(positionOfLetter[2]));  
			System.out.println(linkedLetters.toString());

		}

	}*/
	
	
	

