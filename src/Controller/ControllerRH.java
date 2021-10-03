package Controller;

import Model.User;
import Services.MainInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ControllerRH implements MainInterface {

    @FXML
    Button btcls,btlistemp,btret,btlistmat,btmin,btinfo;

    @FXML
    Label lbtit;

    public void initialize(){
        lbtit.setText("Agent RH : Centre de gestion");btinfo.setText("( "+User.getUsern()+" )");
    }

    @FXML
    public void listEmpBut(){
        try {
            ControllerLog.crtaStg("../sample/samplelock.fxml", "Privilège");
            if (ControllerLock.sign) {
                try {
                    ControllerLog.crtStg("../sampleemp/samplelistemp.fxml", "Agent RH : Liste des emps", btlistemp);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void listMatBut(){
        try {
            ControllerLog.crtStg("../samplemat/samplelistmat.fxml","Agent RH : Liste des materiels",btlistmat);
        }
        catch (Exception e){
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
            ControllerLog.crtStg("../sample/samplelog.fxml","Login",btret);
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

    @FXML
    public void minBut(){
        Stage stage = (Stage) btmin.getScene().getWindow();
        // do what you have to do
        stage.setIconified(true);
    }
}
