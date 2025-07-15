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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author merlijn
 */
public class Round  {

    public Kaarten gekozenKaart;
    public List<Kaarten> slag = new ArrayList<>();
    public Round(Group handView, List<Kaarten> spelersKaarten, ImageView mid, Group midden, Bot bot1, Bot bot2, Bot bot3) {
        for (Node node : handView.getChildren()) {
            if (node instanceof ImageView) {
                ImageView kaartView = (ImageView) node;
                kaartView.setOnMouseClicked(e -> {
                    // Zoek de juiste Kaarten-object bij dit ImageView
                    int index = handView.getChildren().indexOf(kaartView);
                    if (index >= 0 && index < spelersKaarten.size()) {
                        this.gekozenKaart = spelersKaarten.get(index);
                        Kaarten.setUitgekomenSoort(gekozenKaart.getSoort());
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
                            bot1.legKaart(midden, this.gekozenKaart, slag);
                            bot2.legKaart(midden, this.gekozenKaart, slag);
                            bot3.legKaart(midden, this.gekozenKaart, slag);
//                       });
//                        pause.play();
                        // ❌ Disable alle kaarten
                        for (Node n : handView.getChildren()) {
                            n.setDisable(true);
                        }
                        Collections.sort(slag, Kaarten.SlagComparator);
                        Kaarten winnende = slag.get(3);
                        if (gekozenKaart == winnende) {
                            System.out.println("speler wint");
                        } 
                        if (bot1.getGelegdeKaart() == winnende) {
                            System.out.println("bot1 wint");
                        }
                        if (bot2.getGelegdeKaart() == winnende) {
                            System.out.println("bot2 wint");
                        }
                        else {
                            System.out.println("bot3 wint");
                        }
                    }
                });
            }
        }

    }

    public Kaarten getGekozenKaart() {
        return this.gekozenKaart;
    }

}
