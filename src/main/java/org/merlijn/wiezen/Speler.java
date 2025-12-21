package org.merlijn.wiezen;

import java.util.Map;
import java.util.Scanner;
import merlijnsmislukkingen.wiezen.Kaart;

/**
 *
 * @author merlijn
 */
public class Speler extends Deelnemer {
    
    public Speler(String naam) {
        super(naam);
        
    }
    @Override
    public boolean wilVragen() {
        Scanner vraagt = new Scanner(System.in);
        String input = vraagt.nextLine();
        if (input.equals("ja")) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public Kaart uitkomen(Map<Deelnemer, Kaart> map) {
        System.err.println("Dit ligt op tafel: "+map);
        System.err.println("kies een kaart om uit te komen");
        System.err.println(huidigeKaarten);
        Scanner scanner = new Scanner(System.in);
        int kaartnr = scanner.nextInt();
        Kaart gelegdeKaart = huidigeKaarten.get(kaartnr);
        huidigeKaarten.remove(kaartnr);
        System.err.println(gelegdeKaart);
        return gelegdeKaart;
    }
    
    
}
