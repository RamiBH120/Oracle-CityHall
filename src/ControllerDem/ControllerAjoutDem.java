package ControllerDem;

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
import java.time.LocalDate;

import static DataBaseConnection.OracleConnection.getOracleConnection;

public class ControllerAjoutDem implements SousInterface {

    @FXML
    Button btmin,btcls;

    @FXML
    Label lbl,lbtit;

    @FXML
    ComboBox<String> combetat,combtype;

    @FXML
    TextField tftit;

    @FXML
    TextArea tasuj;

    static boolean sign=false;

    @FXML
    public void initialize(){
        lbtit.setText("Agent Municipal : Ajout un dem");
        combetat.getItems().removeAll(combetat.getItems());
        combetat.getItems().addAll("En cours","A faire");
        combtype.getItems().removeAll(combetat.getItems());
        combtype.getItems().addAll("Demande","Doléance");

    }

    @FXML
    public boolean addBut(){
        try {
            int id = ControllerAjoutEmp.callId("idem","demande");
            String titre = tftit.getText();
            String suj = tasuj.getText();
            String etat = combetat.getValue();
            String type = combtype.getValue();
            if(titre == null) lbl.setText("titre est null");
            else if(suj == null) lbl.setText("sujet est null");
            else if(etat == null) lbl.setText("etat est null");
            else if(type == null) lbl.setText("type est null");
            else {
                try {
                    Connection connection = getOracleConnection();

                    PreparedStatement statement = connection.prepareStatement("insert into demande values(" + id + ",'" + titre + "','" + suj + "','" + etat + "', to_date('" + LocalDate.now() + "','yyyy-mm-dd') , type = '" + type + "')");

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
        return sign;
    }

    @FXML
    public void viderBut(){
        tftit.setText(null);
        tasuj.setText(null);
        combetat.setValue(null);
        combtype.setValue(null);
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