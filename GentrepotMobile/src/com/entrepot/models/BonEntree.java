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
public class BonEntree {
    
    
    
    private int id;
    private Date date;
    private Date dateProduction;
    private Date dateExpiration;
    private CommandeDApprovisionnement commandeDApprovisionnement;

    public BonEntree(int id, Date date, Date dateProduction, Date dateExpiration, CommandeDApprovisionnement commandeDApprovisionnement) {
        this.id = id;
        this.date = date;
        this.dateProduction = dateProduction;
        this.dateExpiration = dateExpiration;
        this.commandeDApprovisionnement = commandeDApprovisionnement;
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

    public Date getDateProduction() {
        return dateProduction;
    }

    public void setDateProduction(Date dateProduction) {
        this.dateProduction = dateProduction;
    }

    public Date getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public CommandeDApprovisionnement getCommandeDApprovisionnement() {
        return commandeDApprovisionnement;
    }

    public void setCommandeDApprovisionnement(CommandeDApprovisionnement commandeDApprovisionnement) {
        this.commandeDApprovisionnement = commandeDApprovisionnement;
    }
    
    
    
    
    
    
    
            
    
    
    
}
