package ControllerGrp;

import ControllerEmp.ControllerAjoutEmp;
import Model.Employ;
import Services.SousInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import static DataBaseConnection.OracleConnection.getOracleConnection;

public class ControllerNouvGrp implements SousInterface {

    @FXML
    Label lbtit,lbl;

    @FXML
    TextField tftit;

    @FXML
    TextArea tasuj;

    @FXML
    Button btcls,btmin;

    static ObservableList<Employ> line = FXCollections.observableArrayList();

    static boolean sign = false;


    @FXML
    public void initialize(){

        lbtit.setText("Chef projet : Affecter une tache à personnel");
    }

    @FXML
    public void affBut(){
        try {
            int ideqp = ControllerAjoutEmp.callId("idequipe","equipe");
            String tit = tftit.getText();
            String desk = tasuj.getText();

            if(tit == null) lbl.setText("titre est null");
            else if(desk == null) lbl.setText("description est null");
            else {
                try {
                    Connection connection = getOracleConnection();
                    PreparedStatement statement;


                    PreparedStatement statement1 = connection.prepareStatement("insert into equipe(idequipe,nomequipe,deskequipe,joindate) values(" + ideqp + " , '" + tit + "' , '" + desk + "' , to_date('" + LocalDate.now() + "','yyyy-mm-dd'))");
                    statement1.executeUpdate();

                    for (Employ e : line) {
                        statement = connection.prepareStatement("update employ set idequipe = " + ideqp + " where id = " + e.getId());
                        statement.executeUpdate();
                    }

                    lbl.setText("Ajouté");
                    sign = true;
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
        tasuj.setText(null);
    }

    @FXML
    public void clsBut(){
        Stage stage = (Stage) btcls.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    public void minBut(){
        Stage stage = (Stage) btmin.getScene().getWindow();
        // do what you have to do
        stage.setIconified(true);
    }
}