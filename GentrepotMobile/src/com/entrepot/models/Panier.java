/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.models;



/**
 *
 * @author guiforodrigue
 */
public class Panier {
    
    private String produit;
    private int quantite;
    private double prix;    
    private double tva;
   
    public Panier(String produit, int quantite, double prix, double tva) {
        this.produit = produit;
        this.quantite = quantite;
        this.prix = prix;
        this.tva = tva;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public double getTva() {
        return tva;
    }

    public void setTva(double tva) {
        this.tva = tva;
    }
   
}
