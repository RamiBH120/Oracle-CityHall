package ControllerTask;

import Controller.ControllerLock;
import Controller.ControllerLog;
import ControllerProj.ControllerModifProj;
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

public class ControllerListTask implements MainInterface, Gestion {

    @FXML
    TableView<Employ> tableView;

    @FXML
    TextField tf;

    @FXML
    Button btcls,btret,btmin,btinfo;

    @FXML
    Label lbl,lbtit;

    private final ObservableList<Employ> empdt = FXCollections.observableArrayList();

    private int selectedId;

    @FXML
    private void initialize(){

        btinfo.setText("( "+ User.getUsern() +" )");
        lbtit.setText("Agent Municipal : Liste des taches");
        initList(empdt,tableView);

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { if(newValue != null){ ControllerAjoutTask.selectedId = newValue.getId(); this.selectedId=newValue.getId();}});

        this.callttList();
    }

    public static void initList(ObservableList<Employ> empdt,TableView<Employ> tableView){
        TableColumn id = new TableColumn("id");
        TableColumn nom = new TableColumn("nom");
        TableColumn prenom = new TableColumn("prenom");
        TableColumn date_naiss = new TableColumn("date_naissance");
        TableColumn droit = new TableColumn("droit");
        TableColumn horr = new TableColumn("horraire");
        TableColumn salaire = new TableColumn("salaire");
        TableColumn auto = new TableColumn("autorisation");
        TableColumn joindate = new TableColumn("joindate");

        TableColumn nomtsk = new TableColumn("nom_tache");
        TableColumn desktsk = new TableColumn("description_tache");
        TableColumn etattsk = new TableColumn("etat_tache");

        tableView.getColumns().addAll(id,nom,prenom,date_naiss,droit,horr,salaire,auto,joindate,nomtsk,desktsk,etattsk);


        id.setCellValueFactory(new PropertyValueFactory<Employ,Integer>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<Employ,String>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<Employ,String>("prenom"));
        date_naiss.setCellValueFactory(new PropertyValueFactory<Employ,String>("date_naiss"));
        droit.setCellValueFactory(new PropertyValueFactory<Employ,String>("droit"));
        horr.setCellValueFactory(new PropertyValueFactory<Employ,Integer>("horr"));
        salaire.setCellValueFactory(new PropertyValueFactory<Employ,Integer>("salaire"));
        auto.setCellValueFactory(new PropertyValueFactory<Employ,String>("auto"));
        joindate.setCellValueFactory(new PropertyValueFactory<Employ,String>("joindate"));

        nomtsk.setCellValueFactory(new PropertyValueFactory<Employ,String>("nomtsk"));
        desktsk.setCellValueFactory(new PropertyValueFactory<Employ,String>("desktsk"));
        etattsk.setCellValueFactory(new PropertyValueFactory<Employ,String>("etattsk"));

        tableView.setItems(empdt);
    }

    public static void ttList(ObservableList<Employ> empdt,TableView<Employ> tableView, Label lbl){

        try {
            Connection connection = getOracleConnection();
            PreparedStatement statement = connection.prepareStatement("select e.id,e.nom,e.prenom,e.date_naiss,e.droit,e.horr,e.salaire,e.auto,e.joindate,t.nomtsk,t.desktsk,t.etattsk from employ e left join taskp t on(e.idtskp = t.idtskp)");

            //get data from db

            ResultSet rs = statement.executeQuery();


            empdt.clear();
            //fetch data

            while (rs.next()) {

                String field = rs.getString("id");
                String field1 = rs.getString("nom");
                String field2 = rs.getString("prenom");
                String field3 = rs.getString("date_naiss");
                String field4 = rs.getString("droit");
                String field5 = rs.getString("horr");
                String field6 = rs.getString("salaire");
                String field7 = rs.getString("auto");
                String field11 = rs.getString("joindate");
                String field8 = rs.getString("nomtsk");
                String field9 = rs.getString("desktsk");
                String field10 = rs.getString("etattsk");

                empdt.add(new Employ(Integer.parseInt(field),field1,field2,field3.substring(0,10),field4,Integer.parseInt(field5),Integer.parseInt(field6),field7,field11,field8,field9,field10));

            }
            rs.close();
            connection.close();

        } catch (SQLException e) {
            lbl.setText("Une erreur dans la tente d'affiche");
            e.printStackTrace();
        }

        tableView.setItems(empdt);
    }

    public static void tList(TextField tf, ObservableList<Employ> empdt,TableView<Employ> tableView, Label lbl){

        try {
            int empid = Integer.parseInt(tf.getText());

            Connection connection = getOracleConnection();
            PreparedStatement statement = connection.prepareStatement("select e.nom,e.prenom,e.date_naiss,e.droit,e.horr,e.salaire,e.auto,e.joindate,t.nomtsk,t.desktsk,t.etattsk from employ e left join taskp t using(idtskp) where e.id = "+empid);

            //get data from db

            ResultSet rs = statement.executeQuery();


            empdt.clear();
            //fetch data

            while (rs.next()) {

                String field = rs.getString("id");
                String field1 = rs.getString("nom");
                String field2 = rs.getString("prenom");
                String field3 = rs.getString("date_naiss");
                String field4 = rs.getString("droit");
                String field5 = rs.getString("horr");
                String field6 = rs.getString("salaire");
                String field7 = rs.getString("auto");
                String field11 = rs.getString("joindate");
                String field8 = rs.getString("nomtsk");
                String field9 = rs.getString("desktsk");
                String field10 = rs.getString("etattsk");

                empdt.add(new Employ(Integer.parseInt(field),field1,field2,field3.substring(0,10),field4,Integer.parseInt(field5),Integer.parseInt(field6),field7,field11,field8,field9,field10));

            }
            rs.close();
            connection.close();

        } catch (SQLException e) {
            lbl.setText("Une erreur dans la tente d'affiche");
            e.printStackTrace();
        }

        tableView.setItems(empdt);
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
    public void addBut(){
        try {
            ControllerLog.crtnStg("../sampletask/sampleajouttask.fxml", "Agent Municipal : Ajouter une tache");
            if (ControllerAjoutTask.sign) callttList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void modifBut(){
        if(selectedId != 0) {
            try {
                ControllerLog.crtnStg("../sampletask/samplemodiftask.fxml", "Chef Projet : Modifier une tache");
                if(ControllerModifTask.sign) callttList();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        else lbl.setText("first of all select from the list");
    }

    @FXML
    public void suppBut(){

        try {
            int id = selectedId;

            PreparedStatement statement;

            try {
                Connection connection = getOracleConnection();

                statement = connection.prepareStatement("update employ set idtskp = null where id = " + id);

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
            ControllerLog.crtStg("../sample/samplecp.fxml","Chef projet : Centre de gestion",btret);
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