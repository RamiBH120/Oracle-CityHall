package ControllerProj;

import ControllerEmp.ControllerAjoutEmp;
import Services.AjoutInterface;
import Services.SousInterface;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import static DataBaseConnection.OracleConnection.getOracleConnection;

public class ControllerAjoutProj implements SousInterface, AjoutInterface {

    @FXML
    Button btmin,btcls;

    @FXML
    Label lbl,lbtit;

    @FXML
    DatePicker dtdeb,dtfin;

    @FXML
    ComboBox<String> combetat;

    @FXML
    TextField tftit,tfobj,tfloc;


    public static boolean sign = false;

    @FXML
    public void initialize(){

        lbtit.setText("Chef proj : Ajout un projet");

        combetat.getItems().removeAll(combetat.getItems());
        combetat.getItems().addAll("En cours","A faire");

    }

    @FXML
    public void addBut(){
        try {
            int id = ControllerAjoutEmp.callId("idproj","project");
            String titre = tftit.getText();
            String obj = tfobj.getText();
            String loc = tfloc.getText();
            String dated = String.valueOf(dtdeb.getValue());
            String datef = String.valueOf(dtfin.getValue());
            String etat = combetat.getValue();

            if(titre == null) lbl.setText("titre est null");
            else if(obj == null) lbl.setText("objectif est null");
            else if(loc == null) lbl.setText("location est null");
            else if(Integer.parseInt(dated.substring(4,6)) > Integer.parseInt(datef.substring(4,6)) || Integer.parseInt(dated.substring(8,10)) > Integer.parseInt(datef.substring(8,10)) || Integer.parseInt(dated.substring(0,4)) > Integer.parseInt(datef.substring(0,4))) lbl.setText("date debut est >= à date fin");
            else if(etat==null) lbl.setText("etat est null");
            else {
                try {
                    Connection connection = getOracleConnection();

                    PreparedStatement statement = connection.prepareStatement("insert into project values(" + id + ",'" + titre + "','" + obj + "',to_date('" + dated + "','yyyy-mm-dd'),to_date('" + datef + "','yyyy-mm-dd'),'" + etat + "','" + loc + "' , to_date('" + LocalDate.now() + "','yyyy-mm-dd'))");

                    statement.executeUpdate();

                    lbl.setText("Ajouté");
                    connection.close();
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
        tftit.setText(null);
        tfloc.setText(null);
        tfobj.setText(null);
        dtdeb.setValue(null);
        dtfin.setValue(null);
        combetat.setValue(null);
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