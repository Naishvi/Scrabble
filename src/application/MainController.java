package application;


import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class MainController {
<<<<<<< HEAD
	Dictionary dictionary = new Dictionary();
	Player player = new Player(); 
	@FXML 
	public TextField UserInput = new TextField();
	
    @FXML
    private Label word1;

    @FXML
    private Label word2;

    @FXML
    private Label word3;

    @FXML
    private Label word4;

    @FXML
    public void undoBlur1(MouseEvent event) {
    	word1.setEffect(new GaussianBlur(0));
    }

    @FXML
    public void makeBlur1(MouseEvent event) {
    	word1.setEffect(new GaussianBlur(20.0));
    }

    @FXML
    void undoBlur2(MouseEvent event) {
    	word2.setEffect(new GaussianBlur(0));
    }

    @FXML
    void makeBlur2(MouseEvent event) {
    	word2.setEffect(new GaussianBlur(20.0));
    }

    @FXML
    void undoBlur3(MouseEvent event) {
    	word3.setEffect(new GaussianBlur(0));
    }

    @FXML
    void makeBlur3(MouseEvent event) {
    	word3.setEffect(new GaussianBlur(20.0));
    }

    @FXML
    void undoBlur4(MouseEvent event) {
    	word4.setEffect(new GaussianBlur(0));
    }

    @FXML
    void makeBlur4(MouseEvent event) {
    	word4.setEffect(new GaussianBlur(20.0));
    }
    @FXML
    public void Quit(ActionEvent event) {
    	
			 System.out.println("Thank you for playing!"); 
	        	System.exit(0);  
	           
    }
    
    //JButton okButton = new JButton();
    //okButton.setActionCommand("OK");
    
    @FXML
    public void OK(ActionEvent event){
    	
    	String word = UserInput.getText();
    	System.out.println(word); 
    	if(dictionary.containsWord(word)) {
    	   int value = player.getValOfWord(UserInput.getText(), new LetterBag());
    	   System.out.println(value);
    	}
    	else {
    		System.out.println("Try another word");
    	}
    }
   	
    	
    
    
    @FXML
    public void Cancel(ActionEvent event) {
    	UserInput.setText("");
    }
    
    @FXML
    public void WordEntered(KeyEvent onKeyTyped){
    	
    	
   
    }   
=======

    private Button btnOK = new Button();
    private Button btnCancel = new Button();
    private Button playTurn = new Button();
    private Button skipTurn = new Button();
    
    

>>>>>>> 51489d6b6b5c146d2f431097b0a133ead914ef08
}