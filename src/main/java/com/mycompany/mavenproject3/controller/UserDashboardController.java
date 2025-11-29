package com.mycompany.mavenproject3.controller;

import com.mycompany.mavenproject3.model.Pegawai;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class UserDashboardController {

    private Pegawai currentUser; 

    public void setUserData(Pegawai p) {
        this.currentUser = p;
    }

    @FXML
    private void openAbsensiForm() {
        open("/fxml/absensi_form.fxml", "Isi Absensi");
    }

    @FXML
    private void openProfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/profil_pegawai.fxml"));
            Parent root = loader.load();
            
            ProfilPegawaiController controller = loader.getController();
            controller.setPegawai(this.currentUser); 
            
            Stage st = new Stage();
            
          
            st.setScene(new Scene(root, 1000, 700));
            
            st.setTitle("Profil Saya");
            st.centerOnScreen();
            st.show();
        } catch (IOException e) { e.printStackTrace(); }
    }

    @FXML
    private void logout(ActionEvent event) {
        try {
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            
         
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root, 1000, 700));
            
            loginStage.setTitle("Sistem Informasi Kepegawaian - Login");
            loginStage.centerOnScreen();
            loginStage.show();
        } catch (IOException e) { e.printStackTrace(); }
    }
    
    private void open(String path, String title) {
        try {
            Stage st = new Stage();
            Parent r = FXMLLoader.load(getClass().getResource(path));
            
            st.setScene(new Scene(r, 1000, 700));
            
            st.setTitle(title);
            st.centerOnScreen();
            st.show();
        } catch (IOException e) { e.printStackTrace(); }
    }
}