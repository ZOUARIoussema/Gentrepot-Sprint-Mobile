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
public class InventaireCaisse {

    private int id;
    private Date dateCreation;
    private double soldeCalculer;
    private double soldeTheorique;
    private double soldeCheque;
    private double soldeEspece;
    private double ecart;
    
    
 
    

    public InventaireCaisse(int id, Date dateCreation, double soldeCalculer, double soldeTheorique, double soldeCheque, double soldeEspece, double ecart) {
        this.id = id;
        this.dateCreation = dateCreation;
        this.soldeCalculer = soldeCalculer;
        this.soldeTheorique = soldeTheorique;
        this.soldeCheque = soldeCheque;
        this.soldeEspece = soldeEspece;
        this.ecart = ecart;
    }

    public InventaireCaisse(Date dateCreation, double soldeCalculer, double soldeTheorique, double soldeCheque, double soldeEspece, double ecart) {
        this.dateCreation = dateCreation;
        this.soldeCalculer = soldeCalculer;
        this.soldeTheorique = soldeTheorique;
        this.soldeCheque = soldeCheque;
        this.soldeEspece = soldeEspece;
        this.ecart = ecart;
    }
    
    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public double getSoldeCalculer() {
        return soldeCalculer;
    }

    public void setSoldeCalculer(double soldeCalculer) {
        this.soldeCalculer = soldeCalculer;
    }

    public double getSoldeTheorique() {
        return soldeTheorique;
    }

    public void setSoldeTheorique(double soldeTheorique) {
        this.soldeTheorique = soldeTheorique;
    }

    public double getSoldeCheque() {
        return soldeCheque;
    }

    public void setSoldeCheque(double soldeCheque) {
        this.soldeCheque = soldeCheque;
    }

    public double getSoldeEspece() {
        return soldeEspece;
    }

    public void setSoldeEspece(double soldeEspece) {
        this.soldeEspece = soldeEspece;
    }

    public double getEcart() {
        return ecart;
    }

    public void setEcart(double ecart) {
        this.ecart = ecart;
    }

    @Override
    public String toString() {
        return "InventaireCaisse{" + "id=" + id + ", dateCreation=" + dateCreation + ", soldeCalculer=" + soldeCalculer + ", soldeTheorique=" + soldeTheorique + ", soldeCheque=" + soldeCheque + ", soldeEspece=" + soldeEspece + ", ecart=" + ecart + '}';
    }

   
    
    
    
    
}
