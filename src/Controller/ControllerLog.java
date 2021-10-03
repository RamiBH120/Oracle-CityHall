package Controller;

import DAO.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

public class ControllerLog {

    @FXML
    ComboBox<String> combo;

    @FXML
    Button btcls,btlog,btmin;

    @FXML
    Label lbuse,lbpass,lblog,lbtit;

    @FXML
    TextField fluse;

    @FXML
    PasswordField flpass;

    @FXML
    public void initialize(){
        combo.getItems().removeAll(combo.getItems());
        combo.getItems().addAll("President","Employe","Agent Ressource","Chef Projet","Agent Municipal","Agent Financier");
        lbtit.setText("Municipàl : oued lil mànbàà él àsàtiiir!");


    }
    @FXML
    public static void crtStg(String res, String title, Button btn) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(ControllerLog.class.getResource(res));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.getIcons().add(new Image("/img/M.png"));
        Scene sc = new Scene(root,1224,685);
        sc.getStylesheets().add("css/black.css");
        stage.setTitle(title);
        stage.setScene(sc);
    }

    @FXML
    public static void crtnStg(String res, String title) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(ControllerLog.class.getResource(res));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("/img/M.png"));
        Scene sc = new Scene(root,824,685);
        sc.getStylesheets().add("css/black.css");
        stage.setTitle(title);
        stage.setScene(sc);
        stage.showAndWait();
    }

    @FXML
    public static void crtaStg(String res, String title) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(ControllerLog.class.getResource(res));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("/img/M.png"));
        Scene sc = new Scene(root,824,412);
        sc.getStylesheets().add("css/black.css");
        stage.setTitle(title);
        stage.setScene(sc);
        stage.showAndWait();
    }

    @FXML
    private void handleKeyPressed(KeyEvent ke){
        if(String.valueOf(ke.getCode()).equals("ENTER")) this.logBut();
    }

    @FXML
    public void logBut(){
        UserDAO userDAO = new UserDAO();

        String log = userDAO.userLog(fluse.getText(),flpass.getText());

        if(log.equals("Agent Ressource")) {
            try {
                crtStg("../sample/samplerh.fxml","Agent RH : Centre de gestion",btlog);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else if(log.equals("President")){
            try {
                crtStg("../sample/sampleadmin.fxml","President : Centre de gestion",btlog);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else if(log.equals("Agent Municipal")){
            try {
                crtStg("../sample/samplegm.fxml","Agent municipal : Centre de gestion",btlog);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else if(log.equals("Chef Projet")){
            try {
                crtStg("../sample/samplecp.fxml","chef proj : Centre de gestion",btlog);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else if(log.equals("Agent Financier")){
            try {
                crtStg("../sample/samplegf.fxml","Agent Financier : Centre de gestion",btlog);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else if(log.equals("Employe")){
            try {
                crtStg("../sample/samplee.fxml","Employé : Centre d'information'",btlog);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else {
            lblog.setText("Incomplete login info or wrong entry");
        }
    }

    @FXML
    public void clsBut(){
        Stage stage = (Stage) btcls.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    @FXML
    public void minBut(){
        Stage stage = (Stage) btmin.getScene().getWindow();
        // do what you have to do
        stage.setIconified(true);
    }
}
