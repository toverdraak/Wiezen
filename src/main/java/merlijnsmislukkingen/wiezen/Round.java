/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package merlijnsmislukkingen.wiezen;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author merlijn
 */
public class Round  {

    public Kaarten gekozenKaart;
    public Kaarten BotGekozenKaart;
    public List<Kaarten> slag = new ArrayList<>();
    public static Bot winning = new Bot(null);
    public Round(Group handView, List<Kaarten> spelersKaarten, ImageView mid, Group midden, Bot bot1, Bot bot2, Bot bot3, Button welkeronde, Bot splr) {
        for (Node node : handView.getChildren()) {
            node.setDisable(false);
        }
        if (bot1.getIsWinner()) {
            System.out.println("bot1 komt uit");
            bot1.legEersteKaart(midden, slag);
            BotGekozenKaart = slag.get(0);
            Kaarten.setUitgekomenSoort(BotGekozenKaart.getSoort());
            bot2.legKaart(midden, BotGekozenKaart, slag);
            bot3.legKaart(midden, BotGekozenKaart, slag);
        } else if (bot2.getIsWinner()) {
            System.out.println("bot2 komt uit");
            bot2.legEersteKaart(midden, slag);
            BotGekozenKaart = slag.get(0);
            Kaarten.setUitgekomenSoort(BotGekozenKaart.getSoort());
            bot3.legKaart(midden, BotGekozenKaart, slag);
            bot1.legKaart(midden, BotGekozenKaart, slag);            
        } else if (bot3.getIsWinner()) {
            System.out.println("bot3 komt uit");
            bot3.legEersteKaart(midden, slag);
            BotGekozenKaart = slag.get(0);
            Kaarten.setUitgekomenSoort(BotGekozenKaart.getSoort());
            bot1.legKaart(midden, BotGekozenKaart, slag);
            bot2.legKaart(midden, BotGekozenKaart, slag);            
        }
        for (Node node : handView.getChildren()) {
            if (node instanceof ImageView) {
                ImageView kaartView = (ImageView) node;
                kaartView.setOnMouseClicked(e -> {
                    // Zoek de juiste Kaarten-object bij dit ImageView
                    int index = handView.getChildren().indexOf(kaartView);
                    if (index >= 0 && index < spelersKaarten.size()) {
                        if (splr.getIsWinner()) {
                            System.out.println("speler komt uit");
                            this.gekozenKaart = spelersKaarten.get(index);
                            Kaarten.setUitgekomenSoort(gekozenKaart.getSoort());
                        } else {
                            Kaarten gekozen = spelersKaarten.get(index);
                            boolean heeftSoortNog = spelersKaarten.stream().anyMatch(k -> k.getSoort().equals(BotGekozenKaart.getSoort()));
                            if (!gekozen.getSoort().equals(BotGekozenKaart.getSoort()) && heeftSoortNog) {
                                // Speler speelt verkeerde kleur terwijl hij het wel heeft
                                System.out.println("Ongeldige zet: je moet kleur volgen!");
                                return; // Stop verwerking
                            } else {
                                this.gekozenKaart = gekozen;
                                Kaarten.setUitgekomenSoort(BotGekozenKaart.getSoort());
                        }
                        }
                        String imagePath = "/" + this.gekozenKaart.getSoort() + this.gekozenKaart.getNummer() + ".png";
                        Image gekozenImage = new Image(Wiezen.class.getResourceAsStream(imagePath), 120, 180, true, true);
                        mid.setImage(gekozenImage);
                        midden.getChildren().remove(mid);
                        midden.getChildren().add(mid);
                        handView.getChildren().remove(kaartView);
                        spelersKaarten.remove(this.gekozenKaart);
                        slag.add(gekozenKaart);
//                        PauseTransition pause = new PauseTransition(Duration.seconds(1.0)); // of 1.5 voor iets meer vertraging
//                        pause.setOnFinished(ev -> {
                        if (splr.getIsWinner()) {
                            bot1.legKaart(midden, this.gekozenKaart, slag);
                            bot2.legKaart(midden, this.gekozenKaart, slag);
                            bot3.legKaart(midden, this.gekozenKaart, slag);
                        }
                        bot1.resetIsWinner();
                        bot2.resetIsWinner();
                        bot3.resetIsWinner();
                        splr.resetIsWinner();
//                       });
//                        pause.play();
                        // ❌ Disable alle kaarten
                        for (Node n : handView.getChildren()) {
                            n.setDisable(true);
                        }
                        Collections.sort(slag, Kaarten.SlagComparator);
                        Kaarten winnende = slag.get(3);
                        if (gekozenKaart == winnende) {
                            Wiezen.setSpelerWinner();
                            Round.setWinner(splr);
                            splr.setIsWinner();
                        } else {
                            Wiezen.setSpelerLoser();
                        }
                        if (bot1.getGelegdeKaart() == winnende) {
                            Round.setWinner(bot1);
                            bot1.setIsWinner();
                        }
                        if (bot2.getGelegdeKaart() == winnende) {
                            Round.setWinner(bot2);
                            bot2.setIsWinner();
                        }
                        else if (bot3.getGelegdeKaart() == winnende) {
                            Round.setWinner(bot3);
                            bot3.setIsWinner();
                        }
                        Bot.resetX();
                    }
                    System.out.println(Team.puntToevoegen(Round.getWinner()));
                    welkeronde.setDisable(false);
                });
            }
        }

    }

    public Kaarten getGekozenKaart() {
        return this.gekozenKaart;
    }
    public static void setWinner(Bot bot1) {
        winning = bot1;
        winning.setName(bot1.getName());
        winning.setId(bot1.getId());
        System.out.println("de winnaar"+winning.getId());
    }
    public static Bot getWinner() {
        return winning;
    }
    public static void resetWinner() {
        winning = null;
    }
} 
