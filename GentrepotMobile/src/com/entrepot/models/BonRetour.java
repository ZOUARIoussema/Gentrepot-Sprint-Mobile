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
public class BonRetour implements Comparable<BonRetour>{
    
    
    private int id ; 
    private String date ;
    private String motif ; 
    private int cap ;

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

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public BonRetour() {
    }

    @Override
    public String toString() {
        return "BonRetour{" + "id=" + id + ", date=" + date + ", motif=" + motif + ", cap=" + cap + '}';
    }

    @Override
    public int compareTo(BonRetour o) {
        return this.getDate().compareTo(o.getDate()); 
    }
    
    
    
    
    
}