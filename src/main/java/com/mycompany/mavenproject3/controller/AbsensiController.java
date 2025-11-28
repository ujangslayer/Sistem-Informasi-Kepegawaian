package com.mycompany.mavenproject3.controller;

import com.mycompany.mavenproject3.dao.PegawaiDAOImpl;
import com.mycompany.mavenproject3.model.Absensi;
import com.mycompany.mavenproject3.model.Pegawai;
import com.mycompany.mavenproject3.service.AbsensiService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class AbsensiController {

    // ADMIN VIEW - TABEL
    @FXML private TableView<Absensi> table;
    
    // KITA UBAH NAMA VARIABELNYA BIAR JELAS
    @FXML private TableColumn<Absensi, String> colNamaPegawai; 
    
    @FXML private TableColumn<Absensi, String> colTanggal;
    @FXML private TableColumn<Absensi, String> colMasuk;
    @FXML private TableColumn<Absensi, String> colPulang;
    @FXML private TableColumn<Absensi, String> colStatus;

    // ADMIN VIEW - INPUT
    @FXML private ComboBox<String> comboPegawai;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> comboStatus;

    // USER VIEW - INPUT
    @FXML private TextField txtNip;

    private final AbsensiService service = new AbsensiService();
    private final PegawaiDAOImpl pegDao = new PegawaiDAOImpl();
    private final ObservableList<Absensi> data = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        if (table != null) {
          
            
            // MAPPING YANG BENAR:
            // Variabel FXML "colNamaPegawai" diisi data dari Model "namaPegawai"
            colNamaPegawai.setCellValueFactory(new PropertyValueFactory<>("namaPegawai"));
            
            colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
            colMasuk.setCellValueFactory(new PropertyValueFactory<>("waktuMasuk"));
            colPulang.setCellValueFactory(new PropertyValueFactory<>("waktuPulang"));
            colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            
            loadDataTable();
        }
        
        if (comboStatus != null) comboStatus.getItems().addAll("HADIR", "IJIN", "SAKIT", "ALPHA");
        if (comboPegawai != null) loadPegawaiCombo();
    }

    @FXML
    private void handleBack() {
        try {
            Stage stage = null;
            if (table != null) stage = (Stage) table.getScene().getWindow();
            else if (txtNip != null) stage = (Stage) txtNip.getScene().getWindow();
            if (stage != null) stage.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    // --- LOGIC CLOCK IN/OUT ---
    @FXML private void clockIn() { processAbsensi(true); }
    @FXML private void clockOut() { processAbsensi(false); }

    private void processAbsensi(boolean isMasuk) {
        try {
            if (txtNip == null) { showAlert("Error UI: Kolom NIP tidak ditemukan."); return; }
            String nip = txtNip.getText().trim();
            if (nip.isEmpty()) { showAlert("Masukkan NIP Anda!"); return; }

            Pegawai p = pegDao.findByNip(nip);
            if (p == null) {
                showAlert("NIP tidak ditemukan!");
                return;
            }

            if (isMasuk) service.clockIn(p.getId());
            else service.clockOut(p.getId());

            showAlert("Berhasil Absen! Halo, " + p.getNama());
            txtNip.clear();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Gagal: " + e.getMessage());
        }
    }

    // --- LOGIC ADMIN ---
    private void loadPegawaiCombo() {
        try {
            List<Pegawai> list = pegDao.findAll();
            comboPegawai.getItems().clear();
            for (Pegawai p : list) comboPegawai.getItems().add(p.getId() + " - " + p.getNama());
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void loadDataTable() {
        try {
            data.setAll(service.getAll());
            table.setItems(data);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void save() {
        try {
            String sel = comboPegawai.getValue();
            if(sel == null) return;
            int pegId = Integer.parseInt(sel.split("-")[0].trim());
            
            Absensi a = new Absensi();
            a.setPegawaiId(pegId);
            a.setTanggal(datePicker.getValue());
            a.setStatus(comboStatus.getValue());
            service.saveManual(a);
            loadDataTable();
            showAlert("Tersimpan.");
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(msg);
        a.show();
    }
}