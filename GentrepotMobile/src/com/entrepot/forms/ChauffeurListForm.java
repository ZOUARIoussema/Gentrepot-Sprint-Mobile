/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.AideChauffeur;
import com.entrepot.models.Chauffeur;
import com.entrepot.services.ServiceChauffeur;

/**
 *
 * @author Rym
 */
public class ChauffeurListForm extends Form {
     Resources res = UIManager.initFirstTheme("/themeLogistique");
     ServiceChauffeur sa = new ServiceChauffeur();
     public ChauffeurListForm() {
        super("liste des chauffeur ", BoxLayout.y());

        
        
        
        Image img = res.getImage("kashmir.png");
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        
        
        Container aff =new Container(BoxLayout.y());
        
        for(Chauffeur c : sa.getAllTasks()){
             Container cat =new Container(BoxLayout.y());
             Container carte = new Container(BoxLayout.x());
       
       //      
             Label cin = new Label(c.getCin());
             cin.getAllStyles().setFgColor(000000);
             Label nom = new Label(c.getNom());
             Label prenom = new Label(c.getPrenom());
             Label adr = new Label(c.getAdresse());
             Label etat = new Label(c.getEtat());
             Integer v = c.getVoyage();
             Label V = new Label(v.toString());
             Button books = new Button();
             cat.addAll(cin,nom,prenom,adr,etat,V);
             ImageViewer i = new ImageViewer(res.getImage("round.png").scaled(300, 300));
          //  ImageViewer i = new ImageViewer(img);
              carte.addAll(i,cat);
              aff.add(carte);
             
              
              
            
              
             
         }
        // this.setBgImage(img);
         add(LayeredLayout.encloseIn(
            aff
              
        ));
        
        //this.add(new SpanLabel(new ServiceChauffeur().getAllTasks().toString()));

        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
             new HomeLogistiqueForm().show();
        });
    }
    
}
