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

    static List<Team> teams = new ArrayList<>();

    List<Bot> teamLeden = new ArrayList<>();

    private int slagen = 0;

    private final boolean doel;
    
    public Team(boolean doel) {
        this.doel = doel;
        teams.add(this);
    }

    /**
     * Maak een team met spelers lid1 en lid2. Als het vragers zijn moet
     * doel true zijn, als het passers zijn moet doel false zijn.
     * @param doel
     * @param lid1
     * @param lid2 
     */
    public Team(boolean doel, Bot lid1, Bot lid2) {
        teamLeden.add(lid1);
        teamLeden.add(lid2);
        this.doel = doel;
    }

    public void addMember(Bot member) {
        System.out.println("ADD MEMBER " + member + " tot team " + (doel ? "vragers" : "passers"));
        teamLeden.add(member);
    }

    public List<Bot> getMembers() {
        return teamLeden;
    }

    public static Team getTeamByMember(Bot member) {
        for (Team candidate : teams) {
            if (candidate.getMembers().contains(member)) {
                return candidate;
            }
        }
        return null;
    }

    public boolean containsMember(Bot member) {
        return teamLeden.contains(member);
    }

    /**
     * Return het aantal slagen dat dit team momenteel heeft behaald.
     * @return 
     */
    public int getSlagen() {
        return slagen;
    }

    public String getName(int id) {
        return teamLeden.get(id).getName();
    }

//    public static String puntToevoegen(Bot winnend) {
//        for (int i = 0; i < 2; i++) {
//            a = teamledenvragers.get(i);
//            if (winnend.getId() == a.getId()) {
//                slagenvragers++;
//            }
//        }
//        for (int i = 0; i < 2; i++) {
//            a = teamledenpassers.get(i);
//            if (winnend.getId() == a.getId()) {
//                slagenpassers++;
//            }
//        }
//        return ("De vragers hebben al "+slagenvragers+ " en de passers al "+ slagenpassers);
//    }

    void addSlag() {
        slagen = slagen + 1;
    }
}
