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
public class Kaarten implements Comparable<Kaarten>  {
    private String soort;
    private int nummer;
    private static String troef;

    private static List<String> SOORT_VOLGORDE = new ArrayList();
 
    static {
        SOORT_VOLGORDE.add("klaveren");
        SOORT_VOLGORDE.add("ruiten");
        SOORT_VOLGORDE.add("harten");
        SOORT_VOLGORDE.add("schoppen");
    }
    
    public Kaarten(String soort, int nummer) {
        this.soort = soort;
        this.nummer = nummer;
    } 
    public String welkeKaart() {
        return soort+ "" +nummer;
    }
    
    public String getSoort() {
        return this.soort;
    }
    
    public int getNummer() {
        return this.nummer;    
    }
    
    public static void setTroef(String soort) {
        troef = soort;
    }

    boolean isTroef() {
        return (this.soort.equals(troef));
    }

    @Override
    public int compareTo(Kaarten other) {
        if (this.isTroef()) {
            if (other.isTroef()) {
                return (this.getNummer() - other.getNummer() );
            } else {
                return 1;
            }
        }
        if (other.isTroef()) {
            return -1;
        }
        if (troef.equals("harten") || troef.equals("ruiten")) {
            SOORT_VOLGORDE.remove(troef);
        } else {
            SOORT_VOLGORDE.removeAll(SOORT_VOLGORDE);
            SOORT_VOLGORDE.add("harten");
            if (troef.equals("klaveren")) {
                SOORT_VOLGORDE.add("schoppen");
            } else {
                SOORT_VOLGORDE.add("klaveren");
            }
            SOORT_VOLGORDE.add("ruiten");
        }
        int thisSoortIndex = SOORT_VOLGORDE.indexOf(this.soort);
        int otherSoortIndex = SOORT_VOLGORDE.indexOf(other.soort);

        if (thisSoortIndex != otherSoortIndex) {
            return Integer.compare(thisSoortIndex, otherSoortIndex);
        }
        return Integer.compare(this.nummer, other.nummer);
    }
}