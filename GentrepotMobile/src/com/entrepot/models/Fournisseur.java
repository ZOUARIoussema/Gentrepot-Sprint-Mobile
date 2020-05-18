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
public class Fournisseur {
    
    private int id;
    private String raisonSociale;
    private int numeroTelephone;
    private String adresse;
    private String adresseMail;
    private String matriculeFiscale;
    private int codePostale;

    public Fournisseur(int id, String raisonSociale, int numeroTelephone, String adresse, String adresseMail, String matriculeFiscale, int codePostale) {
        this.id = id;
        this.raisonSociale = raisonSociale;
        this.numeroTelephone = numeroTelephone;
        this.adresse = adresse;
        this.adresseMail = adresseMail;
        this.matriculeFiscale = matriculeFiscale;
        this.codePostale = codePostale;
    }

    public Fournisseur(String raisonSociale, int numeroTelephone, String adresse, String adresseMail, String matriculeFiscale, int codePostale) {
        this.raisonSociale = raisonSociale;
        this.numeroTelephone = numeroTelephone;
        this.adresse = adresse;
        this.adresseMail = adresseMail;
        this.matriculeFiscale = matriculeFiscale;
        this.codePostale = codePostale;
    }

    public Fournisseur(int id) {
        this.id = id;
    }
    
     public Fournisseur(String adresseMail) {
        this.adresseMail = adresseMail;
    }
    
    
    
    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public int getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(int numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public String getMatriculeFiscale() {
        return matriculeFiscale;
    }

    public void setMatriculeFiscale(String matriculeFiscale) {
        this.matriculeFiscale = matriculeFiscale;
    }

    public int getCodePostale() {
        return codePostale;
    }

    public void setCodePostale(int codePostale) {
        this.codePostale = codePostale;
    }

    @Override
    public String toString() {
        return "Fournisseur{" + "id=" + id + ", raisonSociale=" + raisonSociale + ", numeroTelephone=" + numeroTelephone + ", adresse=" + adresse + ", adresseMail=" + adresseMail + ", matriculeFiscale=" + matriculeFiscale + ", codePostale=" + codePostale + '}';
    }
    
    
    
    
    
    
    
    
    
    
    
}
