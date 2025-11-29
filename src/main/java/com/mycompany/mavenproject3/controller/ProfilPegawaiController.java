package com.mycompany.mavenproject3.controller;

import com.mycompany.mavenproject3.dao.AbsensiDAOImpl;
import com.mycompany.mavenproject3.dao.JabatanDAOImpl;
import com.mycompany.mavenproject3.model.Absensi;
import com.mycompany.mavenproject3.model.Jabatan;
import com.mycompany.mavenproject3.model.Pegawai;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class ProfilPegawaiController {

    
    @FXML private Label lblNip;
    @FXML private Label lblNama;
    @FXML private Label lblJabatan;
    @FXML private Label lblRole;

  
    @FXML private TableView<Absensi> table;
    @FXML private TableColumn<Absensi, String> colTanggal;
    @FXML private TableColumn<Absensi, String> colMasuk;
    @FXML private TableColumn<Absensi, String> colPulang;
    @FXML private TableColumn<Absensi, String> colStatus;

    private final AbsensiDAOImpl absensiDao = new AbsensiDAOImpl();
    private final JabatanDAOImpl jabatanDao = new JabatanDAOImpl();
    private final ObservableList<Absensi> dataAbsensi = FXCollections.observableArrayList();

    private Pegawai pegawai; 

   
    public void setPegawai(Pegawai p) {
        this.pegawai = p;
        loadDataDiri();
        loadRiwayatAbsensi();
    }

    private void loadDataDiri() {
        if (pegawai != null) {
            lblNip.setText(": " + pegawai.getNip());
            lblNama.setText(": " + pegawai.getNama());
            lblRole.setText(": " + pegawai.getRole());

            
            try {
                if (pegawai.getJabatanId() != null) {
                    Jabatan j = jabatanDao.findById(pegawai.getJabatanId());
                    if (j != null) {
                        lblJabatan.setText(": " + j.getNama());
                    } else {
                        lblJabatan.setText(": -");
                    }
                } else {
                    lblJabatan.setText(": -");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadRiwayatAbsensi() {
        
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        colMasuk.setCellValueFactory(new PropertyValueFactory<>("waktuMasuk"));
        colPulang.setCellValueFactory(new PropertyValueFactory<>("waktuPulang"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

       
        if (pegawai != null) {
            try {
                List<Absensi> list = absensiDao.findByPegawai(pegawai.getId());
                dataAbsensi.setAll(list);
                table.setItems(dataAbsensi);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleBack() {
        try {
            Stage stage = (Stage) lblNama.getScene().getWindow();
            stage.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
}