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

    public void legKaart(Group midden, Kaarten eersteKaart, List<Kaarten> slag) {
        Text kaart2speler = new Text(this.getName());
        if (this.isteammate) {
            kaart2speler.setText(this.getName() + "(team)");
        }
        kaart2speler.setY(0);
        optiekaarten.clear();
        for (Kaarten kaart : new ArrayList<>(splrdeck)) {
            if (kaart.getSoort().equals(eersteKaart.getSoort())) {
                optiekaarten.add(kaart);
            }
        }
        Random random = new Random();

        if (!optiekaarten.isEmpty()) {
            System.out.println("thinking...");
            opties = optiekaarten.size();
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
            } else {
                int keuze = random.nextInt(optiekaarten.size());
                gelegdeKaart = optiekaarten.get(keuze);
                splrdeck.remove(gelegdeKaart);  // veilig verwijderen
            }
        } else {
            // Geen kaarten van de gevraagde soort → willekeurige kaart
            Collections.sort(splrdeck, Kaarten.nummerComparator);
            gelegdeKaart = splrdeck.remove(0);
        }

        // Voeg afgelegde kaart toe aan slag
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

    public void legEersteKaart(Group midden, List slag) {
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
    public Kaarten getLogicaLastBot(List<Kaarten> slag) {
        if (teammates.get(0).equals(this)) {
        //lijn voor 216
            teammatekaart = teammates.get(1).getGelegdeKaart();
        } else {
            teammatekaart = teammates.get(0).getGelegdeKaart();
        }
        System.out.println(slag.get(2).getNummer());
        if (slag.get(2).equals(teammatekaart)) {
            Collections.sort(optiekaarten, Kaarten.nummerComparator);
            gelegdeKaart = optiekaarten.get(0);
            System.out.println("mijn team heeft al");
        } else {
            for (int i = 0; i < opties - 1; i++) {
                hoogsteKaart = optiekaarten.get(i);
                hoogsteKaart.getNummer();
                System.out.println("optie:" + i);
                if (Kaarten.nummerComparator.compare(hoogsteKaart, slag.get(2)) < 0) {
                    System.out.println("optie:" + i + "verwijderd");
                    verwijderdekaarten.add(optiekaarten.get(i));
                    optiekaarten.remove(i);
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
}
