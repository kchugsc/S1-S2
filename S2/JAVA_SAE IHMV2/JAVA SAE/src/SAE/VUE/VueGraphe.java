/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SAE.VUE;

import SAE.MODELLE.GrapheColor2;
import SAE.MODELLE.GrapheColor3;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;

/**
 *
 * Classe VueGraphe représente une fenêtre pour visualiser et colorier des graphes.
 * 
 * Elle utilise la bibliothèque GraphStream pour l'affichage et la manipulation des graphes,
 * ainsi que Swing pour la création de l'interface utilisateur.
 * 
 * @author hugsc
 * @version 1.0
 */
public class VueGraphe extends JFrame{
    /** Un sélecteur de fichier pour choisir le graphe à afficher. */
    private JFileChooser jfilechooser_choix_graphe;

    /** Un bouton pour sélectionner un fichier de graphe. */
    private JButton jbutton_fichier_graphe;

    /** Un bouton pour lancer la coloration du graphe. */
    private JButton jbutton_colorer;

    /** Une liste déroulante pour choisir le type de coloration. */
    private JComboBox jcombo_choix_coloration;

    /** Un panneau pour afficher le graphe. */
    private JPanel pano;

    /** Un objet Graph pour stocker le graphe chargé depuis le fichier. */
    private Graph graph;

    /** Un objet View pour afficher le graphe dans le panneau. */
    private View view;

    /** Un label pour afficher le chemin du fichier sélectionné. */
    private JLabel label_chemin_fichier;

    /** Un label pour afficher le nombre de noeuds dans le graphe. */
    private JLabel label_nb_noeuds;

    /** Un label pour afficher le nombre d'arêtes dans le graphe. */
    private JLabel label_nb_aretes;

    /** Un label pour afficher le nombre de conflits dans le graphe. */
    private JLabel label_nb_conflits;

    /** Un label pour afficher le nombre de composants connexes dans le graphe. */
    private JLabel label_nb_composant;

    /** Un label pour afficher le degré moyen des noeuds dans le graphe. */
    private JLabel label_nb_degrémoy;

    /** Un label pour afficher le diamètre du graphe. */
    private JLabel label_nb_diamètre;

    /** Un label pour afficher le texte du fichier sélectionné. */
    private JLabel label_texte_fichier;

    /** Un label pour afficher le choix de coloration. */
    private JLabel label_choix_coloration;

    /** Une barre de menu pour l'application. */
    private JMenuBar bar;

    /** Un élément de menu pour changer le type de graphe. */
    private JMenuItem jmenu_changer_type;

    /** Le nombre maximal de couleurs pour la coloration. */
    private int kmax;

    /** Un objet VueCarte pour afficher la carte. */
    private VueCarte carte;

    
    /**
     * Constructeur de la classe VueGraphe. Initialise la fenêtre et ses composants.
     */
    public VueGraphe() {
        this.setTitle("Visualiteur de graphes");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        //Mets une image pour le logo de l'application
        ImageIcon icon = new ImageIcon("1562040.png");
        this.setIconImage(icon.getImage());
        
        //Demande au pc la charte graphique associé
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        init();
        
        view.addMouseWheelListener(new MouseWheelListener() {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
                int rotation = e.getWheelRotation();
                double scaleFactor = 1.1;
                // Ceci est test
                double zoomFactor = (rotation < 0) ? 1.0 / scaleFactor : scaleFactor;
                view.getCamera().setViewPercent(view.getCamera().getViewPercent() * zoomFactor);
            }
        });
        
