package ControllerMat;

import ControllerEmp.ControllerAjoutEmp;
import Services.AjoutInterface;
import Services.SousInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;

import static DataBaseConnection.OracleConnection.getOracleConnection;

public class ControllerAjoutMat implements AjoutInterface, SousInterface {

    @FXML
    Button btvid,btmin,btcls;

    @FXML
    Label lbl,lbtit;

    @FXML
    ComboBox<String> combcat,combetat;

    @FXML
    TextField tfnom,tfstock;

    public static boolean sign = false;

    @FXML
    public void initialize(){

        lbtit.setText("Agent RH : Ajout des mats");
        combetat.getItems().removeAll(combetat.getItems());
        combcat.getItems().removeAll(combcat.getItems());
        combetat.getItems().addAll("Sain","Disponible","Non dispo","Detruit");
        combcat.getItems().addAll("Steel","Brick","Wooden");

    }

    @FXML
    public void addBut(){
        try {
            int id = ControllerAjoutEmp.callId("idmat","materiel");
            int stock = Integer.parseInt(tfstock.getText());
            String nom = tfnom.getText();
            String etat = combetat.getValue();
            String cat = combcat.getValue();

            if(nom==null) lbl.setText("nom est null");
            else if(cat==null) lbl.setText("categorie est null");
            else if(String.valueOf(stock)==null) lbl.setText("stock est null");
            else if(etat==null) lbl.setText("etat est null");
            else {
                try {
                    Connection connection = getOracleConnection();

                    PreparedStatement statement = connection.prepareStatement("insert into materiel values(" + id + ",'" + nom + "','" + cat + "'," + stock + ",'" + etat + "', to_date('" + LocalDate.now() + "','yyyy-mm-dd'))");

                    statement.execute();

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
        tfstock.setText(null);
        tfnom.setText(null);
        combetat.setValue(null);
        combcat.setValue(null);
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
