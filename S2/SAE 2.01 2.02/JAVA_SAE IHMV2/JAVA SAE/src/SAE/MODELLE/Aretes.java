/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SAE.MODELLE;

import java.util.ArrayList;
import java.util.HashMap;
import org.graphstream.graph.Graph;

/**
 * La classe Aretes fournit des méthodes pour détecter les collisions entre les vols
 * et pour vérifier si deux vols sont susceptibles de se croiser.
 * 
 * <p>Les coordonnées géographiques des aéroports sont utilisées pour calculer les
 * coordonnées cartésiennes et pour vérifier les intersections entre les segments de vol.</p>
 * 
 * <p>Le rayon de la terre en kilomètres est utilisé pour ces calculs.</p>
 */
public class Aretes {

    // Rayon de la terre en kilomètres
    private static final double RAYON_TERRE = 6371.0;
    private static int nbconf = 0;

    /**
     * Détecte les collisions entre les vols et ajoute une arête dans le graphe pour indiquer une collision.
     *
     * @param graphe    le graphe représentant les vols
     * @param vols      la liste des vols
     * @param aeroports la liste des aéroports
     * @param ecart_secu l'écart de sécurité en minutes
     */
    public static void detecterCollisions(Graph graphe, ArrayList<Vols> vols, HashMap<String,Aeroport> aeroports,int ecart_secu) {
        // Parcourir tous les paires de vols pour vérifier les collisions
        for (int i = 0; i < vols.size(); i++) {
            for (int j = i + 1; j < vols.size(); j++) {
                Vols vol1 = vols.get(i);
                Vols vol2 = vols.get(j);
                if(graphe.getNode(vol1.getId()) == null){
                    graphe.addNode(vol1.getId());
                }
                if(graphe.getNode(vol2.getId()) == null){
                    graphe.addNode(vol2.getId());
                }
                if (sontSusceptiblesDeSeCroiser(vol1, vol2, aeroports, ecart_secu)) {
                    // Ajouter une arête entre les vols dans le graphe pour indiquer la collision
                    graphe.addEdge(vol1.getId() + "-" + vol2.getId(), vol1.getId(), vol2.getId());
                }
            }
        }
    }

    /**
     * Vérifie si deux vols sont susceptibles de se croiser.
     *
     * @param vol1      le premier vol
     * @param vol2      le deuxième vol
     * @param aeroports la liste des aéroports
     * @param ecart_secu l'écart de sécurité en minutes
     * @return true si les vols sont susceptibles de se croiser, false sinon
     */
    private static boolean sontSusceptiblesDeSeCroiser(Vols vol1, Vols vol2, HashMap<String,Aeroport> aeroports, int ecart_secu) {
        // Calcul des coordonnées cartésiennes des aéroports de départ et d'arrivée
        if (aeroports.get(vol1.getAero_dep()) != null && aeroports.get(vol1.getAero_dest()) != null &&
            aeroports.get(vol2.getAero_dep()) != null && aeroports.get(vol2.getAero_dest()) != null) {
        
            double[] coord1_dep = calculerCoordCartesienne(aeroports.get(vol1.getAero_dep()));
            double[] coord1_dest = calculerCoordCartesienne(aeroports.get(vol1.getAero_dest()));
            double[] coord2_dep = calculerCoordCartesienne(aeroports.get(vol2.getAero_dep()));
            double[] coord2_dest = calculerCoordCartesienne(aeroports.get(vol2.getAero_dest()));

            // Vérifier si tous les aéroports sont en commun
            int taeroportcom = TousAeroportsontcommun(vol1, vol2);
            if (taeroportcom == 0) {
                // Vérifie si il y a des aéroports en commun
                boolean aeroportcom = Aeroportsontegal(vol1, vol2);
                // Vérifier si les segments de droite associés s'intersectent dans le plan
                Object[] croise = segmentsSeCroisent(coord1_dep, coord1_dest, coord2_dep, coord2_dest);
        
                if (aeroportcom == true || (boolean)croise[0] == true) {
                    // Vérifier si l'écart entre les horaires de passage théoriques n'excède pas l'écart de sécurité
                    double ecart = ecartEntreHoraires(vol1.getHoraire(), vol2.getHoraire(), vol1.getDurée(), vol2.getDurée(), coord1_dep, coord1_dest, coord2_dep, coord2_dest, (double[])croise[1]);
                    if (ecart < ecart_secu && ecart > ecart_secu * (-1)) {
                        return true; // Les vols sont susceptibles de se croiser
                    }
                }
            } else {
                // Si les vols font le chemin inverse
                if (taeroportcom == 1) {
                    return ecartEntreHoraires2(vol1.getHoraire(), vol2.getHoraire(), vol1.getDurée(), vol2.getDurée());
                }
                if (taeroportcom == 2) {
                    return ecartEntreHoraires3(vol1.getHoraire(), vol2.getHoraire(), vol1.getDurée(), vol2.getDurée(), ecart_secu);
                }
            }
        }

        return false; // Les vols ne se croisent pas
    }
    
