package ControllerEmp;

import Controller.ControllerAlert;
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

import java.sql.*;

import static DataBaseConnection.OracleConnection.getOracleConnection;

public class ControllerListEmp implements Gestion, MainInterface {

    @FXML
    TableView<Employ> tableView;

    @FXML
    TextField tf;

    @FXML
    Button btcls,btret,btmin,btinfo,btadd,btauto,btsupp,btmodif;

    @FXML
    Label lbl,lbtit;

    private final ObservableList<Employ> empdt = FXCollections.observableArrayList();

    private int selectedId;

    @FXML
    public void initialize(){

        btinfo.setText("( "+User.getUsern()+" )");
        lbtit.setText("Agent Ressource : Lister un employé");
        System.out.println(User.getDro());

        initList(empdt,tableView);

        if(!User.getDro().equals("President") && !User.getDro().equals("Agent Ressource")) initBut(false);

        tableView.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> { if(newValue != null) {ControllerModifEmp.selectedId = newValue.getId(); ControllerAutoEmp.selectedId = newValue.getId(); this.selectedId = newValue.getId();}});


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

        TableColumn usern = new TableColumn("user_name");
        TableColumn mdp = new TableColumn("password");
        TableColumn tel = new TableColumn("telephone");
        TableColumn email = new TableColumn("email");

        tableView.getColumns().addAll(id,nom,prenom,date_naiss,droit,horr,salaire,auto,joindate,usern,mdp,tel,email);


        id.setCellValueFactory(new PropertyValueFactory<Employ,Integer>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<Employ,String>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<Employ,String>("prenom"));
        date_naiss.setCellValueFactory(new PropertyValueFactory<Employ,String>("date_naiss"));
        droit.setCellValueFactory(new PropertyValueFactory<Employ,String>("droit"));
        horr.setCellValueFactory(new PropertyValueFactory<Employ,Integer>("horr"));
        salaire.setCellValueFactory(new PropertyValueFactory<Employ,Integer>("salaire"));
        auto.setCellValueFactory(new PropertyValueFactory<Employ,String>("auto"));
        joindate.setCellValueFactory(new PropertyValueFactory<Employ,String>("joindate"));
        usern.setCellValueFactory(new PropertyValueFactory<Employ,String>("usern"));
        mdp.setCellValueFactory(new PropertyValueFactory<Employ,String>("mdp"));
        tel.setCellValueFactory(new PropertyValueFactory<Employ,Integer>("tel"));
        email.setCellValueFactory(new PropertyValueFactory<Employ,String>("email"));

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
                String field14 = rs.getString("joindate");
                String field10 = rs.getString("usern");
                String field11 = rs.getString("mdp");
                String field12 = rs.getString("tel");
                String field13 = rs.getString("email");

                empdt.add(new Employ(Integer.parseInt(field),field1,field2,field3.substring(0,10),field6,Integer.parseInt(field7),Integer.parseInt(field8),field9,field14,field10,field11,Integer.parseInt(field12),field13));
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
                    String field14 = rs.getString("joindate");
                    String field10 = rs.getString("usern");
                    String field11 = rs.getString("mdp");
                    String field12 = rs.getString("tel");
                    String field13 = rs.getString("email");

                    empdt.add(new Employ(Integer.parseInt(field),field1,field2,field3.substring(0,10),field6,Integer.parseInt(field7),Integer.parseInt(field8),field9,field14,field10,field11,Integer.parseInt(field12),field13));

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
    private void initBut(boolean choice){
        btadd.setVisible(choice);
        btauto.setVisible(choice);
        btmodif.setVisible(choice);
        btsupp.setVisible(choice);
    }

    @FXML
    public void addBut(){
        try {
            ControllerLog.crtnStg("../sampleemp/sampleajoutemp.fxml","Agent RH : Ajout un employé");
        }
        catch (Exception e){
            e.printStackTrace();

        }
    }

    @FXML
    public void autoBut(){
        if(selectedId != 0) {
            try {
                ControllerLog.crtnStg("../sampleemp/sampleautoemp.fxml", "Agent RH : Autoriser un employé");
                if(ControllerModifEmp.sign) callttList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else lbl.setText("first of all select from the list");
    }


    @FXML
    public void modifBut(){
        if(selectedId != 0) {
            try {
                ControllerLog.crtnStg("../sampleemp/samplemodifemp.fxml", "Agent RH : Modifier un employé");
                if(ControllerModifEmp.sign) callttList();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        else lbl.setText("first of all select from the list");
    }

    @FXML
    public void suppBut() {
        try {
            ControllerLog.crtaStg("../sample/samplealert.fxml", "Suppression");
            if (ControllerAlert.sign) {

                try {
                    int id = selectedId;
                    if (User.getId() != id) {

                        PreparedStatement statement;

                        try {
                            Connection connection = getOracleConnection();

                            statement = connection.prepareStatement("delete from employ where id = " + id);

                            statement.executeUpdate();

                            if (statement.getUpdateCount() == 1) {
                                lbl.setText("Supprimé!");
                                this.callttList();
                            } else lbl.setText("Non trouvé");
                            connection.close();

                        } catch (SQLException e) {
                            System.out.println("1000000 dawa7");
                            lbl.setText("error retrieving id");
                        }
                    } else lbl.setText("C'est pas logique de supprimer vous même!");
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
