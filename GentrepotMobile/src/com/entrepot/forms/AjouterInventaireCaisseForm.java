/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.entrepot.models.InventaireCaisse;
import com.entrepot.services.ServiceInventaireCaisse;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author oussema
 */
public class AjouterInventaireCaisseForm extends Form {
    
    
    
    public AjouterInventaireCaisseForm() {
        
        ServiceInventaireCaisse serviceInventaireCaisse = new  ServiceInventaireCaisse();
        
        
        Button bAjouter= new Button("Ajouter");
        
        this.add(bAjouter);
        
        bAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
            
                
               
               // serviceInventaireCaisse.addinventaireCaisse(inventaireCaisse);
              
            }
        });
        
    }
    
    
    
}
