package ControllerMat;

import Controller.ControllerAlert;
import Controller.ControllerLock;
import Controller.ControllerLog;
import Model.Materiel;
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

public class ControllerListMat implements Gestion, MainInterface {

    @FXML
    TableView<Materiel> tableView;

    @FXML
    TextField tf;

    @FXML
    Button btcls, btret, btmin,btinfo;

    @FXML
    Label lbl, lbtit;

    private ObservableList<Materiel> matdt = FXCollections.observableArrayList();

    private int selectedId;

    @FXML
    public void initialize() {

        btinfo.setText("( "+ User.getUsern()+" )");
        lbtit.setText("Agent RH : Liste des mats");
        initList(matdt, tableView);

        tableView.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> {if (newValue != null){ControllerModifMat.selectedId = newValue.getIdmat(); this.selectedId = newValue.getIdmat();}});

        this.callttList();
    }

    public static void initList(ObservableList<Materiel> matdt, TableView<Materiel> tableView) {
        TableColumn idmat = new TableColumn("idmat");
        TableColumn nom = new TableColumn("nom");
        TableColumn cat = new TableColumn("categorie");
        TableColumn stock = new TableColumn("stock");
        TableColumn etat = new TableColumn("etat");
        TableColumn joindate = new TableColumn("date_ajout");

        tableView.getColumns().addAll(idmat, nom, cat, stock, etat,joindate);


        idmat.setCellValueFactory(new PropertyValueFactory<Materiel, Integer>("idmat"));
        nom.setCellValueFactory(new PropertyValueFactory<Materiel, String>("nom"));
        cat.setCellValueFactory(new PropertyValueFactory<Materiel, String>("cat"));
        stock.setCellValueFactory(new PropertyValueFactory<Materiel, Integer>("stock"));
        etat.setCellValueFactory(new PropertyValueFactory<Materiel, String>("etat"));
        joindate.setCellValueFactory(new PropertyValueFactory<Materiel, String>("joindate"));


        tableView.setItems(matdt);
    }

    public static void ttList(ObservableList<Materiel> matdt, TableView<Materiel> tableView, Label lbl) {
        PreparedStatement statement;

        try {
            Connection connection = getOracleConnection();
            statement = connection.prepareStatement("SELECT * FROM materiel");

            ResultSet rs = statement.executeQuery();

            matdt.clear();
            while (rs.next()) {

                String field = rs.getString("idmat");
                String field1 = rs.getString("nom");
                String field2 = rs.getString("cat");
                String field3 = rs.getString("stock");
                String field4 = rs.getString("etat");
                String field5 = rs.getString("joindate");


                matdt.add(new Materiel(Integer.parseInt(field), field1, field2, Integer.parseInt(field3), field4,field5));

            }
            rs.close();
            connection.close();

        } catch (SQLException e) {
            lbl.setText("Une erreur dans la tente d'affiche");
        }
        tableView.setItems(matdt);
    }

    public static void tList(TextField tf,ObservableList<Materiel> matdt, TableView<Materiel> tableView, Label lbl){
        try {
            int matid = Integer.parseInt(tf.getText());

            PreparedStatement statement;

            try {
                Connection connection = getOracleConnection();
                statement = connection.prepareStatement("SELECT * FROM materiel where idmat = " + matid);

                matdt.clear();

                //get data from db

                ResultSet rs = statement.executeQuery();
                //fetch data

                while (rs.next()) {
                    String field = rs.getString("idmat");
                    String field1 = rs.getString("nom");
                    String field2 = rs.getString("cat");
                    String field3 = rs.getString("stock");
                    String field4 = rs.getString("etat");
                    String field5 = rs.getString("joindate");


                    matdt.add(new Materiel(Integer.parseInt(field), field1, field2, Integer.parseInt(field3), field4,field5));

                }
                lbl.setText("Trouvé!");
                rs.close();
                connection.close();
            } catch (SQLException e) {
                lbl.setText("Une erreur dans la tente d'affiche");
            }

            tableView.setItems(matdt);
        } catch (Exception nbe) {
            lbl.setText("id doit etre numerique");
        }
    }

    @FXML
    public void callttList() {
        ttList(matdt,tableView,lbl);
    }

    @FXML
    public void callList() {
        tList(tf,matdt,tableView,lbl);
    }

    @FXML
    public void addBut(){
        try {
            ControllerLog.crtnStg("../samplemat/sampleajoutmat.fxml","Agent RH : Ajout un mat");
            if(ControllerAjoutMat.sign) callttList();
        }
        catch (Exception e){
            e.printStackTrace();

        }
    }

    @FXML
    public void modifBut(){
        if(selectedId != 0) {
            try {
                ControllerLog.crtnStg("../samplemat/samplemodifmat.fxml", "Agent RH : Modifier un mat");
                if(ControllerModifMat.sign) callttList();
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

                        statement = connection.prepareStatement("delete from materiel where idmat = " + id);

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
    public void clsBut() {
        Stage stage = (Stage) btcls.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    public void retBut() {
        try {
            ControllerLog.crtStg("../sample/samplerh.fxml", "Agent RH : Centre de gestion", btret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void minBut() {
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
