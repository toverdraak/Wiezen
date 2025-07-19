/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package merlijnsmislukkingen.wiezen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author merlijn    private boolean splrvraagt;

 */
public class Wiezen extends Application {

    private final List<Kaarten> deck = new ArrayList<>();
    private final List<Kaarten> splr1 = new ArrayList<>();
    private final List<Kaarten> splr2 = new ArrayList<>();
    private final List<Kaarten> splr3 = new ArrayList<>();
    private final List<Kaarten> splr4 = new ArrayList<>();
    public String troef;

    public int spelersgevraagd;
    Font buttonfont = new Font(20);
    private boolean actie;
    public static boolean winner;
    private int ronde = 1;
    public Bot splr = new Bot("Jij");
    Bot bot1 = new Bot("bot1");
    Bot bot2 = new Bot("bot2");
    Bot bot3 = new Bot("bot3");
    public static Team vragersTeam = new Team(true);
    public static Team passersTeam = new Team(false);

    Text scorevragers = new Text();
    Text scorepassers = new Text();

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane kaarten = new BorderPane();
        createDeck();
        Kaarten troefkaart = randomCard();
        String troefkaartsoort = troefkaart.getSoort();
        setTroef(troefkaartsoort);
        Kaarten.setTroef(troefkaartsoort);
        Bot.setTroef(troefkaartsoort);
        int nrtroefkaart = troefkaart.getNummer();
        
        
        scorevragers.setFont(buttonfont);
        scorepassers.setFont(buttonfont);
        
        scorevragers.setTranslateX(20);
        scorepassers.setTranslateX(-20);
        
        scorevragers.setTranslateY(300);
        scorepassers.setTranslateY(300);
        
        
        Collections.shuffle(deck);
        deelKaarten();
        Collections.sort(splr1);
        Collections.sort(splr2);
        Collections.sort(splr3);
        Collections.sort(splr4);


        bot1.setList(splr2);
        bot2.setList(splr3);
        bot3.setList(splr4);
        bot1.setId(1);
        bot2.setId(2);
        bot3.setId(3);
        splr.setId(0);
        
        Group handView = new Group();
        double startAngle = -20;         // Beginrotatie
        double angleIncrement = 3.2;     // Per kaart iets meer draaien
        double overlap = 55;             // Hoeveel kaarten overlappen
        double startX = 500;             // Beginpositie X
        double y = 20;
        
        Group midden = new Group();

        Group buttons = new Group();
        double bstartX = 200;
        double bstartY = 0;

        Button vraag = new Button("Vraag");
        vraag.setPrefHeight(70);
        vraag.setPrefWidth(150);
        vraag.setLayoutX(bstartX);
        vraag.setLayoutY(bstartY);
        vraag.setFont(buttonfont);
        buttons.getChildren().add(vraag);
        double bx = startX + 100;
        Button pas = new Button("Pas");
        pas.setFont(buttonfont);
        pas.setLayoutX(bx);
        pas.setLayoutY(bstartY);
        pas.setPrefHeight(70);
        pas.setPrefWidth(200);
        buttons.getChildren().add(pas);
        bx = startX + 1000;
        Button rondes = new Button("volgende ronde");
        rondes.setFont(buttonfont);
        rondes.setLayoutX(bx);
        rondes.setLayoutY(bstartY);
        rondes.setPrefHeight(70);
        rondes.setPrefWidth(150);
        rondes.setDisable(true);
        buttons.getChildren().add(rondes);
        int middenvan = splr1.size() / 2;

