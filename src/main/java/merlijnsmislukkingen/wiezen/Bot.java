/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package merlijnsmislukkingen.wiezen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

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
    int opties = 0;
    private List<Kaarten> optiekaarten = new ArrayList<>();
    int welkkaart;
    Kaarten gelegdeKaart;
    private String name;
    private int xIncrease = 120 ;
    private static int x = 0;
    
    public Bot(String naam) {
        name = naam;
    }
    public void setList(List cards) {
        splrdeck = cards;
    }

    public static void setTroef(String troef) {
        System.out.println(troef);
        detroef = troef;
    }
    public String getName() {
        return name;
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
    public void legKaart(Group midden, Kaarten eersteKaart, List slag) {
        Text kaart2speler = new Text(this.getName());
        kaart2speler.setY(10);
        eersteKaart.getSoort();
        for (int i=0; i<splrdeck.size();i++){
            Kaarten kaart = splrdeck.get(i);
            if (kaart.getSoort() == eersteKaart.getSoort()){
                opties++;
                optiekaarten.add(kaart);
                splrdeck.remove(i);
            }
        }
        if (opties > 0) {
            Random welkekaart = new Random();
            welkkaart = welkekaart.nextInt(opties);
            opties = 0;
            gelegdeKaart = optiekaarten.get(welkkaart);
            optiekaarten.remove(welkkaart);
            splrdeck.addAll(optiekaarten);
        } else {
            Random welkekaart = new Random();
            welkkaart = welkekaart.nextInt(splrdeck.size());
            gelegdeKaart =splrdeck.get(welkkaart);
            splrdeck.remove(welkkaart);
            
        }
        slag.add(gelegdeKaart);
        String imagePath = "/" + gelegdeKaart.getSoort() + gelegdeKaart.getNummer() + ".png";
        Image gekozenImage = new Image(Wiezen.class.getResourceAsStream(imagePath), 120, 180, true, true);
        ImageView mid = new ImageView(gekozenImage);
        x = x+xIncrease;
        mid.setLayoutX(x);
        kaart2speler.setLayoutX(x+20);
        midden.getChildren().add(mid);
        midden.getChildren().add(kaart2speler);
        System.out.println(x);
    }
    public Kaarten getGelegdeKaart() {
        return this.gelegdeKaart;
    }
}
