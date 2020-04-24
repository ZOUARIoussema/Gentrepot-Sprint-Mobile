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
public class LignePerte {
    
    private int id;
    private Perte perte;
    private ProduitAchat produitAchat;
    private int quantite;
    private String raisonPerte;

    public LignePerte(int id, Perte perte, ProduitAchat produitAchat, int quantite, String raisonPerte) {
        this.id = id;
        this.perte = perte;
        this.produitAchat = produitAchat;
        this.quantite = quantite;
        this.raisonPerte = raisonPerte;
    }
    
    
    
    public LignePerte(Perte perte, ProduitAchat produitAchat, int quantite, String raisonPerte) {
        
        this.perte = perte;
        this.produitAchat = produitAchat;
        this.quantite = quantite;
        this.raisonPerte = raisonPerte;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Perte getPerte() {
        return perte;
    }

    public void setPerte(Perte perte) {
        this.perte = perte;
    }

    public ProduitAchat getProduitAchat() {
        return produitAchat;
    }

    public void setProduitAchat(ProduitAchat produitAchat) {
        this.produitAchat = produitAchat;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getRaisonPerte() {
        return raisonPerte;
    }

    public void setRaisonPerte(String raisonPerte) {
        this.raisonPerte = raisonPerte;
    }
    
    
    
    
    
    
    
}
