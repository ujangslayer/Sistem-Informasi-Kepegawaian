package com.mycompany.mavenproject3.dao;

import com.mycompany.mavenproject3.model.Pegawai;
import com.mycompany.mavenproject3.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PegawaiDAOImpl implements PegawaiDAO {

    private Pegawai mapRow(ResultSet rs) throws SQLException {
        Pegawai p = new Pegawai(
                rs.getInt("id"),
                rs.getString("nip"),
                rs.getString("nama"),
                rs.getObject("jabatan_id") != null ? rs.getInt("jabatan_id") : null,
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("role")
        );
        try {
            String jb = rs.getString("nama_jabatan");
            p.setNamaJabatan(jb != null ? jb : "-");
        } catch (SQLException e) {}
        return p;
    }

    @Override
    public List<Pegawai> findAll() throws Exception {
        List<Pegawai> list = new ArrayList<>();
        String sql = "SELECT p.*, j.nama AS nama_jabatan FROM pegawai p LEFT JOIN jabatan j ON p.jabatan_id = j.id ORDER BY p.id ASC";
        try (Connection c = DBUtil.getConnection(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    @Override
    public void insert(Pegawai p) throws Exception {
        String sql = "INSERT INTO pegawai (nip,nama,jabatan_id,username,password,role) VALUES (?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNip());
            ps.setString(2, p.getNama());
            if (p.getJabatanId() == null) ps.setNull(3, Types.INTEGER); else ps.setInt(3, p.getJabatanId());
            ps.setString(4, p.getUsername());
            ps.setString(5, p.getPassword());
            ps.setString(6, p.getRole());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Pegawai p) throws Exception {
        String sql = "UPDATE pegawai SET nip=?, nama=?, jabatan_id=?, username=?, password=?, role=? WHERE id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNip());
            ps.setString(2, p.getNama());
            if (p.getJabatanId() == null) ps.setNull(3, Types.INTEGER); else ps.setInt(3, p.getJabatanId());
            ps.setString(4, p.getUsername());
            ps.setString(5, p.getPassword());
            ps.setString(6, p.getRole()); // Inilah yg diupdate
            ps.setInt(7, p.getId());
            ps.executeUpdate();
        }
    }

    @Override public void delete(int id) throws Exception {
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement("DELETE FROM pegawai WHERE id=?")) {
            ps.setInt(1, id); ps.executeUpdate();
        }
    }

    @Override public Pegawai findByNip(String nip) throws Exception {
        String sql = "SELECT p.*, j.nama AS nama_jabatan FROM pegawai p LEFT JOIN jabatan j ON p.jabatan_id = j.id WHERE p.nip=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nip);
            try(ResultSet rs = ps.executeQuery()){ if(rs.next()) return mapRow(rs); }
        } return null;
    }

    @Override public Pegawai findById(int id) throws Exception {
        String sql = "SELECT * FROM pegawai WHERE id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){ if(rs.next()) return mapRow(rs); }
        } return null;
    }

    @Override public Pegawai findByUsername(String username) throws Exception {
        String sql = "SELECT p.*, j.nama AS nama_jabatan FROM pegawai p LEFT JOIN jabatan j ON p.jabatan_id = j.id WHERE p.username=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try(ResultSet rs = ps.executeQuery()){ if(rs.next()) return mapRow(rs); }
        } return null;
    }
}