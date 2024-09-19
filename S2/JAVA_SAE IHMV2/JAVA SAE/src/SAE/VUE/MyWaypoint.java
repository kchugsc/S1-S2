/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SAE.VUE;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import SAE.MODELLE.Aeroport;
import SAE.MODELLE.Vols;

/**
 * MyWaypoint
 * <p>
 * Classe représentant un waypoint avec un bouton interactif.
 * </p>
 * <p>
 * Ce waypoint contient des informations sur un aéroport et une liste de vols associés.
 * Le bouton interactif affiche une fenêtre d'information sur l'aéroport lorsqu'il est cliqué.
 * </p>
 * 
 * <p>Auteur : Hissam</p>
 */
public class MyWaypoint extends DefaultWaypoint {
    private String name;
    private JButton button;
    private Aeroport aero;
    private List<Vols> vols;

    /**
     * Constructeur de la classe MyWaypoint.
     * 
     * @param name le nom du waypoint
     * @param coord les coordonnées géographiques du waypoint
     * @param aero l'aéroport associé au waypoint
     * @param vols la liste des vols associés à l'aéroport
     */
    public MyWaypoint(String name, GeoPosition coord, Aeroport aero, List<Vols> vols) {
        super(coord);
        this.name = name;
        this.aero = aero;
        this.vols = vols;
        initButton();
    }

    /**
     * Retourne l'aéroport associé au waypoint.
     * 
     * @return l'aéroport associé
     */
    public Aeroport getAero() {
        return aero;
    }

    /**
     * Définit l'aéroport associé au waypoint.
     * 
     * @param aero l'aéroport à associer
     */
    public void setAero(Aeroport aero) {
        this.aero = aero;
    }

    /**
     * Initialise le bouton interactif du waypoint.
     */
    private void initButton() {
        button = new ButtonWaypoint();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("click " + name);
                AeroportInfoWindow wd = new AeroportInfoWindow(aero, vols);
                wd.setVisible(true);
            }
        });
    }

    /**
     * Retourne le nom du waypoint.
     * 
     * @return le nom du waypoint
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le nom du waypoint.
     * 
     * @param name le nom à définir
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne le bouton interactif du waypoint.
     * 
     * @return le bouton interactif
     */
    public JButton getButton() {
        return button;
    }

    /**
     * Définit le bouton interactif du waypoint.
     * 
     * @param button le bouton à définir
     */
    public void setButton(JButton button) {
        this.button = button;
    }
}
