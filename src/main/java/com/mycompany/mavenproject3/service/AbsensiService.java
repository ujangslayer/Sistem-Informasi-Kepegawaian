package com.mycompany.mavenproject3.service;

import com.mycompany.mavenproject3.dao.AbsensiDAO;
import com.mycompany.mavenproject3.dao.AbsensiDAOImpl;
import com.mycompany.mavenproject3.model.Absensi;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AbsensiService {

    private final AbsensiDAO absensiDAO = new AbsensiDAOImpl();

    public boolean sudahAbsenHariIni(int pegawaiId) {
        try {
            return absensiDAO.findByPegawaiAndDate(pegawaiId, LocalDate.now()) != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void saveManual(Absensi a) throws Exception {
        Absensi existing = absensiDAO.findByPegawaiAndDate(a.getPegawaiId(), a.getTanggal());

        if (existing == null) {
            absensiDAO.insert(a);
        } else {
            existing.setStatus(a.getStatus());
            existing.setWaktuMasuk(a.getWaktuMasuk());
            existing.setWaktuPulang(a.getWaktuPulang());
            absensiDAO.update(existing);
        }
    }

    public void clockIn(int pegawaiId) throws Exception {
        Absensi a = absensiDAO.findByPegawaiAndDate(pegawaiId, LocalDate.now());

        if (a == null) {
            a = new Absensi(0, pegawaiId, LocalDate.now(), LocalTime.now(), null, "HADIR");
            absensiDAO.insert(a);
        } else {
            a.setWaktuMasuk(LocalTime.now());
            a.setStatus("HADIR");
            absensiDAO.update(a);
        }
    }

    public void clockOut(int pegawaiId) throws Exception {
        Absensi a = absensiDAO.findByPegawaiAndDate(pegawaiId, LocalDate.now());

        if (a == null) {
            a = new Absensi(0, pegawaiId, LocalDate.now(), null, LocalTime.now(), "HADIR");
            absensiDAO.insert(a);
        } else {
            a.setWaktuPulang(LocalTime.now());
            absensiDAO.update(a);
        }
    }

    public List<Absensi> getAll() {
        try {
            return absensiDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<Absensi> getByPegawai(int pegawaiId) {
        try {
            return absensiDAO.findByPegawai(pegawaiId);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
