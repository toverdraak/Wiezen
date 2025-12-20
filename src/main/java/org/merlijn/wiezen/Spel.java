package org.merlijn.wiezen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import merlijnsmislukkingen.wiezen.Kaart;
import merlijnsmislukkingen.wiezen.Kaart.SOORT;

/**
 *
 * @author merlijn
 */
public class Spel {

    List<Kaart> alleKaarten;
    List<Deelnemer> spelers;

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
        System.exit(0);
        bepaalTeams();
        for (int i = 0; i < 13; i++) {
            speelRonde();
        }
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

//    public void deelKaarten() {
//        int r = 0;
//        for (Deelnemer d: spelers) {
//            int bkaart = r*13;
//            r++;
//            int ekaart = r*13;
//            for (int i=bkaart; i<(ekaart); i++ ) {
//                d.huidigeKaarten.add(alleKaarten.get(i));
//            }
//            System.err.println(d.huidigeKaarten);
//            System.err.println(d.huidigeKaarten.size());
//        }
//    }
    private void bepaalTeams() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void speelRonde() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void bepaalWinnaar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}
