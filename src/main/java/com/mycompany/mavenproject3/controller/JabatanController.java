/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.controller;

import com.mycompany.mavenproject3.dao.JabatanDAOImpl;
import com.mycompany.mavenproject3.model.Jabatan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage; 

import java.math.BigDecimal;
import java.util.List;

public class JabatanController {

    @FXML private TableView<Jabatan> table;
    
  
    @FXML private TableColumn<Jabatan, Integer> colId;
    @FXML private TableColumn<Jabatan, String> colNama;
    @FXML private TableColumn<Jabatan, BigDecimal> colGaji;
    
    @FXML private TextField txtNama, txtGaji;

    private final JabatanDAOImpl dao = new JabatanDAOImpl();
    private final ObservableList<Jabatan> data = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        if(colId != null) colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        if(colNama != null) colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        if(colGaji != null) colGaji.setCellValueFactory(new PropertyValueFactory<>("gajiPokok"));

        load();
    }

    private void load() {
        try {
            List<Jabatan> l = dao.findAll();
            data.setAll(l);
            table.setItems(data);
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
    }

    @FXML
    private void save() {
        try {
            if (txtNama.getText().isEmpty() || txtGaji.getText().isEmpty()) {
                showAlert("Nama dan Gaji harus diisi!");
                return;
            }

            Jabatan j = new Jabatan();
            j.setNama(txtNama.getText());
            // Konversi String ke BigDecimal
            j.setGajiPokok(new BigDecimal(txtGaji.getText()));
            
            dao.insert(j);
            load();
            clearForm();
            showAlert("Jabatan berhasil disimpan.");
        } catch (Exception e) { 
            e.printStackTrace(); 
            showAlert("Gagal menyimpan: " + e.getMessage());
        }
    }

    @FXML
    private void delete() {
        Jabatan sel = table.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        try { 
            dao.delete(sel.getId()); 
            load(); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
    }

    // --- LOGIC TOMBOL BACK ---
    @FXML
    private void handleBack() {
        // Menutup jendela saat ini
        try {
            Stage stage = (Stage) txtNama.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearForm() {
        txtNama.clear();
        txtGaji.clear();
    }

    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(msg);
        a.show();
    }
}
