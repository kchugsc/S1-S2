/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SAE.VUE;

import SAE.MODELLE.Aeroport;
import SAE.MODELLE.Vols;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * AeroportInfoWindow
 * Classe représentant une fenêtre d'information pour un aéroport.
 * <p>
 * Cette fenêtre affiche des informations sur les vols au départ et à l'arrivée de l'aéroport spécifié.
 * </p>
 * <p>
 * La fenêtre se ferme automatiquement lorsqu'elle perd le focus.
 * </p>
 * 
 * <p>Auteur : hugsc</p>
 */
public class AeroportInfoWindow extends JFrame {
    /**Nom de l'aeroport*/
    private Aeroport aeroport;
    
    /**Liste de vols qui va être afficher*/
    private List<Vols> vols;

    /**
     * Constructeur de la classe AeroportInfoWindow.
     * 
     * @param aeroport l'aéroport pour lequel les informations sont affichées
     * @param vols la liste des vols associés à l'aéroport
     */
    public AeroportInfoWindow(Aeroport aeroport, List<Vols> vols) {
        this.aeroport = aeroport;
        this.vols = vols;

        setTitle("Information sur l'aéroport: " + aeroport.getNom_aero());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        this.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                dispose();
            }
        });
    }

    /**
     * Initialise l'interface utilisateur de la fenêtre.
     */
    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        StringBuilder info = new StringBuilder();
        info.append("Nom de l'aéroport: ").append(aeroport.getNom_aero()).append("\n\n");
        info.append("Vols au départ:\n");
        for (Vols vol : vols) {
            if (vol.getAero_dep().equals(aeroport.getId())) {
                info.append("  - ").append(vol.getId()).append("\n");
            }
        }

        info.append("\nVols à l'arrivée:\n");
        for (Vols vol : vols) {
            if (vol.getAero_dest().equals(aeroport.getId())) {
                info.append("  - ").append(vol.getId()).append("\n");
            }
        }

        textArea.setText(info.toString());
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        add(panel);
    }
}
