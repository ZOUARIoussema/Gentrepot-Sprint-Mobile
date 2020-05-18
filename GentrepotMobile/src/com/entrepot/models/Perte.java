/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.models;

import java.util.ArrayList;
 
import java.util.List;

/**
 *
 * @author oussema
 */
public class Perte {

    private int id;
    private String date;
    private List<LignePerte>lignePertes;

    public Perte(int id, String date) {
        this.id = id;
        this.date = date;
        this.lignePertes=new ArrayList<>();
    }
    
    
    
     
    public Perte(String date) {
        this.date = date;
        //this.lignePertes=new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    

    public List<LignePerte> getLignePertes() {
        return lignePertes;
    }

    public void setLignePertes(List<LignePerte> lignePertes) {
        this.lignePertes = lignePertes;
    }
    
    

}
