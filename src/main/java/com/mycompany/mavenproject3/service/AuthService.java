/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.service;
import com.mycompany.mavenproject3.dao.UserDAO;
import com.mycompany.mavenproject3.dao.UserDAOImpl;
import com.mycompany.mavenproject3.model.Pegawai;

public class AuthService {
    private final UserDAO userDAO = new UserDAOImpl();

    public Pegawai login(String username, String password) {
        System.out.println("--- DEBUG LOGIN ---");
        System.out.println("Mencoba login dengan username: " + username);
        
        try {
            Pegawai p = userDAO.findByUsername(username);
            
            if (p == null) {
                System.out.println(" GAGAL: User tidak ditemukan di database!");
                return null;
            }
            
            System.out.println("User ditemukan: " + p.getNama());
            System.out.println("   Password di DB: " + p.getPassword());
            System.out.println("   Password Input: " + password);
            
      
            if (!password.equals(p.getPassword())) {
                System.out.println(" GAGAL: Password tidak cocok!");
                return null; 
            }
            
            System.out.println("🎉 SUKSES: Login berhasil!");
            return p;
            
        } catch (Exception e) {
            System.out.println("❌ ERROR SYSTEM: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
