package Controller;

import Model.User;
import Services.MainInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ControllerGF implements MainInterface {

    @FXML
    Button btinfo,btret,btmin,btcls,btajoutdep;

    @FXML
    Label lbtit;

    public void initialize(){

        btinfo.setText("( "+ User.getUsern() +" )");
        lbtit.setText("Agent Financier");btinfo.setText(User.getUsern());
    }

    @FXML
    public void ajoutDepBut(){
        try {
            ControllerLog.crtStg("../sampledeprev/samplelistdeprev.fxml","Agent Financier : Ajout un depense / revenu",btajoutdep);
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
}
