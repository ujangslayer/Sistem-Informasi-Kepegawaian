/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.dao;

import com.mycompany.mavenproject3.model.Jabatan;
import java.util.List;

public interface JabatanDAO {
    void insert(Jabatan j) throws Exception;
    void update(Jabatan j) throws Exception;
    void delete(int id) throws Exception;
    Jabatan findById(int id) throws Exception;
    List<Jabatan> findAll() throws Exception;
}