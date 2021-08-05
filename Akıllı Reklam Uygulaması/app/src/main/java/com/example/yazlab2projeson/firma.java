package com.example.yazlab2projeson;

public class firma {

    private String firmaId;
    private String firmaAdi;
    private String xLokasyon;
    private String yLokasyon;
    private String kampanyaIcerik;
    private String kampanyaSuresi;
    private String kategori;

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getFirmaId() {
        return firmaId;
    }

    public void setFirmaId(String firmaId) {
        this.firmaId = firmaId;
    }

    public String getFirmaAdi() {
        return firmaAdi;
    }

    public void setFirmaAdi(String firmaAdi) {
        this.firmaAdi = firmaAdi;
    }

    public String getxLokasyon() {
        return xLokasyon;
    }

    public void setxLokasyon(String xLokasyon) {
        this.xLokasyon = xLokasyon;
    }

    public String getyLokasyon() {
        return yLokasyon;
    }

    public void setyLokasyon(String yLokasyon) {
        this.yLokasyon = yLokasyon;
    }

    public String getKampanyaIcerik() {
        return kampanyaIcerik;
    }

    public void setKampanyaIcerik(String kampanyaIcerik) {
        this.kampanyaIcerik = kampanyaIcerik;
    }

    public String getKampanyaSuresi() {
        return kampanyaSuresi;
    }

    public void setKampanyaSuresi(String kampanyaSuresi) {
        this.kampanyaSuresi = kampanyaSuresi;
    }

    public  firma(){

    }

    public  firma(String firmaId,String firmaAdi,String xLokasyon,String yLokasyon,String kampanyaIcerik,String kampanyaSuresi,String kategori){
        this.firmaId=firmaId;
        this.firmaAdi=firmaAdi;
        this.xLokasyon=xLokasyon;
        this.yLokasyon=yLokasyon;
        this.kampanyaIcerik=kampanyaIcerik;
        this.kampanyaSuresi=kampanyaSuresi;
        this.kategori=kategori;
    }


}
