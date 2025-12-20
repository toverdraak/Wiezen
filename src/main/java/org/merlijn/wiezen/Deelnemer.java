package org.merlijn.wiezen;

import java.util.ArrayList;
import java.util.List;
import merlijnsmislukkingen.wiezen.Kaart;

/**
 *
 * @author merlijn
 */
public class Deelnemer {

    List<Kaart> huidigeKaarten;
    String name;
    
    public Deelnemer (String naam) {
        this.name = naam;
        huidigeKaarten = new ArrayList<Kaart>();
    }

    public void setKaarten(List<Kaart> kaarten) {
        this.huidigeKaarten = kaarten;
    }

    @Override
    public String toString() {
        return "Deelnemer{" + "name=" + name + '}';
    }

}
