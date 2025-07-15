/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package merlijnsmislukkingen.wiezen;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author merlijn
 */
public class Bot {

    private List<Kaarten> splrdeck = new ArrayList<>();
    static String detroef;
    int aas = 0;
    int troefamount = 0;
    int troefboven10 = 0;

    public void setList(List cards) {
        splrdeck = cards;
    }

    public static void setTroef(String troef) {
        System.out.println(troef);
        detroef = troef;
    }

    public boolean getActie() {
        for (int i = 0; i < this.splrdeck.size(); i++) {
            Kaarten kaart = splrdeck.get(i);
//            System.out.println(kaart.getSoort()+" "+ kaart.getNummer());
            if (kaart.getNummer() == 14) {
                aas++;
            }
            if (kaart.getSoort() == detroef) {
                troefamount++;
                int troefnummer = kaart.getNummer();
                if (troefnummer > 10) {
                    troefboven10++;
                }
            }
        }
        if (troefamount > 4) {
            return true;
        } else if (troefamount > 3) {
            if (troefboven10 > 0) {
                return true;
            }
        } else if (troefamount > 2) {
            if (troefboven10 > 1) {
                return true;
            }
        }
        return false;
    }
}
