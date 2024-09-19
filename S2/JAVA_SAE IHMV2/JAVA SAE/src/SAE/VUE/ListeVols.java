/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SAE.VUE;

import SAE.MODELLE.Aeroport;
import SAE.MODELLE.Vols;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 * Classe représentant une fenêtre de liste de vols avec interface graphique.
 * Permet d'afficher des listes de vols, d'aéroports et de conflits éventuels.
 * L'interface comprend des boutons pour choisir quelle liste afficher, des champs de filtre,
 * et des tableaux pour présenter les données.
 * Elle inclut également des fonctionnalités pour filtrer les données et afficher des informations
 * supplémentaires en fonction des actions de l'utilisateur.
 * Utilise les classes Aeroport et Vols définies dans le projet.
 * 
 * @author hugsc
 */
public class ListeVols extends JDialog{
    /** Une table pour afficher les données de vols. */
    private JTable tableVols;

    /** Une table pour afficher les données d'aéroports. */
    private JTable tableAeroport;

    /** Une table pour afficher les données de conflits. */
    private JTable tableConflits;

    /** Un bouton pour afficher les données d'aéroports. */
    private JButton boutonAer;

    /** Un bouton pour afficher les données de vols. */
    private JButton boutonVols;

    /** Un bouton pour afficher les données de conflits. */
    private JButton boutonConflits;

    /** Une liste de vols pour stocker les données de vols. */
    private ArrayList<Vols> vols;

    /** Une liste de vols filtrés pour stocker les données de vols filtrées. */
    private ArrayList<Vols> vols_filtre;

    /** Une liste d'aéroports pour stocker les données d'aéroports. */
    private ArrayList<Aeroport> aer;

    /** Une liste d'aéroports filtrés pour stocker les données d'aéroports filtrées. */
    private ArrayList<Aeroport> aer_filtre;

    /** Une liste de conflits pour stocker les données de conflits. */
    private ArrayList<AbstractMap.SimpleEntry<String, String>> conflits;

    /** Une liste de conflits filtrés pour stocker les données de conflits filtrées. */
    private ArrayList<AbstractMap.SimpleEntry<String, String>> conflits_filtre;

    /** Une table de hachage pour stocker la coloration des noeuds. */
    private HashMap<String,String> coloration;

    /** Un ascenseur pour la table de vols. */
    private JScrollPane jsp_vols;

    /** Un ascenseur pour la table d'aéroports. */
    private JScrollPane jsp_aer;

    /** Un ascenseur pour la table de conflits. */
    private JScrollPane jsp_conf;

    /** Un modèle de table pour la table de vols. */
    private DefaultTableModel model_vols;

    /** Un modèle de table pour la table d'aéroports. */
    private DefaultTableModel model_aer;

    /** Un modèle de table pour la table de conflits. */
    private DefaultTableModel model_conflits;

    /** Un panneau pour organiser les composants de l'interface graphique. */
    private JPanel pano;

    /** Un gestionnaire de disposition GridBagConstraints pour organiser les composants de l'interface graphique. */
    private GridBagConstraints cont = new GridBagConstraints();

    /** Une liste déroulante pour choisir le type de filtre pour les vols. */
    private JComboBox choix_vols;

    /** Une liste déroulante pour choisir le type de filtre pour les aéroports. */
    private JComboBox choix_aer;

    /** Une liste déroulante pour choisir le type de filtre pour les conflits. */
    private JComboBox choix_conflits;

    /** Un champ de texte pour saisir le filtre. */
    private JTextField filtre;

    /**
    * Constructeur de la classe ListeVols.
    *
    * @param vols La liste des vols à afficher dans la fenêtre.
    * @param aer La liste des aéroports à afficher dans la fenêtre.
    */
    public ListeVols(ArrayList<Vols> vols,ArrayList<Aeroport> aer){
        this.setTitle("Planificateur de vols");
        
        ImageIcon icon = new ImageIcon("1562040.png");
        this.setIconImage(icon.getImage());
        
        this.aer=aer;
        this.vols=vols;
        
        //Demande au pc la charte graphique associé
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        
        this.init();
        
        
        
        //ecouteur sur la zone texte de qui fait appel la fonction filter
        filtre.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                filter(filtre.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filter(filtre.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filter(filtre.getText());
            }
        });
        
