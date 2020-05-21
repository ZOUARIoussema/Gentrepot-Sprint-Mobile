/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author oussema
 */
public class OrdreMission {
    
    
    private int id;
    private Vehicule vehicule;
    private Chauffeur chauffeur;
    private AideChauffeur aideChauffeur;
    private Date dateCreation;
    private Date dateSortie;
    private Date dateRetour;
    
    private List<BonLivraison> bonLivraisons;

    public OrdreMission(int id, Vehicule vehicule, Chauffeur chauffeur, AideChauffeur aideChauffeur, Date dateCeation, Date dateSortie, Date dateRetour) {
        this.id = id;
        this.vehicule = vehicule;
        this.chauffeur = chauffeur;
        this.aideChauffeur = aideChauffeur;
        this.dateCreation = dateCeation;
        this.dateSortie = dateSortie;
        this.dateRetour = dateRetour;
        this.bonLivraisons=new ArrayList<>();
    }
    
      public OrdreMission( Vehicule vehicule, Chauffeur chauffeur, AideChauffeur aideChauffeur, Date dateCeation, Date dateSortie, Date dateRetour) {
      
        this.vehicule = vehicule;
        this.chauffeur = chauffeur;
        this.aideChauffeur = aideChauffeur;
        this.dateCreation = dateCeation;
        this.dateSortie = dateSortie;
        this.dateRetour = dateRetour;
        this.bonLivraisons=new ArrayList<>();
    }
    
    
    
     public OrdreMission(int id, Vehicule vehicule, Chauffeur chauffeur, AideChauffeur aideChauffeur, Date dateCreation, Date dateSortie, Date dateRetour, List<BonLivraison> bonLivraisons) {
        this.id = id;
        this.vehicule = vehicule;
        this.chauffeur = chauffeur;
        this.aideChauffeur = aideChauffeur;
        this.dateCreation = dateCreation;
        this.dateSortie = dateSortie;
        this.dateRetour = dateRetour;
        this.bonLivraisons = bonLivraisons;
    }

    public OrdreMission(Vehicule vehicule, Chauffeur chauffeur, AideChauffeur aideChauffeur, Date dateCreation, Date dateSortie, Date dateRetour, List<BonLivraison> bonLivraisons) {
        this.vehicule = vehicule;
        this.chauffeur = chauffeur;
        this.aideChauffeur = aideChauffeur;
        this.dateCreation = dateCreation;
        this.dateSortie = dateSortie;
        this.dateRetour = dateRetour;
        this.bonLivraisons = bonLivraisons;
    }

    

    public OrdreMission(Vehicule vehicule, Chauffeur chauffeur, Date dateCreation, Date dateSortie, Date dateRetour) {
        this.vehicule = vehicule;
        this.chauffeur = chauffeur;
        this.dateCreation = dateCreation;
        this.dateSortie = dateSortie;
        this.dateRetour = dateRetour;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public Chauffeur getChauffeur() {
        return chauffeur;
    }

    public void setChauffeur(Chauffeur chauffeur) {
        this.chauffeur = chauffeur;
    }

    public AideChauffeur getAideChauffeur() {
        return aideChauffeur;
    }

    public void setAideChauffeur(AideChauffeur aideChauffeur) {
        this.aideChauffeur = aideChauffeur;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(Date dateSortie) {
        this.dateSortie = dateSortie;
    }

    public Date getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(Date dateRetour) {
        this.dateRetour = dateRetour;
    }

    public List<BonLivraison> getBonLivraisons() {
        return bonLivraisons;
    }

    public void setBonLivraisons(List<BonLivraison> bonLivraisons) {
        this.bonLivraisons = bonLivraisons;
    }

    @Override
    public String toString() {
        return "OrdreMission{" + "id=" + id + ", vehicule=" + vehicule + ", chauffeur=" + chauffeur + ", aideChauffeur=" + aideChauffeur + ", dateCreation=" + dateCreation + ", dateSortie=" + dateSortie + ", dateRetour=" + dateRetour + ", bonLivraisons=" + bonLivraisons + '}';
    }
    
    

     
    
    
    
    
    
    
    
    
    
    
    
    
    
}
