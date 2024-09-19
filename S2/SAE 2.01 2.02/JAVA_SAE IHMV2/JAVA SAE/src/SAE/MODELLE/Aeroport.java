/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SAE.MODELLE;

/**
 * La classe Aeroport représente un aéroport avec ses coordonnées géographiques.
 * <p>
 * Elle permet de convertir les coordonnées géographiques de l'aéroport en coordonnées cartésiennes.
 * </p>
 * 
 * <p>Les coordonnées sont fournies sous forme de degrés, minutes et secondes pour la latitude (NS) et la longitude (OE).</p>
 * 
 * <p>Le rayon de la Terre est utilisé pour les calculs de conversion.</p>
 * 
 * @author hugsc
 */
public class Aeroport {
    private static final double RAYON_TERRE = 6371.0;
    private String id, nom_aero, coord_ns, coord_oe;
    private int degre_ns, minutes_ns, secondes_ns, degre_oe, minutes_oe, secondes_oe;

    /**
     * Constructeur de la classe Aeroport.
     * 
     * @param id l'identifiant de l'aéroport
     * @param nom_aero le nom de l'aéroport
     * @param degre_ns les degrés de la latitude
     * @param minutes_ns les minutes de la latitude
     * @param secondes_ns les secondes de la latitude
     * @param coord_ns le repère nord/sud (N/S)
     * @param degre_oe les degrés de la longitude
     * @param minutes_oe les minutes de la longitude
     * @param secondes_oe les secondes de la longitude
     * @param coord_oe le repère est/ouest (E/O)
     */
    public Aeroport(String id, String nom_aero, int degre_ns, int minutes_ns, int secondes_ns, String coord_ns, int degre_oe, int minutes_oe, int secondes_oe, String coord_oe) {
        this.id = id;
        this.nom_aero = nom_aero;
        this.degre_ns = degre_ns;
        this.minutes_ns = minutes_ns;
        this.secondes_ns = secondes_ns;
        this.coord_ns = coord_ns;
        this.degre_oe = degre_oe;
        this.minutes_oe = minutes_oe;
        this.secondes_oe = secondes_oe;
        this.coord_oe = coord_oe;
    }

    /**
     * Calcule les coordonnées cartésiennes de l'aéroport à partir de ses coordonnées géographiques.
     * 
     * @return un tableau contenant les coordonnées cartésiennes {latitude, longitude}
     */
    public double[] calculerCoordCartesienne() {
        int coeflat;
        
        if (this.coord_ns.equals("N"))
            coeflat = 1;
        else
            coeflat = -1;

        int coeflong;
        if (this.coord_oe.equals("E"))
            coeflong = 1;
        else
            coeflong = -1;

        double latitude = coeflat * (this.degre_ns + (this.minutes_ns / 60.0) + (this.secondes_ns / 3600.0));
        double longitude = coeflong * (this.degre_oe + (this.minutes_oe / 60.0) + (this.secondes_oe / 3600.0));

        return new double[] { latitude, longitude };
    }

    /**
     * Retourne l'identifiant de l'aéroport.
     * 
     * @return l'identifiant de l'aéroport
     */
    public String getId() {
        return id;
    }

    /**
     * Retourne le nom de l'aéroport.
     * 
     * @return le nom de l'aéroport
     */
    public String getNom_aero() {
        return nom_aero;
    }

    /**
     * Retourne le repère nord/sud de l'aéroport.
     * 
     * @return le repère nord/sud de l'aéroport
     */
    public String getCoord_ns() {
        return coord_ns;
    }

    /**
     * Retourne le repère est/ouest de l'aéroport.
     * 
     * @return le repère est/ouest de l'aéroport
     */
    public String getCoord_oe() {
        return coord_oe;
    }

    /**
     * Retourne les degrés de la latitude de l'aéroport.
     * 
     * @return les degrés de la latitude de l'aéroport
     */
    public int getDegre_ns() {
        return degre_ns;
    }

    /**
     * Retourne les minutes de la latitude de l'aéroport.
     * 
     * @return les minutes de la latitude de l'aéroport
     */
    public int getMinutes_ns() {
        return minutes_ns;
    }

    /**
     * Retourne les secondes de la latitude de l'aéroport.
     * 
     * @return les secondes de la latitude de l'aéroport
     */
    public int getSecondes_ns() {
        return secondes_ns;
    }

    /**
     * Retourne les degrés de la longitude de l'aéroport.
     * 
     * @return les degrés de la longitude de l'aéroport
     */
    public int getDegre_oe() {
        return degre_oe;
    }

    /**
     * Retourne les minutes de la longitude de l'aéroport.
     * 
     * @return les minutes de la longitude de l'aéroport
     */
    public int getMinutes_oe() {
        return minutes_oe;
    }

    /**
     * Retourne les secondes de la longitude de l'aéroport.
     * 
     * @return les secondes de la longitude de l'aéroport
     */
    public int getSecondes_oe() {
        return secondes_oe;
    }
}
