package ControllerDepRev;

import Controller.ControllerAlert;
import Controller.ControllerLock;
import Controller.ControllerLog;
import Main.GenPDF;
import Model.Depense;
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

public class ControllerListDepRev implements Gestion, MainInterface {

    @FXML
    TableView<Depense> tableView;

    @FXML
    TextField tf;

    @FXML
    Button btcls,btret,btmin,btinfo;

    @FXML
    Label lbl,lbtit;

    private ObservableList<Depense> depdt = FXCollections.observableArrayList();

    private int selectedId;

    @FXML
    private void initialize(){

        btinfo.setText("( "+ User.getUsern() +" )");
        lbtit.setText("Agent Municipal : Liste des Depenses / Revenus");
        initList(depdt,tableView);

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { if(newValue != null){
            ControllerModifDepRev.selectedId=newValue.getIddep();this.selectedId=newValue.getIddep();}});

        this.callttList();
    }

    public static void initList(ObservableList<Depense> depdt,TableView<Depense> tableView){
        TableColumn iDep = new TableColumn("iddep");
        TableColumn titre = new TableColumn("montant");
        TableColumn sujet = new TableColumn("description");
        TableColumn etat = new TableColumn("type");
        tableView.getColumns().addAll(iDep,titre,sujet,etat);


        iDep.setCellValueFactory(new PropertyValueFactory<Depense,Integer>("iddep"));
        titre.setCellValueFactory(new PropertyValueFactory<Depense,String>("montant"));
        sujet.setCellValueFactory(new PropertyValueFactory<Depense,String>("forres"));
        etat.setCellValueFactory(new PropertyValueFactory<Depense,String>("type"));

        tableView.setItems(depdt);
    }

    public static void ttList(ObservableList<Depense> depdt,TableView<Depense> tableView, Label lbl){
        PreparedStatement statement;

        try {
            Connection connection = getOracleConnection();
            statement = connection.prepareStatement("SELECT * FROM depense");

            ResultSet rs = statement.executeQuery();

            depdt.clear();
            while (rs.next()) {

                String field = rs.getString("iddep");
                String field1 = rs.getString("montant");
                String field2 = rs.getString("forres");
                String field3 = rs.getString("type");

                depdt.add(new Depense(Integer.parseInt(field),Integer.parseInt(field1),field2,field3));

            }
            rs.close();

        } catch (SQLException e) {
            lbl.setText("Une erreur dans la tente d'affiche");

        }

        tableView.setItems(depdt);
    }

    public static void tList(TextField tf, ObservableList<Depense> depdt,TableView<Depense> tableView, Label lbl){
        try {
            int Depid = Integer.parseInt(tf.getText());

            PreparedStatement statement;

            try{
                Connection connection= getOracleConnection();
                statement = connection.prepareStatement("SELECT * FROM depense where iddep = "+Depid);

                depdt.clear();

                //get data from db

                ResultSet rs = statement.executeQuery();
                //fetch data

                while(rs.next()){
                    String field = rs.getString("iddep");
                    String field1 = rs.getString("montant");
                    String field2 = rs.getString("forres");
                    String field3 = rs.getString("type");

                    depdt.add(new Depense(Integer.parseInt(field),Integer.parseInt(field1),field2,field3));

                }
                lbl.setText("Trouvé!");
                rs.close();

            }
            catch (SQLException e){
                lbl.setText("Une erreur dans la tente d'affiche");
            }

            tableView.setItems(depdt);
        }
        catch (Exception nbe){
            lbl.setText("id doit etre numerique");
        }
    }

    @FXML
    public void callttList(){
        ttList(depdt,tableView,lbl);
        tableView.getItems();
    }

    @FXML
    public void callList(){
        tList(tf,depdt,tableView,lbl);
    }

    @FXML
    public void addBut(){
        try {
            ControllerLog.crtnStg("../sampledeprev/sampleajoutdeprev.fxml", "Agent Financier : Ajouter une Depense / Revenu");
            if (ControllerAjoutDepRev.sign) callttList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void genBut(){

        try{

            GenPDF.genRptPdf(depdt);
            lbl.setText("Généré!");

        }
        catch (Exception nbe){
            nbe.printStackTrace();
        }
    }

    @FXML
    public void modifBut(){
        if(selectedId != 0) {
            try {
                ControllerLog.crtnStg("../sampledeprev/samplemodifdeprev.fxml", "Agent Financier : Modifier une Depense / Revenu");
                if (ControllerModifDepRev.sign) callttList();
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

                        statement = connection.prepareStatement("delete from depense where iddep = " + id);

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
            ControllerLog.crtStg("../sample/samplegf.fxml","Agent Financier : Centre de gestion",btret);
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