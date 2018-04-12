package model.les;

import java.time.LocalDate;

public class Les {

    private LocalDate datum;

    private String van;

    private String tot;

    private String vakCode;

    private String klasLokaal;

    private String leraarEmail;

    private String klasCode;

    public Les(LocalDate dt, String vn, String tt, String vC, String lE, String kL, String kC) {
        datum = dt;
        van = vn;
        tot = tt;
        vakCode = vC;
        leraarEmail = lE;
        klasLokaal = kL;
        klasCode = kC;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public String getTijdVan() {
        return van;
    }

    public String getTijdTot() {
        return tot;
    }

    public String getVakCode() {
        return vakCode;
    }

    public String getLeraarEmail() {
        return leraarEmail;
    }

    public String getKlasLokaal() {
        return klasLokaal;
    }

    public String getKlasCode() {
        return klasCode;
    }

}
