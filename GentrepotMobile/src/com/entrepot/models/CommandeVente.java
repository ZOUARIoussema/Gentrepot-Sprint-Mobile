/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author oussema
 */
public class CommandeVente {
    
    
    private int id;
    private double totalC;
    private Date dateC;
    private String etat;
    private double tauxRemise;
    private User user;
    private List<LigneCommande>ligneCommande;

    public CommandeVente(int id, double totalC, Date dateC, String etat, double tauxRemise, User user) {
        this.id = id;
        this.totalC = totalC;
        this.dateC = dateC;
        this.etat = etat;
        this.tauxRemise = tauxRemise;
        this.user = user;
        this.ligneCommande= new ArrayList<>();
    }

      public CommandeVente() {
        
         this.ligneCommande= new ArrayList<>();
        
    }

    public CommandeVente(int id) {
        this.id = id;
    }

    public CommandeVente(double totalC, String etat) {
        this.totalC = totalC;
        this.etat = etat;
    }
      
         

    public CommandeVente( int id,double totalC, Date dateC, String etat, double tauxRemise) {
        this.totalC = totalC;
        this.dateC = dateC;
        this.etat = etat;
        this.tauxRemise = tauxRemise;
        this.ligneCommande= new ArrayList<>();
        this.id = id;
        
        
    }

    public CommandeVente(double totalC, Date dateC, String etat, double tauxRemise) {
        this.totalC = totalC;
        this.dateC = dateC;
        this.etat = etat;
        this.tauxRemise = tauxRemise;
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalC() {
        return totalC;
    }

    public void setTotalC(double totalC) {
        this.totalC = totalC;
    }

    public Date getDateC() {
        return dateC;
    }

    public void setDateC(Date dateC) {
        this.dateC = dateC;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public double getTauxRemise() {
        return tauxRemise;
    }

    public void setTauxRemise(double tauxRemise) {
        this.tauxRemise = tauxRemise;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<LigneCommande> getLigneCommande() {
        return ligneCommande;
    }

    public void setLigneCommande(List<LigneCommande> ligneCommande) {
        this.ligneCommande = ligneCommande;
    }
    
    
    
    
    
    
    
    
    
}
