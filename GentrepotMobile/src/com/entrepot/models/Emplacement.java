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
public class Emplacement {
    
    private int id;
    private String adresse;
    private int capaciteStockage;
    private int quantiteStocker;
    private String classe;
    private Entrepot entrepot;

    public Emplacement(int id, String adresse, int capaciteStockage, int quantiteStocker, String classe, Entrepot entrepot) {
        this.id = id;
        this.adresse = adresse;
        this.capaciteStockage = capaciteStockage;
        this.quantiteStocker = quantiteStocker;
        this.classe = classe;
        this.entrepot = entrepot;
    }
    
    
    
      
    public Emplacement(String adresse, int capaciteStockage, int quantiteStocker, String classe, Entrepot entrepot) {
        
        this.adresse = adresse;
        this.capaciteStockage = capaciteStockage;
        this.quantiteStocker = quantiteStocker;
        this.classe = classe;
        this.entrepot = entrepot;
        
    }
    
    public Emplacement(String adresse, int capaciteStockage, int quantiteStocker, String classe) {
        
        this.adresse = adresse;
        this.capaciteStockage = capaciteStockage;
        this.quantiteStocker = quantiteStocker;
        this.classe = classe;
        
        
    }
    public Emplacement(int id ,String adresse) {
        
        this.adresse = adresse;
        this.id = id;
               
    }
      
      
    public Emplacement(int id) {
        
        this.id = id;
       
    }

    
         
       

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getCapaciteStockage() {
        return capaciteStockage;
    }

    public void setCapaciteStockage(int capaciteStockage) {
        this.capaciteStockage = capaciteStockage;
    }

    public int getQuantiteStocker() {
        return quantiteStocker;
    }

    public void setQuantiteStocker(int quantiteStocker) {
        this.quantiteStocker = quantiteStocker;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Entrepot getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(Entrepot entrepot) {
        this.entrepot = entrepot;
    }

    @Override
    public String toString() {
        return "Emplacement{" + "id=" + id + ", adresse=" + adresse + ", capaciteStockage=" + capaciteStockage + ", quantiteStocker=" + quantiteStocker + ", classe=" + classe + '}';
    }
    
    
    
    
    
    
    
    
    
    
    
}
