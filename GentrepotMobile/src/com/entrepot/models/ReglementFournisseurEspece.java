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
public class ReglementFournisseurEspece extends ReglementFournisseur {
    
    private int id;
    private FactureAchat factureAchat;
    
     

    public ReglementFournisseurEspece(int id, FactureAchat factureAchat, double montant, Date dateCreation) {
        super(montant, dateCreation);
        this.id = id;
        this.factureAchat = factureAchat;
       
    }

    public ReglementFournisseurEspece(FactureAchat factureAchat, double montant, Date dateCreation) {
        super(montant, dateCreation);
        this.factureAchat = factureAchat;
         
    }

    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FactureAchat getFactureAchat() {
        return factureAchat;
    }

    public void setFactureAchat(FactureAchat factureAchat) {
        this.factureAchat = factureAchat;
    }

    

     

   
     
    
    
    
    
    
}
