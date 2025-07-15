/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package merlijnsmislukkingen.wiezen;

/**
 *
 * @author merlijn
 */
public class Team {
    public Team(Bot teamlid1, Bot teamlid2, boolean teamdoel) {
        if (teamdoel) {
            System.out.println("team bestaat uit"+ teamlid1.getName()+ " en "+ teamlid2.getName()+ "zij moeten 8 rondes winnen");
        } else {
            System.out.println("team bestaat uit"+ teamlid1.getName()+ " en "+ teamlid2.getName()+ "zij moeten 6 rondes winnen");
        }
    }
}
