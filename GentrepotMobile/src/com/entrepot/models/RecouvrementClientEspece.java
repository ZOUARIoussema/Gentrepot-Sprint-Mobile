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
public class RecouvrementClientEspece extends RecouvrementClient {

    private int id;
    private FactureVente factureVente;
    
    

    public RecouvrementClientEspece(int id, FactureVente factureVente, double montant, Date dateCreation) {
        super(montant, dateCreation);
        this.id = id;
        this.factureVente = factureVente;
      
    }

    public RecouvrementClientEspece(FactureVente factureVente, double montant, Date dateCreation) {
        super(montant, dateCreation);
        this.factureVente = factureVente;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FactureVente getFactureVente() {
        return factureVente;
    }

    public void setFactureVente(FactureVente factureVente) {
        this.factureVente = factureVente;
    }

    @Override
    public String toString() {
        return "RecouvrementClientEspece{" + "id=" + id + ", factureVente=" + factureVente.getNumeroF()
                + "  " + super.toString() + '}';
    }

    
    
    

}
