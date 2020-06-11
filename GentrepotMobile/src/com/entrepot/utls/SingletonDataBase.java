/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.utls;

import com.codename1.db.Database;
import java.io.IOException;


/**
 *
 * @author LENOVO
 */
public class SingletonDataBase {
    
    private static SingletonDataBase db;
    
    private static Database base;
    
    
    
    
    private SingletonDataBase(){
        
        try{
            
            base = Database.openOrCreate("FavDB");
            
            base.execute("Create Table if not exists fav("
                    + "id text PRIMARY_KEY ,"
                    + "produit text,"
                    
                    + "prix REAL,"

                    + "image text)" );
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    public static SingletonDataBase getInstance(){
        
        if (db == null)
        {
            db = new SingletonDataBase();
        }
    return db;
    }
    
    public Database getBase(){
        
        return this.base; 
    }
    
}
