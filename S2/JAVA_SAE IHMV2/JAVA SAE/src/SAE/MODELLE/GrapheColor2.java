package SAE.MODELLE;

import java.util.ArrayList;
import java.util.Iterator;
import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/**
 * Classe GrapheColor2 pour colorier un graphe et analyser les conflits.
 * 
 * Cette classe propose des méthodes pour colorier un graphe en utilisant un nombre
 * donné de couleurs, compter les conflits de coloration, et calculer le diamètre du graphe.
 * 
 * @author hissam
 */
public class GrapheColor2 {
    
    /**
     * Génère une couleur aléatoire.
     * 
     * @return un entier représentant une couleur aléatoire.
     */
    private static int couleurAleatoire() {
        int couleur = 112 + (int)(Math.random() * (16777215 - 112 + 1));
        return couleur;
    }

    /**
     * Colore le graphe en utilisant au maximum k couleurs.
     * 
     * @param graph le graphe à colorier.
     * @param k le nombre maximum de couleurs à utiliser.
     */
    public static void colorGraph(Graph graph, int k) {
        ArrayList<Node> nodeList = new ArrayList<>();

        // Ajouter tous les nœuds du graphe à la liste et initialiser les attributs 
        Iterator<Node> nodeIterator = graph.getNodeIterator();
        while (nodeIterator.hasNext()) {
            Node currentNode = nodeIterator.next();
            nodeList.add(currentNode);
            currentNode.addAttribute("color", -1);
            currentNode.addAttribute("color_nbre", -1);
            currentNode.addAttribute("saturation", currentNode.getDegree());
            currentNode.addAttribute("encontactcolor", 0);
            currentNode.addAttribute("degré", currentNode.getDegree());
            currentNode.setAttribute("ui.style", "size: 20px;");
        }

        // Trier la liste des nœuds par ordre décroissant de degrés en utilisant un tri par sélection
        int n = nodeList.size();
        int i = 0;
        while (i < n - 1) {
            int maxIndex = i;
            int j = i + 1;
            while (j < n) {
                if (nodeList.get(j).getDegree() > nodeList.get(maxIndex).getDegree()) {
                    maxIndex = j;
                }
                j++;
            }
            // Échanger les éléments
            Node temp = nodeList.get(maxIndex);
            nodeList.set(maxIndex, nodeList.get(i));
            nodeList.set(i, temp);
            i++;
        }

        // Créer un tableau de couleurs aléatoires de taille k hauteur / couleur
        int[] couleurs = new int[k];
        i = 1;
        while (i < k) {
            couleurs[i] = couleurAleatoire();
            i++;
        }

        // Boucle principale pour colorier les nœuds
        while (!nodeList.isEmpty()) {
            // Prendre le premier élément de la liste avec la plus grande saturation
            Node node = nodeList.get(0);
            nodeList.remove(0);

            int contactColor = node.getAttribute("encontactcolor");

            // Si contactColor est 0, attribuer la première couleur de la liste
            if (contactColor == 0) {
                node.setAttribute("color", couleurs[0]);
                node.setAttribute("color_nbre", 1);
            } else {
                // Trouver la couleur avec le moins de conflits
                int minConflicts = Integer.MAX_VALUE;
                int bestColor = -1;
                int colorIndex = 0;
                while (colorIndex < k) {
                    int conflicts = 0;
                    Iterator<Node> neighborIterator = node.getNeighborNodeIterator();
                    while (neighborIterator.hasNext()) {
                        Node neighbor = neighborIterator.next();
                        int neighborColor = neighbor.getAttribute("color");
                        if (neighborColor == couleurs[colorIndex]) {
                            conflicts++;
                        }
                    }
                    if (conflicts < minConflicts) {
                        minConflicts = conflicts;
                        bestColor = couleurs[colorIndex];
                    }
                    colorIndex++;
                }
                node.setAttribute("color", bestColor);
                node.setAttribute("color_nbre", colorIndex);
            }

            // Mettre à jour la saturation et les couleurs de contact pour les voisins
            Iterator<Node> neighborIterator = node.getNeighborNodeIterator();
            while (neighborIterator.hasNext()) {
                Node neighbor = neighborIterator.next();
                int neighborContactColor = neighbor.getAttribute("encontactcolor");
                if (neighborContactColor == 0) {
                    neighbor.setAttribute("saturation", 1);
                } else {
                    int currentSaturation = neighbor.getAttribute("saturation");
                    neighbor.setAttribute("saturation", currentSaturation + 1);
                }
                neighbor.setAttribute("encontactcolor", 1);
            }

            // Retrier la liste des nœuds en fonction de la saturation
            nodeList.sort((Node a, Node b) -> Integer.compare(b.getAttribute("saturation"), a.getAttribute("saturation")));
        }

        // Mise à jour de la couleur de remplissage (fill-color) pour chaque nœud
        nodeIterator = graph.getNodeIterator();
        while (nodeIterator.hasNext()) {
            Node currentNode = nodeIterator.next();
            int color = currentNode.getAttribute("color");
            String hexColor = String.format("#%06X", (0xFFFFFF & color));
            currentNode.setAttribute("ui.style", "size: 20px; fill-color: " + hexColor + ";");
        }
    }

