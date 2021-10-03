package ControllerIntrv;

import Services.ModifInterface;
import Services.SousInterface;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static DataBaseConnection.OracleConnection.getOracleConnection;

public class ControllerModifIntrv implements SousInterface, ModifInterface {

    @FXML
    DatePicker dtdeb,dtfin;

    @FXML
    ComboBox<String> combetat;

    @FXML
    TextField tftit,tfobj,tfloc;

    @FXML
    Button btcls,btmin;

    @FXML
    Label lbl,lbtit;

    static int selectedId;


    public static boolean sign = false;

    @FXML
    public void initialize(){
        lbtit.setText("Chef proj : Modification d'un projet");
        combetat.getItems().removeAll(combetat.getItems());
        combetat.getItems().addAll("En cours","Terminé","A faire");

        rechBut();
    }

    @FXML
    public void rechBut(){

        try {
            PreparedStatement statement;
            try {
                Connection connection = getOracleConnection();
                statement = connection.prepareStatement("SELECT * FROM interv where idinterv = " + selectedId);

                ResultSet rs = statement.executeQuery();

                while (rs.next()) {

                    String field1 = rs.getString("titre");
                    String field2 = rs.getString("obj");
                    String field3 = rs.getString("deb");
                    String field4 = rs.getString("fin");
                    String field5 = rs.getString("etat");
                    String field6 = rs.getString("loc");


                    tftit.setText(field1);
                    tfobj.setText(field2);
                    tfloc.setText(field6);
                    dtdeb.setValue(LocalDate.parse(field3.substring(0,10)));
                    dtfin.setValue(LocalDate.parse(field4.substring(0,10)));
                    combetat.setValue(field5);

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

            int id = selectedId;
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

                    PreparedStatement statement = connection.prepareStatement("update interv set titre = '" + titre + "' , obj = '" + obj + "' , deb = to_date('" + dated + "','yyyy-mm-dd')  , fin = to_date('" + datef + "','yyyy-mm-dd') , etat = '" + etat + "' , loc = '" + loc + "' where idinterv = " + id);

                    statement.executeUpdate();

                    if (statement.getUpdateCount() == 1) lbl.setText("Modifié!");
                    else lbl.setText("Non trouvé");
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
        tftit.setText(null);
        tfloc.setText(null);
        tfobj.setText(null);
        dtdeb.setValue(null);
        dtfin.setValue(null);
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