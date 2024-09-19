package SAE.MODELLE;

import java.util.AbstractMap.SimpleEntry;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Classe GrapheColor3 pour colorier un graphe et analyser les conflits.
 * 
 * Cette classe propose des méthodes pour colorier un graphe en utilisant un nombre
 * donné de couleurs, compter les conflits de coloration, et calculer les couleurs uniques.
 * 
 * @author hissam
 */
public class GrapheColor3 {

    /**
     * Méthode pour récupérer toutes les couleurs uniques utilisées dans un graphe.
     * 
     * @param graphe le graphe à analyser
     * @param nb_color le nombre maximum de couleurs possibles
     * @return un ensemble de couleurs uniques utilisées dans le graphe
     */
    public Set<Integer> recuperationCouleurs(Graph graphe, int nb_color) {
        // Créer un tableau pour stocker les couleurs
        int[] tabColor = new int[nb_color];

        // Initialiser tous les éléments du tableau à -1
        for (int i = 0; i < nb_color; i++) {
            tabColor[i] = -1;
        }

        // Parcourir tous les nœuds du graphe
        for (Node node : graphe) {
            int color = node.getAttribute("color");

            // Vérifier si la couleur est déjà dans le tableau tabColor
            boolean couleurDejaPresente = false;
            for (int i = 0; i < nb_color; i++) {
                if (tabColor[i] == color) {
                    couleurDejaPresente = true;
                    break;
                }
            }

            // Si la couleur n'est pas déjà présente, la placer dans le tableau
            if (!couleurDejaPresente) {
                for (int i = 0; i < nb_color; i++) {
                    if (tabColor[i] == -1) {
                        tabColor[i] = color;
                        break;
                    }
                }
            }
        }

        // Convertir le tableau en un ensemble pour éliminer les doublons
        Set<Integer> couleurs = new HashSet<>();
        for (int i = 0; i < nb_color; i++) {
            if (tabColor[i] != -1) {
                couleurs.add(tabColor[i]);
            }
        }

        return couleurs;
    }

    /**
     * Compte le nombre de conflits dans le graphe après la coloration.
     * 
     * @param graph le graphe colorié.
     * @return le nombre de conflits.
     */
    public static int countConflicts(Graph graph) {
        int conflicts = 0;

        Iterator<Node> nodeIterator = graph.iterator();
        while (nodeIterator.hasNext()) {
            Node currentNode = nodeIterator.next();
            int currentNodeColor = currentNode.getAttribute("color");

            Iterator<Node> neighborIterator = currentNode.getNeighborNodeIterator();
            while (neighborIterator.hasNext()) {
                Node neighbor = neighborIterator.next();
                int neighborColor = neighbor.getAttribute("color");
                if (currentNodeColor == neighborColor) {
                    conflicts++;
                }
            }
        }

        // Chaque conflit est compté deux fois (une fois pour chaque nœud), donc diviser par 2
        return conflicts / 2;
    }

    /**
     * Colore le graphe en utilisant au maximum k couleurs.
     * 
     * @param graphe le graphe à colorier.
     * @param k le nombre maximum de couleurs à utiliser.
     */
    public void colorGraph(Graph graphe, int k) {
        // Récupérer les couleurs sans doublon
        Set<Integer> couleurSet = recuperationCouleurs(graphe, k);

        // Convertir le Set en une liste
        List<Integer> colors = new ArrayList<>(couleurSet);

        // Initialiser les variables pour la boucle d'amélioration
        boolean amelioration = true;
        int nb_conflit = countConflicts(graphe);

        // Initialiser l'indice de la boucle
        int i = 0;

        // Boucle d'amélioration
        while (i < k && amelioration) {
            amelioration = false;
            int best_amelioration = -1;
            Node best_node = null;
            int best_color = -1;

            // Boucle qui parcourt tous les nœuds
            for (Node node : graphe) {
                int b_color = node.getAttribute("color");
                int b_conflit = nb_conflit_avecVoisin(node);
                int b_amelioration = 0;

                // Boucle qui parcourt toutes les couleurs de la liste colors
                for (int c = 0; c < colors.size(); c++) {
                    // Calculer le nombre de conflits si le nœud actuel était coloré avec 'colors.get(c)'
                    int conflitcolor = nb_conflit_avecVoisinAvecCouleur(node, colors.get(c));

                    // Vérifier si cette couleur apporte une amélioration
                    if (b_amelioration < b_conflit - conflitcolor) {
                        b_amelioration = b_conflit - conflitcolor;
                        b_conflit = conflitcolor;
                        b_color = c;
                    }
                }

                // Mettre à jour best_amelioration, best_color et best_node si nécessaire
                if (best_amelioration < b_amelioration) {
                    best_amelioration = b_amelioration;
                    best_color = b_color;
                    best_node = node;
                }
            }

            // Changer la couleur du meilleur nœud avec la meilleure couleur et amélioration à true si nécessaire
            if (best_amelioration > 0) {
                int couleur = colors.get(best_color); // Récupérer la couleur à l'indice best_color
                best_node.setAttribute("color", couleur);
                amelioration = true;
            }

            i++;
        }
    }

