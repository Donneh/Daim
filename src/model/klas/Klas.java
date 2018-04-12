package model.klas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.les.Les;
import model.persoon.Student;
import model.vak.Vak;

public class Klas {

	private String klasCode;

	private String naam;

	private ArrayList<Student> deStudenten = new ArrayList<Student>();

	private ArrayList<Vak> deVakken = new ArrayList<Vak>();

	public Klas(String klasCode, String naam) {
		this.klasCode = klasCode;
		this.naam = naam;
	}

	public String getKlasCode() {
		return klasCode;
	}

	public String getNaam() {
		return naam;
	}

	public List<Student> getStudenten() {
		return Collections.unmodifiableList(deStudenten);
	}

	public Vak getVak(String vakCode) {
		for(Vak vak: deVakken) {
			if(vak.vakCode.equals(vakCode)) {
				return vak;
			}
		}
		return null;
	}

	public List<Vak> getVakken() {
		return Collections.unmodifiableList(deVakken);
	}

	public boolean bevatStudent(Student pStudent) {
//		for (Student lStudent : this.getStudenten()) {
//			if (lStudent==pStudent) {
//				return true;
//			}
//		}
//		return false;
		return this.getStudenten().contains(pStudent);
	}

	public void voegStudentToe(Student pStudent) {
		if (!getStudenten().contains(pStudent)) {
			deStudenten.add(pStudent);
		}
	}

	public void voegVakToe(Vak pVak) {
		if (!deVakken.contains(pVak)) {
			deVakken.add(pVak);
		}
	}

	public void voegVakkenToe(ArrayList<Vak> pVakken) {

	}

}
