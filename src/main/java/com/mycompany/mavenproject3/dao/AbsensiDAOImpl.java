package com.mycompany.mavenproject3.dao;

import com.mycompany.mavenproject3.model.Absensi;
import com.mycompany.mavenproject3.util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AbsensiDAOImpl implements AbsensiDAO {

    private Absensi map(ResultSet rs) throws Exception {
        Date dateSql = rs.getDate("tanggal");
        LocalDate localDate = (dateSql != null) ? dateSql.toLocalDate() : null;

        Time timeInSql = rs.getTime("waktu_masuk");
        java.time.LocalTime localTimeIn = (timeInSql != null) ? timeInSql.toLocalTime() : null;

        Time timeOutSql = rs.getTime("waktu_pulang");
        java.time.LocalTime localTimeOut = (timeOutSql != null) ? timeOutSql.toLocalTime() : null;

        Absensi a = new Absensi(
                rs.getInt("id"),
                rs.getInt("pegawai_id"),
                localDate,
                localTimeIn,
                localTimeOut,
                rs.getString("status")
        );

      
        try {
            a.setNamaPegawai(rs.getString("nama_pegawai"));
        } catch (SQLException e) {
        }
        
        return a;
    }

    @Override
    public Absensi findById(int id) throws Exception {
        String sql = "SELECT * FROM absensi WHERE id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    @Override
    public Absensi findByPegawaiAndDate(int pegawaiId, LocalDate tanggal) throws Exception {
        String sql = "SELECT * FROM absensi WHERE pegawai_id=? AND tanggal=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, pegawaiId);
            ps.setDate(2, Date.valueOf(tanggal));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    @Override
    public List<Absensi> findByPegawai(int pegawaiId) throws Exception {
        List<Absensi> list = new ArrayList<>();
        String sql = "SELECT * FROM absensi WHERE pegawai_id=? ORDER BY tanggal DESC";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, pegawaiId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    @Override
    public List<Absensi> findAll() throws Exception {
        List<Absensi> list = new ArrayList<>();
        String sql = "SELECT a.*, p.nama AS nama_pegawai " +
                     "FROM absensi a " +
                     "JOIN pegawai p ON a.pegawai_id = p.id " +
                     "ORDER BY a.tanggal DESC";
                     
        try (Connection c = DBUtil.getConnection(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    @Override
    public void insert(Absensi a) throws Exception {
        String sql = "INSERT INTO absensi (pegawai_id, tanggal, waktu_masuk, waktu_pulang, status) VALUES (?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, a.getPegawaiId());
            ps.setDate(2, Date.valueOf(a.getTanggal()));
            if (a.getWaktuMasuk() != null) ps.setTime(3, Time.valueOf(a.getWaktuMasuk())); else ps.setNull(3, Types.TIME);
            if (a.getWaktuPulang() != null) ps.setTime(4, Time.valueOf(a.getWaktuPulang())); else ps.setNull(4, Types.TIME);
            ps.setString(5, a.getStatus());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Absensi a) throws Exception {
        String sql = "UPDATE absensi SET waktu_masuk=?, waktu_pulang=?, status=? WHERE id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            if (a.getWaktuMasuk() != null) ps.setTime(1, Time.valueOf(a.getWaktuMasuk())); else ps.setNull(1, Types.TIME);
            if (a.getWaktuPulang() != null) ps.setTime(2, Time.valueOf(a.getWaktuPulang())); else ps.setNull(2, Types.TIME);
            ps.setString(3, a.getStatus());
            ps.setInt(4, a.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM absensi WHERE id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}