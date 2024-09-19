/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SAE.MODELLE;

/**
 * La classe Horaire représente un horaire avec des heures et des minutes.
 * <p>
 * Elle permet de stocker et de récupérer des informations sur les heures et les minutes,
 * et de convertir l'horaire en une chaîne de caractères.
 * </p>
 * 
 * <p>Cette classe est utilisée pour manipuler des horaires dans d'autres parties du programme.</p>
 * 
 * @author hugsc
 */
public class Horaire {
    private int heure, minute;

    /**
     * Constructeur de la classe Horaire.
     * 
     * @param heure l'heure
     * @param minute la minute
     */
    public Horaire(int heure, int minute) {
        this.heure = heure;
        this.minute = minute;
    }

    /**
     * Retourne l'heure.
     * 
     * @return l'heure
     */
    public int getHeure() {
        return heure;
    }

    /**
     * Retourne les minutes.
     * 
     * @return les minutes
     */
    public int getMinute() {
        return minute;
    }
    
    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'horaire.
     * 
     * @return une chaîne de caractères représentant l'horaire sous la forme "hhh"
     */
    @Override
    public String toString() {
        return this.heure + "h" + this.minute;
    }
}
