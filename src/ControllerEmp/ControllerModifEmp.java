package ControllerEmp;


import Controller.ControllerLog;
import Model.User;
import Services.ModifInterface;
import Services.SousInterface;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;

import static DataBaseConnection.OracleConnection.getOracleConnection;

public class ControllerModifEmp implements SousInterface, ModifInterface {

    @FXML
    TextField tfnom,tfprenom,tfhorr,tfsal,tfuser,tftel,tfmdp,tfem;

    @FXML
    ComboBox<String> combdro;

    @FXML
    DatePicker dt;

    @FXML
    Button btcls,btret,btmin;

    @FXML
    Label lbl,lbtit;

    static int selectedId;

    public static boolean sign=false;

    @FXML
    public void initialize(){

        lbtit.setText(User.getUsern()+"(Agent Ressource) : Modifier un employé");

        combdro.getItems().removeAll(combdro.getItems());
        combdro.getItems().addAll("Employe","Agent Ressource","Chef Projet","Agent Municipal","Agent Financier");

        rechBut();
    }

    @FXML
    public void rechBut(){

        try {
            PreparedStatement statement;

            try {
                Connection connection = getOracleConnection();
                statement = connection.prepareStatement("select e.id,e.nom,e.prenom,e.date_naiss,e.droit,e.horr,e.salaire,e.auto,c.usern,c.mdp,c.tel,c.email from employ e join compte c on(e.id=c.idemp) where e.id = " + selectedId);

                //get data from db

                ResultSet rs = statement.executeQuery();

                while (rs.next()) {

                    String field1 = rs.getString("nom");
                    String field2 = rs.getString("prenom");
                    String field3 = rs.getString("date_naiss");
                    String field4 = rs.getString("droit");
                    String field5 = rs.getString("horr");
                    String field6 = rs.getString("salaire");
                    String field7 = rs.getString("usern");
                    String field8 = rs.getString("mdp");
                    String field9 = rs.getString("tel");
                    String field10 = rs.getString("email");


                    tfnom.setText(field1);
                    tfprenom.setText(field2);
                    dt.setValue(LocalDate.parse(field3.substring(0,10)));
                    combdro.setValue(field4);
                    tfhorr.setText(String.valueOf(field5));
                    tfsal.setText(String.valueOf(field6));
                    tfuser.setText(field7);
                    tfmdp.setText(field8);
                    tftel.setText(String.valueOf(field9));
                    tfem.setText(field10);
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
    public void modifBut(){

        try {

            int id = selectedId;

            PreparedStatement statement,statement1;
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
                    statement = connection.prepareStatement("update employ set nom = '" + tfnom.getText() + "' , prenom = '" + tfprenom.getText() + "' , date_naiss = to_date('" + dt.getValue() + "','yyyy-mm-dd') , droit = '" + combdro.getValue() + "' , horr = " + Integer.parseInt(tfhorr.getText()) + " , salaire = " + Integer.parseInt(tfsal.getText()) + " where id = " + id);
                    statement1 = connection.prepareStatement("update compte set usern = '" + tfuser.getText() + "' , mdp = '" + tfmdp.getText() + "' , tel = to_number('" + tftel.getText() + "') , email = '" + tfem.getText() + "'  where idemp = " + id);

                    statement.executeUpdate();
                    statement1.executeUpdate();

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
        ControllerAjoutEmp.initField(tfnom, tfprenom, dt, combdro, tfhorr, tfsal, tfuser, tfmdp, tftel, tfem);
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
