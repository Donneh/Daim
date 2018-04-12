package model.vak;

import model.les.Les;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Vak {

    public String vakCode;

    public String vakNaam;

    public Vak(String vkC, String vkN) {
        vakCode = vkC;
        vakNaam = vkN;
    }

    public String getVakcode() {
        return vakCode;
    }

    private ArrayList<Les> deLessen = new ArrayList<Les>();

    public void voegLesToe(Les pLes) {
        if (!deLessen.contains(pLes)) {
            deLessen.add(pLes);
        }
    }

    public void voegLessenToe(ArrayList<Les> pLessen) {
        for(Les pLes : pLessen) {
            voegLesToe(pLes);
        }
    }

    public ArrayList<Les> getLessen() {
        return deLessen;
    }

}