        //Lors du clic de l'utilisateur, VueCarte est afficher et VueGraphe est rendue invisible
        jmenu_changer_type.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                carte.setVisible(true);
                VueGraphe.this.setVisible(false);
            }
            
        });
        
        //Lors du clic le graphe est colorrée
        jbutton_colorer.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(kmax!=-1){
                    String s=(String)jcombo_choix_coloration.getSelectedItem();
                    SAE.MODELLE.GrapheColor2.colorGraph(graph, kmax);
                    if(s.equals("SAAD")){
                        GrapheColor3 color3 = new GrapheColor3();
                        color3.colorGraph(graph, 250000);  
                    }
                    label_nb_conflits.setText("Nombre de conflits : \n"+Integer.toString(SAE.MODELLE.GrapheColor2.countConflicts(graph)));
                }
                else{
                    JOptionPane.showMessageDialog(VueGraphe.this, "Veuillez générer un graphe", "Graphe", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
        //Le graphe est générée et le kmax est récupérée
        jbutton_fichier_graphe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = jfilechooser_choix_graphe.showOpenDialog(pano);
                if (result == JFileChooser.APPROVE_OPTION) {
                    graph.clear();
                    
                    File selectedFile = jfilechooser_choix_graphe.getSelectedFile();
                    jbutton_fichier_graphe.setText(selectedFile.getName().toString());
                    try{
                    kmax=SAE.MODELLE.FichierUtils.chargement_graphes(graph,selectedFile.getAbsolutePath());
                    }
                    catch(Exception a){
                        JOptionPane.showMessageDialog(VueGraphe.this, "Il y a eu un problème lors du chargement: " + a.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    label_nb_aretes.setText("Nombre d'aretes :\n"+Integer.toString(graph.getEdgeCount()));
                    label_nb_noeuds.setText("Nombre de noeuds :\n"+Integer.toString(graph.getNodeCount()));
                    label_nb_composant.setText("Nombre de composant connexte :\n"+Integer.toString(GrapheColor2.calculConnexe(graph)));
                    label_nb_conflits.setText("Nombre de conflits : \n Veulliez colorer le graphe");
                    label_nb_degrémoy.setText("Degré moyen :\n"+Double.toString(graph.getEdgeCount()*2/graph.getNodeCount()));
                    label_nb_diamètre.setText("Diametre :\n"+SAE.MODELLE.GrapheColor2.graphDiameter(graph));
                } else if (result == JFileChooser.CANCEL_OPTION) {
                    label_chemin_fichier.setText("");
                } else {
                    label_chemin_fichier.setText("Fichier invalide");
                }
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
                    int choice = JOptionPane.showConfirmDialog(VueGraphe.this, "Voulez-vous fermer la fenetre", "Fermeture", JOptionPane.YES_NO_OPTION);
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
     * Initialisation des composants de la fenêtre.
     */
    private void init() {
        //Initialise le filtre
        FileNameExtensionFilter filter_txt = new FileNameExtensionFilter("Text Files", "txt");
        
        //Initialise des le selectionneur de fichier
        jfilechooser_choix_graphe = new JFileChooser();
        jfilechooser_choix_graphe.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfilechooser_choix_graphe.setCurrentDirectory(new File(System.getProperty("user.home")));
        jfilechooser_choix_graphe.setFileFilter(filter_txt);
        
        //initialise la bar de menu
        bar = new JMenuBar();
        
        jmenu_changer_type = new JMenuItem("Mode Vols et Aeroport");
        bar.add(jmenu_changer_type);
        
        this.setJMenuBar(bar);
        
        //Initialise le graphe
        graph=new SingleGraph("");
        
        //Demande au systeme quel est la taille de l'écran et donne une taille précise.
        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_SWING_THREAD);
        viewer.enableAutoLayout();
        view = viewer.addDefaultView(false);
                
        //taille qui s'adapte en fonction de l'écran
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.8);
        int height = (int) (screenSize.height * 0.8);
        
        //Initialise le pano
        pano=new JPanel();
        pano.setLayout(new GridBagLayout());
        pano.setBackground(Color.white);
        pano.setPreferredSize(new Dimension(width, height));
        
        //Initialise tout les labels
        label_chemin_fichier = new JLabel("      ");
        label_nb_noeuds = new JLabel("    ");
        label_nb_aretes = new JLabel("    ");
        label_nb_conflits = new JLabel("    ");
        label_nb_composant = new JLabel("    ");
        label_nb_degrémoy = new JLabel("     ");
        label_nb_diamètre = new JLabel("    ");
        label_texte_fichier = new JLabel("Dépostion de graphe");
        label_choix_coloration = new JLabel("Type de coloration :");
        
        //Initialise la combobox de pour choisir le l'algorithme de coloration
        jcombo_choix_coloration = new JComboBox(new Object []{"DSATUR","SAAD"});
        
        //Initialise les boutons de l'aplication
        jbutton_fichier_graphe=new JButton("Selectionner un fichier");
        jbutton_colorer = new JButton("Colorer le graphe");
        
        //Initialisation cont afin de positionner les composant
        GridBagConstraints cont=new GridBagConstraints();
        
        cont.fill=GridBagConstraints.BOTH;
        cont.insets = new Insets(5,5,5,5);
        
        cont.gridx=0;
        cont.gridy=0;
        pano.add(label_texte_fichier,cont);
        
        cont.gridx++;
        pano.add(jbutton_fichier_graphe,cont);
        
        cont.gridx++;
        pano.add(label_chemin_fichier,cont);
        
        cont.gridx++;
        cont.gridheight = 10;
        cont.gridwidth = 7; 
        cont.weightx=1.0;
        cont.weighty=1.0;
        pano.add(view,cont);
        
        cont.weightx=0;
        cont.weighty=0;
       
        cont.gridx=0;
        cont.gridy++;
        cont.gridheight=1;
        cont.gridwidth=1;
        pano.add(label_choix_coloration,cont);
        
        cont.gridx++;
        pano.add(jcombo_choix_coloration,cont);
        
        cont.gridy++;
        pano.add(jbutton_colorer,cont);
       
        cont.gridx=0;
        cont.gridy++;
        cont.gridwidth=3;
        pano.add(label_nb_noeuds,cont);
        
        cont.gridy++;
        pano.add(label_nb_aretes,cont);
        
        cont.gridy++;
        pano.add(label_nb_degrémoy,cont);
        
        cont.gridy++;
        pano.add(label_nb_conflits,cont);
        
        cont.gridy++;
        pano.add(label_nb_composant,cont);
        
        cont.gridy++;
        pano.add(label_nb_diamètre,cont);
        
        this.setContentPane(pano);
        this.pack();
        
        kmax=-1;
    }
    
    /**
     * Définit la vue carte associée à cette vue graphe.
     * 
     * @param carte La vue carte à associer.
     */
    public void setVueCarte(VueCarte carte){
        this.carte=carte;
    }
}
