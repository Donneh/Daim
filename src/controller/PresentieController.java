package controller;

import model.PrIS;
import model.klas.Klas;
import model.les.Les;
import model.persoon.Student;
import model.presentie.Presentie;
import model.vak.Vak;
import server.Conversation;
import server.Handler;

import javax.json.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PresentieController implements Handler {

    private PrIS informatieSysteem;

    private String geselecteerdeVakCode;

    private String geselecteerdeKlasCode;

    public PresentieController(PrIS infoSys) {
        informatieSysteem = infoSys;
    }

    @Override
    public void handle(Conversation conversation) {
        if (conversation.getRequestedURI().startsWith("/presentie/vakken")) {
            vakkenDropdownOphalen(conversation);
        } else if (conversation.getRequestedURI().startsWith("/presentie/klassen")) {
            klassenDropdownOphalen(conversation);
        } else if(conversation.getRequestedURI().startsWith("/presentie/presentie")) {
            presentieOphalen(conversation);
        }
    }

    public void vakkenDropdownOphalen(Conversation conversation) {
        ArrayList<Vak> vakken = informatieSysteem.getVakken();

        JsonArrayBuilder _JsonArrayBuilder = Json.createArrayBuilder();
        for (Vak vak : vakken) {
            JsonObjectBuilder _JsonObjectBuilderVoorVak = Json.createObjectBuilder();
            _JsonObjectBuilderVoorVak.add("vakCode", vak.getVakcode());

            _JsonArrayBuilder.add(_JsonObjectBuilderVoorVak);
        }

        String JsonOutStr = _JsonArrayBuilder.build().toString();
        conversation.sendJSONMessage(JsonOutStr);
    }

    public void klassenDropdownOphalen(Conversation conversation) {
        JsonObject JsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
        String vakCode = JsonObjectIn.getString("vakCode");
        geselecteerdeVakCode = vakCode;

        ArrayList<Klas> klassen = informatieSysteem.getKlassen();
        JsonArrayBuilder _JsonArrayBuilder = Json.createArrayBuilder();
        for (Klas klas : klassen) {
            JsonObjectBuilder _JsonObjectBuilderVoorKlas = Json.createObjectBuilder();
            _JsonObjectBuilderVoorKlas.add("klasCode", klas.getKlasCode());

            _JsonArrayBuilder.add(_JsonObjectBuilderVoorKlas);
        }

        String JsonOutStr = _JsonArrayBuilder.build().toString();
        conversation.sendJSONMessage(JsonOutStr);
    }

    public void presentieOphalen(Conversation conversation) {
        JsonObject JsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
        String klasCode = JsonObjectIn.getString("klasCode");
        geselecteerdeKlasCode = klasCode;

        JsonObjectBuilder _JsonObjectBuilder = Json.createObjectBuilder();

        JsonArrayBuilder _JsonArrayBuilderVoorStudenten = Json.createArrayBuilder();
        List<Student> studenten = informatieSysteem.getStudentenVanKlas(klasCode);

        for (Student student : studenten) {
            JsonObjectBuilder _JsonObjectBuilderVoorStudent = Json.createObjectBuilder();
            _JsonObjectBuilderVoorStudent.add("voornaam", student.getVoornaam());
            _JsonObjectBuilderVoorStudent.add("achternaam", student.getVolledigeAchternaam());

            List<Presentie> presentieItems = informatieSysteem.getPresentieVan(student, geselecteerdeVakCode, klasCode);
            JsonArrayBuilder _JsonArrayBuilderVoorPresentieItems = Json.createArrayBuilder();

            for (Presentie presentie : presentieItems) {
                // Presentie.
                JsonObjectBuilder _JsonObjectBuilderVoorPresentieItems = Json.createObjectBuilder();
                _JsonObjectBuilderVoorPresentieItems.add("present", presentie.getPresentie().toString().toLowerCase());

                _JsonArrayBuilderVoorPresentieItems.add(_JsonObjectBuilderVoorPresentieItems);
            }

            _JsonObjectBuilderVoorStudent.add("presentieItems", _JsonArrayBuilderVoorPresentieItems);

            _JsonArrayBuilderVoorStudenten.add(_JsonObjectBuilderVoorStudent);
        }

        JsonArrayBuilder _JsonArrayBuilderVoorLessen = Json.createArrayBuilder();
        List<Presentie> presentieItemsVoorEersteStudent = informatieSysteem.getPresentieVan(studenten.get(0), geselecteerdeVakCode, klasCode);

        ArrayList<Les> lessen = new ArrayList<Les>();
        for (Presentie pPresentie : presentieItemsVoorEersteStudent) {
            Les pLes = pPresentie.getLes();

            JsonObjectBuilder _jsonObjectBuilderVoorLes = Json.createObjectBuilder();
            _jsonObjectBuilderVoorLes.add("datum", pLes.getDatum().toString());
            _jsonObjectBuilderVoorLes.add("van", pLes.getTijdVan());
            _jsonObjectBuilderVoorLes.add("tot", pLes.getTijdTot());

            _JsonArrayBuilderVoorLessen.add(_jsonObjectBuilderVoorLes);
        }

        _JsonObjectBuilder.add("lessen", _JsonArrayBuilderVoorLessen);
        _JsonObjectBuilder.add("studenten", _JsonArrayBuilderVoorStudenten);

        String JsonOutStr = _JsonObjectBuilder.build().toString();
        conversation.sendJSONMessage(JsonOutStr);
    }

}
