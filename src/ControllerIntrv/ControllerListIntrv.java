package ControllerIntrv;

import Controller.ControllerAlert;
import Controller.ControllerLock;
import Controller.ControllerLog;
import Model.Interv;
import Model.User;
import Services.Gestion;
import Services.MainInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static DataBaseConnection.OracleConnection.getOracleConnection;

public class ControllerListIntrv implements Gestion, MainInterface {

    @FXML
    TableView<Interv> tableView;

    @FXML
    TextField tf;

    @FXML
    Button btcls,btret,btmin,btinfo;

    @FXML
    Label lbl,lbtit;

    private final ObservableList<Interv> intervdt = FXCollections.observableArrayList();

    private int selectedId;

    @FXML
    public void initialize(){
        lbtit.setText("Chef proj : List un projet");
        btinfo.setText(" ( "+ User.getUsern()+" ) ");
        initList(intervdt,tableView);
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { if(newValue != null){
            ControllerModifIntrv.selectedId=newValue.getIdinterv();this.selectedId=newValue.getIdinterv();}});

        this.callttList();
    }

    public static void initList(ObservableList<Interv> intervdt, TableView<Interv> tableView){

        TableColumn idproj = new TableColumn("idinterv");
        TableColumn titre = new TableColumn("titre");
        TableColumn obj = new TableColumn("objectif");
        TableColumn deb = new TableColumn("date_debut");
        TableColumn fin = new TableColumn("date_fin");
        TableColumn etat = new TableColumn("etat");
        TableColumn loc = new TableColumn("location");
        TableColumn joindate = new TableColumn("joindate");


        tableView.getColumns().addAll(idproj,titre,obj,deb,fin,etat,loc,joindate);


        idproj.setCellValueFactory(new PropertyValueFactory<Interv,Integer>("idinterv"));
        titre.setCellValueFactory(new PropertyValueFactory<Interv,String>("titre"));
        obj.setCellValueFactory(new PropertyValueFactory<Interv,String>("obj"));
        deb.setCellValueFactory(new PropertyValueFactory<Interv,String>("deb"));
        fin.setCellValueFactory(new PropertyValueFactory<Interv,String>("fin"));
        etat.setCellValueFactory(new PropertyValueFactory<Interv,String>("etat"));
        loc.setCellValueFactory(new PropertyValueFactory<Interv,String>("loc"));
        joindate.setCellValueFactory(new PropertyValueFactory<Interv,String>("joindate"));


        tableView.setItems(intervdt);
    }

    public static void ttList(ObservableList<Interv> intervdt, TableView<Interv> tableView, Label lbl){
        PreparedStatement statement;

        try {
            Connection connection = getOracleConnection();
            statement = connection.prepareStatement("SELECT * FROM interv");

            ResultSet rs = statement.executeQuery();

            intervdt.clear();
            while (rs.next()) {

                String field = rs.getString("idinterv");
                String field1 = rs.getString("titre");
                String field2 = rs.getString("obj");
                String field3 = rs.getString("deb");
                String field4 = rs.getString("fin");
                String field5 = rs.getString("etat");
                String field6 = rs.getString("loc");
                String field7 = rs.getString("joindate");

                intervdt.add(new Interv(Integer.parseInt(field),field1,field2,field3,field4,field5,field6,field7));

            }
            rs.close();
            connection.close();
        } catch (SQLException e) {
            lbl.setText("Une erreur dans la tente d'affiche");

        }

        tableView.setItems(intervdt);
    }

    public static void tList(TextField tf, ObservableList<Interv> intervdt, TableView<Interv> tableView, Label lbl){
        try {
            int intervid = Integer.parseInt(tf.getText());
            PreparedStatement statement;

            try{
                Connection connection= getOracleConnection();
                statement = connection.prepareStatement("SELECT * FROM interv where idinterv = "+intervid);

                intervdt.clear();

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

                    intervdt.add(new Interv(Integer.parseInt(field),field1,field2,field3,field4,field5,field6,field7));

                }
                lbl.setText("Trouvé!");
                rs.close();
                connection.close();
            }
            catch (SQLException e){
                lbl.setText("Une erreur dans la tente d'affiche");
            }

            tableView.setItems(intervdt);
        }
        catch (Exception nbe){
            lbl.setText("id doit etre numerique");
        }
    }

    @FXML
    public void callttList(){
        ttList(intervdt,tableView,lbl);
    }

    @FXML
    public void callList(){
        tList(tf,intervdt,tableView,lbl);
    }

    @FXML
    public void addBut(){
        try {
            ControllerLog.crtnStg("../sampleinterv/sampleajoutinterv.fxml", "Chef Projet : Ajouter un projet");
            if(ControllerAjoutIntrv.sign) callttList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void modifBut(){
        if(selectedId != 0) {
            try {
                ControllerLog.crtnStg("../sampleinterv/samplemodifinterv.fxml", "Chef Projet : Modifier un projet");
                if(ControllerModifIntrv.sign) callttList();
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

                        PreparedStatement statement = connection.prepareStatement("delete from interv where idinterv = " + id);

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
            ControllerLog.crtStg("../sample/sampleadmin.fxml","President : Centre de gestion",btret);
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