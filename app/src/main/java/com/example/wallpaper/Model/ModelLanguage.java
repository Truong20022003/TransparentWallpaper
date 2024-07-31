package com.example.wallpaper.Model;

public class ModelLanguage {
    private String idLanguage;
    private int frag;
    private String countryName;
    private boolean status;

    public ModelLanguage() {
    }

    public ModelLanguage(String idLanguage, int frag, String countryName, boolean status) {
        this.idLanguage = idLanguage;
        this.frag = frag;
        this.countryName = countryName;
        this.status = status;
    }

    public String getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(String idLanguage) {
        this.idLanguage = idLanguage;
    }

    public int getFrag() {
        return frag;
    }

    public void setFrag(int frag) {
        this.frag = frag;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
