package ControllerProj;

import Controller.ControllerAlert;
import Controller.ControllerLock;
import Controller.ControllerLog;
import Model.Projet;
import Model.User;
import Services.Gestion;
import Services.MainInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.*;

import static DataBaseConnection.OracleConnection.getOracleConnection;

public class ControllerListProj implements Gestion, MainInterface {

    @FXML
    TableView<Projet> tableView;

    @FXML
    TextField tf;

    @FXML
    Button btcls,btret,btmin,btinfo;

    @FXML
    Label lbl,lbtit;

    private final ObservableList<Projet> projdt = FXCollections.observableArrayList();

    private int selectedId;

    @FXML
    public void initialize(){
        lbtit.setText("Chef proj : List un projet");
        btinfo.setText(" ( "+User.getUsern()+" ) ");
        initList(projdt,tableView);
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { if(newValue != null){
            ControllerModifProj.selectedId=newValue.getIdproj();this.selectedId=newValue.getIdproj();}});

        this.callttList();
    }

    public static void initList(ObservableList<Projet> projdt, TableView<Projet> tableView){

        TableColumn idproj = new TableColumn("idproj");
        TableColumn titre = new TableColumn("titre");
        TableColumn obj = new TableColumn("objectif");
        TableColumn deb = new TableColumn("date_debut");
        TableColumn fin = new TableColumn("date_fin");
        TableColumn etat = new TableColumn("etat");
        TableColumn loc = new TableColumn("location");
        TableColumn joindate = new TableColumn("date_ajout");


        tableView.getColumns().addAll(idproj,titre,obj,deb,fin,etat,loc,joindate);


        idproj.setCellValueFactory(new PropertyValueFactory<Projet,Integer>("idproj"));
        titre.setCellValueFactory(new PropertyValueFactory<Projet,String>("titre"));
        obj.setCellValueFactory(new PropertyValueFactory<Projet,String>("obj"));
        deb.setCellValueFactory(new PropertyValueFactory<Projet,String>("deb"));
        fin.setCellValueFactory(new PropertyValueFactory<Projet,String>("fin"));
        etat.setCellValueFactory(new PropertyValueFactory<Projet,String>("etat"));
        loc.setCellValueFactory(new PropertyValueFactory<Projet,String>("loc"));
        joindate.setCellValueFactory(new PropertyValueFactory<Projet,String>("joindate"));


        tableView.setItems(projdt);
    }

    public static void ttList(ObservableList<Projet> projdt, TableView<Projet> tableView, Label lbl){
        PreparedStatement statement;

        try {
            Connection connection = getOracleConnection();
            statement = connection.prepareStatement("SELECT * FROM project");

            ResultSet rs = statement.executeQuery();

            projdt.clear();
            while (rs.next()) {

                String field = rs.getString("idproj");
                String field1 = rs.getString("titre");
                String field2 = rs.getString("obj");
                String field3 = rs.getString("deb");
                String field4 = rs.getString("fin");
                String field5 = rs.getString("etat");
                String field6 = rs.getString("loc");
                String field7 = rs.getString("joindate");

                projdt.add(new Projet(Integer.parseInt(field),field1,field2,field3,field4,field5,field6,field7));

            }
            rs.close();
            connection.close();
        } catch (SQLException e) {
            lbl.setText("Une erreur dans la tente d'affiche");

        }

        tableView.setItems(projdt);
    }

    public static void tList(TextField tf, ObservableList<Projet> projdt, TableView<Projet> tableView, Label lbl){
        try {
            int projid = Integer.parseInt(tf.getText());
            PreparedStatement statement;

            try{
                Connection connection= getOracleConnection();
                statement = connection.prepareStatement("SELECT * FROM project where idproj = "+projid);

                projdt.clear();

                //get data from db

                ResultSet rs = statement.executeQuery();
                //fetch data

                while(rs.next()){
                    String field = rs.getString("idproj");
                    String field1 = rs.getString("titre");
                    String field2 = rs.getString("obj");
                    String field3 = rs.getString("deb");
                    String field4 = rs.getString("fin");
                    String field5 = rs.getString("etat");
                    String field6 = rs.getString("loc");
                    String field7 = rs.getString("joindate");

                    projdt.add(new Projet(Integer.parseInt(field),field1,field2,field3,field4,field5,field6,field7));

                }
                lbl.setText("Trouvé!");
                rs.close();
                connection.close();
            }
            catch (SQLException e){
                lbl.setText("Une erreur dans la tente d'affiche");
            }

            tableView.setItems(projdt);
        }
        catch (Exception nbe){
            lbl.setText("id doit etre numerique");
        }
    }

    @FXML
    public void callttList(){
        ttList(projdt,tableView,lbl);
    }

    @FXML
    public void callList(){
        tList(tf,projdt,tableView,lbl);
    }

    @FXML
    public void addBut(){
        try {
            ControllerLog.crtnStg("../sampleproj/sampleajoutproj.fxml", "Chef Projet : Ajouter un projet");
            if(ControllerAjoutProj.sign) callttList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void modifBut(){
        if(selectedId != 0) {
            try {
                ControllerLog.crtnStg("../sampleproj/samplemodifproj.fxml", "Chef Projet : Modifier un projet");
                if(ControllerModifProj.sign) callttList();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        else lbl.setText("first of all select from the list");
    }

    @FXML
    public void suppBut(){
        try {
            ControllerLog.crtaStg("../sample/samplealert.fxml", "Suppression");
            if(ControllerAlert.sign) {

                try {
                    int id = selectedId;

                    try {
                        Connection connection = getOracleConnection();

                        PreparedStatement statement = connection.prepareStatement("delete from project where idproj = " + id);

                        statement.executeUpdate();

                        if(statement.getUpdateCount() == 1) {
                            lbl.setText("Supprimé!");
                            this.callttList();
                        }
                        else lbl.setText("Non trouvé");
                        connection.close();
                    } catch (SQLException e) {
                        lbl.setText("error retrieving id");
                    }
                }
                catch (NumberFormatException nb){
                    lbl.setText("id doit etre numerique");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
    @FXML
    public void retBut(){
        try {
            ControllerLog.crtStg("../sample/samplecp.fxml","Chef proj : Centre de gestion",btret);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void infoBut(){
        try {
            ControllerLog.crtaStg("../sample/samplelock.fxml", "Privilège");
            if (ControllerLock.sign) {
                try {
                    ControllerLog.crtnStg("../sample/sampleinfo.fxml", "Info");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public void propBut(){
        try {
            ControllerLog.crtnStg("../sample/sampleprop.fxml","à Propos");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}