/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.models;


import java.util.Date;


/**
 *
 * @author oussema
 */
public class FactureVente {

    private int numeroF;
    private Date dateCreation;
    private Date dateEchaillancePaiement;
    private double totalTTC;
    private String etat;
    private double totalPaye;
    private double restePaye;
    private double timbreFiscale;
    private double fraisTransport;
    
    private BonLivraison bonLivraison;
    
    

    public FactureVente(int numeroF, Date dateCreation, Date dateEchaillancePaiement, double totalTTC, String etat, double totalPaye, double restePaye, double timbreFiscale, double fraisTransport, BonLivraison bonLivraison) {
        this.numeroF = numeroF;
        this.dateCreation = dateCreation;
        this.dateEchaillancePaiement = dateEchaillancePaiement;
        this.totalTTC = totalTTC;
        this.etat = etat;
        this.totalPaye = totalPaye;
        this.restePaye = restePaye;
        this.timbreFiscale = timbreFiscale;
        this.fraisTransport = fraisTransport;
        this.bonLivraison = bonLivraison;
    }
    
     public FactureVente(Date dateCreation, Date dateEchaillancePaiement, double totalTTC, String etat, double totalPaye, double restePaye, double timbreFiscale, double fraisTransport, BonLivraison bonLivraison) {
        this.dateCreation = dateCreation;
        this.dateEchaillancePaiement = dateEchaillancePaiement;
        this.totalTTC = totalTTC;
        this.etat = etat;
        this.totalPaye = totalPaye;
        this.restePaye = restePaye;
        this.timbreFiscale = timbreFiscale;
        this.fraisTransport = fraisTransport;
        this.bonLivraison = bonLivraison;
    }


    public FactureVente(int numeroF, Date dateCreation, Date dateEchaillancePaiement, double totalTTC, String etat,double tPaye) {
        this.numeroF = numeroF;
        this.dateCreation = dateCreation;
        this.dateEchaillancePaiement = dateEchaillancePaiement;
        this.totalTTC = totalTTC;
        this.etat = etat;
        this.totalPaye=tPaye;
    }

    public FactureVente(int numeroF, Date dateCreation, Date dateEchaillancePaiement, double totalTTC, String etat, double totalPaye, double restePaye) {
        this.numeroF = numeroF;
        this.dateCreation = dateCreation;
        this.dateEchaillancePaiement = dateEchaillancePaiement;
        this.totalTTC = totalTTC;
        this.etat = etat;
        this.totalPaye = totalPaye;
        this.restePaye = restePaye;
    }
    
    
     public FactureVente(Date dateCreation, Date dateEchaillancePaiement, double totalTTC, String etat, double totalPaye, double restePaye, double timbreFiscale, double fraisTransport) {
        this.dateCreation = dateCreation;
        this.dateEchaillancePaiement = dateEchaillancePaiement;
        this.totalTTC = totalTTC;
        this.etat = etat;
        this.totalPaye = totalPaye;
        this.restePaye = restePaye;
        this.timbreFiscale = timbreFiscale;
        this.fraisTransport = fraisTransport;
    }
    

    public FactureVente(int numeroF) {
        this.numeroF = numeroF;
    }
    
    
    

    public int getNumeroF() {
        return numeroF;
    }

    public void setNumeroF(int numeroF) {
        this.numeroF = numeroF;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateEchaillancePaiement() {
        return dateEchaillancePaiement;
    }

    public void setDateEchaillancePaiement(Date dateEchaillancePaiement) {
        this.dateEchaillancePaiement = dateEchaillancePaiement;
    }

    public double getTotalTTC() {
        return totalTTC;
    }

    public void setTotalTTC(double totalTTC) {
        this.totalTTC = totalTTC;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public double getTotalPaye() {
        return totalPaye;
    }

    public void setTotalPaye(double totalPaye) {
        this.totalPaye = totalPaye;
    }

    public double getRestePaye() {
        return restePaye;
    }

    public void setRestePaye(double restePaye) {
        this.restePaye = restePaye;
    }

    public double getTimbreFiscale() {
        return timbreFiscale;
    }

    public void setTimbreFiscale(double timbreFiscale) {
        this.timbreFiscale = timbreFiscale;
    }

    public double getFraisTransport() {
        return fraisTransport;
    }

    public void setFraisTransport(double fraisTransport) {
        this.fraisTransport = fraisTransport;
    }

    public BonLivraison getBonLivraison() {
        return bonLivraison;
    }

    public void setBonLivraison(BonLivraison bonLivraison) {
        this.bonLivraison = bonLivraison;
    }

   
  
   
    
    

}
