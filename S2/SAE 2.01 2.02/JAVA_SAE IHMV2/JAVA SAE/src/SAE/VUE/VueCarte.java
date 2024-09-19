/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package SAE.VUE;

import SAE.MODELLE.Aeroport;
import SAE.MODELLE.GrapheColor2;
import SAE.MODELLE.GrapheColor3;
import SAE.MODELLE.Vols;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.MouseInputListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.NumberFormatter;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;


/**
 * La classe VueCarte représente l'interface utilisateur pour visualiser les graphes et les vols sur la carte de france.
 * Elle permet de charger des fichiers de vols et d'aéroports, et fait appel a des algorithmes pour générer des graphes, de colorier les graphes
 * et d'afficher les informations sur les graphes.
 * 
 * Elle utilise les bibliothèques Swing pour l'interface utilisateur et JXMapViewer pour afficher les cartes.
 * Elle utilise également GraphStream pour la visualisation des graphes.
 * 
 * @author hugsc
 * @version 1.0
 */
public class VueCarte extends JFrame{
    /** Un ensemble de waypoints pour stocker les waypoints. */
    private Set<MyWaypoint> waypoints = new HashSet<>();

    /** Un objet WaypointPainter pour peindre les waypoints sur la carte. */
    private WaypointPainter<MyWaypoint> waypointPainter = new WaypointPainter<>();

    /** Une carte pour afficher les données géographiques. */
    private static JXMapViewer carte;

    /** Un sélecteur de fichier pour choisir le fichier de vols. */
    private JFileChooser jfilechooser_vols;

    /** Un sélecteur de fichier pour choisir le fichier d'aéroports. */
    private JFileChooser jfilechooser_aeroports;

    /** Un bouton pour sélectionner un fichier de vols. */
    private JButton jbutton_fichier_vols;

    /** Un bouton pour sélectionner un fichier d'aéroports. */
    private JButton jbutton_fichier_aeroports;

    /** Un bouton pour lancer la coloration du graphe. */
    private JButton jbutton_colorer;

    /** Un bouton pour générer le graphe à partir des données. */
    private JButton jbutton_générer_graphe;

    /** Une liste déroulante pour choisir le type de coloration. */
    private JComboBox jcombo_choix_coloration;

    /** Un panneau pour afficher le graphe. */
    private JPanel pano;

    /** Un objet Graph pour stocker le graphe généré à partir des données. */
    private Graph graph;

    /** Un objet View pour afficher le graphe dans le panneau. */
    private View view;

    /** Un label pour afficher le chemin du fichier de vols sélectionné. */
    private JLabel label_fichier_vols;

    /** Un label pour afficher le chemin du fichier d'aéroports sélectionné. */
    private JLabel label_fichier_aeroports;

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

    /** Un label pour afficher le texte du fichier de vols sélectionné. */
    private JLabel label_texte_fichier_vols;

    /** Un label pour afficher le texte du fichier d'aéroports sélectionné. */
    private JLabel label_texte_fichier_aeroports;

    /** Un label pour afficher le choix de coloration. */
    private JLabel label_choix_coloration;

    /** Un label pour afficher l'écart de sécurité. */
    private JLabel label_ecart_secu;

    /** Un champ de texte formaté pour saisir la valeur maximale de k. */
    private JFormattedTextField jtext_kmax;

    /** Un champ de texte formaté pour saisir l'écart de sécurité. */
    private JFormattedTextField jtext_ecart_secu;

    /** Un formateur de nombre pour formater la valeur de kmax. */
    private NumberFormatter format_kmax;

    /** Un objet VueGraphe pour afficher le graphe. */
    private VueGraphe graphe;

    /** Une barre de menu pour l'application. */
    private JMenuBar bar;

    /** Un élément de menu pour changer le type de graphe. */
    private JMenuItem jmenu_changer_type;

    /** Un élément de menu pour afficher le graphe. */
    private JMenuItem jmenu_graphe;

    /** Un élément de menu pour afficher la carte. */
    private JMenuItem jmenu_carte;

    /** Un élément de menu pour afficher la liste des vols. */
    private JMenuItem jmenu_liste_vols;

    /** Une fenêtre pour afficher la liste des vols. */
    private ListeVols fen;

