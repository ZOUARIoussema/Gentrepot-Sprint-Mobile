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
public class CommandeDApprovisionnement {
    
    
    private int numeroC;
    private double totalC;
    private Date dateCreation;
    private String etat;
    private double tauxRemise;
    private double totalTva;
    private Fournisseur fournisseur;
    private List<LigneCommandeDApprovisionnement>ligneCommandeDApprovisionnements;
    
    
    
    public CommandeDApprovisionnement(Date dateCreation) {
        this.dateCreation = dateCreation;
        this.ligneCommandeDApprovisionnements= new ArrayList<>();
    }
    
    public CommandeDApprovisionnement(int nc, Date dateCreation, Fournisseur fournisseur) {
        this.numeroC = nc;
        this.dateCreation = dateCreation;
        this.fournisseur = fournisseur;
        
    }
    public CommandeDApprovisionnement(Date dateCreation, Fournisseur fournisseur,double tauxRemise) {
        this.ligneCommandeDApprovisionnements= new ArrayList<>();
        this.dateCreation = dateCreation;
        this.fournisseur = fournisseur;
        this.tauxRemise = tauxRemise;
    }
       

    public CommandeDApprovisionnement(int numeroC, double totalC, Date dateCreation, String etat, double tauxRemise, double totalTva, Fournisseur fournisseur) {
        this.numeroC = numeroC;
        this.totalC = totalC;
        this.dateCreation = dateCreation;
        this.etat = etat;
        this.tauxRemise = tauxRemise;
        this.totalTva = totalTva;
        this.fournisseur = fournisseur;
        this.ligneCommandeDApprovisionnements= new ArrayList<>();
       
    }

    public CommandeDApprovisionnement(int numeroC) {
        this.numeroC = numeroC;
    }

    
    
     
    public CommandeDApprovisionnement(double totalC, Date dateCreation, String etat, double tauxRemise, double totalTva, Fournisseur fournisseur) {
        this.totalC = totalC;
        this.dateCreation = dateCreation;
        this.etat = etat;
        this.tauxRemise = tauxRemise;
        this.totalTva = totalTva;
        this.fournisseur = fournisseur;
        this.ligneCommandeDApprovisionnements= new ArrayList<>();
    }

    public CommandeDApprovisionnement() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
       
    
    
    
    public int getNumeroC() {
        return numeroC;
    }

    public void setNumeroC(int numeroC) {
        this.numeroC = numeroC;
    }

    public double getTotalC() {
        return totalC;
    }

    public void setTotalC(double totalC) {
        this.totalC = totalC;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
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

    public double getTotalTva() {
        return totalTva;
    }

    public void setTotalTva(double totalTva) {
        this.totalTva = totalTva;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public List<LigneCommandeDApprovisionnement> getLigneCommandeDApprovisionnements() {
        return ligneCommandeDApprovisionnements;
    }

    public void setLigneCommandeDApprovisionnements(List<LigneCommandeDApprovisionnement> ligneCommandeDApprovisionnements) {
        this.ligneCommandeDApprovisionnements = ligneCommandeDApprovisionnements;
    }

   
    
    
    
    
    
    
    
    
    
    
    
    
}
