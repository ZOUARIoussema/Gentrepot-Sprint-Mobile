/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.models;

/**
 *
 * @author Mohamed
 */
public class Be {
    private int id ; 
    private String date ; 
    private String dateProduction ;
    private String dateExpiration ;
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

    public String getDateProduction() {
        return dateProduction;
    }

    public void setDateProduction(String dateProduction) {
        this.dateProduction = dateProduction;
    }

    public String getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(String dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public Be() {
    }

    @Override
    public String toString() {
        return "Bonentree{" + "id=" + id + ", date=" + date + ", dateProduction=" + dateProduction + ", dateExpiration=" + dateExpiration + ", cap=" + cap + '}';
    }
    
    
    
}