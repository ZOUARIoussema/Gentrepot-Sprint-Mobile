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
public class Entrepot {
    
    
    private String matriculeFiscale;
    private String adresse;
    private String raisonSociale;
    private String adresseMail;
    private String numeroTel;

    public Entrepot(String matriculeFiscale, String adresse, String raisonSociale, String adresseMail, String numeroTel) {
        this.matriculeFiscale = matriculeFiscale;
        this.adresse = adresse;
        this.raisonSociale = raisonSociale;
        this.adresseMail = adresseMail;
        this.numeroTel = numeroTel;
    }
    public Entrepot(String matriculeFiscale) {
        this.matriculeFiscale = matriculeFiscale;
    }

    public String getMatriculeFiscale() {
        return matriculeFiscale;
    }

    public void setMatriculeFiscale(String matriculeFiscale) {
        this.matriculeFiscale = matriculeFiscale;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public String getNumeroTel() {
        return numeroTel;
    }

    public void setNumeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
    }
    
    
    
    
    
    
}
