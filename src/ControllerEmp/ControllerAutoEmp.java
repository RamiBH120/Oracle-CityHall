package ControllerEmp;

import Controller.ControllerLog;
import Services.SousInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static DataBaseConnection.OracleConnection.getOracleConnection;

public class ControllerAutoEmp implements SousInterface {

    @FXML
    Label lbl,lbtit;

    @FXML
    Button btret,btmin;

    @FXML
    TextField tfp,tfnop;

    static int selectedId;

    static boolean sign=false;

    @FXML
    public void initialize(){

        lbtit.setText("Agent Ressource : Autorisàtion d'un emp");
    }

    @FXML
    public void autoBut(){
        if(!tfp.getText().isEmpty()) {
            lbl.setText("Autorisé à " + tfp.getText());

            try {
                Connection connection = getOracleConnection();

                PreparedStatement statement = connection.prepareStatement("update employ set auto = 'permis a "+tfp.getText()+"' where id = "+selectedId);

                statement.executeUpdate();

                if(statement.getUpdateCount() == 1){ lbl.setText("Modifié!"); sign=true;}
                else lbl.setText("Non trouvé");
                connection.close();

            } catch (SQLException e) {
                lbl.setText("error in the update");
            }

        }
        else {
            lbl.setText("le ràison pour l'autorisation non spécifié");
        }
    }

    @FXML
    public void nonAutoBut(){
        if(!tfnop.getText().isEmpty()){
            lbl.setText("non Autorisé à "+tfnop.getText());

            try {
                Connection connection = getOracleConnection();

                PreparedStatement statement = connection.prepareStatement("update employ set auto = 'non permis a "+tfnop.getText()+"' where id "+selectedId);
                statement.executeUpdate();

                if(statement.getUpdateCount() == 1){ lbl.setText("Modifié!");sign=true;}
                else lbl.setText("Non trouvé");

                connection.close();

            } catch (SQLException e) {
                lbl.setText("error in the update");
            }
        }
        else {
            lbl.setText("le ràison pour l'enlevement d'autorisation non spécifié");
        }
    }

    @FXML
    Button btcls;

    @FXML
    public void viderBut(){
        tfp.setText(null);
        tfnop.setText(null);
    }

    @FXML
    public void clsBut(){
        Stage stage = (Stage) btcls.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    public void retBut(){
        try {
            ControllerLog.crtStg("../sample/samplerh.fxml","Agent RH : Centre de gestion",btret);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public void minBut(){
        Stage stage = (Stage) btmin.getScene().getWindow();
        // do what you have to do
        stage.setIconified(true);
    }

}
