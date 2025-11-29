package com.mycompany.mavenproject3.controller;

import com.mycompany.mavenproject3.dao.JabatanDAOImpl;
import com.mycompany.mavenproject3.dao.PegawaiDAOImpl;
import com.mycompany.mavenproject3.model.Jabatan;
import com.mycompany.mavenproject3.model.Pegawai;
import com.mycompany.mavenproject3.service.PayrollService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.YearMonth;
import java.util.List;
import java.util.Locale;

public class PegawaiController {

    @FXML private TableView<Pegawai> table;
    @FXML private TableColumn<Pegawai, Integer> colId;
    @FXML private TableColumn<Pegawai, String> colNip;
    @FXML private TableColumn<Pegawai, String> colNama;
    @FXML private TableColumn<Pegawai, String> colJabatan; 
    
    @FXML private TextField txtNip, txtNama, txtUsername, txtPassword;
    @FXML private ComboBox<String> comboJabatan;
    @FXML private Button btnSave;

    private final PegawaiDAOImpl dao = new PegawaiDAOImpl();
    private final JabatanDAOImpl jabatanDao = new JabatanDAOImpl();
    private final PayrollService payrollService = new PayrollService();
    
    private final ObservableList<Pegawai> data = FXCollections.observableArrayList();
    
    private Integer editId = null; 
    private String tempRoleForEdit = "USER"; 

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNip.setCellValueFactory(new PropertyValueFactory<>("nip"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colJabatan.setCellValueFactory(new PropertyValueFactory<>("namaJabatan"));

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) populateForm(newVal);
        });

        loadJabatanCombo();
        refresh();
    }

    private void populateForm(Pegawai p) {
        editId = p.getId(); 
 
        tempRoleForEdit = p.getRole(); 
        
        txtNip.setText(p.getNip());
        txtNama.setText(p.getNama());
        txtUsername.setText(p.getUsername());
        txtPassword.setText(p.getPassword());
        
        if (p.getJabatanId() != null) {
            for (String item : comboJabatan.getItems()) {
                if (item.startsWith(p.getJabatanId() + " -")) {
                    comboJabatan.setValue(item);
                    break;
                }
            }
        }
        
        btnSave.setText("Update Data");
        btnSave.setStyle("-fx-base: #2196F3; -fx-text-fill: white;");
    }

    private void loadJabatanCombo() {
        try {
            List<Jabatan> listJabatan = jabatanDao.findAll();
            comboJabatan.getItems().clear();
            for (Jabatan j : listJabatan) {
                comboJabatan.getItems().add(j.getId() + " - " + j.getNama());
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void refresh() {
        try {
            List<Pegawai> list = dao.findAll();
            data.setAll(list);
            table.setItems(data);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void save() {
        try {
            if (txtNip.getText().isEmpty() || txtNama.getText().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Peringatan", "NIP dan Nama harus diisi!");
                return;
            }

            Pegawai p = new Pegawai();
            p.setNip(txtNip.getText());
            p.setNama(txtNama.getText());
            p.setUsername(txtUsername.getText());
            p.setPassword(txtPassword.getText());

            String sel = comboJabatan.getValue();
            if (sel != null && sel.contains("-")) {
                p.setJabatanId(Integer.parseInt(sel.split("-")[0].trim()));
            }

            if (editId == null) {
             
                p.setRole("USER"); 
                dao.insert(p);
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data disimpan.");
            } else {
               
                p.setId(editId); 
                p.setRole(tempRoleForEdit); 
                dao.update(p);
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data diupdate.");
            }
            clearForm();
            refresh();
        } catch (Exception e) { 
            e.printStackTrace(); 
            showAlert(Alert.AlertType.ERROR, "Eror", "Gagal menyimpan: " + e.getMessage());
        }
    }

    @FXML
    private void clearForm() {
        editId = null;
        tempRoleForEdit = "USER"; 
        txtNip.clear();
        txtNama.clear();
        txtUsername.clear();
        txtPassword.clear();
        comboJabatan.getSelectionModel().clearSelection();
        table.getSelectionModel().clearSelection(); 
        btnSave.setText("Simpan Baru");
        btnSave.setStyle("-fx-base: #4CAF50; -fx-text-fill: white;");
    }

    @FXML
    private void delete() {
        Pegawai sel = table.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        try { 
            dao.delete(sel.getId()); 
            clearForm(); 
            refresh(); 
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML private void viewGaji() { 
        Pegawai sel = table.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        try {
            BigDecimal gaji = payrollService.hitungGajiBulan(sel.getId(), YearMonth.now());
            NumberFormat kurs = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            showAlert(Alert.AlertType.INFORMATION, "Info Gaji", "Estimasi: " + kurs.format(gaji));
        } catch(Exception e) { e.printStackTrace(); }
    }
    
    @FXML private void exportGaji() {
        Pegawai sel = table.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        try {
            FileChooser fc = new FileChooser();
            fc.setInitialFileName("Slip_" + sel.getNama() + ".csv");
            File f = fc.showSaveDialog(table.getScene().getWindow());
            if (f != null) {
                payrollService.exportPayrollCSV(sel.getId(), YearMonth.now(), f.getAbsolutePath());
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "File tersimpan.");
            }
        } catch(Exception e) { e.printStackTrace(); }
    }
    
    @FXML private void handleBack() {
        try { ((Stage)table.getScene().getWindow()).close(); } catch(Exception e) {}
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}