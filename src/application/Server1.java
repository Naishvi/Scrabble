package application;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Server1 {

	public static void main(String[] args) throws Exception {
		ServerSocket listener = new ServerSocket(5231, 2);
		System.out.println("Scrabble server is Running");
		System.out.println("Waiting for first player...");

		Socket socket1 = listener.accept();

		DataOutputStream dos1 = new DataOutputStream(socket1.getOutputStream());
		DataInputStream dis1 = new DataInputStream(socket1.getInputStream());

		String player1Name = dis1.readUTF();
		dos1.writeUTF("Hello " + player1Name + ". Player 2 is offline, please wait.");

		System.out.println("Server accepted " + player1Name);
		System.out.println("Waiting for opponent...");

		Socket socket2 = listener.accept();
		DataOutputStream dos2 = new DataOutputStream(socket2.getOutputStream());
		DataInputStream dis2 = new DataInputStream(socket2.getInputStream());

		String player2Name = dis2.readUTF();
		dos2.writeUTF("Hello " + player2Name + ". Player 1 is online.");

		System.out.println("Server accepted " + player2Name);
		System.out.println("Telling players that game can begin...");

        dos1.writeUTF(player1Name + ", you can begin to play. Enjoy your Scrabble game!\n");
        dos2.writeUTF(player2Name + ", you can begin to play. Enjoy your Scrabble game!\n");

		try {
			Game game = new Game();
			Game.Player player1 = game.new Player(listener.accept(), 1, new LetterBag());
			Game.Player player2 = game.new Player(listener.accept(), 2, new LetterBag());
			player1.setOpponent(player2);
			player2.setOpponent(player1);
			game.currPlayer = player1;
			player1.start();
			player2.start();
			
			
			while (true) {
				String encodedString = dis1.readUTF(); 
	            System.out.println("Received encoded string from client 1:");
	            System.out.println(encodedString);
	            dos2.writeUTF(encodedString);
	            System.out.println("Sent encoded string to client 2");
	            String encodedString2 = dis2.readUTF();
	            System.out.println("Received encoded string from client 2:");
	            System.out.println(encodedString2);
	            dos1.writeUTF(encodedString2);
	            System.out.println("Sent encoded string to client 1");
			}
		} finally {
			listener.close();
		}
	}

	static class Game {

		Player currPlayer;
		Player winner;

		public Player getCurrPlayer() {
			return currPlayer;
		}

		public void setCurrPlayer(Player p) {
			currPlayer = p;
		}

		public void checkForWinner() {
			if (currPlayer.getScore() > currPlayer.getOpponent().getScore())
				winner = currPlayer;
			else
				winner = currPlayer.getOpponent();
		}

		class Player extends Thread {

			private int score;
			private long id;
			private String currWord;
			private LinkedList<Character> hand;
			private static final int MAX_HAND_SIZE = 7;
			private LinkedList<Position> letterPlacements; // only keeps track of squares where player
															// placed a letter
			private LinkedList<Position> validClicksSequence; // keeps track of all squares clicked
																// to determine placement validity
			private int jokerCount; // refreshed at every action, even cancellable ones
			private int originalJokerCount; // actual number of jokers before/after turn
			private Player opponent;

			Socket socket;
			BufferedReader input;
			PrintWriter output;

			public Player(Socket newSocket, int givenId, LetterBag letters) {
				score = 0;
				socket = newSocket;
				id = givenId;
				currWord = "";
				hand = new LinkedList<>();
				addLettersToHand(letters);
				letterPlacements = new LinkedList<>();
				validClicksSequence = new LinkedList<>();
				opponent = null;

				try {
					input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					output = new PrintWriter(socket.getOutputStream(), true);
					output.println("You are Player " + id);
					output.println("Waiting for opponent to connect...");
				} catch (IOException e) {
					System.out.println("Player died: " + e);
				}
			}

			public void run() {
				try {
					output.println("All players connected");

					if (id == 1) {
						output.println("Your move");
					}

					while (true) {
						System.out.print(".");
					}
				} finally {
					try {
						socket.close();
					} catch (IOException e) {
					}
				}
			}

			public int getScore() {
				return score;
			}

			public void addToScore(int value) {
				score += value;
			}

			public void setScore(int value) {
				score = value;
			}

			public void addLettersToHand(LetterBag letterBag) {
				while (hand.getLength() < MAX_HAND_SIZE) {
					Character currLetter = letterBag.fetchOneLetter();
					getHand().add(currLetter);
					if (currLetter == ' ') {
						addToJokerCount(1);
						addToActualJokerCount(1);
					}
				}
			}

			public String getWord() {
				return currWord;
			}

			public void setWord(String newWord) {
				currWord = newWord;
			}

			public LinkedList<Character> getHand() {
				return hand;
			}

			public int getFinalHandSize() {
				return MAX_HAND_SIZE;
			}

			public boolean handIsFull() {
				return hand.getLength() == MAX_HAND_SIZE;
			}

			public int getJokerCount() {
				return jokerCount;
			}

			public void setJokerCount(int value) {
				jokerCount = value;
			}

			public void addToJokerCount(int value) {
				jokerCount += value;
			}

			public int getActualJokerCount() {
				return originalJokerCount;
			}

			public void setActualJokerCount(int value) {
				originalJokerCount = value;
			}

			public void addToActualJokerCount(int value) {
				originalJokerCount += value;
			}

			public void addToPlayerPositions(Position pos) {
				validClicksSequence.add(pos);
			}

			public LinkedList<Position> getPlayerPositions() {
				return validClicksSequence;
			}

			public void addToLetterPlacements(Position pos) {
				letterPlacements.add(pos);
			}

			public LinkedList<Position> getLetterPlacements() {
				return letterPlacements;
			}

			public Player getOpponent() {
				return opponent;
			}

			public void setOpponent(Player opp) {
				opponent = opp;
			}

			public long getId() {
				return id;
			}

			public void setId(int value) {
				id = value;
			}

			// private boolean wordIsInDictionary(Scanner dictionary) {
			// while (dictionary.hasNextLine()) {
			// if (dictionary.nextLine().equalsIgnoreCase(currWord)) {
			// return true;
			// }
			// }
			// return false;
			// }
			//
			// public static int getValOfWord(String word, LetterBag letterValue) {
			// int wordPoints = 0;
			// char[] letterArray = word.toCharArray();
			// for (int i = 0; i < letterArray.length; i++) {
			// wordPoints += letterValue.getValueOf(letterArray[i]);
			// }
			// return wordPoints;
			// }

		}
	}

}
