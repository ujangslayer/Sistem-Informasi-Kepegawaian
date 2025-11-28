/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.dao;



import com.mycompany.mavenproject3.model.Pegawai;

public interface UserDAO {
    Pegawai findByUsername(String username) throws Exception;
}