    /**
     * Compte le nombre de conflits dans le graphe après la coloration.
     * 
     * @param graph le graphe colorié.
     * @return le nombre de conflits.
     */
    public static int countConflicts(Graph graph) {
        int conflicts = 0;

        Iterator<Node> nodeIterator = graph.getNodeIterator();
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
     * Compte les conflits et les liste par noeud.
     * 
     * @param graph le graphe colorié.
     * @return une liste de conflits par noeud.
     */
    public static ArrayList<String> countConflicts2(Graph graph) {
        int conflicts = 0;
        ArrayList<String> conflits = new ArrayList<>();
        String vols;
        Iterator<Node> nodeIterator = graph.getNodeIterator();
        while (nodeIterator.hasNext()) {
            Node currentNode = nodeIterator.next();
            int currentNodeColor = currentNode.getAttribute("color");
            vols = "Conflits pour le vol " + currentNode.getId() + ": ";  
            Iterator<Node> neighborIterator = currentNode.getNeighborNodeIterator();
            while (neighborIterator.hasNext()) {
                Node neighbor = neighborIterator.next();
                int neighborColor = neighbor.getAttribute("color");
                if (currentNodeColor == neighborColor) {
                    vols += neighbor.getId() + ", ";
                    conflicts++;
                }
            }
            System.out.println(vols);
            conflits.add(vols);
        }

        // Chaque conflit est compté deux fois (une fois pour chaque nœud), donc diviser par 2
        return conflits;
    }

    /**
     * Calcule le diamètre du graphe en utilisant l'algorithme de Floyd-Warshall.
     * 
     * @param graph le graphe dont le diamètre doit être calculé.
     * @return le diamètre du graphe.
     */
    public static int graphDiameter(Graph graph) {
        int n = graph.getNodeCount();
        int[][] dist = new int[n][n];

        // Initialiser les distances
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    dist[i][j] = Integer.MAX_VALUE / 2; // Éviter le débordement
                }
            }
        }

        // Mettre à jour les distances initiales basées sur les arêtes du graphe
        Iterator<Node> nodeIterator = graph.getNodeIterator();
        while (nodeIterator.hasNext()) {
            Node u = nodeIterator.next();
            int uIndex = u.getIndex();
            Iterator<Node> neighborIterator = u.getNeighborNodeIterator();
            while (neighborIterator.hasNext()) {
                Node v = neighborIterator.next();
                int vIndex = v.getIndex();
                dist[uIndex][vIndex] = 1; // Distance entre voisins immédiats
            }
        }

        // Appliquer l'algorithme de Floyd-Warshall
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        // Trouver le diamètre (la plus grande des plus courtes distances)
        int diameter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dist[i][j] < Integer.MAX_VALUE / 2 && dist[i][j] > diameter) {
                    diameter = dist[i][j];
                }
            }
        }

        return diameter;
    }

    /**
     * Calcule le nombre de composantes connexes dans un graphe.
     * 
     * Cette méthode utilise la classe ConnectedComponents de la bibliothèque GraphStream
     * pour initialiser le graphe et déterminer le nombre de composantes connexes. Une composante
     * connexe est un sous-graphe dans lequel tous les nœuds sont connectés entre eux par des chemins,
     * et qui n'est connecté à aucun nœud supplémentaire dans le super-graphe.
     * 
     * @param graph le graphe pour lequel le nombre de composantes connexes doit être calculé.
     * @return le nombre de composantes connexes dans le graphe.
     */
    public static int calculConnexe(Graph graph) {
        ConnectedComponents cc = new ConnectedComponents();
        cc.init(graph);
        return cc.getConnectedComponentsCount();
    }
}
