/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package merlijnsmislukkingen.wiezen;

/**
 *
 * @author merlijn
 */
public class Kaarten {
    private String soort;
    private String nummer;
    public Kaarten(String soort, String nummer) {
        this.soort = soort;
        this.nummer = nummer;
    } 
    public String welkeKaart() {
        return soort+ "" +nummer;
    }
}