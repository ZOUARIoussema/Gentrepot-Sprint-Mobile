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
public class ProduitAchat {

    private String reference;
    private String libelle;
    private int quantiteStock;
    private String classe;

    public ProduitAchat() {
    }
    private int quantiteStockSecurite;
    private double dernierPrixAchat;
    private double tva;
    private double dimension;
    private String description;
    private String typeDeConditionnement;
    private double prixVente;
    private String image;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private SousCategorieAchat sousCategorieAchat;

    public ProduitAchat(String reference, String libelle, int quantiteStock, String classe, int quantiteStockSecurite, double dernierPrixAchat, double tva, double dimension, String description, String typeDeConditionnement, double prixVente, String image, String image1, String image2, String image3, String image4, SousCategorieAchat sousCategorieAchat) {
        this.reference = reference;
        this.libelle = libelle;
        this.quantiteStock = quantiteStock;
        this.classe = classe;
        this.quantiteStockSecurite = quantiteStockSecurite;
        this.dernierPrixAchat = dernierPrixAchat;
        this.tva = tva;
        this.dimension = dimension;
        this.description = description;
        this.typeDeConditionnement = typeDeConditionnement;
        this.prixVente = prixVente;
        this.image = image;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.sousCategorieAchat = sousCategorieAchat;
    }

    public ProduitAchat(String reference, String libelle, int quantiteStock, String classe, int quantiteStockSecurite, double dernierPrixAchat, double tva, String description, String typeDeConditionnement, double prixVente, String image) {
        this.reference = reference;
        this.libelle = libelle;
        this.quantiteStock = quantiteStock;
        this.classe = classe;
        this.quantiteStockSecurite = quantiteStockSecurite;
        this.dernierPrixAchat = dernierPrixAchat;
        this.tva = tva;
        this.description = description;
        this.typeDeConditionnement = typeDeConditionnement;
        this.prixVente = prixVente;
        this.image = image;
    }

    public ProduitAchat(String reference, String libelle, int quantiteStock, String classe, int quantiteStockSecurite, double dernierPrixAchat, double tva, double dimension, String description, String typeDeConditionnement, double prixVente, String image, SousCategorieAchat sousCategorieAchat) {
        this.reference = reference;
        this.libelle = libelle;
        this.quantiteStock = quantiteStock;
        this.classe = classe;
        this.quantiteStockSecurite = quantiteStockSecurite;
        this.dernierPrixAchat = dernierPrixAchat;
        this.tva = tva;
        this.dimension = dimension;
        this.description = description;
        this.typeDeConditionnement = typeDeConditionnement;
        this.prixVente = prixVente;
        this.image = image;
        this.sousCategorieAchat = sousCategorieAchat;
    }

    public ProduitAchat(String reference) {
        this.reference = reference;
    }
    
     
    public ProduitAchat(String reference, String libelle, int quantiteStock,double prix) {
        this.reference = reference;
        this.libelle = libelle;
        this.quantiteStock = quantiteStock;
        this.prixVente=prix;
    }

    
    
    
    public ProduitAchat(String reference, String libelle, int quantiteStock, String classe, int quantiteStockSecurite, double dernierPrixAchat, double tva, double dimension, String description, String typeDeConditionnement, double prixVente) {
        this.reference = reference;
        this.libelle = libelle;
        this.quantiteStock = quantiteStock;
        this.classe = classe;
        this.quantiteStockSecurite = quantiteStockSecurite;
        this.dernierPrixAchat = dernierPrixAchat;
        this.tva = tva;
        this.dimension = dimension;
        this.description = description;
        this.typeDeConditionnement = typeDeConditionnement;
        this.prixVente = prixVente;
    }
    
     public ProduitAchat(String reference, String libelle, int quantiteStock, String classe, double prixVente, String image) {
        this.reference = reference;
        this.libelle = libelle;
        this.quantiteStock = quantiteStock;
        this.classe = classe;
        this.prixVente = prixVente;
        this.image = image;
    }


    public ProduitAchat(String reference, String libelle) {
        this.reference = reference;
        this.libelle = libelle;
    }
    

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getQuantiteStock() {
        return quantiteStock;
    }

    public void setQuantiteStock(int quantiteStock) {
        this.quantiteStock = quantiteStock;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public int getQuantiteStockSecurite() {
        return quantiteStockSecurite;
    }

    public void setQuantiteStockSecurite(int quantiteStockSecurite) {
        this.quantiteStockSecurite = quantiteStockSecurite;
    }

    public double getDernierPrixAchat() {
        return dernierPrixAchat;
    }

    public void setDernierPrixAchat(double dernierPrixAchat) {
        this.dernierPrixAchat = dernierPrixAchat;
    }

    public double getTva() {
        return tva;
    }

    public void setTva(double tva) {
        this.tva = tva;
    }

    public double getDimension() {
        return dimension;
    }

    public void setDimension(double dimension) {
        this.dimension = dimension;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeDeConditionnement() {
        return typeDeConditionnement;
    }

    public void setTypeDeConditionnement(String typeDeConditionnement) {
        this.typeDeConditionnement = typeDeConditionnement;
    }

    public double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(double prixVente) {
        this.prixVente = prixVente;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public SousCategorieAchat getSousCategorieAchat() {
        return sousCategorieAchat;
    }

    public void setSousCategorieAchat(SousCategorieAchat sousCategorieAchat) {
        this.sousCategorieAchat = sousCategorieAchat;
    }
    
    
    
    

}
