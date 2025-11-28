/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.dao;

import com.mycompany.mavenproject3.dao.UserDAO;
import com.mycompany.mavenproject3.model.Pegawai;
import com.mycompany.mavenproject3.util.DBUtil;

import java.sql.*;

public class UserDAOImpl implements UserDAO {
    @Override
    public Pegawai findByUsername(String username) throws Exception {
        String sql = "SELECT * FROM pegawai WHERE username = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Pegawai(
                            rs.getInt("id"),
                            rs.getString("nip"),
                            rs.getString("nama"),
                            rs.getObject("jabatan_id")!=null ? rs.getInt("jabatan_id") : null,
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role")
                    );
                }
            }
        }
        return null;
    }
}