/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.models;



/**
 *
 * @author Mohamed
 */
public class CommandeApp {
    
    private int numeroC ; 
    private Fournisseur f ;
    private Double total_c ;
    private String dateCreation ;
    private String etat ;
    private Double taux_remise ;
    private Double total_tva;

    public int getNumeroC() {
        return numeroC;
    }

    public void setNumeroC(int numeroC) {
        this.numeroC = numeroC;
    }

    public Fournisseur getF() {
        return f;
    }

    public void setF(Fournisseur f) {
        this.f = f;
    }

    public Double getTotal_c() {
        return total_c;
    }

    public void setTotal_c(Double total_c) {
        this.total_c = total_c;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Double getTaux_remise() {
        return taux_remise;
    }

    public void setTaux_remise(Double taux_remise) {
        this.taux_remise = taux_remise;
    }

    public Double getTotal_tva() {
        return total_tva;
    }

    public void setTotal_tva(Double total_tva) {
        this.total_tva = total_tva;
    }

    public CommandeApp() {
    }

    @Override
    public String toString() {
        return "CommandeApp{" + "numeroC=" + numeroC + ", f=" + f + ", total_c=" + total_c + ", dateCreation=" + dateCreation + ", etat=" + etat + ", taux_remise=" + taux_remise + ", total_tva=" + total_tva + '}';
    }
    
    
    
    
    
}
