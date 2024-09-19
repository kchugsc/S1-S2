/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SAE.VUE;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Set;
import javax.swing.JButton;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.Painter;

/**
 * WaypointRender
 * <p>
 * Classe pour rendre les waypoints avec des boutons sur la carte.
 * </p>
 * <p>Auteur : Hissam</p>
 */
public class WaypointRender implements Painter<JXMapViewer> {
    private Set<MyWaypoint> waypoints;

    /**
     * Constructeur de la classe WaypointRender.
     * 
     * @param waypoints l'ensemble des waypoints à rendre
     */
    public WaypointRender(Set<MyWaypoint> waypoints) {
        this.waypoints = waypoints;
    }

    /**
     * Méthode de peinture pour dessiner les waypoints sur la carte.
     * 
     * @param g l'objet Graphics2D utilisé pour dessiner
     * @param map la carte JXMapViewer
     * @param width la largeur de la carte
     * @param height la hauteur de la carte
     */
    @Override
    public void paint(Graphics2D g, JXMapViewer map, int width, int height) {
        for (MyWaypoint wp : waypoints) {
            Point2D p = map.getTileFactory().geoToPixel(wp.getPosition(), map.getZoom());
            Rectangle rec = map.getViewportBounds();
            int x = (int) (p.getX() - rec.getX());
            int y = (int) (p.getY() - rec.getY());
            JButton cmd = wp.getButton();
            cmd.setLocation(x - cmd.getWidth() / 2, y - cmd.getHeight());

            // Ensure the button is added to the map's parent component
            if (cmd.getParent() != map) {
                map.add(cmd);
            }
        }
    }
}
