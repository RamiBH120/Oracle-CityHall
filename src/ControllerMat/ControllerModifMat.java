package ControllerMat;

import Controller.ControllerLog;
import Services.ModifInterface;
import Services.SousInterface;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;

import static DataBaseConnection.OracleConnection.getOracleConnection;

public class ControllerModifMat implements SousInterface, ModifInterface {


    @FXML
    ComboBox<String> combcat,combetat;

    @FXML
    TextField tfnom,tfstock;

    @FXML
    Button btcls,btret,btmin,btvid;

    @FXML
    Label lbl,lbtit;

    static int selectedId;

    public static boolean sign = false;

    @FXML
    public void initialize(){
        lbtit.setText("Agent RH : Modification d'un materiel");

        combcat.getItems().removeAll(combcat.getItems());
        combetat.getItems().removeAll(combetat.getItems());
        combcat.getItems().addAll("Steel","Brick","Wooden");
        combetat.getItems().addAll("Sain","Disponible","Non dispo","Detruit");

        this.rechBut();

    }

    @FXML
    public void rechBut(){

        try {
            PreparedStatement statement;

            try {
                Connection connection = getOracleConnection();
                statement = connection.prepareStatement("SELECT * FROM materiel where idmat = " + selectedId);

                ResultSet rs = statement.executeQuery();

                while (rs.next()) {

                    String field1 = rs.getString("nom");
                    String field2 = rs.getString("cat");
                    String field3 = rs.getString("stock");
                    String field4 = rs.getString("etat");

                    tfnom.setText(field1);
                    tfstock.setText(field3);
                    combcat.setValue(field2);
                    combetat.setValue(field4);
                }
                rs.close();
                connection.close();
            } catch (SQLException e) {
                lbl.setText("select error");

            }
        }catch (NumberFormatException nb){
            lbl.setText("Non trouvé ou entré invalid");
        }
    }

    @FXML
    public void modifBut(){

        try {

            int id = this.selectedId;
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

                    PreparedStatement statement = connection.prepareStatement("update materiel set nom = '" + tfnom.getText() + "' , cat = '" + combcat.getValue() + "' , stock = " + Integer.parseInt(tfstock.getText()) + " , etat = '" + combetat.getValue() + "' where idmat = " + id);

                    statement.executeUpdate();

                    if (statement.getUpdateCount() == 1) {
                        lbl.setText("Modifié!");
                    } else lbl.setText("Non trouvé");
                    connection.close();

                } catch (SQLException e) {
                    lbl.setText("error in the update");
                    e.printStackTrace();
                }
            }
            //
        }
        catch (NumberFormatException nb){
            lbl.setText("id doit etre numerique");
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