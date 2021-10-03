package ControllerTask;

import ControllerEmp.ControllerAjoutEmp;
import Model.Employ;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static DataBaseConnection.OracleConnection.getOracleConnection;

public class ControllerAjoutTask {

    @FXML
    Label lbtit,lbl;

    @FXML
    TextField tftit;

    @FXML
    TextArea tasuj;

    @FXML
    ComboBox<String> combetat;

    @FXML
    Button btcls,btmin;
    static boolean sign = false;


    static int selectedId;

    @FXML
    public void initialize(){

        lbtit.setText("Chef projet : Affecter une tache à personnel");

        combetat.getItems().removeAll(combetat.getItems());
        combetat.getItems().addAll("En cours","Terminé","A faire");
    }

    @FXML
    public void affBut(){
        try {
            int idtsk = ControllerAjoutEmp.callId("idtskp","taskp");
            String tit = tftit.getText();
            String desk = tasuj.getText();
            String etat = combetat.getValue();
            if(tit==null) lbl.setText("titre est null");
            else if(desk==null) lbl.setText("description est null");
            else if(etat==null) lbl.setText("etat est null");
            else {
                try {
                    Connection connection = getOracleConnection();

                    PreparedStatement statement = connection.prepareStatement("insert into taskp values(" + idtsk + " , '" + tit + "' , '" + desk + "' , '" + etat + "')");
                    PreparedStatement statement1 = connection.prepareStatement("update employ set idtskp = " + idtsk + " where id = " + selectedId);
                    statement.executeUpdate();
                    statement1.executeUpdate();
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
        combetat.setValue(null);
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