    /**
     * Méthode pour compter le nombre de conflits pour un nœud donné.
     * 
     * @param node le nœud pour lequel les conflits doivent être comptés.
     * @return le nombre de conflits.
     */
    private int nb_conflit_avecVoisin(Node node) {
        int conflicts = 0;
        int nodeColor = node.getAttribute("color");

        Iterator<Node> neighborIterator = node.getNeighborNodeIterator();
        while (neighborIterator.hasNext()) {
            Node neighbor = neighborIterator.next();
            int neighborColor = neighbor.getAttribute("color");
            if (nodeColor == neighborColor) {
                conflicts++;
            }
        }

        return conflicts;
    }

    /**
     * Méthode pour compter le nombre de conflits pour un nœud donné avec une couleur spécifique.
     * 
     * @param node le nœud pour lequel les conflits doivent être comptés.
     * @param color la couleur à utiliser pour vérifier les conflits.
     * @return le nombre de conflits.
     */
    private int nb_conflit_avecVoisinAvecCouleur(Node node, int color) {
        int conflicts = 0;
        int nodeColor = color; // La couleur est déjà un entier

        Iterator<Node> neighborIterator = node.getNeighborNodeIterator();
        while (neighborIterator.hasNext()) {
            Node neighbor = neighborIterator.next();
            int neighborColor = neighbor.getAttribute("color");
            if (nodeColor == neighborColor) {
                conflicts++;
            }
        }

        return conflicts;
    }

    /**
     * Identifie et renvoie une liste de conflits dans un graphe.
     * 
     * Un conflit est défini comme deux nœuds voisins ayant le même attribut de couleur.
     * La méthode parcourt tous les nœuds du graphe et vérifie les voisins de chaque nœud pour détecter les conflits.
     * Chaque conflit est représenté par une SimpleEntry où la clé est l'ID du premier nœud
     * et la valeur est l'ID du nœud voisin ayant la même couleur.
     * 
     * @param graph le graphe à analyser pour les conflits.
     * @return une ArrayList d'objets SimpleEntry, chaque objet représentant un conflit entre deux nœuds.
     */
    public static ArrayList<SimpleEntry<String, String>> ListeConflits(Graph graph) {
        ArrayList<SimpleEntry<String, String>> conflits = new ArrayList<>();
        Iterator<Node> nodeIterator = graph.getNodeIterator();
        while (nodeIterator.hasNext()) {
            Node currentNode = nodeIterator.next();
            int currentNodeColor = currentNode.getAttribute("color");
            Iterator<Node> neighborIterator = currentNode.getNeighborNodeIterator();
            while (neighborIterator.hasNext()) {
                Node neighbor = neighborIterator.next();
                int neighborColor = neighbor.getAttribute("color");
                if (currentNodeColor == neighborColor) {
                    conflits.add(new SimpleEntry<>(currentNode.getId(), neighbor.getId()));
                }
            }
        }

        // Retourne une liste de conflits
        return conflits;
    }

    /**
     * Affiche les couleurs utilisées pour chaque nœud dans un graphe.
     * 
     * @param graph le graphe à analyser.
     * @return une HashMap où chaque clé est l'ID d'un nœud et la valeur est la couleur utilisée pour ce nœud.
     */
    public static HashMap<String, String> affichage_des_couleurs(Graph graph) {
        HashMap<String, String> coloration = new HashMap<>();

        Iterator<Node> nodeIterator = graph.iterator();
        while (nodeIterator.hasNext()) {
            Node currentNode = nodeIterator.next();
            int currentNodeColor = currentNode.getAttribute("color_nbre");
            coloration.put(currentNode.getId(), Integer.toString(currentNodeColor));
        }
        return coloration;
    }
}
