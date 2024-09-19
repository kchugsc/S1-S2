/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SAE.VUE;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.GeoPosition;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * LinePainter
 * <p>
 * Classe permettant de dessiner une ligne entre deux positions géographiques sur une carte.
 * </p>
 * 
 * <p>Auteur : Hissam</p>
 */
public class LinePainter implements Painter<JXMapViewer> {
    private final GeoPosition gp1;
    private final GeoPosition gp2;
    private final Color color;

    /**
     * Constructeur de la classe LinePainter.
     * 
     * @param gp1 la première position géographique
     * @param gp2 la deuxième position géographique
     * @param color la couleur de la ligne
     */
    public LinePainter(GeoPosition gp1, GeoPosition gp2, Color color) {
        this.gp1 = gp1;
        this.gp2 = gp2;
        this.color = color;
    }

    /**
     * Méthode de peinture pour dessiner la ligne sur la carte.
     * 
     * @param g l'objet Graphics2D utilisé pour dessiner
     * @param map la carte JXMapViewer
     * @param w la largeur de la carte
     * @param h la hauteur de la carte
     */
    @Override
    public void paint(Graphics2D g, JXMapViewer map, int w, int h) {
        g = (Graphics2D) g.create();

        // Convertir les coordonnées géographiques en coordonnées bitmap mondiales
        Point2D pt1 = map.getTileFactory().geoToPixel(gp1, map.getZoom());
        Point2D pt2 = map.getTileFactory().geoToPixel(gp2, map.getZoom());

        // Convertir en coordonnées d'écran
        Rectangle rect = map.getViewportBounds();
        int x1 = (int) (pt1.getX() - rect.getX());
        int y1 = (int) (pt1.getY() - rect.getY());
        int x2 = (int) (pt2.getX() - rect.getX());
        int y2 = (int) (pt2.getY() - rect.getY());

        g.setColor(color);
        g.setStroke(new BasicStroke(1));
        g.drawLine(x1, y1, x2, y2);

        g.dispose();
    }
}
