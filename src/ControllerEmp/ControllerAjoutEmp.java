package ControllerEmp;

import Controller.ControllerLog;
import Services.AjoutInterface;
import Services.SousInterface;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;

import static DataBaseConnection.OracleConnection.getOracleConnection;

public class ControllerAjoutEmp implements SousInterface, AjoutInterface {

    @FXML
    TextField tfnom,tfprenom,tfhorr,tfsal,tfuser,tfmdp,tfem,tftel;

    @FXML
    ComboBox<String> combdro;

    @FXML
    DatePicker dt;

    @FXML
    Button btcls,btmin;

    @FXML
    Label lbl,lbtit;


    @FXML
    public void initialize(){

        lbtit.setText("Agent Ressource : Ajouter un employé");

        combdro.getItems().removeAll(combdro.getItems());
        combdro.getItems().addAll("Employe","Agent Ressource","Chef Projet","Agent Municipal","Agent Financier");
        viderBut();
    }

    public static int callId(String id, String table){
        String field="";

        try {
            PreparedStatement statement;

            try{
                Connection connection= getOracleConnection();
                statement = connection.prepareStatement("SELECT max("+id+") FROM "+table);
                //get data from db

                ResultSet rs = statement.executeQuery();
                //fetch data

                while(rs.next()){
                    field = rs.getString("MAX("+id.toUpperCase()+')');
                    if(field == null) field = "0";
                }
                rs.close();
                connection.close();

            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        catch (Exception nbe){
            nbe.printStackTrace();
        }
        int idc = Integer.parseInt(field);

        idc++;

        System.out.println(idc);
        return idc;

    }

    public boolean alpha(String s){
        boolean res = false;
        int i;
        for (i=0;i<s.length();i++){
            if(s.charAt(i)>=0 && s.charAt(i)<=9) break;
        }
        if (i < s.length()) res = true;
        return res;
    }

    @FXML
    public void addBut(){
        int id = callId("id", "employ");
        int idcpt = callId("idcpt", "compte");
        String horr = tfhorr.getText();
        String sal = tfsal.getText();
        String date = String.valueOf(dt.getValue());
        String nom = tfnom.getText();
        String prenom = tfprenom.getText();
        String droit = combdro.getValue();
        String user = tfuser.getText();
        String mdp = tfmdp.getText();
        String tel = tftel.getText();
        String email = tfem.getText();

        System.out.println();

        if(nom == null) lbl.setText("nom est null");
        else if(!alpha(nom)) lbl.setText("nom contient des nombres");
        else if(prenom== null) lbl.setText("prenom est null");
        else if(!alpha(prenom)) lbl.setText("prenom contient des nombres");
        else if(user== null) lbl.setText("username est null");
        else if(user.length() < 5) lbl.setText("username doit avoir comme longeur 5");
        else if(mdp== null) lbl.setText("mot de pass est vide");
        else if(mdp.length() < 8) lbl.setText("mot de pass est faible");
        else if(sal== null) lbl.setText("salaire est vide");
        else if(Integer.parseInt(sal) > 1500 || Integer.parseInt(sal) < 100) lbl.setText("salaire est invalid");
        else if(date== null) lbl.setText("date est null");
        else if(Integer.parseInt(date.substring(0,4)) > 2000) lbl.setText("age trop petit");
        else if(Integer.parseInt(date.substring(0,4)) < 1960) lbl.setText("age trop grand");
        else if(droit== null) lbl.setText("droit est null");
        else if(tel== null) lbl.setText("tel est vide");
        else if(tel.length() == 8) lbl.setText("tel invalid");
        else if(email== null) lbl.setText("email invalid");
        else if (!email.contains("@")) lbl.setText("email doit contenir @");
        else if(horr== null) lbl.setText("horr est null");
        else if(Integer.parseInt(horr) > 35 || Integer.parseInt(horr) < 1) lbl.setText("horr invalid");
        else {
            try {
                Connection connection = getOracleConnection();

                PreparedStatement statement = connection.prepareStatement("insert into employ(id,nom,prenom,date_naiss,droit,horr,salaire,joindate) values(" + id + ",'" + nom + "','" + prenom + "',to_date('" + date + "','yyyy-mm-dd'),'" + droit + "'," + horr + "," + sal + ",to_date('" + LocalDate.now() + "','yyyy-mm-dd'))");

                PreparedStatement statement1 = connection.prepareStatement("insert into compte values(" + idcpt + " , '" + user + "' , '" + mdp + "' , " + tel + " , '" + email + "' , " + id + ")");

                statement.executeUpdate();
                statement1.executeUpdate();

                lbl.setText("Ajouté");

                connection.close();

            } catch (SQLException e) {
                lbl.setText("insert érror");
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void viderBut(){
        initField(tfnom, tfprenom, dt, combdro, tfhorr, tfsal, tfuser, tfmdp, tftel, tfem);
    }

    static void initField(TextField tfnom, TextField tfprenom, DatePicker dt, ComboBox<String> combdro, TextField tfhorr, TextField tfsal, TextField tfuser, TextField tfmdp, TextField tftel, TextField tfem) {
        tfnom.setText(null);
        tfprenom.setText(null);
        dt.setValue(null);
        combdro.setValue(null);
        tfhorr.setText(null);
        tfsal.setText(null);
        tfuser.setText(null);
        tfmdp.setText(null);
        tftel.setText(null);
        tfem.setText(null);
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
