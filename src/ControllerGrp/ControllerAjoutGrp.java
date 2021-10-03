package ControllerGrp;

import Controller.ControllerLock;
import Controller.ControllerLog;
import Model.Employ;
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

public class ControllerAjoutGrp implements MainInterface {
    @FXML
    TableView<Employ> tableView,tableView1;

    @FXML
    TextField tf;

    @FXML
    Button btcls,btret,btmin,btinfo;

    @FXML
    Label lbl,lbtit;

    private final ObservableList<Employ> empdt = FXCollections.observableArrayList();
    private final ObservableList<Employ> empdt1 = FXCollections.observableArrayList();


    private int selectedId;

    Employ line;

    @FXML
    public void initialize(){

        btinfo.setText("( "+ User.getUsern()+" )");
        lbtit.setText("Agent Ressource : Lister un employé");

        initList(empdt,tableView);
        initList(empdt1,tableView1);
        empdt1.clear();

        tableView.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> { if(newValue != null) { this.selectedId = newValue.getId(); line=newValue;}});
        tableView1.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> { if(newValue != null) { this.selectedId = newValue.getId(); line=newValue;}});

        this.callttList();
    }

    public static void initList(ObservableList<Employ> empdt,TableView<Employ> tableView){

        TableColumn id = new TableColumn("id");
        TableColumn nom = new TableColumn("nom");
        TableColumn prenom = new TableColumn("prenom");
        TableColumn date_naiss = new TableColumn("date_naiss");
        TableColumn droit = new TableColumn("droit");
        TableColumn horr = new TableColumn("horr");
        TableColumn salaire = new TableColumn("salaire");
        TableColumn auto = new TableColumn("auto");
        TableColumn joindate = new TableColumn("joindate");

        tableView.getColumns().addAll(id,nom,prenom,date_naiss,droit,horr,salaire,auto,joindate);


        id.setCellValueFactory(new PropertyValueFactory<Employ,Integer>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<Employ,String>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<Employ,String>("prenom"));
        date_naiss.setCellValueFactory(new PropertyValueFactory<Employ,String>("date_naiss"));
        droit.setCellValueFactory(new PropertyValueFactory<Employ,String>("droit"));
        horr.setCellValueFactory(new PropertyValueFactory<Employ,Integer>("horr"));
        salaire.setCellValueFactory(new PropertyValueFactory<Employ,Integer>("salaire"));
        auto.setCellValueFactory(new PropertyValueFactory<Employ,String>("auto"));
        joindate.setCellValueFactory(new PropertyValueFactory<Employ,String>("joindate"));

        tableView.setItems(empdt);

    }

    public static void ttList(ObservableList<Employ> empdt,TableView<Employ> tableView, Label lbl){

        try {
            Connection connection = getOracleConnection();
            PreparedStatement statement = connection.prepareStatement("select e.id,e.nom,e.prenom,e.date_naiss,e.droit,e.horr,e.salaire,e.auto,e.joindate,c.usern,c.mdp,c.tel,c.email from employ e join compte c on(e.id=c.idemp)");

            //get data from db

            ResultSet rs = statement.executeQuery();
            System.out.println(rs);
            empdt.clear();
            //fetch data

            while (rs.next()) {

                String field = rs.getString("id");
                String field1 = rs.getString("nom");
                String field2 = rs.getString("prenom");
                String field3 = rs.getString("date_naiss");
                String field6 = rs.getString("droit");
                String field7 = rs.getString("horr");
                String field8 = rs.getString("salaire");
                String field9 = rs.getString("auto");
                String field10 = rs.getString("joindate");

                empdt.add(new Employ(Integer.parseInt(field),field1,field2,field3.substring(0,10),field6,Integer.parseInt(field7),Integer.parseInt(field8),field9,field10));
            }
            rs.close();
            connection.close();

        } catch (SQLException e) {
            lbl.setText("Une erreur dans la tente d'affiche");
        }
        tableView.setItems(empdt);
    }

    @FXML
    public static void tList(TextField tf,ObservableList<Employ> empdt,TableView<Employ> tableView, Label lbl){

        try {
            int empid = Integer.parseInt(tf.getText());

            try{
                Connection connection= getOracleConnection();
                PreparedStatement statement = connection.prepareStatement("select e.id,e.nom,e.prenom,e.date_naiss,e.droit,e.horr,e.salaire,e.auto,e.joindate,c.usern,c.mdp,c.tel,c.email from employ e join compte c on(e.id=c.idemp) where id = "+empid);

                empdt.clear();

                //get data from db

                ResultSet rs = statement.executeQuery();
                //fetch data

                while(rs.next()){
                    String field = rs.getString("id");
                    String field1 = rs.getString("nom");
                    String field2 = rs.getString("prenom");
                    String field3 = rs.getString("date_naiss");
                    String field6 = rs.getString("droit");
                    String field7 = rs.getString("horr");
                    String field8 = rs.getString("salaire");
                    String field9 = rs.getString("auto");
                    String field10 = rs.getString("joindate");

                    empdt.add(new Employ(Integer.parseInt(field),field1,field2,field3.substring(0,10),field6,Integer.parseInt(field7),Integer.parseInt(field8),field9,field10));

                }
                rs.close();
                connection.close();

            }
            catch (SQLException e){
                lbl.setText("Une erreur dans la tente d'affiche");
            }

            tableView.setItems(empdt);
        }
        catch (Exception nbe){
            lbl.setText("id doit etre numerique");
        }

    }

    @FXML
    public void callttList(){
        ttList(empdt,tableView,lbl);
    }

    @FXML
    public void callList(){
        tList(tf,empdt,tableView,lbl);
    }

    @FXML
    public void grpBut(){
        if(!tableView1.getItems().isEmpty()) {
            try {
                ControllerNouvGrp.line = empdt1;
                ControllerLog.crtnStg("../samplegrp/samplenouvgrp.fxml", "Chef projet : Creation des equipes");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else lbl.setText("il faut remplir la table à droite");
    }

    @FXML
    public void addBut(){
        empdt1.add(line);
        tableView1.setItems(empdt1);
        empdt.remove(line);
        tableView.setItems(empdt);
    }

    @FXML
    public void suppBut() {
        empdt.add(line);
        tableView.setItems(empdt);
        empdt1.remove(line);
        tableView1.setItems(empdt1);
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
            ControllerLog.crtStg("../samplegrp/samplelistgrp.fxml","Chef projet : Liste des equipes",btret);
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
