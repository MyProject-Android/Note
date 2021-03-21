package com.hqv.notepad;

public class Note {

    private int id;
    private String tenGhiChu;
    private String thoiGian;
    private String noiDungGhiChu;

    public Note(int id, String tenGhiChu, String thoiGian, String noiDungGhiChu) {
        this.id = id;
        this.tenGhiChu = tenGhiChu;
        this.thoiGian = thoiGian;
        this.noiDungGhiChu = noiDungGhiChu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenGhiChu() {
        return tenGhiChu;
    }

    public void setTenGhiChu(String tenGhiChu) {
        this.tenGhiChu = tenGhiChu;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getNoiDungGhiChu() {
        return noiDungGhiChu;
    }

    public void setNoiDungGhiChu(String noiDungGhiChu) {
        this.noiDungGhiChu = noiDungGhiChu;
    }

}
