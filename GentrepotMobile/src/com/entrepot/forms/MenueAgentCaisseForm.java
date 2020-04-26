/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

/**
 *
 * @author oussema
 */
public class MenueAgentCaisseForm extends Form {
    
    
    
    Resources  theme = UIManager.initFirstTheme("/themeTresorerie");

    public MenueAgentCaisseForm() {
        
        
         

       

        this.setTitle("Menue");

        this.getToolbar().addCommandToLeftSideMenu("Ajouter Inventaire Caisse", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

            }
        });
        
         this.getToolbar().addCommandToLeftSideMenu("Liste Inventaire Caisse", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                
                

            }
        });
          this.getToolbar().addCommandToLeftSideMenu("Ajouter Lettre de relance", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

            }
        });
           this.getToolbar().addCommandToLeftSideMenu("Liste lettre de relance", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

            }
        });

    }

}
