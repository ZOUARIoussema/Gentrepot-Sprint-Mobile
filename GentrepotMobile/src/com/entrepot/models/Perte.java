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
public class Perte {

    private int id;
    private Date date;
    private List<LignePerte>lignePertes;

    public Perte(int id, Date date) {
        this.id = id;
        this.date = date;
        this.lignePertes=new ArrayList<>();
    }
    
    
    
     
    public Perte(Date date) {
        this.date = date;
        this.lignePertes=new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    

    public List<LignePerte> getLignePertes() {
        return lignePertes;
    }

    public void setLignePertes(List<LignePerte> lignePertes) {
        this.lignePertes = lignePertes;
    }
    
    

}