    /** Une liste d'aéroports pour stocker les données d'aéroports. */
    private static ArrayList<Aeroport> aer_listt;

    /** Une liste de vols pour stocker les données de vols. */
    private static ArrayList<Vols> volst;

    /** Une table de hachage pour stocker les aéroports en fonction de leur code. */
    private static HashMap<String, Aeroport> aert;

    
     /**
     * Constructeur de la classe VueCarte. Initialise les composants de l'interface utilisateur
     * et configure les actions des différents éléments.
     */
    public VueCarte(){
        this.setTitle("Visualiteur Aeroport et graphes");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        //Met une image pour le logo de l'application
        ImageIcon icon = new ImageIcon("1562040.png");
        this.setIconImage(icon.getImage());
        
        //Demande au pc la charte graphique associé        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        init();
        
        //zoom sur le view du graphe
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
        
        //Affiche la carte dans la JFrame et enleve le graphe
        jmenu_carte.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                view.setVisible(false);
                carte.setVisible(true);
                bar.remove(jmenu_carte);
                bar.add(jmenu_graphe,1);
            }
        });
        
    
        
        //Affiche la graphe dans la JFrame et enleve la carte
        jmenu_graphe.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                carte.setVisible(false);
                view.setVisible(true);
                bar.remove(jmenu_graphe);
                bar.add(jmenu_carte,1);
            }
        });
        
        //Lros du clic de l'utilisateur ListeVols est afficher
        jmenu_liste_vols.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!fen.isVisible())
                    fen.setVisible(true);
            }
            
        });
        
        //Lors du clic de l'utilisateur, VueGraphe est afficher et VueCarte est rendue invisible
        jmenu_changer_type.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                graphe.setVisible(true);
                VueCarte.this.setVisible(false);
            }
            
        });
        
        //Lors du clic le graphe est colorrée
        jbutton_colorer.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(graph.getNodeCount()!=0){
                    if(!jtext_kmax.getText().equals("")){
                        String s=(String)jcombo_choix_coloration.getSelectedItem();
                        SAE.MODELLE.GrapheColor2.colorGraph(graph, Integer.parseInt(jtext_kmax.getText()));
                        if(s.equals("SAAD")){
                            GrapheColor3 color3 = new GrapheColor3();
                            color3.colorGraph(graph, 250000);  
                        }
                        label_nb_conflits.setText("Nombre de conflits : \n"+Integer.toString(SAE.MODELLE.GrapheColor2.countConflicts(graph)));
                        fen.setColoration(GrapheColor3.affichage_des_couleurs(graph));
                        fen.setConflits(GrapheColor3.ListeConflits(graph));
                    }
                    else{
                        JOptionPane.showMessageDialog(VueCarte.this, "Veuillez ecrire un chiffre dans la zone de texte", "Coloration", JOptionPane.WARNING_MESSAGE);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(VueCarte.this, "Veuillez générer un graphe", "Graphe", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
        //Lors du clic l'utilisateur une fenetre de choix de fichier
        jbutton_fichier_vols.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = jfilechooser_vols.showOpenDialog(pano);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfilechooser_vols.getSelectedFile();
                    jbutton_fichier_vols.setText(selectedFile.getName().toString());
                } else if (result == JFileChooser.CANCEL_OPTION) {
                    label_fichier_vols.setText("");
                } else {
                    label_fichier_vols.setText("Fichier invalide");
                }
            }
        });
        
        //Lors du clic l'utilisateur une fenetre de choix de fichier
        jbutton_fichier_aeroports.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = jfilechooser_aeroports.showOpenDialog(pano);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfilechooser_aeroports.getSelectedFile();
                    jbutton_fichier_aeroports.setText(selectedFile.getName().toString());
                } else if (result == JFileChooser.CANCEL_OPTION) {
                    label_fichier_aeroports.setText("");
                } else {
                    label_fichier_aeroports.setText("Fichier invalide");
                }
            }
        });
        
        //Le graph est générée et ListVols est crée
        jbutton_générer_graphe.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
               if(jfilechooser_aeroports.getSelectedFile()!=null && jfilechooser_vols.getSelectedFile()!=null){
                try{
                   if(fen==null){
                       bar.add(jmenu_liste_vols);
                   }
                   graph.clear();
                   File selectedFile = jfilechooser_aeroports.getSelectedFile();
                   
                   HashMap<String, Aeroport> aer= SAE.MODELLE.FichierUtils.chargement_aeroport(selectedFile.getAbsolutePath());
                   
                   ArrayList<Aeroport> aer_list=SAE.MODELLE.FichierUtils.chargement_aeroport_liste(selectedFile.getAbsolutePath());
                   if(aer_list.size()==0){
                       throw new Exception("le fichier d'aeroport n'est pas bon!");
                   }
                   selectedFile = jfilechooser_vols.getSelectedFile();
                   ArrayList<Vols> vols= SAE.MODELLE.FichierUtils.chargement_vols(selectedFile.getAbsolutePath()); 
                   if(vols.size()==0){
                       throw new Exception("le fichier de vols n'est pas bon!");
                   }
                   
                   aer_listt= aer_list;
                   volst = vols;
                   aert = aer;
                   positionneaer(aer_list,vols,aer);
                   
                   SAE.MODELLE.Aretes.detecterCollisions(graph, vols, aer,Integer.parseInt(jtext_ecart_secu.getText()));
                   
                   fen=new ListeVols(vols,aer_list);
                   
                   label_nb_aretes.setText("Nombre d'aretes :\n"+Integer.toString(graph.getEdgeCount()));
                   label_nb_noeuds.setText("Nombre de noeuds :\n"+Integer.toString(graph.getNodeCount()));
                   label_nb_composant.setText("Nombre de composant connexte :\n"+Integer.toString(GrapheColor2.calculConnexe(graph)));
                   label_nb_conflits.setText("Nombre de conflits : \n Veulliez colorer le graphe");
                   label_nb_degrémoy.setText("Degré moyen :\n"+Double.toString(graph.getEdgeCount()*2/graph.getNodeCount()));
                   label_nb_diamètre.setText("Diametre :\n"+SAE.MODELLE.GrapheColor2.graphDiameter(graph));
                   }
                   catch(Exception a){
                       JOptionPane.showMessageDialog(VueCarte.this, "Il y a eu un problème lors du chargement: " + a.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                   }
               }
               else{
                    JOptionPane.showMessageDialog(VueCarte.this, "Veuillez deposer les fichier pour pouvoir générer le graphe", "Graphe", JOptionPane.INFORMATION_MESSAGE);
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
                    int choice = JOptionPane.showConfirmDialog(VueCarte.this, "Voulez-vous fermer la fenetre", "Fermeture", JOptionPane.YES_NO_OPTION);
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
     * Initialise les composants de l'interface utilisateur.
     */
    private void init(){
        //Initialiser les paramètres de la carte
        TileFactoryInfo info=new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory=new DefaultTileFactory(info);
        carte=new JXMapViewer();
        carte.setTileFactory(tileFactory);
        GeoPosition geo=new GeoPosition(46.2746136,3.9655853);
        carte.setAddressLocation(geo);
        carte.setZoom(13);
        MouseInputListener mm=new PanMouseInputListener(carte);
        carte.addMouseListener(mm);
        carte.addMouseMotionListener(mm);
        carte.addMouseWheelListener(new ZoomMouseWheelListenerCenter(carte));
        
       //Initialise le graphe
        graph=new SingleGraph("");
        
        //Initalise le composant view qui affiche le graphe
        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_SWING_THREAD);
        viewer.enableAutoLayout();
        view = viewer.addDefaultView(false);
        
        
        //Initialise un NumberFormatter pour accepter seulement les nombres.
        NumberFormatter formatter = new NumberFormatter();
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(1);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        
        //Initialise la zone de texte avec seulement des nombre 
        jtext_kmax = new JFormattedTextField(formatter);
        jtext_kmax.setColumns(10);
        jtext_ecart_secu = new JFormattedTextField(formatter);
        jtext_ecart_secu.setColumns(10);
        jtext_ecart_secu.setText(Integer.toString(15));
        
        //Initialise des filtres
        FileNameExtensionFilter filter_txt = new FileNameExtensionFilter("Text Files", "txt");
        FileNameExtensionFilter filter_csv = new FileNameExtensionFilter("csv Files", "csv");
        
        //Initialise des le selectionneur de fichier
        jfilechooser_aeroports = new JFileChooser();
        jfilechooser_vols = new JFileChooser();
        
        //Accepte seulement des fichiers 
        jfilechooser_aeroports.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfilechooser_vols.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        //Lors de l'ouverture du selectionneur de fichier l'utilisateur sera dans "user.home"
        jfilechooser_aeroports.setCurrentDirectory(new File(System.getProperty("user.home")));
        jfilechooser_vols.setCurrentDirectory(new File(System.getProperty("user.home")));
        
        //accepte seulment les types indiquée
        jfilechooser_aeroports.setFileFilter(filter_txt);
        jfilechooser_vols.setFileFilter(filter_csv);
        
        //initialise la bar de menu
        bar = new JMenuBar();
        
        //initialiser les item de menu
        jmenu_changer_type = new JMenuItem("Mode graphe");
        bar.add(jmenu_changer_type);
        
        jmenu_carte = new JMenuItem("Afficher carte");
        jmenu_graphe = new JMenuItem("Afficher graphe");
        bar.add(jmenu_graphe);
        
        jmenu_liste_vols = new JMenuItem("Afficher liste vols");
        
        this.setJMenuBar(bar);
        
        //Demande au systeme quel est la taille de l'écran et donne une taille précise.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.8);
        int height = (int) (screenSize.height * 0.8);
        
        //Initialise le pano
        pano=new JPanel();
        pano.setLayout(new GridBagLayout());
        pano.setBackground(Color.white);
        pano.setPreferredSize(new Dimension(width, height));
        
        //Initialise tout les labels
        label_fichier_aeroports = new JLabel("     ");
        label_fichier_vols = new JLabel("    ");
        label_nb_noeuds = new JLabel("    ");
        label_nb_aretes = new JLabel("    ");
        label_nb_conflits = new JLabel("    ");
        label_nb_composant = new JLabel("    ");
        label_nb_degrémoy = new JLabel("     ");
        label_nb_diamètre = new JLabel("    ");
        label_choix_coloration = new JLabel("Type de coloration :");
        label_texte_fichier_vols = new JLabel("Deposition de la liste de vols");
        label_texte_fichier_aeroports = new JLabel("Deposition de la liste aeroport");
        label_ecart_secu = new JLabel("Ecart de sécurité en minutes");
       
        //Initialise la combobox de pour choisir le l'algorithme de coloration
        jcombo_choix_coloration = new JComboBox(new Object []{"DSATUR","SAAD"});

        //Initialise les boutons de l'aplication
        jbutton_colorer = new JButton("Colorer le graphe");
        jbutton_fichier_aeroports = new JButton("Selectionner un fichier");
        jbutton_fichier_vols = new JButton("Selectionner un fichier");
        jbutton_générer_graphe = new JButton("Générer le graphe");
        
        //Initialisation cont afin de positionner les composant
        GridBagConstraints cont=new GridBagConstraints();
        
        cont.fill=GridBagConstraints.BOTH;
        cont.insets = new Insets(5,5,5,5);
        
        cont.gridx=0;
        cont.gridy=0;
        pano.add(label_texte_fichier_aeroports,cont);
        
        cont.gridx++;
        pano.add(jbutton_fichier_aeroports,cont);
        
        cont.gridx++;
        pano.add(label_fichier_aeroports,cont);
        
        cont.gridx++;
        cont.gridheight = 15;
        cont.gridwidth = 7; 
        cont.weightx=1.0;
        cont.weighty=1.0;
        pano.add(carte,cont);
        pano.add(view,cont);
        view.setVisible(false);
        
        cont.weightx=0;
        cont.weighty=0;
        cont.gridheight = 1;
        cont.gridwidth = 1; 
        cont.gridx=2;
        cont.gridy++;
        pano.add(label_fichier_vols,cont);
        
        cont.gridx--;
        pano.add(jbutton_fichier_vols,cont);
        
        cont.gridx--;
        pano.add(label_texte_fichier_vols,cont);
        
        cont.gridy++;
        pano.add(label_choix_coloration,cont);
        
        cont.gridx++;
        pano.add(jcombo_choix_coloration,cont);
        
        cont.gridy++;
        pano.add(jbutton_colorer,cont);
        
        cont.gridx--;
        pano.add(jtext_kmax,cont);
        
        cont.gridy++;
        pano.add(label_ecart_secu,cont);
        
        cont.gridx++;
        pano.add(jtext_ecart_secu,cont);
        
        cont.gridy++;
        pano.add(jbutton_générer_graphe,cont);
        
        cont.gridx--;
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
    }
    
    
    
      private static void  clearOverlays() {
    carte.setOverlayPainter(null);
    carte.removeAll(); // Enlever tous les composants ajoutés, comme les boutons waypoints
    carte.revalidate();
    carte.repaint();
}

    /**
     * Positionne les aéroports et les vols sur la carte.
     * 
     * @param aeroport_list La liste des aéroports.
     * @param vols          La liste des vols.
     * @param aer           Une map des aéroports.
     */
    private static void  positionneaer(ArrayList<Aeroport> aer_list,ArrayList<Vols> vols,HashMap<String, Aeroport> aer){
        clearOverlays();
        ArrayList<org.jxmapviewer.painter.Painter<JXMapViewer>> painters = new ArrayList<>();
    
    // Ajout des lignes entre les aéroports pour les vols
    for (Vols vol : vols) {
        Aeroport dep = aer.get(vol.getAero_dep());
        Aeroport dest = aer.get(vol.getAero_dest());

        if (dep != null && dest != null) {
            double[] coorDep = dep.calculerCoordCartesienne();
            double[] coorDest = dest.calculerCoordCartesienne();
            GeoPosition geoDep = new GeoPosition(coorDep[0], coorDep[1]);
            GeoPosition geoDest = new GeoPosition(coorDest[0], coorDest[1]);

            painters.add(new LinePainter(geoDep, geoDest, Color.RED));
        }
    }

    // Création des boutons waypoints pour les aéroports
    Set<MyWaypoint> waypoints = new HashSet<>();
    for (Aeroport aeroport : aer_list) {
        double[] coor = aeroport.calculerCoordCartesienne();
        GeoPosition geoPosition = new GeoPosition(coor[0], coor[1]);
        MyWaypoint waypoint = new MyWaypoint(aeroport.getNom_aero(), geoPosition, aeroport, vols);
        waypoints.add(waypoint);
    }

    // Ajout des boutons waypoints au painter
    WaypointRender waypointRenderer = new WaypointRender(waypoints);
    painters.add(waypointRenderer);

    CompoundPainter<JXMapViewer> painter = new CompoundPainter<>(painters);
    carte.setOverlayPainter(painter);
    }
    
    /**
     * Positionne les aéroports et les vols sur la carte. en fonction du filterVols
     * 
     * @param volss          La liste des vols.
     */
    
    protected static void positionneaerfiltervol(ArrayList<String> volss){
        clearOverlays();
        ArrayList<org.jxmapviewer.painter.Painter<JXMapViewer>> painters = new ArrayList<>();
        HashMap<String, Aeroport> aer= new HashMap<>();
        ArrayList<Aeroport> aer_list= new ArrayList<>();
        ArrayList<Vols> vols = new ArrayList<>();
        
        for (Vols vol : volst){
            for(String id : volss){
                if(vol.getId().equals(id)){
                    vols.add(vol);
                }
            }
            
        }
        for (Aeroport aero : aer_listt){
            for (Vols vol : vols) {
                if (vol.getAero_dest().equals(aero.getId()) || vol.getAero_dep().equals(aero.getId())) {
                    if(!aer_list.contains(aero)){
                    aer_list.add(aero);
                    }
                }
            }
        }
        for(Aeroport aero2 : aer_list){
            aer.put(aero2.getId(), aero2);
        }  
    // Ajout des lignes entre les aéroports pour les vols
    for (Vols vol : vols) {
        Aeroport dep = aer.get(vol.getAero_dep());
        Aeroport dest = aer.get(vol.getAero_dest());

        if (dep != null && dest != null) {
            double[] coorDep = dep.calculerCoordCartesienne();
            double[] coorDest = dest.calculerCoordCartesienne();
            GeoPosition geoDep = new GeoPosition(coorDep[0], coorDep[1]);
            GeoPosition geoDest = new GeoPosition(coorDest[0], coorDest[1]);

            painters.add(new LinePainter(geoDep, geoDest, Color.RED));
        }
    }

    // Création des boutons waypoints pour les aéroports
    Set<MyWaypoint> waypoints = new HashSet<>();
    for (Aeroport aeroport : aer_list) {
        double[] coor = aeroport.calculerCoordCartesienne();
        GeoPosition geoPosition = new GeoPosition(coor[0], coor[1]);
        MyWaypoint waypoint = new MyWaypoint(aeroport.getNom_aero(), geoPosition, aeroport, vols);
        waypoints.add(waypoint);
    }

    // Ajout des boutons waypoints au painter
    WaypointRender waypointRenderer = new WaypointRender(waypoints);
    painters.add(waypointRenderer);

    CompoundPainter<JXMapViewer> painter = new CompoundPainter<>(painters);
    carte.setOverlayPainter(painter);
    }
    
    /**
     * Positionne les aéroports et les vols sur la carte. en fonction du filteraeroport
     * 
     * @param aeroportfiltre La liste des aéroports.
     */
    
    protected static void positionneaerfilteraeroport(ArrayList<String> aeroportfiltre){
        clearOverlays();
        ArrayList<org.jxmapviewer.painter.Painter<JXMapViewer>> painters = new ArrayList<>();
        HashMap<String, Aeroport> aer= new HashMap<>();
        ArrayList<Aeroport> aer_list= new ArrayList<>();
        ArrayList<Vols> vols = new ArrayList<>();
        
        for (Aeroport aero : aer_listt){
            for(String id : aeroportfiltre){
                if(aero.getId().equals(id)){
                    aer_list.add(aero);
                }
            }
            
        }
        for(Aeroport aero2 : aer_list){
            aer.put(aero2.getId(), aero2);
        } 
        for (Vols vol : volst){
            for (Aeroport aerop : aer_list) {
                if (vol.getAero_dest().equals(aerop.getId()) || vol.getAero_dep().equals(aerop.getId())) {
                    if(!vols.contains(vol)){
                        vols.add(vol);
                    }
                }
            }
}
        
    // Ajout des lignes entre les aéroports pour les vols
    for (Vols vol : vols) {
        Aeroport dep = aer.get(vol.getAero_dep());
        Aeroport dest = aer.get(vol.getAero_dest());

        if (dep != null && dest != null) {
            double[] coorDep = dep.calculerCoordCartesienne();
            double[] coorDest = dest.calculerCoordCartesienne();
            GeoPosition geoDep = new GeoPosition(coorDep[0], coorDep[1]);
            GeoPosition geoDest = new GeoPosition(coorDest[0], coorDest[1]);

            painters.add(new LinePainter(geoDep, geoDest, Color.RED));
        }
    }

    // Création des boutons waypoints pour les aéroports
    Set<MyWaypoint> waypoints = new HashSet<>();
    for (Aeroport aeroport : aer_list) {
        double[] coor = aeroport.calculerCoordCartesienne();
        GeoPosition geoPosition = new GeoPosition(coor[0], coor[1]);
        MyWaypoint waypoint = new MyWaypoint(aeroport.getNom_aero(), geoPosition, aeroport, vols);
        waypoints.add(waypoint);
    }

    // Ajout des boutons waypoints au painter
    WaypointRender waypointRenderer = new WaypointRender(waypoints);
    painters.add(waypointRenderer);

    CompoundPainter<JXMapViewer> painter = new CompoundPainter<>(painters);
    carte.setOverlayPainter(painter);
    }
    
  
    
    
    /**
    * Définit la vue graphique pour la visualisation du graphe.
    * Cette méthode permet de lier une instance de VueGraphe à VueCarte.
    * Elle nécéssaire pour pouvoir changer entre VueCarte et VueGraphe
    * 
    * @param graphe L'instance de VueGraphe à associer.
    */
    public void setVueGraphe(VueGraphe graphe){
        this.graphe=graphe;
    }
}
