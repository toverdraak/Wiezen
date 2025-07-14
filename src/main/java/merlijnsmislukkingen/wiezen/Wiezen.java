/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package merlijnsmislukkingen.wiezen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
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
        StackPane kaarten = new StackPane();
        
        createDeck();
        Collections.shuffle(deck);
        Scene kaarttafel = new Scene(kaarten, 900, 900, Color.GREEN);
        stage.setTitle("Wiezen");
        stage.setScene(kaarttafel);
        stage.setX(3000);
        stage.setY(70);
        stage.show();
    }
    private void createDeck() {
        String[] soorten = {"Harten", "Ruiten", "Klaveren", "Schoppen"};
        String[] waardes = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "V", "D", "R"};

        for (String soort : soorten) {
            for (String waarde : waardes) {
                Kaarten kaart = new Kaarten(soort, waarde);
                deck.add(kaart);
            }
        }
    }
    private void deelKaarten() {
        
    }
}