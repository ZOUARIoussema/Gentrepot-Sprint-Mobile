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
public class SousCategorieAchat {

    public SousCategorieAchat(int id) {
        this.id = id;
    }

    public SousCategorieAchat(String nom) {
        this.nom = nom;
    }

    private int id;
    private String nom;
    private CategorieAchat categorieAchat;

    public SousCategorieAchat(int id, String nom, CategorieAchat categorieAchat) {
        this.id = id;
        this.nom = nom;
        this.categorieAchat = categorieAchat;
    }

    public SousCategorieAchat(String nom, CategorieAchat categorieAchat) {
        this.nom = nom;
        this.categorieAchat = categorieAchat;
    }

    public SousCategorieAchat(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }
    
    

    @Override
    public String toString() {
        return "SousCategorieAchat{" + "id=" + id + ", nom=" + nom + ", categorieAchat=" + categorieAchat + '}';
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public CategorieAchat getCategorieAchat() {
        return categorieAchat;
    }

    public void setCategorieAchat(CategorieAchat categorieAchat) {
        this.categorieAchat = categorieAchat;
    }
    
    

}
