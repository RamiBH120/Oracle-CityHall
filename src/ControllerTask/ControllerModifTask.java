package ControllerTask;

import Services.ModifInterface;
import Services.SousInterface;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static DataBaseConnection.OracleConnection.getOracleConnection;

public class ControllerModifTask implements SousInterface, ModifInterface{

    @FXML
    TextField tftit;

    @FXML
    TextArea tasuj;

    @FXML
    ComboBox<String> combetat;

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
                statement = connection.prepareStatement("SELECT t.nomtsk,t.desktsk,t.etattsk from employ e join taskp t using(idtskp) where e.id = " + selectedId);

                ResultSet rs = statement.executeQuery();

                while (rs.next()) {


                    String field = rs.getString("nomtsk");
                    String field1 = rs.getString("desktsk");
                    String field2 = rs.getString("etattsk");


                    tftit.setText(field);
                    tasuj.setText(field1);
                    combetat.setValue(field2);

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

            String tit = tftit.getText();
            String desk = tasuj.getText();
            String etat = combetat.getValue();
            if(tit==null) lbl.setText("titre est null");
            else if(desk==null) lbl.setText("description est null");
            else if(etat==null) lbl.setText("etat est null");
            else {
                try {
                    Connection connection = getOracleConnection();

                    PreparedStatement statement = connection.prepareStatement("update taskp set nomtsk = '" + tftit.getText() + "' , desktsk = '" + tasuj.getText() + "' , '" + combetat.getValue() + "' where idtskp = " + id);

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