package org.merlijn.wiezen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import merlijnsmislukkingen.wiezen.Kaart;

/**
 *
 * @author merlijn
 */
public class Bot extends Deelnemer {
    
    public Bot(String name) {
        super(name);
    }

    @Override
    public boolean wilVragen() {
        int w = 0;
        int a = 0;
        int b = 1;
        for (Kaart k: huidigeKaarten) {
            b = 1;
            if (k.getSoort() == troef){
                b = 4;
            }
            a = k.getNummer();
            if (a == 0) {
                a=15;
            }
            w = w +(a*b);
            System.err.println(w);
                
        }   
        System.err.println("totaal" + w);
        if (w>160) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public Kaart uitkomen(Map<Deelnemer, Kaart> map) {
        int size = huidigeKaarten.size();
        Random random = new Random();
        int gekozen = random.nextInt(size);
        Kaart gelegdeKaart = huidigeKaarten.get(gekozen);
        huidigeKaarten.remove(gelegdeKaart);
        return gelegdeKaart;
    }
//    @Override
//    public Kaart volgendeKaart(List vragers, Kaart troefkaart, Kaart eerstekaart, Kaart hoogstekaart) {
//        List<Kaart> legbaar = new ArrayList<>();
//        for (Kaart k: huidigeKaarten) {
//            if (k.getSoort().equals(eerstekaart.getSoort())){
//                legbaar.add(k);
//            }
//        }
//        if (legbaar.size()== 0) {
//            
//        }
//        Kaart gelegdeKaart = null;
//        return gelegdeKaart;
//    }
}