        for (int i = 0; i < splr1.size(); i++) {
            Kaarten kaart = splr1.get(i);
            String soort = kaart.getSoort();
            int nr = kaart.getNummer();
            String location = "/" + soort + nr + ".png";
            Image image = new Image(Wiezen.class.getResourceAsStream(location), 120, 180, true, true);
            //kaarten.getChildren().add(new ImageView(image));
            ImageView kaartView = new ImageView(image);
            double angle = startAngle + i * angleIncrement;
            double x = startX + i * overlap;
            double offsetY = y + Math.pow(i - middenvan, 2) * 0.8;

            kaartView.setLayoutX(x);
            kaartView.setLayoutY(offsetY);
            kaartView.setRotate(angle);

            handView.getChildren().add(kaartView);
        }
        String location = "/" + troef + nrtroefkaart + ".png";
        Image image = new Image(Wiezen.class.getResourceAsStream(location), 120, 180, true, true);
        ImageView kaartView = new ImageView(image);

        pas.setOnAction(e -> {
            passersTeam.addMember(splr);
            actie = true;
            List<Bot> vragers = new ArrayList<>();
            List<Bot> passer = new ArrayList<>();
            vraag.setDisable(true);
            pas.setDisable(true);
            verdeelBots();
            checkTeams();
            rondes.setDisable(false);
            Round.setWinner(splr);
            splr.setIsWinner();
            scorepassers.setText(passersTeam.getName(0)+" & "+ passersTeam.getName(1)+" met "+passersTeam.getSlagen()+" slagen");
            Round round1 = new Round(handView, splr1, kaartView, midden, bot1,bot2,bot3,rondes, splr);
        });
        vraag.setOnAction(e -> {
            vragersTeam.addMember(splr);
            actie = false;
            spelersgevraagd++;
            vraag.setDisable(true);
            pas.setDisable(true);
            System.out.println(spelersgevraagd);
            verdeelBots();
            checkTeams();
            rondes.setDisable(false);
            Round.setWinner(splr);
            splr.setIsWinner();
            scorevragers.setText(vragersTeam.getName(0)+" & "+ vragersTeam.getName(1)+" met "+vragersTeam.getSlagen()+" slagen");
            scorepassers.setText(passersTeam.getName(0)+" & "+ passersTeam.getName(1)+" met "+passersTeam.getSlagen()+" slagen");
            Round round1 = new Round(handView, splr1, kaartView, midden, bot1, bot2, bot3, rondes, splr);
        });

        rondes.setOnAction(e -> {
            if (Round.getWinner() != null) {
                Round.resetWinner();
                rondes.setDisable(true);
                midden.getChildren().clear();
                if (ronde == 12){
                    Round round12 = new Round(handView,splr1,kaartView,midden,bot1,bot2,bot3,rondes,splr);
                    Bot.getGelegdeKaarten();
                }
                if (ronde == 11){
                    Round round12 = new Round(handView,splr1,kaartView,midden,bot1,bot2,bot3,rondes,splr);
                }
                if (ronde == 10){
                    Round round11 = new Round(handView,splr1,kaartView,midden,bot1,bot2,bot3,rondes,splr);
                }
                if (ronde == 9){
                    Round round10 = new Round(handView,splr1,kaartView,midden,bot1,bot2,bot3,rondes,splr);
                }
                if (ronde == 8){
                    Round round9 = new Round(handView,splr1,kaartView,midden,bot1,bot2,bot3,rondes,splr);
                }
                if (ronde == 7){
                    Round round8 = new Round(handView,splr1,kaartView,midden,bot1,bot2,bot3,rondes,splr);
                }
                if (ronde == 6){
                    Round round7 = new Round(handView,splr1,kaartView,midden,bot1,bot2,bot3,rondes,splr);
                }
                if (ronde == 5){
                    Round round6 = new Round(handView,splr1,kaartView,midden,bot1,bot2,bot3,rondes,splr);
                }
                if (ronde == 4){
                    Round round5 = new Round(handView,splr1,kaartView,midden,bot1,bot2,bot3,rondes,splr);
                }
                if (ronde == 3){
                    Round round4 = new Round(handView,splr1,kaartView,midden,bot1,bot2,bot3,rondes,splr);
                }
                if (ronde == 2){
                    Round round3 = new Round(handView,splr1,kaartView,midden,bot1,bot2,bot3,rondes,splr);
                }
                if (ronde == 1){
                    Round round2 = new Round(handView,splr1,kaartView,midden,bot1,bot2,bot3,rondes,splr);
                }
                updateTexts();
                Bot.getGelegdeKaarten();
                ronde++;
            }
        });
        
