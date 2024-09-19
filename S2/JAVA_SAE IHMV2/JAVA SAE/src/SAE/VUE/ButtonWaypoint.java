/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SAE.VUE;

import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * ButtonWaypoint
 * <p>
 * Classe représentant un bouton pour les waypoints.
 * </p>
 * <p>
 * Ce bouton utilise une icône personnalisée et ajuste automatiquement sa taille 
 * en fonction de la taille de l'icône. Le curseur change en une main lorsqu'il survole le bouton.
 * </p>
 * <p>Auteur : Hissam</p>
 */
public class ButtonWaypoint extends JButton {

    /**
     * Constructeur de la classe ButtonWaypoint.
     * <p>
     * Initialise le bouton avec une icône de waypoint et définit les propriétés du bouton.
     * </p>
     */
    public ButtonWaypoint() {
        setContentAreaFilled(false);
        ImageIcon icon = new ImageIcon(getClass().getResource("check-point.png"));  // Assurez-vous que le chemin est correct
        setIcon(icon);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setSize(icon.getIconWidth(), icon.getIconHeight());  // Adaptez la taille en fonction de l'icône
        setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));  // Fixe la taille préférée
    }
}
