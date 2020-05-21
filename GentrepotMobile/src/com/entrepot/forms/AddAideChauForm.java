/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.AideChauffeur;
import com.entrepot.services.ServiceAideChauffeur;

/**
 *
 * @author Rym
 */
public class AddAideChauForm extends Form {
    Resources theme = UIManager.initFirstTheme("/themeLogistique");
    public AddAideChauForm() {
        super("Ajouter AideChauffeur", BoxLayout.y());
        
       
        
          this.getStyle().setBgImage(theme.getImage("kashmir.png"), focusScrolling);
       this.setLayout(new FlowLayout(CENTER, CENTER));
        TextField tfCin = new TextField(null, "AideChauffeur cin");
        TextField tfName = new TextField(null, "AideChauffeur name");
        TextField tfPrenom= new TextField(null, "AideChauffeur prenom");
         TextField tfAdresse= new TextField(null, "AideChauffeur adresse");
      
        Button btn = new Button("AJOUTER");
         
        ServiceAideChauffeur sc = new ServiceAideChauffeur();

        btn.addActionListener((evt) -> {
            if ((tfName.getText().length() == 0) || (tfCin.getText().length() == 0)) {
                Dialog.show("Alert", "Please fill all the fields", "OK", null);
            } else {
                try {
                   AideChauffeur A = new AideChauffeur(tfCin.getText(),tfName.getText(), tfPrenom.getText(), tfAdresse.getText());
                    if (sc.addAideChauffeur(A)) {
                        Dialog.show("SUCCESS", " Aide chauffeur sent", "OK", null);
                    } 
                    
                } catch (NumberFormatException e) {
                    Dialog.show("ERROR", "cin must be a number", "OK", null);
                }

            }
        });

        this.addAll(tfCin, tfName,tfPrenom,tfAdresse , btn);

        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
            new HomeLogistiqueForm().show();
            
        });
       

       
        
    }

}
