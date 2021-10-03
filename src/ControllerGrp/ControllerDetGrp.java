package ControllerGrp;

import Model.Employ;
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

public class ControllerDetGrp {
    @FXML
    TableView<Employ> tableView;

    @FXML
    Button btcls,btmin;

    @FXML
    Label lbl,lbtit;

    private final ObservableList<Employ> empdt = FXCollections.observableArrayList();

    static int selectedId;

    public static boolean sign=false;

    @FXML
    public void initialize(){
        lbtit.setText("Chef projet : Lister des equipes");

        this.callttList();
    }

    public static void initList(ObservableList<Employ> empdt, TableView<Employ> tableView){

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
            PreparedStatement statement = connection.prepareStatement("select * from employ where idequipe = "+selectedId);

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
    public void callttList(){
        ttList(empdt,tableView,lbl);
    }

    @FXML
    public void clsBut() {
        sign=true;
        Stage stage = (Stage) btcls.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    public void minBut() {
        Stage stage = (Stage) btmin.getScene().getWindow();
        // do what you have to do
        stage.setIconified(true);
    }
}
