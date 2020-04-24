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
public class LigneCommandeDApprovisionnement {

    private int id;
    private CommandeDApprovisionnement commandeDApprovisionnement;
    private ProduitAchat produitAchat;
    private double prix;
    private int quantite;
    private double total;
    private double tva;
    
    private String refP;

    public LigneCommandeDApprovisionnement(int id, CommandeDApprovisionnement commandeDApprovisionnement, ProduitAchat produitAchat, double prix, int quantite, double total, double tva) {
        this.id = id;
        this.commandeDApprovisionnement = commandeDApprovisionnement;
        this.produitAchat = produitAchat;
        this.prix = prix;
        this.quantite = quantite;
        this.total = total;
        this.tva = tva;
        this.refP=produitAchat.getReference();
    }

    
      
    public LigneCommandeDApprovisionnement(CommandeDApprovisionnement commandeDApprovisionnement, ProduitAchat produitAchat, double prix, int quantite, double total, double tva) {
        this.commandeDApprovisionnement = commandeDApprovisionnement;
        this.produitAchat = produitAchat;
        this.prix = prix;
        this.quantite = quantite;
        this.total = total;
        this.tva = tva;
    }
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CommandeDApprovisionnement getCommandeDApprovisionnement() {
        return commandeDApprovisionnement;
    }

    public void setCommandeDApprovisionnement(CommandeDApprovisionnement commandeDApprovisionnement) {
        this.commandeDApprovisionnement = commandeDApprovisionnement;
    }

    public ProduitAchat getProduitAchat() {
        return produitAchat;
    }

    public void setProduitAchat(ProduitAchat produitAchat) {
        this.produitAchat = produitAchat;
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

    public String getRefP() {
        return refP;
    }

    public void setRefP(String refP) {
        this.refP = refP;
    }
    
    
    

}
