package org.merlijn.wiezen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import merlijnsmislukkingen.wiezen.Kaart;
import merlijnsmislukkingen.wiezen.Kaart.SOORT;

/**
 *
 * @author merlijn
 */
public class Spel {

    List<Kaart> alleKaarten;
    List<Deelnemer> spelers;
    List<Deelnemer> vragers;
    Kaart troefkaart;
//    List<Kaart> alleGelegdeKaarten;

    public Spel() {
        System.err.println("Nieuw spel wordt gestart");
    }

    public void speelSpel() {
        schudKaarten();
        System.err.println("Kaarten gemaakt: " + alleKaarten);
        maakSpelers();
        System.err.println("Spelers gemaakt: "+ spelers);
        System.err.println("");
        System.err.println("");
        deelKaarten();
        bepaalTroef();
        bepaalTeams();
        int firstPlayer = 0;
        for (int i = 0; i < 13; i++) {
            Map<Kaart, Deelnemer> speelRonde = speelRonde(firstPlayer);
            Kaart uitgekomen = speelRonde.keySet().iterator().next();
            Set<Kaart> kaarten = speelRonde.keySet();
            List<Kaart> kaartLijst = new ArrayList<>(kaarten);
            kaartLijst.sort(Kaart.slagComparator(uitgekomen.getSoort(), troefkaart.getSoort()));
            Kaart winnerKaart = kaartLijst.get(0);
            Deelnemer winnaar = speelRonde.get(winnerKaart);
            firstPlayer = spelers.indexOf(winnaar);
            System.err.println("Ronde is gewonnen door "+ spelers.get(firstPlayer));
        }
        System.exit(0);
        bepaalWinnaar();
    }

    /**
     * Maak alleKaarten en schud ze.
     */
    public void schudKaarten() {
        alleKaarten = new LinkedList<>();
        for (int i = 0; i < 13; i++) {
            for (SOORT soort: SOORT.values()) {
                Kaart kaart = new Kaart(soort, i);
                alleKaarten.add(kaart);
            }
        }
        Collections.shuffle(alleKaarten);
    }

    public void maakSpelers() {
        spelers = new ArrayList<>();
        Speler speler = new Speler("ik");
        spelers.add(speler);
        for (int i = 0; i < 3; i++) {
            Bot bot = new Bot("bot "+i);
            spelers.add(bot);
        }
    }

    public void deelKaarten() {
        int r = 0;
        for (Deelnemer d : spelers) {
            d.huidigeKaarten.addAll(alleKaarten.subList(r, r + 13));
            r = r + 13;
            System.err.println(d.huidigeKaarten);
            System.err.println(d.huidigeKaarten.size());
        }
    }

    private void bepaalTroef() {
        troefkaart = alleKaarten.getFirst();
        troefkaart.setTroef(troefkaart.getSoort());
    }

    private void bepaalTeams() {
        vragers = new ArrayList<Deelnemer>();
        for (Deelnemer d: spelers) {
            d.setVrager(false);
            d.setTroef(troefkaart.getSoort());
            if (vragers.size()<2) {
                if (d.wilVragen()) {
                    vragers.add(d);
                } 
            }
        }
        for (Deelnemer d : vragers) {
            d.setVrager(true);
        }
        System.err.println(vragers);
    }

    private Map<Kaart, Deelnemer> speelRonde(int firstPlayer) {
        List<Deelnemer> spelersVolgorde = new ArrayList<>();
        for (int i = 0; i<4; i++) {
            int add = firstPlayer+i;
            if (add > 3) {
                add = add - 4;
            }
            spelersVolgorde.add(spelers.get(add));
        }
        System.err.println(spelersVolgorde);
        Map<Kaart, Deelnemer> gespeeld = new HashMap<>();
        for (Deelnemer d: spelers) {
            Kaart kaart =  d.uitkomen(gespeeld);
            gespeeld.put(kaart, d);
        }
        return gespeeld;
    }

    private void bepaalWinnaar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}