    /**
     * Méthode privée pour vérifier que les vols ont des aéroports en commun.
     *
     * @param vol1 le premier vol
     * @param vol2 le deuxième vol
     * @return true si les vols ont des aéroports en commun, false sinon
     */
    private static boolean Aeroportsontegal(Vols vol1, Vols vol2) {
        if (vol1.getAero_dep().compareTo(vol2.getAero_dep()) == 0) {
            return true;
        }
        if (vol1.getAero_dep().compareTo(vol2.getAero_dest()) == 0) {
            return true;
        }
        if (vol1.getAero_dest().compareTo(vol2.getAero_dest()) == 0) {
            return true;
        }
        if (vol1.getAero_dest().compareTo(vol2.getAero_dep()) == 0) {
            return true;
        }
        return false;
    }
    
    /**
     * Méthode pour vérifier si tous les aéroports sont en commun entre deux vols.
     *
     * @param vol1 le premier vol
     * @param vol2 le deuxième vol
     * @return 1 si les vols font le chemin inverse, 2 si les aéroports de départ et d'arrivée sont les mêmes, 0 sinon
     */
    private static int TousAeroportsontcommun(Vols vol1, Vols vol2) {
        if (vol1.getAero_dep().compareTo(vol2.getAero_dest()) == 0 && vol1.getAero_dest().compareTo(vol2.getAero_dep()) == 0) {
            return 1;
        }
        if (vol1.getAero_dep().compareTo(vol2.getAero_dep()) == 0 && vol1.getAero_dest().compareTo(vol2.getAero_dest()) == 0) {
            return 2;
        }
        return 0;
    }

    /**
     * Convertit les coordonnées géographiques en coordonnées cartésiennes.
     *
     * @param coordGeo les coordonnées géographiques
     * @return un tableau contenant les coordonnées cartésiennes {x, y}
     */
    private static double[] calculerCoordCartesienne(Aeroport coordGeo) {
        int deglat = coordGeo.getDegre_ns();
        int minlat = coordGeo.getMinutes_ns();
        int seclat = coordGeo.getSecondes_ns();
        int coeflat;
        
        if (coordGeo.getCoord_ns().equals("N"))
            coeflat = 1;
        else
            coeflat = -1;
        
        int deglong = coordGeo.getDegre_oe();
        int minlong = coordGeo.getMinutes_oe();
        int seclong = coordGeo.getSecondes_oe();
        int coeflong;
        
        if (coordGeo.getCoord_oe().equals("E"))
            coeflong = 1;
        else
            coeflong = -1;

        double latitude = coeflat * (deglat + (minlat / 60.0) + (seclat / 3600.0));
        double longitude = coeflong * (deglong + (minlong / 60.0) + (seclong / 3600.0));
        double radlat = Math.toRadians(latitude);
        double radlong = Math.toRadians(longitude);

        double x = RAYON_TERRE * Math.cos(radlat) * Math.sin(radlong);
        double y = RAYON_TERRE * Math.cos(radlat) * Math.cos(radlong);

        return new double[] { x, y };
    }

    /**
     * Vérifie si deux segments de droite se croisent dans le plan.
     *
     * @param A les coordonnées du premier point du premier segment
     * @param B les coordonnées du deuxième point du premier segment
     * @param C les coordonnées du premier point du deuxième segment
     * @param D les coordonnées du deuxième point du deuxième segment
     * @return un tableau contenant un boolean indiquant si les segments se croisent et les coordonnées du point d'intersection
     */
    private static Object[] segmentsSeCroisent(double[] A, double[] B, double[] C, double[] D) {
        double m1 = (B[1] - A[1]) / (B[0] - A[0]);
        double m2 = (D[1] - C[1]) / (D[0] - C[0]);
        double p1 = A[1] - (A[0] * m1);
        double p2 = C[1] - (C[0] * m2);
        double xintsect = ((p2 - p1) / (m1 - m2));
        double yintsect = m1 * xintsect + p1;
        double[] I = { xintsect, yintsect }; // calcul du point d'intersection
        double[] vectAB = { (B[0] - A[0]), (B[1] - A[1]) };
        double[] vectCD = { (D[0] - C[0]), (D[1] - C[1]) };
        double[] vectAI = { (I[0] - A[0]), (I[1] - A[1]) };
        double[] vectCI = { (I[0] - C[0]), (I[1] - C[1]) }; // calcul des vecteurs
        double KAB = vectAB[0] * vectAB[0] + vectAB[1] * vectAB[1];
        double KCD = vectCD[0] * vectCD[0] + vectCD[1] * vectCD[1];
        double KAI = vectAB[0] * vectAI[0] + vectAB[1] * vectAI[1];
        double KCI = vectCD[0] * vectCI[0] + vectCD[1] * vectCI[1]; // calcul des produit scalair
        if ((KAI >= 0 && KAI <= KAB) && (KCI >= 0 && KCI <= KCD)) {
            return new Object[] { true, I }; // le point d'intersection est sur les deux segments
        } else {
            return new Object[] { false, I }; // le point d'intersection est sur les droites mais pas sur les segments
        }
    }
    

