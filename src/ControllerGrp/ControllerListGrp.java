package ControllerGrp;

import Controller.ControllerAlert;
import Controller.ControllerLock;
import Controller.ControllerLog;
import ControllerMat.ControllerModifMat;
import Model.Equipe;
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

public class ControllerListGrp implements Gestion, MainInterface {
    @FXML
    TableView<Equipe> tableView;

    @FXML
    TextField tf;

    @FXML
    Button btcls,btret,btmin,btinfo,btadd,btdet,btsupp;

    @FXML
    Label lbl,lbtit;

    private final ObservableList<Equipe> eqpdt = FXCollections.observableArrayList();

    private int selectedId;

    @FXML
    public void initialize(){

        btinfo.setText("( "+ User.getUsern()+" )");
        lbtit.setText("Chef projet : Lister des equipes");

        initList(eqpdt,tableView);

        tableView.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> { if(newValue != null) {
            ControllerDetGrp.selectedId = newValue.getIdequipe(); this.selectedId = newValue.getIdequipe();}});


        this.callttList();
    }

    public static void initList(ObservableList<Equipe> eqpdt, TableView<Equipe> tableView) {
        TableColumn idequipe = new TableColumn("idequipe");
        TableColumn nomequipe = new TableColumn("nom_equipe");
        TableColumn deskequipe = new TableColumn("description_equipe");
        TableColumn joindate = new TableColumn("joindate");

        tableView.getColumns().addAll(idequipe,nomequipe,deskequipe,joindate);


        idequipe.setCellValueFactory(new PropertyValueFactory<Equipe, Integer>("idequipe"));
        nomequipe.setCellValueFactory(new PropertyValueFactory<Equipe, String>("nomequipe"));
        deskequipe.setCellValueFactory(new PropertyValueFactory<Equipe, String>("deskequipe"));
        joindate.setCellValueFactory(new PropertyValueFactory<Equipe, String>("joindate"));


        tableView.setItems(eqpdt);
    }

    public static void ttList(ObservableList<Equipe> eqpdt, TableView<Equipe> tableView, Label lbl) {
        PreparedStatement statement;

        try {
            Connection connection = getOracleConnection();
            statement = connection.prepareStatement("SELECT * FROM equipe");

            ResultSet rs = statement.executeQuery();

            eqpdt.clear();
            while (rs.next()) {

                String field = rs.getString("idequipe");
                String field1 = rs.getString("nomequipe");
                String field2 = rs.getString("deskequipe");
                String field4 = rs.getString("joindate");


                eqpdt.add(new Equipe(Integer.parseInt(field), field1, field2, field4.substring(0,10)));
            }
            rs.close();
            connection.close();

        } catch (SQLException e) {
            lbl.setText("Une erreur dans la tente d'affiche");
        }
        tableView.setItems(eqpdt);
    }

    public static void tList(TextField tf,ObservableList<Equipe> eqpdt, TableView<Equipe> tableView, Label lbl){
        try {
            int eqpid = Integer.parseInt(tf.getText());

            PreparedStatement statement;

            try {
                Connection connection = getOracleConnection();
                statement = connection.prepareStatement("SELECT * FROM equipe where idequipe = " + eqpid);

                eqpdt.clear();

                //get data from db

                ResultSet rs = statement.executeQuery();
                //fetch data

                while (rs.next()) {
                    String field = rs.getString("idequipe");
                    String field1 = rs.getString("nomequipe");
                    String field2 = rs.getString("deskequipe");
                    String field4 = rs.getString("joindate");


                    eqpdt.add(new Equipe(Integer.parseInt(field), field1, field2, field4.substring(0,10)));

                }
                rs.close();
                connection.close();
            } catch (SQLException e) {
                lbl.setText("Une erreur dans la tente d'affiche");
            }

            tableView.setItems(eqpdt);
        } catch (Exception nbe) {
            lbl.setText("id doit etre numerique");
        }
    }

    @FXML
    public void callttList() {
        ttList(eqpdt,tableView,lbl);
    }

    @FXML
    public void callList() {
        tList(tf,eqpdt,tableView,lbl);
    }

    @FXML
    public void addBut(){
        try {
            ControllerLog.crtStg("../samplegrp/sampleajoutgrp.fxml","Chef projet : Ajout un groupe",btadd);
        }
        catch (Exception e){
            e.printStackTrace();

        }
    }

    @FXML
    public void modifBut(){
        if(selectedId != 0) {
            try {
                ControllerLog.crtnStg("../samplegrp/samplemodifgrp.fxml", "Chef projet : Modifier un groupe");
                if(ControllerModifMat.sign) callttList();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        else lbl.setText("first of all select from the list");
    }

    @FXML
    public void detBut(){
        if(selectedId != 0) {
            try {
                ControllerLog.crtnStg("../samplegrp/sampledetgrp.fxml", "Chef projet : Detailler un groupe");
                if(ControllerDetGrp.sign) callttList();
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

                        statement = connection.prepareStatement("delete from equipe where idequipe = " + id);

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
            ControllerLog.crtStg("../sample/samplecp.fxml", "Chef projet : Centre de gestion", btret);
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
