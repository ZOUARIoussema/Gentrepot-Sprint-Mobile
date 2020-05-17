/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.entrepot.models.AideChauffeur;
import com.entrepot.services.ServiceAideChauffeur;

/**
 *
 * @author Rym
 */
public class AddAideChauForm extends Form {
    public AddAideChauForm() {
        super("Ajouter AideChauffeur", BoxLayout.y());
        TextField tfCin = new TextField(null, "AideChauffeur cin");
        TextField tfName = new TextField(null, "AideChauffeur name");
        TextField tfPrenom= new TextField(null, "AideChauffeur prenom");
         TextField tfAdresse= new TextField(null, "AideChauffeur adresse");
      
        Button btn = new Button("Add the Aidechauffeur");
        
        ServiceAideChauffeur sc = new ServiceAideChauffeur();

        btn.addActionListener((evt) -> {
            if ((tfName.getText().length() == 0) || (tfCin.getText().length() == 0)) {
                Dialog.show("Alert", "Please fill all the fields", "OK", null);
            } else {
                try {
                   AideChauffeur A = new AideChauffeur(tfCin.getText(),tfName.getText(), tfPrenom.getText(), tfAdresse.getText());
                   /* if (sc.addChauffeur(ch)) {
                        Dialog.show("SUCCESS", "chauffeur sent", "OK", null);
                    } else {
                        Dialog.show("ERROR", "Server error", "OK", null);
                   
                   
                    } */  sc.addAideChauffeur(A);
                    
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
