package ControllerDepRev;

import Controller.ControllerLog;
import ControllerEmp.ControllerAjoutEmp;
import Services.AjoutInterface;
import Services.SousInterface;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static DataBaseConnection.OracleConnection.getOracleConnection;

public class ControllerAjoutDepRev implements SousInterface, AjoutInterface {

    @FXML
    Button btret,btmin,btcls;

    @FXML
    Label lbl,lbtit;

    @FXML
    TextField tfmont;

    @FXML
    TextArea tares;

    @FXML
    ComboBox<String> combtype;

    public static boolean sign=false;

    @FXML
    public void initialize(){
        lbtit.setText("Agent Financier : Ajout un depense / revenu");
        combtype.getItems().removeAll(combtype.getItems());
        combtype.getItems().addAll("Depense","Revenu");
    }

    @FXML
    public void addBut(){
        try {
            int id = ControllerAjoutEmp.callId("iddep","depense");
            int mont = Integer.parseInt(tfmont.getText());
            String res = tares.getText();
            String type = combtype.getValue();
            if(String.valueOf(mont) == null)lbl.setText("montant est null");
            else if(res == null)lbl.setText("raison est null");
            else if(type == null)lbl.setText("type est null");
            else {
                try {
                    Connection connection = getOracleConnection();

                    PreparedStatement statement = connection.prepareStatement("insert into depense values(" + id + "," + mont + ",'" + res + "','" + combtype.getValue() + "')");

                    statement.execute();

                    lbl.setText("Ajouté");
                    sign = true;

                } catch (SQLException e) {
                    lbl.setText("un ou plusieurs chàmps sont érronés");
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e){
            lbl.setText("Une erreur dans la saisi");
        }
    }

    @FXML
    public void viderBut(){
        tfmont.setText(null);
        tares.setText(null);
        combtype.setValue(null);
    }

    @FXML
    public void retBut(){
        try {
            ControllerLog.crtStg("../sample/samplegf.fxml","Agent Financier : Centre de gestion",btret);
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

    @FXML
    public void clsBut(){
        Stage stage = (Stage) btcls.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}