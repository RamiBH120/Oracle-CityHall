package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static DataBaseConnection.OracleConnection.getOracleConnection;

public class ControllerProp {

    @FXML
    Button btcls,btmin;

    @FXML
    Label nom,dt,loc,nbremp,nbrgui,horr,tr,lbl;

    @FXML
    private void initialize(){
        nom.setText("Titre du municipàlité : oued lil");
        dt.setText("Date de creation : 03/04/2021");
        loc.setText("Location : fsb");
        callProp();
        horr.setText("Horraire : 8h -> 12h / 14h -> 18h");
        tr.setText("System de travail : numero de presence");
        lbl.setText("AU 2020/2021");
    }

    public void callProp(){
        try {
            PreparedStatement statement,statement1;

            try {
                Connection connection = getOracleConnection();
                statement = connection.prepareStatement("select count(id) from employ");
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    nbremp.setText("Nombre d'employés : "+rs.getString("COUNT(ID)"));
                }
                rs.close();

                statement1 = connection.prepareStatement("select count(id) from employ where droit = 'Agent Municipal'");
                ResultSet rs1 = statement1.executeQuery();
                while (rs1.next()) {
                    nbrgui.setText("Nombre de guichets actives : "+rs1.getString("COUNT(ID)"));
                }
                rs1.close();
                connection.close();


            } catch (SQLException e) {
                e.printStackTrace();

            }
        }catch (NumberFormatException nb){
            nb.printStackTrace();
        }
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