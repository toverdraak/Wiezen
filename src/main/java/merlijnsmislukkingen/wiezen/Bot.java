/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package merlijnsmislukkingen.wiezen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
// lijn 15
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
    private List<Bot> teammates = new ArrayList<>();
    private List<Kaarten> verwijderdekaarten = new ArrayList<>();
    private static List<Kaarten> gelegdeKaarten = new ArrayList<>();
    int welkkaart;
    Kaarten gelegdeKaart;
    private String name;
    private int xIncrease = 120;
    private static int x = 0;
    private boolean iswinner = false;
    private int bid;
    public boolean isteammate = false;
    private Kaarten teammatekaart;
    public Kaarten hoogsteKaart;

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

    public void setId(int id) {
        bid = id;
    }

    public int getId() {
        return bid;
    }

    public void setName(String naam) {
        name = naam;
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

    public static void resetX() {
        x = 0;
    }

    public void legKaart(Group midden, Kaarten eersteKaart, List<Kaarten> slag) {;
        verwijderdekaarten.clear();
        Text kaart2speler = new Text(this.getName());
        if (this.isteammate) {
            kaart2speler.setText(this.getName() + "(team)");
        }
        kaart2speler.setY(0);
        optiekaarten.clear();
        for (Kaarten kaart : new ArrayList<>(splrdeck)) {
            if (kaart.getSoort().equals(eersteKaart.getSoort())) {
                optiekaarten.add(kaart);
                System.out.println("deze kaart wordt toegevoegd:" +kaart.getInfo());
            }
        }
        Random random = new Random();
        
        if (!optiekaarten.isEmpty()) {
            if (slag.size() == 3) {
                Collections.sort(slag, Kaarten.SlagComparator);
                teammates.addAll(Wiezen.vragersTeam.getMembers());
                if (teammates.contains(this)) {
                    gelegdeKaart = getLogicaLastBot(slag);
                } else {
                    teammates.clear();
                    teammates.addAll(Wiezen.passersTeam.getMembers());
                    gelegdeKaart = getLogicaLastBot(slag);
                }
                splrdeck.remove(gelegdeKaart);
            } else if (slag.size() == 2) {
                Collections.sort(slag, Kaarten.SlagComparator);
                teammates.addAll(Wiezen.vragersTeam.getMembers());
                if (teammates.contains(this)) {
                    gelegdeKaart = getLogica3rdBot(slag);
                } else {
                    teammates.clear();
                    teammates.addAll(Wiezen.passersTeam.getMembers());
                    gelegdeKaart = getLogica3rdBot(slag);
                }
                splrdeck.remove(gelegdeKaart);
            } 
            else {
                Collections.sort(slag, Kaarten.SlagComparator);
                teammates.addAll(Wiezen.vragersTeam.getMembers());
                if (teammates.contains(this)) {
                    gelegdeKaart = getLogica2ndBot(slag);
                } else {
                    teammates.clear();
                    teammates.addAll(Wiezen.passersTeam.getMembers());
                    gelegdeKaart = getLogica2ndBot(slag);
                }
                splrdeck.remove(gelegdeKaart);
            }
        } else {
            // Geen kaarten van de gevraagde soort → willekeurige laagste kaart
            System.out.println("GEVAAR");
            Collections.sort(splrdeck, Kaarten.nummerComparator);
            gelegdeKaart = splrdeck.get(0);
            gelegdeKaart.getInfo();
            splrdeck.remove(gelegdeKaart);
        }

        // Voeg afgelegde kaart toe aan slag
//        System.out.println("legkaart, voeg "+gelegdeKaart+" toe aan " + slag+", size was "+slag.size());
        slag.add(gelegdeKaart);
//        System.out.println("legkaart, en nu size = "+slag.size());
        // Teken afbeelding
        String imagePath = "/" + gelegdeKaart.getSoort() + gelegdeKaart.getNummer() + ".png";
        Image gekozenImage = new Image(Wiezen.class.getResourceAsStream(imagePath), 120, 180, true, true);
        ImageView mid = new ImageView(gekozenImage);

        // Positioneer en voeg toe
        x = x + xIncrease;
        mid.setLayoutX(x);
        kaart2speler.setLayoutX(x + 20);

        midden.getChildren().add(mid);
        midden.getChildren().add(kaart2speler);
        System.out.println("aantal kaarten over: " + splrdeck.size());
    }

    public void legEersteKaart(Group midden, List slag) {
        verwijderdekaarten.clear();
        Text kaart2speler = new Text(this.getName());
        if (this.isteammate) {
            kaart2speler.setText(this.getName() + "(team)");
        }
        kaart2speler.setY(0);
        Random eersterandom = new Random();
        int keuze = eersterandom.nextInt(splrdeck.size());
        gelegdeKaart = splrdeck.remove(keuze);
        slag.add(gelegdeKaart);

        // Teken afbeelding
        String imagePath = "/" + gelegdeKaart.getSoort() + gelegdeKaart.getNummer() + ".png";
        Image gekozenImage = new Image(Wiezen.class.getResourceAsStream(imagePath), 120, 180, true, true);
        ImageView mid = new ImageView(gekozenImage);

        // Positioneer en voeg toe
        x = x + xIncrease;
        mid.setLayoutX(x);
        kaart2speler.setLayoutX(x + 20);

        midden.getChildren().add(mid);
        midden.getChildren().add(kaart2speler);
    }

    public void setGelegdeKaart(Kaarten kaart) {
        this.gelegdeKaart = kaart;
    }

    public Kaarten getGelegdeKaart() {
        return this.gelegdeKaart;
    }

    public void setIsWinner() {
        iswinner = true;
    }

    public boolean getIsWinner() {
        return iswinner;
    }

    public void resetIsWinner() {
        iswinner = false;
    }

    public void setTeammate() {
        isteammate = true;
    }

    public boolean getTeammate() {
        return isteammate;
    }
// klass om fout op te sporen (later nog nuttig??)
    public static void setGelegdeKaarten(List<Kaarten> slag) {
        gelegdeKaarten.addAll(slag);
    }
    public static void getGelegdeKaarten() {
        System.out.println("aantal gelegde kaarten = " + gelegdeKaarten.size());
//        for (int i = 0; i<gelegdeKaarten.size(); i++) {
//            System.out.println(gelegdeKaarten.get(i).getNummer()+" van " + gelegdeKaarten.get(i).getSoort());
//            System.out.println("");
//        }
    }
    public Kaarten getLogicaLastBot(List<Kaarten> slag) {
        if (teammates.get(0).equals(this)) {
            teammatekaart = teammates.get(1).getGelegdeKaart();
        } else {
            teammatekaart = teammates.get(0).getGelegdeKaart();
        }
        if (slag.get(2).equals(teammatekaart)) {
            Collections.sort(optiekaarten, Kaarten.nummerComparator);
            gelegdeKaart = optiekaarten.get(0);
            System.out.println("mijn team heeft al");
        } else {
            for (int i = 0; i < optiekaarten.size(); i++) {
                hoogsteKaart = optiekaarten.get(i);
                if (Kaarten.nummerComparator.compare(hoogsteKaart, slag.get(2)) < 0) {
                    verwijderdekaarten.add(optiekaarten.get(i));
                    optiekaarten.remove(i);
                    i--;
                }
                
            }
            if (!optiekaarten.isEmpty()) {
                gelegdeKaart = optiekaarten.get(0);
                System.out.println("het laagste dat er bovengaat (hopelijk):)");
            } else {
                Collections.sort(verwijderdekaarten, Kaarten.nummerComparator);
                gelegdeKaart = verwijderdekaarten.get(0);
                System.out.println("kan er niet boven");
            }
        }
        return gelegdeKaart;
    }
// wordt foutief aangeroepen
    public Kaarten getLogica3rdBot(List<Kaarten> slag) {
        System.out.println("ENIGE FOUT: ik heb optiekaarten:"+ optiekaarten.size());
        Bot teammate = teammates.get(0).equals(this) ? teammates.get(1) : teammates.get(0);
        Kaarten teammateKaart = teammate.getGelegdeKaart();
        if (teammateKaart== null){
            System.out.println("teammate nog niet gelegd");
            Collections.sort(optiekaarten, Kaarten.nummerComparator);
            for (int i = 0; i < optiekaarten.size(); i++) {
                hoogsteKaart = optiekaarten.get(i);
                System.out.println("optiekaarten 3rd player: " +hoogsteKaart.getInfo());
                if (Kaarten.nummerComparator.compare(hoogsteKaart, slag.get(1)) < 0) {
                    verwijderdekaarten.add(optiekaarten.get(i));
                    optiekaarten.remove(i);
                    i--;
                }
                
            }
            if (!optiekaarten.isEmpty()) {
                gelegdeKaart = optiekaarten.get(0);
                System.out.println("het laagste dat er bovengaat (hopelijk):)");
            } else {
                Collections.sort(verwijderdekaarten, Kaarten.nummerComparator);
                gelegdeKaart = verwijderdekaarten.get(0);
                System.out.println("kan er niet boven");
            }
        } else {
            System.out.println(teammateKaart.getInfo());
            if (slag.get(1).equals(teammatekaart)) {
                Collections.sort(optiekaarten, Kaarten.nummerComparator);
                gelegdeKaart = optiekaarten.get(0);
                System.out.println("mijn team heeft al");
            } else {
                for (int i = 0; i < optiekaarten.size(); i++) {
                    hoogsteKaart = optiekaarten.get(i);
                    if (Kaarten.nummerComparator.compare(hoogsteKaart, slag.get(1)) < 0) {
                        verwijderdekaarten.add(optiekaarten.get(i));
                        optiekaarten.remove(i);
                        i--;
                    }
                }
                if (!optiekaarten.isEmpty()) {
                    gelegdeKaart = optiekaarten.get(0);
                    System.out.println("het laagste dat er bovengaat (hopelijk):)");
                } else {
                    Collections.sort(verwijderdekaarten, Kaarten.nummerComparator);
                    gelegdeKaart = verwijderdekaarten.get(0);
                    System.out.println("kan er niet boven");
                }
            }
        }   
        return gelegdeKaart;
    }
    public Kaarten getLogica2ndBot(List<Kaarten> slag) {
        Bot teammate = teammates.get(0).equals(this) ? teammates.get(1) : teammates.get(0);
        Kaarten teammateKaart = teammate.getGelegdeKaart();
        if (teammateKaart== null){
            Collections.sort(optiekaarten, Kaarten.nummerComparator);
            for (int i = 0; i < optiekaarten.size(); i++) {
                hoogsteKaart = optiekaarten.get(i);
                if (Kaarten.nummerComparator.compare(hoogsteKaart, slag.get(0)) < 0) {
                    verwijderdekaarten.add(optiekaarten.get(i));
                    optiekaarten.remove(i);
                    i--;
                }
                
            }
            if (!optiekaarten.isEmpty()) {
                gelegdeKaart = optiekaarten.get(0);
                System.out.println("het laagste dat er bovengaat (hopelijk):)");
            } else {
                Collections.sort(verwijderdekaarten, Kaarten.nummerComparator);
                gelegdeKaart = verwijderdekaarten.get(0);
                System.out.println("kan er niet boven");
            }
        } else {
            if (slag.get(0).equals(teammatekaart)) {
                Collections.sort(optiekaarten, Kaarten.nummerComparator);
                gelegdeKaart = optiekaarten.get(0);
                if (teammatekaart.getNummer()>9) {
                    for (int i = 0; i < optiekaarten.size(); i++) {
                        hoogsteKaart = optiekaarten.get(i);
                        if (hoogsteKaart.getNummer()==14){
                            gelegdeKaart=hoogsteKaart;
                        }
                    }
                }
            } else {
                for (int i = 0; i < optiekaarten.size(); i++) {
                    hoogsteKaart = optiekaarten.get(i);
                    if (Kaarten.nummerComparator.compare(hoogsteKaart, slag.get(0)) < 0) {
                        verwijderdekaarten.add(optiekaarten.get(i));
                        optiekaarten.remove(i);
                        i--;
                    }
                }
                if (!optiekaarten.isEmpty()) {
                    gelegdeKaart = optiekaarten.get(0);
                    System.out.println("het laagste dat er bovengaat (hopelijk):)");
                } else {
                    Collections.sort(verwijderdekaarten, Kaarten.nummerComparator);
                    gelegdeKaart = verwijderdekaarten.get(0);
                    System.out.println("kan er niet boven");
                }
            }
        }   
        return gelegdeKaart;
    }
}
