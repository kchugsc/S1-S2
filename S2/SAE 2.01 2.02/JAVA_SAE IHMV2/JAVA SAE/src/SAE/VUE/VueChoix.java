/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SAE.VUE;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.graphstream.graph.Graph;

/**
 * La classe VueChoix représente la fenêtre de choix entre visualiser les vols et aéroports
 * ou visualiser les graphes. Elle permet à l'utilisateur de naviguer entre les deux modes.
 * 
 * @author hugsc
 * @version 1.0
 */
public class VueChoix extends JFrame{
    /** Un bouton pour afficher la carte. */
    private JButton jbutton_carte;

    /** Un bouton pour afficher le graphe. */
    private JButton jbutton_graphe;

    /** Une étiquette pour afficher le titre de la carte. */
    private JLabel label_carte;

    /** Une étiquette pour afficher le titre du graphe. */
    private JLabel label_graphe;

    /** Un panneau pour organiser les composants de l'interface graphique. */
    private JPanel pano;

    /** Un objet Graph pour stocker le graphe. */
    private Graph graph;

    /** Un objet VueCarte pour afficher la carte. */
    private VueCarte carte;

    /** Un objet VueGraphe pour afficher le graphe. */
    private VueGraphe graphe;
    
    /**
     * Constructeur de la classe VueChoix. Initialise les composants de la fenêtre
     * et configure les actions des différents éléments.
     */
    public VueChoix() {
        this.setTitle("Choix type");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        //Mets une image pour le logo de l'application
        ImageIcon icon = new ImageIcon("1562040.png");
        this.setIconImage(icon.getImage());
        
        init();
        
        //Demande au pc la charte graphique associé
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        //zoom sur le view du graphe
        jbutton_carte.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                carte.setVisible(true);
                VueChoix.this.dispose();
            }
        });
        jbutton_graphe.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                graphe.setVisible(true);
                VueChoix.this.dispose();
            }
            
        });
        
        //Ecouteur de l'etat de la fenetre
        this.addWindowListener(new WindowAdapter(){
                @Override
                public void windowOpened(WindowEvent e) {
                }
                
                //Lorsque la fenetre est entrain de se fermer, l'utilisateur est demander la confirmation
                @Override
                public void windowClosing(WindowEvent e) {
                    int choice = JOptionPane.showConfirmDialog(VueChoix.this, "Voulez-vous fermer la fenetre", "Fermeture", JOptionPane.YES_NO_OPTION);
                    if(choice == JOptionPane.YES_OPTION){
                       System.exit(0); 
                    }
                }

                @Override
                public void windowClosed(WindowEvent e) {
                }

                @Override
                public void windowIconified(WindowEvent e) {
                }

                @Override
                public void windowDeiconified(WindowEvent e) {
                }

                @Override
                public void windowActivated(WindowEvent e) {
                }

                @Override
                public void windowDeactivated(WindowEvent e) {
                }
            }); 
    }

    /**
     * Initialise les composants de la fenêtre.
     */
    private void init() {
        //taille qui s'adapte en fonction de l'écran
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.8);
        int height = (int) (screenSize.height * 0.8);
        
        //Initialise VueCarte et VueGraphe afin que l'utilisateur n'est pas besoin d'attendre
        carte = new VueCarte();
        graphe =new VueGraphe();
        graphe.setVueCarte(carte);
        carte.setVueGraphe(graphe);
        
        //Initialise le pano
        pano=new JPanel();
        pano.setLayout(new GridBagLayout());
        pano.setBackground(Color.white);
        pano.setPreferredSize(new Dimension(width, height));
        
        //Initialisation des labels
        label_carte = new JLabel("Vols et Aeroports", JLabel.CENTER);
        label_graphe = new JLabel("Graphes", JLabel.CENTER);
        
        //initialisation des boutons
        Dimension buttonSize = new Dimension(400, 400);
        ImageIcon Iconmap = new ImageIcon(getClass().getResource("carte.png"));
        Image ImgMap = Iconmap.getImage();
        Image imgCorrect = ImgMap.getScaledInstance(buttonSize.width, buttonSize.height, Image.SCALE_SMOOTH);
        ImageIcon IconCorrect = new ImageIcon(imgCorrect);
        jbutton_carte = new JButton("", IconCorrect);
        
        ImageIcon Iconmap2 = new ImageIcon(getClass().getResource("graphe.png"));
        Image ImgMap2 = Iconmap2.getImage();
        Image imgCorrect2 = ImgMap2.getScaledInstance(buttonSize.width, buttonSize.height, Image.SCALE_SMOOTH);
        ImageIcon IconCorrect2 = new ImageIcon(imgCorrect2);
        jbutton_graphe = new JButton("", IconCorrect2);
        
        //Initialisation cont afin de positionner les composant
        GridBagConstraints cont=new GridBagConstraints();
        
        cont.fill=GridBagConstraints.BOTH;
        cont.insets = new Insets(5,5,5,5);
        
        cont.gridx=0;
        cont.gridy=0;
        pano.add(label_carte,cont);
        
        cont.gridx++;
        pano.add(label_graphe,cont);
         
        cont.gridy++;
        pano.add(jbutton_graphe,cont);
        
        cont.gridx--;
        pano.add(jbutton_carte,cont);

        this.setContentPane(pano);
        this.pack();
    }
    
}
