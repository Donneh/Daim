package controller;

import model.PrIS;
import model.klas.Klas;
import model.les.Les;
import server.Conversation;
import server.Handler;

import javax.json.*;
import java.util.ArrayList;
import java.util.List;

public class KlassenroosterController implements Handler {

    private PrIS informatieSysteem;

    public KlassenroosterController(PrIS infoSys) {
        informatieSysteem = infoSys;
    }

    public void handle(Conversation conversation) {
        if (conversation.getRequestedURI().startsWith("/klassenrooster/klassen")) {
            klassenDropdownOphalen(conversation);
        } else if(conversation.getRequestedURI().startsWith("/klassenrooster/rooster")) {
            roosterOphalen(conversation);
        }
    }

    public void klassenDropdownOphalen(Conversation conversation) {
        List<Klas> klassen = informatieSysteem.getKlassen();

        JsonArrayBuilder _JsonArrayBuilder = Json.createArrayBuilder();
        for (Klas klas : klassen) {
            JsonObjectBuilder _JsonObjectBuilderVoorKlas = Json.createObjectBuilder();
            _JsonObjectBuilderVoorKlas.add("klasCode", klas.getKlasCode());
            _JsonObjectBuilderVoorKlas.add("klasNaam", klas.getNaam());

            _JsonArrayBuilder.add(_JsonObjectBuilderVoorKlas);
        }

        String JsonOutStr = _JsonArrayBuilder.build().toString();
        conversation.sendJSONMessage(JsonOutStr);
    }

    public void roosterOphalen(Conversation conversation) {
        // Haal geselecteerde klas op
        JsonObject JsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
        String klasCode = JsonObjectIn.getString("klasCode");

        ArrayList<Les> lessen = informatieSysteem.getLessenVanKlas(klasCode);

        JsonArrayBuilder _JsonArrayBuilder = Json.createArrayBuilder();
        for (Les les : lessen) {
            JsonObjectBuilder _JsonObjectBuilderVoorLes = Json.createObjectBuilder();
            _JsonObjectBuilderVoorLes.add("datum", les.getDatum().toString());
            _JsonObjectBuilderVoorLes.add("van", les.getTijdVan());
            _JsonObjectBuilderVoorLes.add("tot", les.getTijdTot());
            _JsonObjectBuilderVoorLes.add("vakCode", les.getVakCode());
            _JsonObjectBuilderVoorLes.add("leraarEmail", les.getLeraarEmail());
            _JsonObjectBuilderVoorLes.add("klasLokaal", les.getKlasLokaal());
            _JsonObjectBuilderVoorLes.add("klasCode", les.getKlasCode());

            _JsonArrayBuilder.add(_JsonObjectBuilderVoorLes);
        }

        String JsonOutStr = _JsonArrayBuilder.build().toString();
        conversation.sendJSONMessage(JsonOutStr);
    }

}
