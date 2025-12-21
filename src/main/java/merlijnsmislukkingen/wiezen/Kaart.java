/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package merlijnsmislukkingen.wiezen;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author merlijn
 */
public class Kaart implements Comparable<Kaart>  {
    
    public enum SOORT {HARTEN, RUITEN, KLAVEREN, SCHOPPEN};
    
    private SOORT kaartSoort;
    private String soort;
    private int nummer;
    private static SOORT troef;
    private static String oudeTroef;
    private static String uitgekomen;

    private static List<String> SOORT_VOLGORDE = new ArrayList();
 
//    static {
//        SOORT_VOLGORDE.add("klaveren");
//        SOORT_VOLGORDE.add("ruiten");
//        SOORT_VOLGORDE.add("harten");
//        SOORT_VOLGORDE.add("schoppen");
//    }
    
    public Kaart(SOORT soort, int nummer) {
        this.kaartSoort = soort;
        this.nummer = nummer;
    }

    public Kaart(String soort, int nummer) {
        this.soort = soort;
        this.nummer = nummer;
    } 
    public String welkeKaart() {
        return soort+ "" +nummer;
    }

    public static void setTroef(SOORT troef) {
        Kaart.troef = troef;
    }
    
    public SOORT getSoort() {
        return this.kaartSoort;
    }
    public String getOldSoort() {
      return this.soort;
    }
    
    public int getNummer() {
        return this.nummer;    
    }
    
    public static void setTroef(String soort) {
        oudeTroef = soort;
    }

    public static String getTroef() {
        return oudeTroef;
    }
    
    public String getInfo() {
        return (this.getOldSoort()+this.getNummer());
    }

    boolean isTroef() {
        return (this.kaartSoort.equals(troef));
//        return (this.soort.equals(oudeTroef));
    }
    public static void setUitgekomenSoort(String soort) {
        uitgekomen = soort;
    }
    public static String getUitGekomenSoort() {
        return uitgekomen;
    }
    boolean isUitgekomenSoort() {
        return (this.soort.equals(uitgekomen));
    }

    @Override
    public int compareTo(Kaart other) {
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
        if (oudeTroef.equals("harten") || oudeTroef.equals("ruiten")) {
            SOORT_VOLGORDE.remove(oudeTroef);
        } else {
            SOORT_VOLGORDE.removeAll(SOORT_VOLGORDE);
            SOORT_VOLGORDE.add("harten");
            if (oudeTroef.equals("klaveren")) {
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
    public static Comparator<Kaart> SlagComparator = new Comparator<Kaart>() {
        @Override
        public int compare(Kaart k1, Kaart k2) {
            if (k1.isTroef()) {
                if (k2.isTroef()) {
                    return Integer.compare(k1.nummer, k2.nummer);
                } else {
                    return 1;
                }
            } else{
                if (k2.isTroef()) {
                    return-1;
                } else {
                    if (k1.isUitgekomenSoort()){
                        if (k2.isUitgekomenSoort()) {
                            return Integer.compare(k1.nummer, k2.nummer);
                        } else {
                            return 1;
                        }
                    } else {
                        if (k2.isUitgekomenSoort()) {
                            return -1;
                        } else {
                            return -1;
                        }
                    }
                }
            }
        }
    };
    public static Comparator<Kaart> nummerComparator = new Comparator<Kaart>() {
        @Override
        public int compare(Kaart k, Kaart c) {
            return Integer.compare(k.nummer, c.nummer);
        }
    };
    
    
//    @Override
//    public boolean equals(Object k1) {
//        if (this.getOldSoort == k1.getOldSoort) {
//            if (this.getNummer == k1.getNummer){
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//            return false;
//        }
//    }
//        

    @Override
    public String toString() {
        return kaartSoort + "," + nummer;
    }
}