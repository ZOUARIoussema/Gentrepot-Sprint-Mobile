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
public class InventaireStock {
    
    
    private int id;
    private ProduitAchat produitAchat;
    private Emplacement emplacement;
    private String dateCreation;
    private int qunatiteInventiare;
    private int ecart;
    private int quantiteTheorique;

   public InventaireStock(int id, ProduitAchat produitAchat, Emplacement emplacement, String dateCreation, int qunatiteInventiare, int ecart, int quantiteTheorique) {
        this.id = id;
        this.produitAchat = produitAchat;
        this.emplacement = emplacement;
        this.dateCreation = dateCreation;
        this.qunatiteInventiare = qunatiteInventiare;
        this.ecart = ecart;
        this.quantiteTheorique = quantiteTheorique;
    }
    public InventaireStock(ProduitAchat produitAchat, Emplacement emplacement, int qunatiteInventiare) {
        
        this.produitAchat = produitAchat;
        this.emplacement = emplacement;
        this.qunatiteInventiare = qunatiteInventiare;
    }
    
    public InventaireStock(ProduitAchat produitAchat, Emplacement emplacement, String dateCreation, int qunatiteInventiare, int ecart, int quantiteTheorique) {
        
        this.produitAchat = produitAchat;
        this.emplacement = emplacement;
        this.dateCreation = dateCreation;
        this.qunatiteInventiare = qunatiteInventiare;
        this.ecart = ecart;
        this.quantiteTheorique = quantiteTheorique;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProduitAchat getProduitAchat() {
        return produitAchat;
    }

    public void setProduitAchat(ProduitAchat produitAchat) {
        this.produitAchat = produitAchat;
    }

    public Emplacement getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(Emplacement emplacement) {
        this.emplacement = emplacement;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    

    public int getQunatiteInventiare() {
        return qunatiteInventiare;
    }

    public void setQunatiteInventiare(int qunatiteInventiare) {
        this.qunatiteInventiare = qunatiteInventiare;
    }

    public int getEcart() {
        return ecart;
    }

    public void setEcart(int ecart) {
        this.ecart = ecart;
    }

    public int getQuantiteTheorique() {
        return quantiteTheorique;
    }

    public void setQuantiteTheorique(int quantiteTheorique) {
        this.quantiteTheorique = quantiteTheorique;
    }
    
    
    
    
    
    
    
}
