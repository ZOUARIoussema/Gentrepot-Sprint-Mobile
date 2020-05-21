/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.models;

/**
 *
 * @author oussema
 */
public class Vehicule {

    private int id;
    private String etat;
    private int matricule;
    private int capacite;
    private String type;

    public Vehicule(int id, String etat, int matricule, int capacite, String type) {
        this.id = id;
        this.etat = etat;
        this.matricule = matricule;
        this.capacite = capacite;
        this.type = type;
    }
    
      public Vehicule(int matricule, int capacite, String type) {
        this.matricule = matricule;
        this.capacite = capacite;
        this.type = type;
    }

    public Vehicule() {
    }
    
       
    
     

    public Vehicule(String etat, int matricule, int capacite, String type) {
        this.etat = etat;
        this.matricule = matricule;
        this.capacite = capacite;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getMatricule() {
        return matricule;
    }

    public void setMatricule(int matricule) {
        this.matricule = matricule;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Vehicule{" + "id=" + id + ", etat=" + etat + ", matricule=" + matricule + ", capacite=" + capacite + ", type=" + type + '}';
    }
    
    
    
    
    
    

}
