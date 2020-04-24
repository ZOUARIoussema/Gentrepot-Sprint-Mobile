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
public class RecouvrementClientCheque extends RecouvrementClient {

    private int id;
    private Date dateCheque;
    private int numeroCheque;

    private FactureVente factureVente;

   

    public RecouvrementClientCheque(int id, Date dateCheque, int numeroCheque, FactureVente factureVente, double montant, Date dateCreation) {
        super(montant, dateCreation);
        this.id = id;
        this.dateCheque = dateCheque;
        this.numeroCheque = numeroCheque;
        this.factureVente = factureVente;
    
    }

    public RecouvrementClientCheque(Date dateCheque, int numeroCheque, FactureVente factureVente, double montant, Date dateCreation) {
        super(montant, dateCreation);
        this.dateCheque = dateCheque;
        this.numeroCheque = numeroCheque;
        this.factureVente = factureVente;
       
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateCheque() {
        return dateCheque;
    }

    public void setDateCheque(Date dateCheque) {
        this.dateCheque = dateCheque;
    }

    public int getNumeroCheque() {
        return numeroCheque;
    }

    public void setNumeroCheque(int numeroCheque) {
        this.numeroCheque = numeroCheque;
    }

    public FactureVente getFactureVente() {
        return factureVente;
    }

    public void setFactureVente(FactureVente factureVente) {
        this.factureVente = factureVente;
    }

    @Override
    public String toString() {
        return "RecouvrementClientCheque{" + "id=" + id + ", dateCheque=" + dateCheque + ", numeroCheque=" + numeroCheque + ", factureVente=" + factureVente.getNumeroF()
                + "   " + super.toString() + '}';
    }

   
}
