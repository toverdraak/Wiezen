package org.merlijn.wiezen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import merlijnsmislukkingen.wiezen.Kaart;
import merlijnsmislukkingen.wiezen.Kaart.SOORT;

/**
 *
 * @author merlijn
 */
public class Deelnemer {

    SOORT troef;
    List<Kaart> huidigeKaarten;
    boolean isVrager;
    String name;
    
    
    public Deelnemer (String naam) {
        this.name = naam;
        huidigeKaarten = new ArrayList<Kaart>();
    }

    public void setVrager(boolean v) {
        this.isVrager = v;
    }

    public void setTroef(SOORT troef) {
        this.troef = troef;
    }

    public void setKaarten(List<Kaart> kaarten) {
        this.huidigeKaarten = kaarten;
    }

    public boolean wilVragen() {
        return false;
    }

    public Kaart uitkomen(Map<Kaart, Deelnemer> gelegdeKaarten) {
        Kaart gelegdeKaart = null;
        return gelegdeKaart;
    }
    public Kaart volgendeKaart(List vragers, Kaart troefkaart, Kaart eerstekaart, Kaart hoogstekaart) {
        Kaart gelegdeKaart = null;
        return gelegdeKaart;
    }
//    public void sortkaarten() {
//        huidigeKaarten
//    }
    @Override
    public String toString() {
        return "Deelnemer{" + "name=" + name + '}';
    }

}
