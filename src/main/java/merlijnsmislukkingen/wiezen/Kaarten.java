/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package merlijnsmislukkingen.wiezen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author merlijn
 */
public class Kaarten {
    private static List<Kaart> gelegdeKaarten = new ArrayList<>();
    public List<Kaart> kaartenDezeSoort = new ArrayList<>();
    private String name;
    private Kaart hoogste;
    public Kaarten(String soort) {
        this.setName(soort);
        for (int i = 2; i<15; i++) {
            Kaart kaart = new Kaart(soort , i);
            kaartenDezeSoort.add(kaart);
        }
    }
    public void setName(String naamsoort) {
        this.name = naamsoort;
    }

    public String getName() {
        return name;
    }
    
    public static void setGelegdeKaarten(List<Kaart> slag) {
        gelegdeKaarten.addAll(slag);
        
        
    }
    public static void getGelegdeKaarten() {
        System.out.println("aantal gelegde kaarten = " + gelegdeKaarten.size());
    }
    public void updateGebruikteKaarten(List<Kaart> slag) {
        kaartenDezeSoort.removeIf(kaart ->
        slag.stream().anyMatch(slagKaart ->
        slagKaart.getSoort().equals(this.getName()) && slagKaart.getNummer() == kaart.getNummer()));
        this.setHoogsteKaart();
        System.out.println("resterende aantal kaarten per soort:" +kaartenDezeSoort.size());
    }
    public void setHoogsteKaart() {
        System.out.println("");
        Collections.sort(kaartenDezeSoort, Kaart.nummerComparator);
        if (kaartenDezeSoort.size()> 0) {
            System.out.println("hoogstekaart = "+ kaartenDezeSoort.get(kaartenDezeSoort.size()-1).getInfo());
            hoogste = kaartenDezeSoort.get(kaartenDezeSoort.size()-1);
        }
    }

    public Kaart getHoogste() {
        return hoogste;
    }
}