        midden.getChildren().add(kaartView);
        BorderPane onderkant = new BorderPane(handView);
        onderkant.setMinHeight(200);
        kaarten.setLeft(scorevragers);
        kaarten.setRight(scorepassers);
        kaarten.setBottom(onderkant);
        kaarten.setCenter(midden);
        kaarten.setTop(buttons);
        kaarten.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene kaarttafel = new Scene(kaarten, 1500, 900, Color.GREEN);
        stage.setTitle("Wiezen");
        stage.setScene(kaarttafel);
        stage.setX(3000);
        stage.setY(70);
        stage.show();
    }

    void checkTeams() {
        int vragersCount = vragersTeam.getMembers().size();
        System.out.println("vragers: " + vragersTeam.getMembers());
        System.out.println("passers: " + passersTeam.getMembers());
        if (vragersTeam.getMembers().size() != 2) {
            System.out.println("We hebben " + vragersCount + " vragers en " + (4 - vragersCount) + " passers, dus we kunnen niet veel doen.");
            System.exit(0);
        }
    }
    void updateTexts() {
        scorevragers.setText(vragersTeam.getName(0) + " & " + vragersTeam.getName(1) + " met " + vragersTeam.getSlagen() + " slagen");
        scorepassers.setText(passersTeam.getName(0) + " & " + passersTeam.getName(1) + " met " + passersTeam.getSlagen() + " slagen");
    }

    void verdeelBots() {
        boolean splr1vraag = bot1.getActie();
        if (splr1vraag == true) {
            System.out.println("bot1vraagt");
            spelersgevraagd++;
            vragersTeam.addMember(bot1);
        } else {
            passersTeam.addMember(bot1);
        }
        if (spelersgevraagd < 2) {
            boolean splr2vraag = bot2.getActie();
            if (splr2vraag == true) {
                System.out.println("bot2vraagt");
                spelersgevraagd++;
                vragersTeam.addMember(bot2);
            } else {
                passersTeam.addMember(bot2);
            }
            if (spelersgevraagd < 2) {
                boolean splr3vraag = bot3.getActie();
                System.out.println("bot3vraagt");
                spelersgevraagd++;
                vragersTeam.addMember(bot3);
            } else {
                passersTeam.addMember(bot3);
            }

        } else {
            passersTeam.addMember(bot2);
            passersTeam.addMember(bot3);
        }
    }
    private void createDeck() {
        String[] soorten = {"harten", "ruiten", "klaveren", "schoppen"};
        int[] waardes = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};

        for (String soort : soorten) {
            for (int waarde : waardes) {
                Kaarten kaart = new Kaarten(soort, waarde);
                deck.add(kaart);
            }
        }
    }

    private void deelKaarten() {
        for (int i = 0; i < deck.size(); i++) {
            if (i % 4 == 0) {
                splr1.add(deck.get(i));
            } else if (i % 4 == 1) {
                splr2.add(deck.get(i));
            } else if (i % 4 == 2) {
                splr3.add(deck.get(i));
            } else {
                splr4.add(deck.get(i));
            }
        }

    }

    private Kaarten randomCard() {
        Random rkaart = new Random();
        int randomkaart = rkaart.nextInt(53);
        return deck.get(randomkaart);
    }

    public void setTroef(String soort) {
        troef = soort;
    }

    public String getTroef() {
        return troef;
    }
    public static void setSpelerWinner() {
        winner = true;
    }
    public static void setSpelerLoser() {
        winner = false;
    }
    public boolean getSpelerResult(){
        return winner;
    }
}