/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.service;

import com.mycompany.mavenproject3.dao.AbsensiDAO;
import com.mycompany.mavenproject3.dao.JabatanDAO;
import com.mycompany.mavenproject3.dao.PegawaiDAO;
import com.mycompany.mavenproject3.dao.AbsensiDAOImpl;
import com.mycompany.mavenproject3.dao.JabatanDAOImpl;
import com.mycompany.mavenproject3.dao.PegawaiDAOImpl;
import com.mycompany.mavenproject3.model.Absensi;
import com.mycompany.mavenproject3.model.Jabatan;
import com.mycompany.mavenproject3.model.Pegawai;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

public class PayrollService {
    private final PegawaiDAO pegawaiDAO = new PegawaiDAOImpl();
    private final JabatanDAO jabatanDAO = new JabatanDAOImpl();
    private final AbsensiDAO absensiDAO = new AbsensiDAOImpl();

    private final int HARI_KERJA = 22; // asumsi

    public BigDecimal hitungGajiBulan(int pegawaiId, YearMonth month) throws Exception {
        Pegawai p = pegawaiDAO.findById(pegawaiId);
        if (p == null) return BigDecimal.ZERO;
        Jabatan j = jabatanDAO.findById(p.getJabatanId());
        if (j == null) return BigDecimal.ZERO;
        BigDecimal gajiPokok = j.getGajiPokok();

        List<Absensi> all = absensiDAO.findByPegawai(pegawaiId);
        long alpha = all.stream()
                .filter(a -> a.getTanggal().getMonth().equals(month.getMonth()) && a.getTanggal().getYear() == month.getYear())
                .filter(a -> "ALPHA".equalsIgnoreCase(a.getStatus()))
                .count();

        BigDecimal potonganPerHari = gajiPokok.divide(BigDecimal.valueOf(HARI_KERJA), BigDecimal.ROUND_HALF_UP);
        BigDecimal potonganTotal = potonganPerHari.multiply(BigDecimal.valueOf(alpha));
        return gajiPokok.subtract(potonganTotal);
    }

    public void exportPayrollCSV(int pegawaiId, YearMonth month, String filepath) throws Exception {
        BigDecimal gaji = hitungGajiBulan(pegawaiId, month);
        Pegawai p = pegawaiDAO.findById(pegawaiId);
        Jabatan j = jabatanDAO.findById(p.getJabatanId());

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))) {
            bw.write("NIP,Nama,Jabatan,GajiPokok,GajiBersih");
            bw.newLine();
            bw.write(String.join(",", p.getNip(), p.getNama(), j.getNama(), j.getGajiPokok().toString(), gaji.toString()));
        }
    }
}
