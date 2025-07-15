/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package merlijnsmislukkingen.wiezen;

import java.util.ArrayList;
import java.util.List;

/**index
 *
 * @author merlijn
 */
public class Team {
    public List<String> teamledenvragers = new ArrayList<>();
    public List<String> teamledenpassers = new ArrayList<>();
    public Team(Bot teamlid1, Bot teamlid2, boolean teamdoel, Bot rest) {
        if (teamdoel) {
            System.out.println("team 1  bestaat uit "+ teamlid1.getName()+ " en "+ teamlid2.getName()+ " zij moeten 8 rondes winnen");
            teamledenvragers.add(teamlid1.getName());
            teamledenvragers.add(teamlid2.getName());
            System.out.println("Jij zit in een team met " + rest.getName());
            teamledenpassers.add("jij");
            teamledenpassers.add(rest.getName());
        } else {
            System.out.println("team 2 bestaat uit "+ teamlid1.getName()+ " en "+ teamlid2.getName()+ " zij moeten 6 rondes winnen");
            System.out.println("Jij zit in een team met " + rest.getName());
            teamledenvragers.add("jij");
            teamledenvragers.add(rest.getName());
        }
    }
}
