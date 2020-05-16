/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

/**
 *
 * @author LENOVO
 */
public class HomeVente extends Form{
    
    
     public HomeVente( Form previous ) {
         
         setTitle("Gentrepot shop");
         setLayout(new FlowLayout (CENTER));
         
         Label lbwlcm = new Label("Welcome client");
        
  
         lbwlcm.setAlignment(CENTER);
         
         add(lbwlcm);
         
         getToolbar().addCommandToLeftSideMenu("Home", null,ev->{
             
             Dialog.show("info", "Vous etes dans la form home", "OK", null);
         });
        
         
           getToolbar().addCommandToLeftSideMenu("Shop", null,ev->{
                
               new ProduitsListForm(this).show();
             
         });
           
           
           getToolbar().addCommandToLeftSideMenu("Panier", null,ev->{
                
               new PanierForm(this).show();
             
         });
           
            getToolbar().addCommandToLeftSideMenu("Commandes", null,ev->{
                
               new CommandeForm(this).show();
             
         });
     }
    
 

    
}
