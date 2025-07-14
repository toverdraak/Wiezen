/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package merlijnsmislukkingen.wiezen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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

    @Override
    public void start(Stage stage) throws Exception  {
        BorderPane kaarten = new BorderPane();
        
        createDeck();
        Collections.shuffle(deck);
        deelKaarten();
        
        Group handView = new Group();
        double startAngle = -20;         // Beginrotatie
        double angleIncrement = 3.2;     // Per kaart iets meer draaien
        double overlap = 35;             // Hoeveel kaarten overlappen
        double startX = 200;             // Beginpositie X
        double y = 20;
        
        for (int i = 0; i <splr1.size(); i++) {
            Kaarten kaart =splr1.get(i);
            String soort = kaart.getSoort();
            int nr = kaart.getNummer();
            String location = "/"+ soort+nr+".png";
            System.out.println(location);
            Image image = new Image(Wiezen.class.getResourceAsStream(location), 80,120, true, false);
            //kaarten.getChildren().add(new ImageView(image));
            ImageView kaartView = new ImageView(image);
            double angle = startAngle + i * angleIncrement;
            double x = startX + i * overlap;

            kaartView.setLayoutX(x);
            kaartView.setLayoutY(y);
            kaartView.setRotate(angle);

            handView.getChildren().add(kaartView);
        }
        kaarten.setBottom(handView);

        
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
}