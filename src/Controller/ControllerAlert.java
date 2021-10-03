package Controller;

import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ControllerAlert {
    @FXML
    Button btcls;

    @FXML
    Label lbtit,lbl;

    @FXML
    TextField flpass;

    public static boolean sign = false;

    @FXML
    private void initialize(){
        lbtit.setText("Vous aller prendre une decision");
    }

    @FXML
    private void handleKeyPressed(KeyEvent ke){
        if(String.valueOf(ke.getCode()).equals("ENTER")) this.ouiBut();
    }

    @FXML
    public void ouiBut(){
        if(flpass.getText().equals(User.getMdp())) {
            sign = true;
            closeBut();
        }
        else lbl.setText("Mot de pass invalid");

    }

    @FXML
    public void nonBut(){
        sign = false;
        closeBut();
    }

    @FXML
    public void clsBut(){
        nonBut();
    }

    @FXML
    public void closeBut(){
        Stage stage = (Stage) btcls.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}