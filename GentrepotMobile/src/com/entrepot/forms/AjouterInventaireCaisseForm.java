/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.ui.Button;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.entrepot.models.InventaireCaisse;
import com.entrepot.services.ServiceInventaireCaisse;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author oussema
 */
public class AjouterInventaireCaisseForm extends Form {
    
     public void CreationMenu() {

        this.getToolbar().addMaterialCommandToSideMenu("Ajouter Inventaire Caisse", FontImage.MATERIAL_ADD, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                new AjouterInventaireCaisseForm().show();

            }
        });

        this.getToolbar().addMaterialCommandToSideMenu("Liste Inventaire Caisse", FontImage.MATERIAL_ARCHIVE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                new ListeInventaireCaisseForm().show();

            }
        });
        this.getToolbar().addMaterialCommandToSideMenu("Ajouter Lettre de relance", FontImage.MATERIAL_ADD, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                new AjouterLettreDeRelanceForm().show();

            }
        });
        this.getToolbar().addMaterialCommandToSideMenu("Liste lettre de relance", FontImage.MATERIAL_ARCHIVE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                new ListeLettreDeRelanceForm().show();

            }
        });

        this.getToolbar().addMaterialCommandToSideMenu("Deconnecter", FontImage.MATERIAL_EXIT_TO_APP, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                new AuthentificationForm().show();

            }
        });

    }
    
    public AjouterInventaireCaisseForm() {
        
        
        CreationMenu();
        
        ServiceInventaireCaisse serviceInventaireCaisse = new  ServiceInventaireCaisse();
        
        
        
        TextField soldeCalculer = new TextField();
       
         
        
        Button bAjouter= new Button("Ajouter");
        
        this.setLayout(BoxLayout.y());
        
        this.addAll(soldeCalculer);
        this.add(bAjouter);
        
        bAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
            
                
                InventaireCaisse inventaireCaisse =new InventaireCaisse(Double.parseDouble(soldeCalculer.getText()));
               
              if(  serviceInventaireCaisse.addinventaireCaisse(inventaireCaisse)){
                  
                  new ListeInventaireCaisseForm().show();
              }
              
            }
        });
        
        
        
          this.setTitle("Ajouter inventairs caisse");
        
    }
    
    
    
    
    
    public AjouterInventaireCaisseForm(InventaireCaisse i) {
        
        
        CreationMenu();
        
        ServiceInventaireCaisse serviceInventaireCaisse = new  ServiceInventaireCaisse();
        
        
        
        TextField soldeCalculer = new TextField();
       
         
        
        Button bAjouter= new Button("Ajouter");
        
        this.setLayout(BoxLayout.y());
        
        this.addAll(soldeCalculer);
        this.add(bAjouter);
        
        bAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
            
                
                i.setEcart(Double.parseDouble(soldeCalculer.getText()));
               
              if(  serviceInventaireCaisse.addinventaireCaisse(i)){
                  
                  new ListeInventaireCaisseForm().show();
              }
              
            }
        });
        
        
        
          this.setTitle("Ajouter inventairs caisse");
        
    }
    
    
    
    
    
}
