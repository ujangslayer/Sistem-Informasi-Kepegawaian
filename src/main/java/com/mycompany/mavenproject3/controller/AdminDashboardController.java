package com.mycompany.mavenproject3.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class AdminDashboardController {

    @FXML private void openPegawai() { open("/fxml/pegawai_list.fxml", "Manage Pegawai"); }
    @FXML private void openJabatan() { open("/fxml/jabatan_list.fxml", "Manage Jabatan"); }
    @FXML private void openAbsensi() { open("/fxml/absensi_list.fxml", "Manage Absensi"); }

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