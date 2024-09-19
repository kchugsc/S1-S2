/**
 * <p>Package: SAE.MODELLE</p>
 * Cette classe représente le point d'entrée de l'application SAE.
 * Elle initialise la fenêtre de choix et la rend visible.
 *
 * @author hugsc
 */
package SAE.MODELLE;

import SAE.VUE.VueChoix;

/**
 * Classe principale de l'application SAE.
 */
public class JAVASAE {

    /**
     * Méthode principale qui exécute l'application SAE.
     *
     * @param args les arguments de la ligne de commande (non utilisés dans cette application)
     */
    public static void main(String[] args) {
        VueChoix fen = new VueChoix();
        fen.setVisible(true);
    }
}
