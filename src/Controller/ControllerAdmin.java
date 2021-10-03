package Controller;

import Model.User;
import Services.MainInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ControllerAdmin implements MainInterface {

    @FXML
    Label lbtit;

    @FXML
    Button btcls,btret,btmin,btinfo,btlistev,btlistproj,btafftask,btlistemp,btlistmat,btlistdem,btorggrp,btajoutdep,btlistinterv;

    @FXML
    public void initialize(){
        btinfo.setText("( "+User.getUsern()+" )");
        lbtit.setText("President de la municipàlité");
    }

    @FXML
    public void listEvBut(){
        try {
            ControllerLog.crtStg("../sampleev/samplelistev.fxml","Chef proj : Liste des ev",btlistev);
        }
        catch (Exception e){
            e.printStackTrace();

        }
    }

    @FXML
    public void listProjBut(){
        try {
            ControllerLog.crtStg("../sampleproj/samplelistproj.fxml","Chef proj : Liste des projets",btlistproj);
        }
        catch (Exception e){
            e.printStackTrace();

        }
    }

    @FXML
    public void affTaskBut(){
        try {
            ControllerLog.crtStg("../sampletask/samplelisttask.fxml","Chef proj : Affecter une tache",btafftask);
        }
        catch (Exception e){
            e.printStackTrace();

        }
    }

    @FXML
    public void listEmpBut(){
        try {
            ControllerLog.crtStg("../sampleemp/samplelistemp.fxml","Agent RH : Liste des emps",btlistemp);
        }
        catch (Exception e){
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
    public void listDemBut(){
        try {
            ControllerLog.crtStg("../sampledem/samplelistdem.fxml","Agent Municipal : Liste des dems",btlistdem);
        }
        catch (Exception e){
            e.printStackTrace();

        }
    }

    @FXML
    public void orgGroupBut(){
        try {
            ControllerLog.crtStg("../samplegrp/samplelistgrp.fxml","Chef proj : Organiser des groups",btorggrp);
        }
        catch (Exception e){
            e.printStackTrace();

        }
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
    public void listIntervBut(){
        try {
            ControllerLog.crtStg("../sampleinterv/samplelistinterv.fxml","President : Lister interventions",btlistinterv);
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
    public void clsBut(){
        Stage stage = (Stage) btcls.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    @FXML
    public void retBut(){
        try {
            ControllerLog.crtStg("../sample/samplegm.fxml","Agent Municipal : Centre de gestion",btret);
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
