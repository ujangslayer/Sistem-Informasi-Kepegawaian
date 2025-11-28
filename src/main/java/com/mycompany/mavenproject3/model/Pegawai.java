package com.mycompany.mavenproject3.model;

public class Pegawai extends Person {
    private String nip;
    private Integer jabatanId;
    private String username;
    private String password;
    private String role; 
    private String namaJabatan; // PENTING

    public Pegawai() { super(); }
    public Pegawai(int id, String nip, String nama, Integer jabatanId, String username, String password, String role) {
        super(id, nama);
        this.nip = nip;
        this.jabatanId = jabatanId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getNip() { return nip; }
    public void setNip(String nip) { this.nip = nip; }
    public Integer getJabatanId() { return jabatanId; }
    public void setJabatanId(Integer jabatanId) { this.jabatanId = jabatanId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getNamaJabatan() { return namaJabatan; }
    public void setNamaJabatan(String namaJabatan) { this.namaJabatan = namaJabatan; }
}