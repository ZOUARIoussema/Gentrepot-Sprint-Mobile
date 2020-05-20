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
import com.entrepot.models.Vehicule;
import com.entrepot.services.ServiceChauffeur;
import com.entrepot.services.ServiceVehicule;

/**
 *
 * @author Rym
 */
public class VehiculeListForm extends Form {
     Resources res = UIManager.initFirstTheme("/themeLogistique");
     ServiceVehicule sa = new ServiceVehicule();
     public VehiculeListForm() {
        super("liste des vehicules ", BoxLayout.y());

        
             Image img = res.getImage("kashmir.png");
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        
        
        Container aff =new Container(BoxLayout.y());
        
        for(Vehicule c : sa.getAllTasks()){
             Container cat =new Container(BoxLayout.y());
             Container carte = new Container(BoxLayout.x());
       
            Integer mat = c.getMatricule();
            Label Mat = new Label(mat.toString());
               Integer t = c.getCapacite();
             Label ca = new Label(t.toString());
             Label etat = new Label(c.getEtat());
             Label type = new Label(c.getType());
            
             Button books = new Button();
             cat.addAll(Mat,ca,etat,type);
             ImageViewer i = new ImageViewer(res.getImage("round.png").scaled(300, 300));
          //  ImageViewer i = new ImageViewer(img);
              carte.addAll(i,cat);
              aff.add(carte);
             
              
              
            
              
             
         }
        // this.setBgImage(img);
         add(LayeredLayout.encloseIn(
            aff
              
        ));
        
        
        

       /* this.add(new SpanLabel(new ServiceAideChauffeur().getAllTasks().toString()));*/

       
       // this.add(new SpanLabel(new ServiceVehicule().getAllTasks().toString()));

        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
             new HomeLogistiqueForm().show();
        });
}
}
