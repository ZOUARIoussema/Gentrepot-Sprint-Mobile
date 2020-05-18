/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.models;

import java.util.ArrayList;

 

/**
 *
 * @author oussema
 */
public class LigneCommande {
    public static ArrayList<ProduitAchat> pan ;
    
    private int id;
    private CommandeVente commandeVente;
    private ProduitAchat produit;
    private User user;
    private double prix;
    private int quantite;

    public LigneCommande() {
    }
    private double total;
    private double tva;
    
    
    
    
    
    
    
    private String refp;

    public LigneCommande( CommandeVente commandeVente, ProduitAchat produit, User user, double prix, int quantite, double total, double tva) {
      
        this.commandeVente = commandeVente;
        this.produit = produit;
        this.user = user;
        this.prix = prix;
        this.quantite = quantite;
        this.total = total;
        this.tva = tva;
        
        
        this.refp=produit.getReference();
    }

    public LigneCommande(CommandeVente commandeVente, ProduitAchat produit, double prix, int quantite, double total, double tva) {
        this.commandeVente = commandeVente;
        this.produit = produit;
        this.prix = prix;
        this.quantite = quantite;
        this.total = total;
        this.tva = tva;
        
        
        this.refp=produit.getReference();
    }

    public LigneCommande( double prix, int quantite, double total, double tva) {
        this.prix = prix;
        this.quantite = quantite;
        this.total = total;
        this.tva = tva;
       this.refp=produit.getLibelle();

       
    }

    

    
    
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CommandeVente getCommandeVente() {
        return commandeVente;
    }

    public void setCommandeVente(CommandeVente commandeVente) {
        this.commandeVente = commandeVente;
    }

    public ProduitAchat getProduit() {
        return produit;
    }

    public void setProduit(ProduitAchat produit) {
        this.produit = produit;
    }

  

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTva() {
        return tva;
    }

    public void setTva(double tva) {
        this.tva = tva;
    }

    @Override
    public String toString() {
        return "LigneCommande{" + "id=" + id + ", produit=" + produit + ", user=" + user + ", prix=" + prix + ", quantite=" + quantite + ", total=" + total + ", tva=" + tva + '}';
    }

    public String getRefp() {
        return refp;
    }

    public void setRefp(String refp) {
        this.refp = refp;
    }

     
    
    
    
    
}
