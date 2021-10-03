package Controller;

import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static DataBaseConnection.OracleConnection.getOracleConnection;

public class ControllerInfo {


    @FXML
    Button btcls,btret,btmin;

    @FXML
    Label outnom,outpre,outuser,outsal,outmdp,outhorr,outdob,outem,outnum,outdro;

    @FXML
    private void initialize(){
        this.callInfo();
    }

    public void callInfo(){

        try {
            PreparedStatement statement;

            try {
                Connection connection = getOracleConnection();
                statement = connection.prepareStatement("select c.usern,c.mdp,c.tel,c.email,e.nom,e.prenom,e.date_naiss,e.droit,e.horr,e.salaire from employ e join compte c on(e.id=c.idemp) where c.usern = '"+User.getUsern()+"' and c.mdp = '"+User.getMdp()+"'");

                //get data from db

                ResultSet rs = statement.executeQuery();

                while (rs.next()) {

                    outuser.setText("User Name : "+rs.getString("usern"));
                    outmdp.setText("Mot de passe : "+rs.getString("mdp"));
                    outnum.setText("Telephone : "+rs.getString("tel"));
                    outem.setText("Email : "+rs.getString("email"));
                    outnom.setText("Nom de l'employé : "+rs.getString("nom"));
                    outpre.setText("Prenom : "+rs.getString("prenom"));
                    outdob.setText("Date de Naissance: "+LocalDate.parse(rs.getString("date_naiss").substring(0,10)));
                    outdro.setText("Droit : "+rs.getString("droit"));
                    outhorr.setText("Heures de travail par semaine : "+rs.getString("horr"));
                    outsal.setText("Salaire par mois: "+rs.getString("salaire"));


                }
                rs.close();
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
}
