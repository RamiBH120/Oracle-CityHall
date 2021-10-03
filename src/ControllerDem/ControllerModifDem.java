package ControllerDem;

import Controller.ControllerLog;
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

public class ControllerModifDem implements ModifInterface, SousInterface {

    @FXML
    ComboBox<String> combetat,combtype;

    @FXML
    TextField tftit;

    @FXML
    TextArea tasuj;

    @FXML
    Button btcls,btret,btmin;

    @FXML
    Label lbl,lbtit;

    static int selectedId;

    public static boolean sign = false;
    @FXML
    public void initialize(){
        lbtit.setText("Agent Municipal : Modification d'une demande");
        combetat.getItems().removeAll(combetat.getItems());
        combetat.getItems().addAll("En cours","Terminé","A faire");
        combtype.getItems().removeAll(combetat.getItems());
        combtype.getItems().addAll("Demande","Doléance");

        rechBut();
    }

    @FXML
    public void rechBut(){

        try {
            PreparedStatement statement;

            try {
                Connection connection = getOracleConnection();
                statement = connection.prepareStatement("SELECT * FROM demande where idem = " + selectedId);

                ResultSet rs = statement.executeQuery();

                while (rs.next()) {

                    String field1 = rs.getString("titre");
                    String field2 = rs.getString("sujet");
                    String field3 = rs.getString("etat");

                    tftit.setText(field1);
                    tasuj.setText(field2);
                    combetat.setValue(field3);
                }
                rs.close();
                initFields(true);
                connection.close();
            } catch (SQLException e) {
                initFields(false);
                lbl.setText("select error");

            }
        }catch (NumberFormatException nb){
            initFields(false);
            lbl.setText("Non trouvé ou entré invalid");
        }
    }

    private void initFields(boolean choice) {
        tftit.setVisible(choice);
        tasuj.setVisible(choice);
        combetat.setVisible(choice);
    }

    @FXML
    public void modifBut(){

        try {

            int id = this.selectedId;
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

                    PreparedStatement statement = connection.prepareStatement("update demande set titre = '" + titre + "' , sujet = '" + suj + "' , etat = '" + etat + "' , type = '" + type + "' where idem = " + id);

                    statement.executeUpdate();

                    if (statement.getUpdateCount() == 1) {
                        lbl.setText("Modifié!");
                        sign = true;
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
    public void retBut(){
        try {
            ControllerLog.crtStg("../sample/samplegm.fxml","Agent Municipal: Centre de gestion",btret);
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