    /**
     * Calcule l'écart entre les horaires de deux vols en minutes.
     *
     * @param horaire1 l'horaire du premier vol
     * @param horaire2 l'horaire du deuxième vol
     * @param duree1   la durée du premier vol en minutes
     * @param duree2   la durée du deuxième vol en minutes
     * @param A        les coordonnées du premier point du premier segment
     * @param B        les coordonnées du deuxième point du premier segment
     * @param C        les coordonnées du premier point du deuxième segment
     * @param D        les coordonnées du deuxième point du deuxième segment
     * @param I        les coordonnées du point d'intersection
     * @return l'écart en minutes entre les horaires de passage théoriques des vols
     */
    private static double ecartEntreHoraires(Horaire horaire1, Horaire horaire2, int duree1, int duree2, double[] A, double[] B, double[] C, double[] D, double[] I) {
        int heures1 = horaire1.getHeure();
        int minutes1 = horaire1.getMinute();
        int heures2 = horaire2.getHeure();
        int minutes2 = horaire2.getMinute();
        
        int totalMinutes1 = heures1 * 60 + minutes1;
        int totalMinutes2 = heures2 * 60 + minutes2;
        
        double distAB = Math.sqrt(Math.pow((B[0] - A[0]), 2) + Math.pow((B[1] - A[1]), 2));
        double distAI = Math.sqrt(Math.pow((I[0] - A[0]), 2) + Math.pow((I[1] - A[1]), 2));
        double distCD = Math.sqrt(Math.pow((D[0] - C[0]), 2) + Math.pow((D[1] - C[1]), 2));
        double distCI = Math.sqrt(Math.pow((I[0] - C[0]), 2) + Math.pow((I[1] - C[1]), 2));
        
        double dureeAI = duree1 * distAI / distAB;
        double dureeCI = duree2 * distCI / distCD;
        
        double arriveAI = totalMinutes1 + dureeAI;
        double arriveCI = totalMinutes2 + dureeCI;
        
        return Math.abs(Math.abs((arriveAI - arriveCI))); // Retourne l'écart en minutes
    }

    /**
     * Vérifie si l'écart entre les horaires de deux vols est dans l'écart de sécurité.
     *
     * @param horaire1 l'horaire du premier vol
     * @param horaire2 l'horaire du deuxième vol
     * @param duree1   la durée du premier vol en minutes
     * @param duree2   la durée du deuxième vol en minutes
     * @return true si les vols se croisent, false sinon
     */
    private static boolean ecartEntreHoraires2(Horaire horaire1, Horaire horaire2, int duree1, int duree2) {
        boolean secroise = false;
        int heures1 = horaire1.getHeure();
        int minutes1 = horaire1.getMinute();
        int heures2 = horaire2.getHeure();
        int minutes2 = horaire2.getMinute();
        
        int totalMinutes1 = heures1 * 60 + minutes1;
        int totalMinutes2 = heures2 * 60 + minutes2;
        int minutearrive1 = totalMinutes1 + duree1;
        int minutearrive2 = totalMinutes2 + duree2;
        
        if (totalMinutes1 <= totalMinutes2) {
            if (minutearrive1 > totalMinutes2) {
                secroise = true;
            }
        } else if (totalMinutes2 <= totalMinutes1) {
            if (minutearrive2 > totalMinutes1) {
                secroise = true;
            }
        }
        return secroise;
    }

    /**
     * Vérifie si l'écart entre les horaires de deux vols est dans l'écart de sécurité.
     *
     * @param horaire1   l'horaire du premier vol
     * @param horaire2   l'horaire du deuxième vol
     * @param duree1     la durée du premier vol en minutes
     * @param duree2     la durée du deuxième vol en minutes
     * @param ecart_secu l'écart de sécurité en minutes
     * @return true si les vols se croisent, false sinon
     */
    private static boolean ecartEntreHoraires3(Horaire horaire1, Horaire horaire2, int duree1, int duree2, int ecart_secu) {
        boolean secroise = false;
        int heures1 = horaire1.getHeure();
        int minutes1 = horaire1.getMinute();
        int heures2 = horaire2.getHeure();
        int minutes2 = horaire2.getMinute();
        
        int Minutesdepart1 = heures1 * 60 + minutes1;
        int Minutesdepart2 = heures2 * 60 + minutes2;
        int minutearrive1 = Minutesdepart1 + duree1;
        int minutearrive2 = Minutesdepart2 + duree2;
        
        if ((Minutesdepart1 - Minutesdepart2) < ecart_secu && Minutesdepart1 - Minutesdepart2 > ecart_secu * (-1)) {
            secroise = true;
        } else if (Minutesdepart1 <= Minutesdepart2) {
            if (minutearrive1 > minutearrive2) {
                secroise = true;
            }
        } else if (Minutesdepart2 <= Minutesdepart1) {
            if (minutearrive2 > minutearrive1) {
                secroise = true;
            }
        }
        return secroise;
    }
}
