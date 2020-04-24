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
public class ReglementFournisseurCheque extends ReglementFournisseur {
    
    private int id;
    private Date dateCheque;
    private int numeroCheque;
    private FactureAchat factureAchat;
 
   

    public ReglementFournisseurCheque(int id, Date dateCheque, int numeroCheque, FactureAchat factureAchat, double montant, Date dateCreation) {
        super(montant, dateCreation);
        this.id = id;
        this.dateCheque = dateCheque;
        this.numeroCheque = numeroCheque;
        this.factureAchat = factureAchat;
      
    }

    public ReglementFournisseurCheque(Date dateCheque, int numeroCheque, FactureAchat factureAchat, double montant, Date dateCreation) {
        super(montant, dateCreation);
        this.dateCheque = dateCheque;
        this.numeroCheque = numeroCheque;
        this.factureAchat = factureAchat;
       
    }
    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateCheque() {
        return dateCheque;
    }

    public void setDateCheque(Date dateCheque) {
        this.dateCheque = dateCheque;
    }

    public int getNumeroCheque() {
        return numeroCheque;
    }

    public void setNumeroCheque(int numeroCheque) {
        this.numeroCheque = numeroCheque;
    }

    public FactureAchat getFactureAchat() {
        return factureAchat;
    }

    public void setFactureAchat(FactureAchat factureAchat) {
        this.factureAchat = factureAchat;
    }

    @Override
    public String toString() {
        return "ReglementFournisseurCheque{" + "id=" + id + ", dateCheque=" + dateCheque + ", numeroCheque=" + numeroCheque + ", factureAchat=" + factureAchat.getNumeroF()+" "+super.toString() + '}';
    }

     
}
