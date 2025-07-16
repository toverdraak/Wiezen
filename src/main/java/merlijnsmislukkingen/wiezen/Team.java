/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package merlijnsmislukkingen.wiezen;

import java.util.ArrayList;
import java.util.List;

/**
 * index
 *
 * @author merlijn
 */
public class Team {

    public static List<Bot> teamledenvragers = new ArrayList<>();
    public static List<Bot> teamledenpassers = new ArrayList<>();
    private static Bot a = new Bot(null);
    public static int slagenvragers = 0;
    public static int slagenpassers = 0;

    public Team(Bot teamlid1, Bot teamlid2, boolean teamdoel, Bot rest, Bot splr) {
        if (teamdoel) {
            System.out.println("team 1  bestaat uit " + teamlid1.getName() + " en " + teamlid2.getName() + " zij moeten 8 rondes winnen");
            teamledenvragers.add(teamlid1);
            teamledenvragers.add(teamlid2);
            System.out.println("Jij zit in een team met " + rest.getName());
            teamledenpassers.add(splr);
            teamledenpassers.add(rest);
            rest.setTeammate();
        } else {
            System.out.println("team 2 bestaat uit " + teamlid1.getName() + " en " + teamlid2.getName() + " zij moeten 6 rondes winnen");
            System.out.println("Jij zit in een team met " + rest.getName());
            teamledenvragers.add(splr);
            teamledenvragers.add(rest);
            teamledenpassers.add(teamlid1);
            teamledenpassers.add(teamlid2);
            rest.setTeammate();
        }
    }

    public static String puntToevoegen(Bot winnend) {
        for (int i = 0; i < 2; i++) {
            a = teamledenvragers.get(i);
            if (winnend.getId() == a.getId()) {
                slagenvragers++;
            }
        }
        for (int i = 0; i < 2; i++) {
            a = teamledenpassers.get(i);
            if (winnend.getId() == a.getId()) {
                slagenpassers++;
            }
        }
        return ("De vragers hebben al "+slagenvragers+ " en de passers al "+ slagenpassers);
    }
    public static int getSlagenVragers(){
        return slagenvragers;
    }
    public static int getSlagenPassers(){
        return slagenpassers;
    }
    public static List<Bot> getVragers() {
        return teamledenvragers;
    }
    public static List<Bot> getPassers() {
        return teamledenpassers;
    }
    
}
