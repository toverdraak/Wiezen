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

    private List<Kaart> splrdeck = new ArrayList<>();
    static String detroef;
    int aas = 0;
    int troefamount = 0;
    int troefboven10 = 0;
    int opties = 0;
    private List<Kaart> optiekaarten = new ArrayList<>();
    private List<Bot> teammates = new ArrayList<>();
    private List<Kaart> verwijderdekaarten = new ArrayList<>();
    int welkkaart;
    public Kaart gelegdeKaart;
    private String name;
    private int xIncrease = 120;
    private static int x = 0;
    private boolean iswinner = false;
    private int bid;
    public boolean isteammate = false;
    private Kaart teammateKaart;
    public Kaart hoogsteKaart;

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
            Kaart kaart = splrdeck.get(i);
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

    public void legKaart(Group midden, Kaart eersteKaart, List<Kaart> slag) {;
        verwijderdekaarten.clear();
        Text kaart2speler = new Text(this.getName());
        if (this.isteammate) {
            kaart2speler.setText(this.getName() + "(team)");
        }
        kaart2speler.setY(0);
        optiekaarten.clear();
        for (Kaart kaart : new ArrayList<>(splrdeck)) {
            if (kaart.getSoort().equals(eersteKaart.getSoort())) {
                optiekaarten.add(kaart);
                //System.out.println("deze kaart wordt toegevoegd:" +kaart.getInfo());
            }
        }
        Random random = new Random();
        
        if (!optiekaarten.isEmpty()) {
            if (slag.size() == 3) {
                Collections.sort(slag, Kaart.SlagComparator);
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
                Collections.sort(slag, Kaart.SlagComparator);
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
                Collections.sort(slag, Kaart.SlagComparator);
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
//            System.out.println("GEVAAR");
            Collections.sort(slag, Kaart.SlagComparator);
            teammates.addAll(Wiezen.vragersTeam.getMembers());
            if (teammates.contains(this)) {
                gelegdeKaart = getLogicaCantFollow(slag);
            } else {
                teammates.clear();
                teammates.addAll(Wiezen.passersTeam.getMembers());
                gelegdeKaart = getLogicaCantFollow(slag);            
            }
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
    }

    public void legEersteKaart(Group midden, List slag, Kaarten totaalHarten, Kaarten totaalRuiten,Kaarten totaalKlaveren,Kaarten totaalSchoppen) {
        verwijderdekaarten.clear();
        Text kaart2speler = new Text(this.getName());
        if (this.isteammate) {
            kaart2speler.setText(this.getName() + "(team)");
        }
        kaart2speler.setY(0); 
        teammates.clear();
        teammates.addAll(Wiezen.vragersTeam.getMembers());
        if (teammates.contains(this)) {
            System.out.println("vragers");
            if (totaalHarten.getIstroef()){
                gelegdeKaart = legEersteVraagKaart(totaalHarten,totaalRuiten,totaalKlaveren,totaalSchoppen,slag);
            } else if (totaalRuiten.getIstroef()){
                gelegdeKaart = legEersteVraagKaart(totaalRuiten,totaalKlaveren,totaalSchoppen,totaalHarten,slag);                
            } else if (totaalKlaveren.getIstroef()){
                gelegdeKaart = legEersteVraagKaart(totaalKlaveren,totaalSchoppen,totaalHarten,totaalRuiten,slag);    
            } else if (totaalSchoppen.getIstroef()){
                gelegdeKaart = legEersteVraagKaart(totaalSchoppen,totaalHarten,totaalRuiten,totaalKlaveren,slag);                
            }
            
        } else {
            System.out.println("passer");
            Random eersterandom = new Random();
            int keuze = eersterandom.nextInt(splrdeck.size());
            gelegdeKaart = splrdeck.remove(keuze);
            slag.add(gelegdeKaart);
        }
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
    public Kaart legEersteVraagKaart(Kaarten totaalTroef,Kaarten totaal2, Kaarten totaal3, Kaarten totaal4,List slag){
        System.out.println("totaaltroef over:"+totaalTroef.kaartenDezeSoort.size());
        optiekaarten.clear();
        if (totaalTroef.kaartenDezeSoort.size()>0) {
            troefamount=0;
            for (int i = 0; i<splrdeck.size(); i++) {
                gelegdeKaart = splrdeck.get(i);
                if (gelegdeKaart.getSoort().equals(totaalTroef.getName())) {
                    troefamount++;
                    optiekaarten.add(gelegdeKaart);
                }
            }
            if (totaalTroef.kaartenDezeSoort.size()>2*troefamount) {
                Collections.sort(optiekaarten, Kaart.nummerComparator);
                if (optiekaarten.get(optiekaarten.size()-1).getNummer()==(totaalTroef.getHoogste().getNummer())){
                    gelegdeKaart = optiekaarten.getLast();
                } else {
                    gelegdeKaart = optiekaarten.getFirst();
                }
            }
        }       
        Random eersterandom = new Random();
        int keuze = eersterandom.nextInt(splrdeck.size());
        gelegdeKaart = splrdeck.remove(keuze);
        slag.add(gelegdeKaart);
        return gelegdeKaart;
    }
    public void setGelegdeKaart(Kaart kaart) {
        this.gelegdeKaart = kaart;
    }

    public Kaart getGelegdeKaart() {
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
//        for (int i = 0; i<gelegdeKaarten.size(); i++) {
//            System.out.println(gelegdeKaarten.get(i).getNummer()+" van " + gelegdeKaarten.get(i).getSoort());
//            System.out.println("");
//        }
    public Kaart getLogicaLastBot(List<Kaart> slag) {
        System.out.println("");
        System.out.println("4");
        System.out.println("");
        if (teammates.get(0).equals(this)) {
            teammateKaart = teammates.get(1).getGelegdeKaart();
        } else {
            teammateKaart = teammates.get(0).getGelegdeKaart();
        }
        if (slag.get(2).getInfo().equals(teammateKaart.getInfo())) {
            Collections.sort(optiekaarten, Kaart.nummerComparator);
            gelegdeKaart = optiekaarten.get(0);
            System.out.println("mijn team heeft al");
        } else {
            for (int i = 0; i < optiekaarten.size(); i++) {
                hoogsteKaart = optiekaarten.get(i);
                if (Kaart.nummerComparator.compare(hoogsteKaart, slag.get(2)) < 0) {
                    verwijderdekaarten.add(optiekaarten.get(i));
                    optiekaarten.remove(i);
                    i--;
                }
                
            }
            if (!optiekaarten.isEmpty()) {
                gelegdeKaart = optiekaarten.get(0);
                System.out.println("het laagste dat er bovengaat (hopelijk):)");
            } else {
                Collections.sort(verwijderdekaarten, Kaart.nummerComparator);
                gelegdeKaart = verwijderdekaarten.get(0);
                System.out.println("kan er niet boven");
            }
        }
        return gelegdeKaart;
    }
// wordt foutief aangeroepen
    public Kaart getLogica3rdBot(List<Kaart> slag) {
        System.out.println("");
        System.out.println("3rd");
        System.out.println("");
//        System.out.println("ENIGE FOUT: ik heb optiekaarten:"+ optiekaarten.size());
        Bot teammate = teammates.get(0).equals(this) ? teammates.get(1) : teammates.get(0);
        System.out.println("3 "+ teammate.getName());
        Kaart teammateKaart = teammate.getGelegdeKaart();
        if (teammateKaart== null){
            Collections.sort(optiekaarten, Kaart.nummerComparator);
            for (int i = 0; i < optiekaarten.size(); i++) {
                hoogsteKaart = optiekaarten.get(i);
//               System.out.println("optiekaarten 3rd player: " +hoogsteKaart.getInfo());
                if (Kaart.nummerComparator.compare(hoogsteKaart, slag.get(1)) < 0) {
                    verwijderdekaarten.add(optiekaarten.get(i));
                    optiekaarten.remove(i);
                    i--;
                }
                
            }
            if (!optiekaarten.isEmpty()) {
                gelegdeKaart = optiekaarten.get(0);
                System.out.println("het laagste dat er bovengaat (hopelijk):)");
            } else {
                Collections.sort(verwijderdekaarten, Kaart.nummerComparator);
                gelegdeKaart = verwijderdekaarten.get(0);
                System.out.println("kan er niet boven");
            }
        } else {
            System.out.println(teammateKaart.getInfo());
            System.out.println(slag.get(1).getInfo());
            if (slag.get(1).getInfo().equals(teammateKaart.getInfo())) {
                Collections.sort(optiekaarten, Kaart.nummerComparator);
                gelegdeKaart = optiekaarten.get(0);
                System.out.println("mijn team heeft al");
            } else {
                for (int i = 0; i < optiekaarten.size(); i++) {
                    hoogsteKaart = optiekaarten.get(i);
                    if (Kaart.nummerComparator.compare(hoogsteKaart, slag.get(1)) < 0) {
                        verwijderdekaarten.add(optiekaarten.get(i));
                        optiekaarten.remove(i);
                        i--;
                    }
                }
                if (!optiekaarten.isEmpty()) {
                    gelegdeKaart = optiekaarten.get(0);
                    System.out.println("het laagste dat er bovengaat (hopelijk):)");
                } else {
                    Collections.sort(verwijderdekaarten, Kaart.nummerComparator);
                    gelegdeKaart = verwijderdekaarten.get(0);
                    System.out.println("kan er niet boven");
                }
            }
        }   
        return gelegdeKaart;
    }
    public Kaart getLogica2ndBot(List<Kaart> slag) {
        System.out.println("");
        System.out.println("2");
        System.out.println("");
        Bot teammate = teammates.get(0).equals(this) ? teammates.get(1) : teammates.get(0);
        System.out.println("2 "+teammate.getName());
        Kaart teammateKaart = teammate.getGelegdeKaart();
        if (teammateKaart== null){
            System.out.println("teammate is niet de eerste");
            Collections.sort(optiekaarten, Kaart.nummerComparator);
            for (int i = 0; i < optiekaarten.size(); i++) {
                hoogsteKaart = optiekaarten.get(i);
                if (Kaart.nummerComparator.compare(hoogsteKaart, slag.get(0)) < 0) {
                    verwijderdekaarten.add(optiekaarten.get(i));
                    optiekaarten.remove(i);
                    i--;
                }
                
            }
            if (!optiekaarten.isEmpty()) {
                gelegdeKaart = optiekaarten.get(0);
                System.out.println("het laagste dat er bovengaat (hopelijk):)");
            } else {
                Collections.sort(verwijderdekaarten, Kaart.nummerComparator);
                gelegdeKaart = verwijderdekaarten.get(0);
                System.out.println("kan er niet boven");
            }
        } else {
            if (slag.get(0).getInfo().equals(teammateKaart.getInfo())) {
                System.out.println("teammate heeft");
                Collections.sort(optiekaarten, Kaart.nummerComparator);
                gelegdeKaart = optiekaarten.get(0);
                System.out.println(teammateKaart.getNummer());
                if (teammateKaart.getNummer()>9) {
                    System.out.println("hoog genoeg blijf eraf");
                    for (int i = 0; i < optiekaarten.size(); i++) {
                        hoogsteKaart = optiekaarten.get(i);
                        if (hoogsteKaart.getNummer()==14){
                            gelegdeKaart=hoogsteKaart;
                            return gelegdeKaart;
                        } else {
                            verwijderdekaarten.add(optiekaarten.get(i));
                            optiekaarten.remove(i);
                            i--;
                        }
                    }
                    Collections.sort(verwijderdekaarten, Kaart.nummerComparator);
                    gelegdeKaart = verwijderdekaarten.get(0);
                } else {
                    System.out.println("niet hoog genoeg");
                    gelegdeKaart = optiekaarten.get(optiekaarten.size()-1);
                }
            } else {
                for (int i = 0; i < optiekaarten.size(); i++) {
                    hoogsteKaart = optiekaarten.get(i);
                    if (Kaart.nummerComparator.compare(hoogsteKaart, slag.get(0)) < 0) {
                        verwijderdekaarten.add(optiekaarten.get(i));
                        optiekaarten.remove(i);
                        i--;
                    }
                }
                if (!optiekaarten.isEmpty()) {
                    gelegdeKaart = optiekaarten.get(0);
                    System.out.println("het laagste dat er bovengaat (hopelijk):)");
                } else {
                    Collections.sort(verwijderdekaarten, Kaart.nummerComparator);
                    gelegdeKaart = verwijderdekaarten.get(0);
                    System.out.println("kan er niet boven");
                }
            }
        }   
        return gelegdeKaart;
    }
    public Kaart getLogicaCantFollow(List<Kaart> slag) {
        Bot teammate = teammates.get(0).equals(this) ? teammates.get(1) : teammates.get(0);
        System.out.println("kan niet volgen "+teammate.getName());
        Kaart teammateKaart = teammate.getGelegdeKaart();
        for (int i=0; i<splrdeck.size(); i++) {
            Kaart kaart = splrdeck.get(i);
            if (kaart.getSoort().equals(detroef)) {
                optiekaarten.add(kaart);
            }
        }
        if (teammateKaart== null){
            if (optiekaarten.size()>0) {
                gelegdeKaart = optiekaarten.get(0);
                System.out.println("ik koop op voorhand");
            } else {
                Collections.sort(splrdeck, Kaart.nummerComparator);
                gelegdeKaart = splrdeck.get(0);
                System.out.println("kan nie tkopen");
            }
        } else if (slag.get(slag.size()-1).getInfo().equals(this.teammateKaart.getInfo())) {
            if (slag.size()>2){
                Collections.sort(splrdeck, Kaart.nummerComparator);
                gelegdeKaart = splrdeck.get(0);
                System.out.println("teammate heeft ik leg laag");
            } else if (slag.get(0).getNummer()>10) {
// if hoogste van soort {
                Collections.sort(splrdeck, Kaart.nummerComparator);
                gelegdeKaart = splrdeck.get(0);
                System.out.println("teammate heeft hoger dan 10 ik leg laag");
            } else {
                if (optiekaarten.size()>0) {
                    System.out.println("Teammate ligt voor maar met lage kaart, ik koop");
                    gelegdeKaart = optiekaarten.get(0);
                } else {
                    System.out.println("Teammate heeft niet maar ik heb geen troef");
                    Collections.sort(splrdeck, Kaart.nummerComparator);
                    gelegdeKaart = splrdeck.get(0);
                }
            }
                    
        } else {
            if (optiekaarten.size()>0) {
                System.out.println("teammate heeft niet dus ik koop");
                gelegdeKaart = optiekaarten.get(0);
            } else {
                System.out.println("teammateheeft niet maar ik kan niet kopen");
                Collections.sort(splrdeck, Kaart.nummerComparator);
                gelegdeKaart = splrdeck.get(0);
            }
        }
        return gelegdeKaart;
    }
}
