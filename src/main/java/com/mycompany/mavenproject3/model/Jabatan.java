/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.model;

/**
 *
 * @author asus
 */
import java.math.BigDecimal;

public class Jabatan {
    private int id;
    private String nama;
    private BigDecimal gajiPokok;

    public Jabatan() {}
    public Jabatan(int id, String nama, BigDecimal gajiPokok) {
        this.id = id; this.nama = nama; this.gajiPokok = gajiPokok;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public BigDecimal getGajiPokok() { return gajiPokok; }
    public void setGajiPokok(BigDecimal gajiPokok) { this.gajiPokok = gajiPokok; }
}
