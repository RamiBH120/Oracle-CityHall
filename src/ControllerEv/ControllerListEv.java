package ControllerEv;

import Controller.ControllerAlert;
import Controller.ControllerLock;
import Controller.ControllerLog;
import Model.Evenement;
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

public class ControllerListEv implements Gestion, MainInterface {

    @FXML
    TableView<Evenement> tableView;

    @FXML
    TextField tf;

    @FXML
    Button btcls,btret,btmin,btinfo;

    @FXML
    Label lbl,lbtit;

    private ObservableList<Evenement> evdt = FXCollections.observableArrayList();

    private int selectedId;

    @FXML
    public void initialize(){
        lbtit.setText("Chef proj : List un ev");
        btinfo.setText("( "+ User.getUsern()+" )");

        initList(evdt,tableView);

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { if(newValue != null){ControllerModifEv.selectedId=newValue.getIdev();this.selectedId=newValue.getIdev();}});

        this.callttList();
    }

    public static void initList(ObservableList<Evenement> evdt,TableView<Evenement> tableView){
        TableColumn idev = new TableColumn("idev");
        TableColumn titre = new TableColumn("titre");
        TableColumn obj = new TableColumn("objectif");
        TableColumn deb = new TableColumn("date_debut");
        TableColumn fin = new TableColumn("date_fin");
        TableColumn etat = new TableColumn("etat");
        TableColumn loc = new TableColumn("location");
        TableColumn joindate = new TableColumn("date_creation");

        tableView.getColumns().addAll(idev,titre,obj,deb,fin,etat,loc,joindate);


        idev.setCellValueFactory(new PropertyValueFactory<Evenement,Integer>("idev"));
        titre.setCellValueFactory(new PropertyValueFactory<Evenement,String>("titre"));
        obj.setCellValueFactory(new PropertyValueFactory<Evenement,String>("obj"));
        deb.setCellValueFactory(new PropertyValueFactory<Evenement,String>("deb"));
        fin.setCellValueFactory(new PropertyValueFactory<Evenement,String>("fin"));
        etat.setCellValueFactory(new PropertyValueFactory<Evenement,String>("etat"));
        loc.setCellValueFactory(new PropertyValueFactory<Evenement,String>("loc"));
        joindate.setCellValueFactory(new PropertyValueFactory<Evenement,String>("joindate"));

        tableView.setItems(evdt);
    }

    public static void ttList(ObservableList<Evenement> evdt,TableView<Evenement> tableView,Label lbl){
        PreparedStatement statement;

        try {
            Connection connection = getOracleConnection();
            statement = connection.prepareStatement("SELECT * FROM event");

            ResultSet rs = statement.executeQuery();

            evdt.clear();
            while (rs.next()) {

                String field = rs.getString("idev");
                String field1 = rs.getString("titre");
                String field2 = rs.getString("obj");
                String field3 = rs.getString("deb");
                String field4 = rs.getString("fin");
                String field5 = rs.getString("etat");
                String field6 = rs.getString("loc");
                String field7 = rs.getString("joindate");


                evdt.add(new Evenement(Integer.parseInt(field),field1,field2,field3,field4,field5,field6,field7));

            }
            rs.close();
            connection.close();

        } catch (SQLException e) {
            lbl.setText("Une erreur dans la tente d'affiche");

        }
        tableView.setItems(evdt);
    }

    public static void tList(TextField tf,ObservableList<Evenement> evdt,TableView<Evenement> tableView,Label lbl){
        try {
            int evid = Integer.parseInt(tf.getText());

            PreparedStatement statement;

            try{
                Connection connection= getOracleConnection();
                statement = connection.prepareStatement("SELECT * FROM event where idev = "+evid);

                evdt.clear();

                //get data from db

                ResultSet rs = statement.executeQuery();
                //fetch data

                while(rs.next()){
                    String field = rs.getString("idev");
                    String field1 = rs.getString("titre");
                    String field2 = rs.getString("obj");
                    String field3 = rs.getString("deb");
                    String field4 = rs.getString("fin");
                    String field5 = rs.getString("etat");
                    String field6 = rs.getString("loc");
                    String field7 = rs.getString("joindate");


                    evdt.add(new Evenement(Integer.parseInt(field),field1,field2,field3,field4,field5,field6,field7));

                }
                lbl.setText("Trouvé!");
                rs.close();
                connection.close();

            }
            catch (SQLException e){
                lbl.setText("Une erreur dans la tente d'affiche");
            }

            tableView.setItems(evdt);
        }
        catch (Exception nbe){
            lbl.setText("id doit etre numerique");
        }
    }
    @FXML
    public void callttList(){
        ttList(evdt,tableView,lbl);
    }
    @FXML
    public void callList(){
        tList(tf,evdt,tableView,lbl);
    }

    @FXML
    public void addBut(){
        try {
            ControllerLog.crtnStg("../sampleev/sampleajoutev.fxml", "Chef Projet : Ajouter un evenement");
            if(ControllerAjoutEv.sign) callttList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void modifBut(){
        if(selectedId != 0) {
            try {
                ControllerLog.crtnStg("../sampleev/samplemodifev.fxml", "Chef Projet : Modifier un evenement");
                if(ControllerModifEv.sign) callttList();
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


                    PreparedStatement statement;

                    try {
                        Connection connection = getOracleConnection();

                        statement = connection.prepareStatement("delete from event where idev = " + id);

                        statement.executeUpdate();

                        if (statement.getUpdateCount() == 1) {
                            lbl.setText("Supprimé!");
                            this.callttList();
                        } else lbl.setText("Non trouvé");

                        connection.close();
                    } catch (SQLException e) {
                        lbl.setText("error retrieving id");
                    }
                } catch (NumberFormatException nb) {
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