/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.dao;


import com.mycompany.mavenproject3.model.Pegawai;
import java.util.List;

public interface PegawaiDAO {
    void insert(Pegawai p) throws Exception;
    void update(Pegawai p) throws Exception;
    void delete(int id) throws Exception;
    Pegawai findByNip(String nip) throws Exception;
    Pegawai findById(int id) throws Exception;
    Pegawai findByUsername(String username) throws Exception;
    List<Pegawai> findAll() throws Exception;
}
