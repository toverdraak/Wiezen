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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author merlijn
 */
public class Wiezen extends Application {
    private List<Kaarten> deck = new ArrayList<>();
    private List<Kaarten> splr1 = new ArrayList<>(); 
    private List<Kaarten> splr2 = new ArrayList<>();
    private List<Kaarten> splr3 = new ArrayList<>();
    private List<Kaarten> splr4 = new ArrayList<>();
    public String troef;
    @Override
    public void start(Stage stage) throws Exception  {
        BorderPane kaarten = new BorderPane();
        createDeck();
        Kaarten troefkaart = randomCard();
        String troefkaartsoort = troefkaart.getSoort();
        setTroef(troefkaartsoort);
        Kaarten.setTroef(troefkaartsoort);
        int nrtroefkaart = troefkaart.getNummer();
        
        Collections.shuffle(deck);
        deelKaarten();
        Collections.sort(splr1);
        
        Group handView = new Group();
        double startAngle = -20;         // Beginrotatie
        double angleIncrement = 3.2;     // Per kaart iets meer draaien
        double overlap = 55;             // Hoeveel kaarten overlappen
        double startX = 500;             // Beginpositie X
        double y = 20;
        
        Group buttons = new Group();
        double bstartX = 200;
        double bstartY = 0;
        
        Button vraag = new Button("Vraag");
        vraag.setScaleX(3);
        vraag.setScaleY(3);
        vraag.setLayoutX(bstartX);
        vraag.setLayoutY(bstartY);
        buttons.getChildren().add(vraag);
        double bx = startX + 100;
        Button pas = new Button("Pas");
        pas.setLayoutX(bx);
        pas.setLayoutY(bstartY);
        pas.setScaleX(2);
        pas.setScaleY(2);
        buttons.getChildren().add(pas);
        int midden = splr1.size() / 2;
        
        for (int i = 0; i <splr1.size(); i++) {
            Kaarten kaart =splr1.get(i);
            String soort = kaart.getSoort();
            int nr = kaart.getNummer();
            String location = "/"+ soort+nr+".png";
            Image image = new Image(Wiezen.class.getResourceAsStream(location), 120,180, true, true);
            //kaarten.getChildren().add(new ImageView(image));
            ImageView kaartView = new ImageView(image);
            double angle = startAngle + i * angleIncrement;
            double x = startX + i * overlap;
            double offsetY = y + Math.pow(i - midden, 2) * 0.8;

            kaartView.setLayoutX(x);
            kaartView.setLayoutY(offsetY);
            kaartView.setRotate(angle);

            handView.getChildren().add(kaartView);
        }
        String location = "/"+ troef+nrtroefkaart+".png";
        Image image = new Image(Wiezen.class.getResourceAsStream(location), 120,180, true, true);
        ImageView kaartView = new ImageView(image);
        
        
        BorderPane onderkant = new BorderPane(handView);
        onderkant.setMinHeight(200);
        kaarten.setBottom(onderkant);
        kaarten.setCenter(kaartView);
        kaarten.setTop(buttons);
        kaarten.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene kaarttafel = new Scene(kaarten, 1500, 900, Color.GREEN);
        stage.setTitle("Wiezen");
        stage.setScene(kaarttafel);
        stage.setX(3000);
        stage.setY(70);
        stage.show();
    }
    private void createDeck() {
        String[] soorten = {"harten", "ruiten", "klaveren", "schoppen"};
        int[] waardes = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,14};

        for (String soort : soorten) {
            for (int waarde : waardes) {
                Kaarten kaart = new Kaarten(soort, waarde);
                deck.add(kaart);
            }
        }
    }
    private void deelKaarten() {
        for (int i = 0; i < deck.size(); i++) {
            if (i % 4 == 0) 
                splr1.add(deck.get(i));
            else if (i % 4 == 1) 
                splr2.add(deck.get(i));
            else   if (i % 4 == 2) 
                splr3.add(deck.get(i));
            else 
                splr4.add(deck.get(i));
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
}