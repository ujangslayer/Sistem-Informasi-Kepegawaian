/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.dao;

import com.mycompany.mavenproject3.dao.JabatanDAO;
import com.mycompany.mavenproject3.model.Jabatan;
import com.mycompany.mavenproject3.util.DBUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JabatanDAOImpl implements JabatanDAO {
    @Override
    public void insert(Jabatan j) throws Exception {
        String sql = "INSERT INTO jabatan (nama,gaji_pokok) VALUES (?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, j.getNama());
            ps.setBigDecimal(2, j.getGajiPokok());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Jabatan j) throws Exception {
        String sql = "UPDATE jabatan SET nama=?,gaji_pokok=? WHERE id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, j.getNama());
            ps.setBigDecimal(2, j.getGajiPokok());
            ps.setInt(3, j.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM jabatan WHERE id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Jabatan findById(int id) throws Exception {
        String sql = "SELECT * FROM jabatan WHERE id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Jabatan(rs.getInt("id"), rs.getString("nama"), rs.getBigDecimal("gaji_pokok"));
            }
        }
        return null;
    }

    @Override
    public List<Jabatan> findAll() throws Exception {
        List<Jabatan> list = new ArrayList<>();
        String sql = "SELECT * FROM jabatan";
        try (Connection c = DBUtil.getConnection(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(new Jabatan(rs.getInt("id"), rs.getString("nama"), rs.getBigDecimal("gaji_pokok")));
        }
        return list;
    }
}