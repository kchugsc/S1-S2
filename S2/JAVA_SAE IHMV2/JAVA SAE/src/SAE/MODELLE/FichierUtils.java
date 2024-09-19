package SAE.MODELLE;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import org.graphstream.graph.Graph;

/**
 * Classe utilitaire pour le chargement des fichiers de données.
 */
public abstract class FichierUtils {

    /**
     * Charge un graphe à partir d'un fichier.
     *
     * @param graphe     Le graphe à remplir.
     * @param nom_fichier Le nom du fichier à charger.
     * @return Le nombre maximum de sommets (kmax).
     */
    public static int chargement_graphes(Graph graphe,String nom_fichier){
       int i = 0;
        try{
            FileInputStream file = new FileInputStream(nom_fichier);
            Scanner scanner = new Scanner(file);
            int kmax =Integer.parseInt(scanner.nextLine());
            String nbsommet =scanner.nextLine();

            while(scanner.hasNextLine()){
                String[] s=scanner.nextLine().split(" ");
                if(s.length==2)
                    if(graphe.getNode(s[0]) == null){
                    graphe.addNode(s[0]);
                    }
                    if(graphe.getNode(s[1]) == null){
                    graphe.addNode(s[1]);
                    }
                    if(graphe.getEdge(s[0]+"-"+s[1])== null && graphe.getEdge(s[1]+"-"+s[0])== null){
                    graphe.addEdge(s[0]+"-"+s[1],s[0],s[1]);
                    i+=1;}
            }
            scanner.close();
            return kmax;
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Charge une liste d'aéroports à partir d'un fichier.
     *
     * @param nom_fichier Le nom du fichier à charger.
     * @return Une map des aéroports avec leur identifiant.
     */
    public static HashMap<String, Aeroport> chargement_aeroport(String nom_fichier){
        HashMap<String, Aeroport> aeroports=new HashMap<>();
        try{
            FileInputStream file = new FileInputStream(nom_fichier);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String[] s=scanner.nextLine().split(";");
                if(s.length==10)
                    aeroports.put(s[0],new Aeroport(s[0],s[1],parseInt(s[2]),parseInt(s[3]),parseInt(s[4]),s[5],parseInt(s[6]),parseInt(s[7]),parseInt(s[8]),s[9]));
            }
            scanner.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return aeroports;
    }

    /**
     * Charge une liste d'aéroports à partir d'un fichier.
     *
     * @param nom_fichier Le nom du fichier à charger.
     * @return Une liste des aéroports.
     */
    public static ArrayList<Aeroport> chargement_aeroport_liste(String nom_fichier){
        ArrayList<Aeroport> aeroports=new ArrayList<>();
        try{
            FileInputStream file = new FileInputStream(nom_fichier);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String[] s=scanner.nextLine().split(";");
                if(s.length==10)
                    aeroports.add(new Aeroport(s[0],s[1],parseInt(s[2]),parseInt(s[3]),parseInt(s[4]),s[5],parseInt(s[6]),parseInt(s[7]),parseInt(s[8]),s[9]));
            }
            scanner.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return aeroports;
    }

    /**
     * Charge une liste de vols à partir d'un fichier.
     *
     * @param nom_fichier Le nom du fichier à charger.
     * @return Une liste des vols.
     */
    public static ArrayList<Vols> chargement_vols(String nom_fichier){
        ArrayList<Vols> vols=new ArrayList();
        try{
            FileInputStream file = new FileInputStream(nom_fichier);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String[] s=scanner.nextLine().split(";");
                if(s.length==6)
                    vols.add(new Vols(s[0],s[1],s[2],new Horaire(parseInt(s[3]),parseInt(s[4])),parseInt(s[5])));
            }
            scanner.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return vols;
    }
}