package ControllerDem;

import Controller.ControllerAlert;
import Controller.ControllerLock;
import Controller.ControllerLog;
import ControllerEv.ControllerModifEv;
import Main.GenPDF;
import Model.Demande;
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

public class ControllerListDem implements MainInterface, Gestion {

    @FXML
    TableView<Demande> tableView;

    @FXML
    TextField tf;

    @FXML
    Button btcls,btret,btmin,btinfo;

    @FXML
    Label lbl,lbtit;

    private ObservableList<Demande> demdt = FXCollections.observableArrayList();

    private int selectedId;

    @FXML
    private void initialize(){

        btinfo.setText("( "+ User.getUsern() +" )");
        lbtit.setText("Agent Municipal : Liste des demandes");
        initList(demdt,tableView);

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { if(newValue != null){
            ControllerModifDem.selectedId=newValue.getIdem();this.selectedId=newValue.getIdem();}});

        this.callttList();
    }

    public static void initList(ObservableList<Demande> demdt,TableView<Demande> tableView){
        TableColumn idem = new TableColumn("idem");
        TableColumn titre = new TableColumn("titre");
        TableColumn sujet = new TableColumn("sujet");
        TableColumn etat = new TableColumn("etat");
        TableColumn joindate = new TableColumn("date_de_creation");
        TableColumn type = new TableColumn("type");

        tableView.getColumns().addAll(idem,titre,sujet,etat,joindate,type);


        idem.setCellValueFactory(new PropertyValueFactory<Demande,Integer>("idem"));
        titre.setCellValueFactory(new PropertyValueFactory<Demande,String>("titre"));
        sujet.setCellValueFactory(new PropertyValueFactory<Demande,Integer>("sujet"));
        etat.setCellValueFactory(new PropertyValueFactory<Demande,String>("etat"));
        joindate.setCellValueFactory(new PropertyValueFactory<Demande,String>("joindate"));
        type.setCellValueFactory(new PropertyValueFactory<Demande,String>("type"));


        tableView.setItems(demdt);
    }

    public static void ttList(ObservableList<Demande> demdt,TableView<Demande> tableView, Label lbl){
        PreparedStatement statement;

        try {
            Connection connection = getOracleConnection();
            statement = connection.prepareStatement("SELECT * FROM demande");

            ResultSet rs = statement.executeQuery();

            demdt.clear();
            while (rs.next()) {

                String field = rs.getString("idem");
                String field1 = rs.getString("titre");
                String field2 = rs.getString("sujet");
                String field3 = rs.getString("etat");
                String field4 = rs.getString("joindate");
                String field5 = rs.getString("type");


                demdt.add(new Demande(Integer.parseInt(field),field1,field2,field3,field4,field5));

            }
            rs.close();

        } catch (SQLException e) {
            lbl.setText("Une erreur dans la tente d'affiche");

        }

        tableView.setItems(demdt);
    }

    public static void tList(TextField tf, ObservableList<Demande> demdt,TableView<Demande> tableView, Label lbl){
        try {
            int demid = Integer.parseInt(tf.getText());

            PreparedStatement statement;

            try{
                Connection connection= getOracleConnection();
                statement = connection.prepareStatement("SELECT * FROM demande where idem = "+demid);

                demdt.clear();

                //get data from db

                ResultSet rs = statement.executeQuery();
                //fetch data

                while(rs.next()){
                    String field = rs.getString("idem");
                    String field1 = rs.getString("titre");
                    String field2 = rs.getString("sujet");
                    String field3 = rs.getString("etat");
                    String field4 = rs.getString("joindate");
                    String field5 = rs.getString("type");

                    demdt.add(new Demande(Integer.parseInt(field),field1,field2,field3,field4,field5));

                }
                lbl.setText("Trouvé!");
                rs.close();

            }
            catch (SQLException e){
                lbl.setText("Une erreur dans la tente d'affiche");
            }

            tableView.setItems(demdt);
        }
        catch (Exception nbe){
            lbl.setText("id doit etre numerique");
        }
    }

    @FXML
    public void callttList(){
        ttList(demdt,tableView,lbl);
    }

    @FXML
    public void callList(){
        tList(tf,demdt,tableView,lbl);
    }

    @FXML
    public void addBut(){
        try {
            ControllerLog.crtnStg("../sampledem/sampleajoutdem.fxml", "Agent Municipal : Ajouter une demande");
            if (ControllerAjoutDem.sign) callttList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void genBut(){

        try {

            PreparedStatement statement;

            try{
                Connection connection= getOracleConnection();
                statement = connection.prepareStatement("SELECT * FROM demande where idem = "+selectedId);

                //get data from db

                ResultSet rs = statement.executeQuery();
                //fetch data

                while(rs.next()){
                    String field1 = rs.getString("titre");
                    String field2 = rs.getString("sujet");
                    String field3 = rs.getString("etat");
                    String field4 = rs.getString("joindate");
                    String field5 = rs.getString("type");

                    GenPDF.genPdf(field1,field2,field3,field4,field5);

                }
                lbl.setText("Généré!");
                rs.close();

            }
            catch (SQLException e){
                e.printStackTrace();
            }

        }
        catch (Exception nbe){
            nbe.printStackTrace();
        }
    }

    @FXML
    public void modifBut(){
        if(selectedId != 0) {
            try {
                ControllerLog.crtnStg("../sampledem/samplemodifdem.fxml", "Agent Municipal : Modifier une demande");
                if (ControllerModifDem.sign) callttList();
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

                        statement = connection.prepareStatement("delete from demande where idem = " + id);

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
    public void clsBut(){
        Stage stage = (Stage) btcls.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    @FXML
    public void retBut(){
        try {
            ControllerLog.crtStg("../sample/samplegm.fxml","Agent Municipal : Centre de gestion",btret);
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
