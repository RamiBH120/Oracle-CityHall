package ControllerDepRev;

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

public class ControllerModifDepRev implements SousInterface, ModifInterface {

    @FXML
    ComboBox<String> combtype;
    
    @FXML
    TextField tfmont;

    @FXML
    TextArea tares;

    @FXML
    Button btcls,btret,btmin;

    @FXML
    Label lbl,lbtit;

    static int selectedId;

    public static boolean sign = false;
    @FXML
    public void initialize(){
        lbtit.setText("Agent Financier : Modification d'une depense / revenu");
        combtype.getItems().removeAll(combtype.getItems());
        combtype.getItems().addAll("Depense","Revenu");

        rechBut();
    }

    @FXML
    public void rechBut(){

        try {
            PreparedStatement statement;

            try {
                Connection connection = getOracleConnection();
                statement = connection.prepareStatement("SELECT * FROM depense where iddep = " + selectedId);

                ResultSet rs = statement.executeQuery();

                while (rs.next()) {

                    String field1 = rs.getString("montant");
                    String field2 = rs.getString("forres");
                    String field3 = rs.getString("type");

                    tfmont.setText(field1);
                    tares.setText(field2);
                    combtype.setValue(field3);
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
        tfmont.setVisible(choice);
        tares.setVisible(choice);
        combtype.setVisible(choice);
    }

    @FXML
    public void modifBut(){

        try {

            int id = this.selectedId;
            int mont = Integer.parseInt(tfmont.getText());
            String res = tares.getText();
            String type = combtype.getValue();
            if(String.valueOf(mont) == null)lbl.setText("montant est null");
            else if(res == null)lbl.setText("raison est null");
            else if(type == null)lbl.setText("type est null");
            else {
                try {
                    Connection connection = getOracleConnection();

                    PreparedStatement statement = connection.prepareStatement("update depense set montant = " + mont + " , forres = '" + res + "' , type = '" + type + "' where iddep = " + id);

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
        tfmont.setText(null);
        tares.setText(null);
        combtype.setValue(null);
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