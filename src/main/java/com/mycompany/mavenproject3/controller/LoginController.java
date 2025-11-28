package com.mycompany.mavenproject3.controller;

import com.mycompany.mavenproject3.model.Pegawai;
import com.mycompany.mavenproject3.service.AuthService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblMsg;

    private final AuthService authService = new AuthService();

    @FXML
    public void initialize() {}

    @FXML
    private void onLogin() {
        lblMsg.setText(""); 
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            lblMsg.setText("Username & password harus diisi");
            return;
        }

        Pegawai peg = authService.login(username, password);
        
        if (peg == null) {
            lblMsg.setText("Username atau password salah");
            return;
        }

        try {
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            FXMLLoader loader;
            Parent root;
            
            if ("ADMIN".equalsIgnoreCase(peg.getRole())) {
                loader = new FXMLLoader(getClass().getResource("/fxml/admin_dashboard.fxml"));
                root = loader.load();
            } else {
                loader = new FXMLLoader(getClass().getResource("/fxml/user_dashboard.fxml"));
                root = loader.load();
                UserDashboardController userController = loader.getController();
                userController.setUserData(peg); 
            }


            Scene scene = new Scene(root, 1000, 700);
            
            stage.setScene(scene);
            stage.setTitle("Dashboard - " + peg.getNama());
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            lblMsg.setText("Gagal memuat dashboard.");
        }
    }
}