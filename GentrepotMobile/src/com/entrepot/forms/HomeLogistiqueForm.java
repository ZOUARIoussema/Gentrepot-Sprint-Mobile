/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.ImageViewer;
import static com.codename1.io.Log.p;
import com.codename1.ui.Button;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import static com.codename1.ui.FontImage.MATERIAL_ARCHIVE;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

/**
 *
 * @author Rym
 */
public class HomeLogistiqueForm extends Form{
     private Resources theme1; 
     public HomeLogistiqueForm() {
         
       
         
        super("Logistique Home", BoxLayout.y());
        
        
        Button btnAddChauffeur = new Button("Ajouter Chauffeur");
        Button btnChauffeurList = new Button(" llste des Chauffeurs ");
        
        Button btnAddVehicule = new Button("Ajouter vehicule");
        Button btnVehiculeList = new Button("llste des vehicule List");
        
        Button btnAddAidChau= new Button("Ajouter Aide Chauffeur");
        Button btnAideChauffeurList = new Button(" llste des aideChauffeurs");
        
        Button btnAddOrdre = new Button("Ajouter Ordre");
        Button btnOrdreList = new Button(" llste des Ordres");
        
       
        
        btnAddChauffeur.addActionListener((evt) -> {
            new AddChauffeurForm().show();
        });
        btnChauffeurList.addActionListener((evt) -> {
            new ChauffeurListForm().show();
        });
        
        
        btnAddVehicule.addActionListener((evt) -> {
            new AddAideChauForm().show();
        });
        
        
        
        
         btnAddAidChau.addActionListener((evt) -> {
           new AideChauffeurListForm().show();
        });
         
         
         
         btnAddVehicule.addActionListener((evt) -> {
           new AddVehiculeForm().show();
        });
         
          btnAddVehicule.addActionListener((evt) -> {
           new VehiculeListForm().show();
        });
          theme1 = UIManager.initFirstTheme("/themeLogistique");
          EncodedImage enco = EncodedImage.createFromImage(theme1.getImage("imag.png"), false);
        
        Image im =URLImage.createToStorage(enco,"image", "imag.png"); 
        
        ImageViewer imv = new ImageViewer(im);
         add(imv);
          this.getToolbar().addMaterialCommandToSideMenu(" Ajouter Chauffeur ", FontImage.MATERIAL_GROUP_ADD, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                
                  new AddChauffeurForm().show();
               
            }
            
        });
          
          
           this.getToolbar().addMaterialCommandToSideMenu(" liste des Chauffeurs  ", FontImage.MATERIAL_PLAYLIST_ADD, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                
                  new ChauffeurListForm().show();
               
            }
            
        });
           
           
            this.getToolbar().addMaterialCommandToSideMenu("Ajouter Aide Chauffeur ", FontImage.MATERIAL_GROUP_ADD, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                
                  new AddAideChauForm().show();
               
            }
            
        });
        
             this.getToolbar().addMaterialCommandToSideMenu("liste des Aide Chauffeurs ", FontImage.MATERIAL_PLAYLIST_ADD, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                
                  new AideChauffeurListForm().show();
               
            }
            
        });
        
             
             
             
              this.getToolbar().addMaterialCommandToSideMenu("Ajouter vehicule", FontImage.MATERIAL_ADD_CIRCLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                
                  new AddVehiculeForm().show();
               
            }
            
        });
        
              
               this.getToolbar().addMaterialCommandToSideMenu("liste des vehicules", FontImage.MATERIAL_PLAYLIST_ADD, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                
                  new VehiculeListForm().show();
               
            }
            
        });
        /*
        btnVehiculeList.addActionListener((evt) -> {
            new AideChauffeurListForm(this);
        });
        
        
        btnAddChauffeur.addActionListener((evt) -> {
            new AddChauffeurForm(this).show();
        });
        btnChauffeurList.addActionListener((evt) -> {
            new ChauffeurListForm(this).show();
        });
        
        
        btnAddChauffeur.addActionListener((evt) -> {
            new AddChauffeurForm(this).show();
        });
        btnChauffeurList.addActionListener((evt) -> {
            new ChauffeurListForm(this).show();
        });*/
        
        
    }

}
