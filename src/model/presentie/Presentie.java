package model.presentie;

import model.les.Les;
import model.persoon.Student;

public class Presentie {

    private Student student;

    private Les les;

    private PresentieMogelijkheden presentie;

    public Presentie(Student st, Les ls) {
        this(st, ls, PresentieMogelijkheden.ONGEMELD_ABSENT);
    }

    public Presentie(Student st, Les ls, PresentieMogelijkheden pr) {
        student = st;
        les = ls;
        presentie = pr;
    }

    public PresentieMogelijkheden getPresentie() {
        return presentie;
    }

    public Les getLes() {
        return les;
    }
}