        //ecouteur sur le bouton vols qui affichera la liste de vols
        boutonVols.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!jsp_vols.isVisible()){
                    if(jsp_aer.isVisible()){
                        jsp_aer.setVisible(false);
                        choix_aer.setVisible(false);
                    }
                    if(jsp_conf.isVisible()){
                        jsp_conf.setVisible(false);
                        choix_conflits.setVisible(false);
                    }
                    jsp_vols.setVisible(true);
                    choix_vols.setVisible(true);
                    ListeVols.this.pack();
                    filter("");//appel afin de remplir la liste de vols
                }
            }
        });
        
        //ecouteur sur le bouton vols qui affichera la liste de vols avec des conflits
        boutonConflits.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!jsp_conf.isVisible()){
                    if(jsp_vols.isVisible()){
                        jsp_vols.setVisible(false);
                        choix_vols.setVisible(false);
                    }
                    if(jsp_aer.isVisible()){
                        jsp_aer.setVisible(false);
                        choix_aer.setVisible(false);
                    }
                    jsp_conf.setVisible(true);
                    choix_conflits.setVisible(true);
                    choix_conflits.setSelectedItem("id");
                    ListeVols.this.pack();
                    filter("");//appel afin de remplir la liste de vols
                }
            }
        });
        
        //ecouteur sur le bouton vols qui affichera la liste d'aeroport
        boutonAer.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!jsp_aer.isVisible()){
                    if(jsp_vols.isVisible()){
                        jsp_vols.setVisible(false);
                        choix_vols.setVisible(false);
                    }
                    if(jsp_conf.isVisible()){
                        jsp_conf.setVisible(false);
                        choix_conflits.setVisible(false);
                    }
                    jsp_aer.setVisible(true);
                    choix_aer.setVisible(true);
                    choix_aer.setSelectedItem("id");
                    ListeVols.this.pack();
                    filter("");//appel afin de remplir la liste de vols
                }
            }
            
        });
    }
    
    /**
     * Initialise les composants graphiques de la fenêtre.
     * Initialise les tables, les boutons, les champs de filtre, et les JScrollPane.
     * Configure les dimensions et les positions des composants dans le JPanel.
     */
    private void init(){
        //Initialisation des liste de filtres
        this.aer_filtre=new ArrayList<>();
        this.vols_filtre=new ArrayList<>();
        this.conflits_filtre=new ArrayList<>();
        
        //Initialisation de la zone de texte
        filtre=new JTextField();
        
        //Initialisation des comboBox
        choix_aer=new JComboBox(new Object[]{"id","nom_aero","degre_ns","minutes_ns","secondes_ns","coord_ns","degre_oe","minutes_oe","secondes_oe","coord_oe"});
        choix_vols=new JComboBox(new Object[]{"id","aero_dep","aero_dest","horaire","durée"});
        choix_conflits=new JComboBox(new Object[]{"Id vols","Id vols conflit","altitude"});
        
        //Initalisation des noms de colonnes
        String[]nom_colonnes_vols={"Id","Aeroport départ","Aeroport destination","Horaire","Durée vols"};
        String[]nom_colonnes_aer={"Id","nom_aero","degre_ns","minutes_ns","secondes_ns","coord_ns","degre_oe","minutes_oe","secondes_oe","coord_oe"};
        String[]nom_colonnes_conflits={"Id vols","Id vols conflit","altitude"};
        
        //Initialisation des model de tables
        model_vols = new DefaultTableModel(null,nom_colonnes_vols);
        model_aer = new DefaultTableModel(null,nom_colonnes_aer);
        model_conflits = new DefaultTableModel(null,nom_colonnes_conflits);
        
        //Initialisation des tables
        tableVols=new JTable(model_vols);
        tableAeroport=new JTable(model_aer);
        tableConflits=new JTable(model_conflits);
        
        //taille des table
        tableVols.setPreferredScrollableViewportSize(new Dimension(500, 400));
        tableAeroport.setPreferredScrollableViewportSize(new Dimension(500,400));
        tableConflits.setPreferredScrollableViewportSize(new Dimension(500,400));
 
        //Initialisation des JScrollPane
        jsp_vols=new JScrollPane(tableVols);
        jsp_aer=new JScrollPane(tableAeroport);
        jsp_conf=new JScrollPane(tableConflits);
        
        
        //Demande au systeme quel est la taille de l'écran et donne une taille précise.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.4);
        int height = (int) (screenSize.height * 0.55);
       
        //Initialisation des boutons
        boutonAer=new JButton("Liste Aeroport");
        boutonConflits=new JButton("Liste conflits");
        boutonVols=new JButton("Liste des vols");
        
        //Initialisation des pano graphique
        pano =new JPanel();
        pano.setLayout(new GridBagLayout());
        pano.setBackground(Color.white);
        pano.setPreferredSize(new Dimension(width, height));
        
        //Initialisation cont afin de positionner les composant
        cont.fill=GridBagConstraints.BOTH;
        
        cont.insets=new Insets(5,5,5,5);
        cont.gridx=0;
        cont.gridy=0;
        pano.add(boutonVols,cont);
        
        cont.gridx++;
        pano.add(boutonAer,cont);
        
        cont.gridx++;
        pano.add(boutonConflits,cont);
        boutonConflits.setVisible(false);
        
        cont.gridx++;
        pano.add(choix_vols,cont);
        pano.add(choix_aer,cont);
        pano.add(choix_conflits,cont);
        choix_aer.setVisible(false);
        choix_conflits.setVisible(false);
                
        cont.gridx++;
        pano.add(filtre,cont);
        
        cont.gridx=0;
        cont.gridy++;
        cont.gridwidth=5;
        pano.add(jsp_vols,cont);
        pano.add(jsp_aer,cont);
        pano.add(jsp_conf,cont);
        jsp_aer.setVisible(false);
        jsp_conf.setVisible(false);
        
        cont.gridwidth=1;
        
        filter("");//appel afin de remplir la liste de vols
        
        
        this.setContentPane(pano);
        this.pack();
    }

    /**
    * Charge les données des vols filtrés dans le modèle de table des vols.
    * Supprime les anciennes lignes du modèle de table des vols et ajoute les nouvelles données des vols filtrés.
    * Et place sur la map les vols filtré   
    */
    private void chargertablevols(){
        //supression de toutes les anciennes lignes
        for(int i=model_vols.getRowCount()-1;i>=0;i--){
            model_vols.removeRow(i);
        }
        //ajout des nouveles information
        Object[] vols_t = new Object[5];
        if(boutonConflits.isVisible()){
            vols_t = new Object[6];
        }
        ArrayList<String> volsfiltre = new ArrayList<>();
        for(int i=0;i<vols_filtre.size();i++){
            vols_t[0]=vols_filtre.get(i).getId();
            vols_t[1]=vols_filtre.get(i).getAero_dep();
            vols_t[2]=vols_filtre.get(i).getAero_dest();
            vols_t[3]=vols_filtre.get(i).getHoraire();
            vols_t[4]=vols_filtre.get(i).getDurée();
            if(boutonConflits.isVisible()){
                vols_t[5]=coloration.get(vols_filtre.get(i).getId());
            }
            model_vols.addRow(vols_t);
            //Il recupere l'id des vols filtré
            volsfiltre.add((String)vols_t[0]);
            
        }
        //Positionne les aeroports en fonction du filtre
        VueCarte.positionneaerfiltervol(volsfiltre);
    }
    
    /**
    * Charge les données des conflits filtrés dans le modèle de table des conflits.
    * Supprime les anciennes lignes du modèle de table des conflits et ajoute les nouvelles données des conflits filtrés.
    * Et place sur la map les vols filtré 
    */
    private void chargertableconflits(){
        //supression des anciennes lignes
        for(int i=model_conflits.getRowCount()-1;i>=0;i--){
            model_conflits.removeRow(i);
        }
        Object[] conf_t = new Object[3];
        for(int i=0;i<conflits_filtre.size();i++){
            conf_t[0]=conflits_filtre.get(i).getKey();
            conf_t[1]=conflits_filtre.get(i).getValue();
            conf_t[2]=coloration.get(conflits_filtre.get(i).getKey());
            model_conflits.addRow(conf_t);
        }
    }
    
    
    /**
    * Charge les données des aéroports filtrés dans le modèle de table des aéroports.
    * Supprime les anciennes lignes du modèle de table des aéroports et ajoute les nouvelles données des aéroports filtrés.
    */

    private void chargertableaer(){
        //supression des anciennes lignes
        for(int i=model_aer.getRowCount()-1;i>=0;i--){
            model_aer.removeRow(i);
        }
        ArrayList<String> aerofiltre = new ArrayList<>();
        Object[] aer_t = new Object[10];
        for(int i=0;i<aer_filtre.size();i++){
            aer_t[0]=aer_filtre.get(i).getId();
            aer_t[1]=aer_filtre.get(i).getNom_aero();
            aer_t[2]=aer_filtre.get(i).getDegre_ns();
            aer_t[3]=aer_filtre.get(i).getMinutes_ns();
            aer_t[4]=aer_filtre.get(i).getSecondes_ns();
            aer_t[5]=aer_filtre.get(i).getCoord_ns();
            aer_t[6]=aer_filtre.get(i).getDegre_oe();
            aer_t[7]=aer_filtre.get(i).getMinutes_oe();
            aer_t[8]=aer_filtre.get(i).getSecondes_oe();
            aer_t[9]=aer_filtre.get(i).getCoord_oe();
            model_aer.addRow(aer_t);
            aerofiltre.add((String)aer_t[0]);
        }
        //Positionne les aeroports en fonction du filtre
        VueCarte.positionneaerfilteraeroport(aerofiltre);
        
        
        
    }
    
    
    /**
    * Filtre les listes en fonction du texte saisi par l'utilisateur et met à jour les tables affichées.
    * @param filtre Le texte à utiliser pour filtrer les listes.
    */
    private void filter(String filtre){
        //si la table d'aeroport est afficher
        if(jsp_aer.isVisible()){
           aer_filtre=new ArrayList<>();
           String s=(String) choix_aer.getSelectedItem();
           for(int i=0;i<aer.size();i++){
                if(s.equals("id")&&aer.get(i).getId().startsWith(filtre))
                    aer_filtre.add(aer.get(i));
                if(s.equals("nom_aero")&&aer.get(i).getNom_aero().startsWith(filtre))
                    aer_filtre.add(aer.get(i));
                if(s.equals("degre_ns")&&Integer.toString(aer.get(i).getDegre_ns()).startsWith(filtre))
                    aer_filtre.add(aer.get(i));
                if(s.equals("minutes_ns")&&Integer.toString(aer.get(i).getMinutes_ns()).startsWith(filtre))
                    aer_filtre.add(aer.get(i));
                if(s.equals("secondes_ns")&&Integer.toString(aer.get(i).getSecondes_ns()).startsWith(filtre))
                    aer_filtre.add(aer.get(i));
                if(s.equals("coord_ns")&&aer.get(i).getCoord_ns().startsWith(filtre))
                    aer_filtre.add(aer.get(i));
                if(s.equals("degre_oe")&&Integer.toString(aer.get(i).getDegre_oe()).startsWith(filtre))
                    aer_filtre.add(aer.get(i));
                if(s.equals("minutes_oe")&&Integer.toString(aer.get(i).getMinutes_oe()).startsWith(filtre))
                    aer_filtre.add(aer.get(i));
                if(s.equals("secondes_oe")&&Integer.toString(aer.get(i).getSecondes_oe()).startsWith(filtre))
                    aer_filtre.add(aer.get(i));
                if(s.equals("coord_oe")&&aer.get(i).getCoord_oe().startsWith(filtre))
                    aer_filtre.add(aer.get(i));
           }
           chargertableaer();
        }
        //Filtrage pour les conflits
        if(jsp_conf.isVisible()){
           conflits_filtre=new ArrayList<>();
           String s=(String) choix_conflits.getSelectedItem();
           for(int i=0;i<conflits.size();i++){
                if(s.equals("Id vols")&&conflits.get(i).getKey().startsWith(filtre))
                    conflits_filtre.add(conflits.get(i));
                if(s.equals("Id vols conflit")&&conflits.get(i).getValue().startsWith(filtre))
                    conflits_filtre.add(conflits.get(i));
                if(s.contains("altitude")&&coloration.get(conflits.get(i).getKey()).startsWith(filtre))
                    conflits_filtre.add(conflits.get(i));
           }
           chargertableconflits();
        }
        //Filtrage pour les vols
        if(jsp_vols.isVisible()){
           vols_filtre=new ArrayList<>();
           String s=(String) choix_vols.getSelectedItem();
           for(int i=0;i<vols.size();i++){
                if(s.equals("id")&&vols.get(i).getId().startsWith(filtre))
                    vols_filtre.add(vols.get(i));
                if(s.equals("aero_dep")&&vols.get(i).getAero_dep().startsWith(filtre))
                    vols_filtre.add(vols.get(i));
                if(s.equals("aero_dest")&&vols.get(i).getAero_dest().startsWith(filtre))
                    vols_filtre.add(vols.get(i));
                if(s.equals("horaire")&&vols.get(i).getHoraire().toString().startsWith(filtre))
                    vols_filtre.add(vols.get(i));
                if(s.equals("durée")&&Integer.toString(vols.get(i).getDurée()).startsWith(filtre))
                    vols_filtre.add(vols.get(i));
                if(boutonConflits.isVisible()){
                    if(s.equals("altitude")&&coloration.get(vols.get(i).getId()).startsWith(filtre)){
                       vols_filtre.add(vols.get(i)); 
                    }
                }
           }
           chargertablevols();
        }
    }
    
    /**
    * Définit la coloration pour chaque vol.
    * @param coloration Un HashMap contenant la coloration pour chaque vol.
    */
    public void setColoration(HashMap<String,String> coloration){
        this.coloration = coloration;
    }
    
    /**
    * Définit la liste des conflits et met à jour l'affichage pour inclure les conflits.
    * @param conflits Une ArrayList contenant les conflits sous forme de paires clé-valeur.
    */
    public void setConflits(ArrayList<AbstractMap.SimpleEntry<String, String>> conflits){
        this.conflits = conflits;
        if(!boutonConflits.isVisible()){
            boutonConflits.setVisible(true);
            model_vols.addColumn("Altitude");
            choix_vols.insertItemAt("altitude", 5);
        }
    }
}
