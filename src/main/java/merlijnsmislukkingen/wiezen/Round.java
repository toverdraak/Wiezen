/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package merlijnsmislukkingen.wiezen;

import java.util.List;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author merlijn
 */
public class Round {
    public static Kaarten gekozenKaart;
    public Round(Group handView, List<Kaarten> spelersKaarten, ImageView mid, Group midden, Bot bot1, Bot bot2, Bot bot3) {
        for (Node node : handView.getChildren()) {
            if (node instanceof ImageView) {
                ImageView kaartView = (ImageView) node;
                kaartView.setOnMouseClicked(e -> {
                    // Zoek de juiste Kaarten-object bij dit ImageView
                    int index = handView.getChildren().indexOf(kaartView);
                    if (index >= 0 && index < spelersKaarten.size()) {
                        Kaarten gekozenKaart = spelersKaarten.get(index);
                        String imagePath = "/" + gekozenKaart.getSoort() + gekozenKaart.getNummer() + ".png";
                        Image gekozenImage = new Image(Wiezen.class.getResourceAsStream(imagePath), 120, 180, true, true);
                        mid.setImage(gekozenImage);
                        midden.getChildren().add(mid);
                        handView.getChildren().remove(kaartView);
                        spelersKaarten.remove(gekozenKaart);
                        // ❌ Disable alle kaarten
                        for (Node n : handView.getChildren()) {
                            n.setDisable(true);
                        }
                    }
                });
            }
        }
    
    }
    public static Kaarten getGekozenKaart() {
        return gekozenKaart;
    }
}