package com.mycompany.mavenproject3.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Absensi {
    private int id;
    private int pegawaiId;
    private LocalDate tanggal;
    private LocalTime waktuMasuk;
    private LocalTime waktuPulang;
    private String status;
    
    // TAMBAHAN: Variabel untuk menyimpan Nama
    private String namaPegawai; 

    public Absensi() {}
    
    public Absensi(int id, int pegawaiId, LocalDate tanggal, LocalTime waktuMasuk, LocalTime waktuPulang, String status) {
        this.id = id; 
        this.pegawaiId = pegawaiId; 
        this.tanggal = tanggal;
        this.waktuMasuk = waktuMasuk; 
        this.waktuPulang = waktuPulang; 
        this.status = status;
    }

    // Getter & Setter Standar
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPegawaiId() { return pegawaiId; }
    public void setPegawaiId(int pegawaiId) { this.pegawaiId = pegawaiId; }

    public LocalDate getTanggal() { return tanggal; }
    public void setTanggal(LocalDate tanggal) { this.tanggal = tanggal; }

    public LocalTime getWaktuMasuk() { return waktuMasuk; }
    public void setWaktuMasuk(LocalTime waktuMasuk) { this.waktuMasuk = waktuMasuk; }

    public LocalTime getWaktuPulang() { return waktuPulang; }
    public void setWaktuPulang(LocalTime waktuPulang) { this.waktuPulang = waktuPulang; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // TAMBAHAN: Getter & Setter untuk Nama Pegawai
    public String getNamaPegawai() { return namaPegawai; }
    public void setNamaPegawai(String namaPegawai) { this.namaPegawai = namaPegawai; }
}