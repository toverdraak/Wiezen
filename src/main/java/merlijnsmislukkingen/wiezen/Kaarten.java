/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package merlijnsmislukkingen.wiezen;

import java.util.List;

/**
 *
 * @author merlijn
 */
public class Kaarten implements Comparable<Kaarten>  {
    private String soort;
    private int nummer;
    
    private static final List<String> SOORT_VOLGORDE = List.of("klaveren", "ruiten", "harten", "schoppen");
 
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
        System.out.println(this.nummer);
        return this.nummer;
        
    }

    @Override
    public int compareTo(Kaarten other) {
        int thisSoortIndex = SOORT_VOLGORDE.indexOf(this.soort);
        int otherSoortIndex = SOORT_VOLGORDE.indexOf(other.soort);

        if (thisSoortIndex != otherSoortIndex) {
            return Integer.compare(thisSoortIndex, otherSoortIndex);
        }
        return Integer.compare(this.nummer, other.nummer);
    }
}