/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.dao;

import com.mycompany.mavenproject3.model.Absensi;

import java.time.LocalDate;
import java.util.List;

public interface AbsensiDAO {

    Absensi findById(int id) throws Exception;

    Absensi findByPegawaiAndDate(int pegawaiId, LocalDate tanggal) throws Exception;

    List<Absensi> findByPegawai(int pegawaiId) throws Exception;

    List<Absensi> findAll() throws Exception;

    void insert(Absensi absensi) throws Exception;

    void update(Absensi absensi) throws Exception;

    void delete(int id) throws Exception;
}