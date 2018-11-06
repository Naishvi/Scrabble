package application;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class MainController {

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

}