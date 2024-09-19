/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SAE.MODELLE;

/**
 * La classe Vols représente un vol avec des informations sur l'identifiant, 
 * l'aéroport de départ, l'aéroport de destination, l'horaire et la durée.
 * <p>
 * Cette classe est utilisée pour manipuler des informations sur les vols dans le programme.
 * </p>
 * 
 * <p>Auteur : Hugsc</p>
 */
public class Vols {
    private String id, aero_dep, aero_dest;
    private Horaire horaire;
    private int durée;

    /**
     * Constructeur de la classe Vols.
     * 
     * @param id l'identifiant du vol
     * @param aero_dep l'aéroport de départ
     * @param aero_dest l'aéroport de destination
     * @param horaire l'horaire du vol
     * @param durée la durée du vol en minutes
     */
    public Vols(String id, String aero_dep, String aero_dest, Horaire horaire, int durée) {
        this.id = id;
        this.aero_dep = aero_dep;
        this.aero_dest = aero_dest;
        this.horaire = horaire;
        this.durée = durée;
    }

    /**
     * Retourne l'identifiant du vol.
     * 
     * @return l'identifiant du vol
     */
    public String getId() {
        return id;
    }

    /**
     * Retourne l'aéroport de départ.
     * 
     * @return l'aéroport de départ
     */
    public String getAero_dep() {
        return aero_dep;
    }

    /**
     * Retourne l'aéroport de destination.
     * 
     * @return l'aéroport de destination
     */
    public String getAero_dest() {
        return aero_dest;
    }

    /**
     * Retourne l'horaire du vol.
     * 
     * @return l'horaire du vol
     */
    public Horaire getHoraire() {
        return horaire;
    }

    /**
     * Retourne la durée du vol en minutes.
     * 
     * @return la durée du vol en minutes
     */
    public int getDurée() {
        return durée;
    }
}
