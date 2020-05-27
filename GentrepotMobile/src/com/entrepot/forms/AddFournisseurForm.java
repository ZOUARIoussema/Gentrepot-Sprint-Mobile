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
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.Fournisseur;
import com.entrepot.services.ServiceFournisseur;

/**
 *
 * @author Mohamed
 */
public class AddFournisseurForm extends Form {
    Resources theme = UIManager.initFirstTheme("/themeLogistique");
   public AddFournisseurForm() {
        super("Ajouter Fournisseur", BoxLayout.y());
        
        this.getStyle().setBgImage(theme.getImage("kashmir.png"), focusScrolling);
        this.setLayout(new FlowLayout(CENTER, CENTER));
        TextField tfRais = new TextField(null, "Raison Sociale");
        TextField tfNum = new TextField(null, "Numero De Telephone");
        TextField tfAd= new TextField(null, "Adresse");
         TextField tfAdM= new TextField(null, "Adresse Mail");
         TextField tfMat= new TextField(null, "matricule Fiscale");
         TextField tfCode= new TextField(null, "code Postale");
       
        Button btn = new Button("Ajouter le fournisseur");
        
        ServiceFournisseur sc = new ServiceFournisseur();

        btn.addActionListener((evt) -> {
            if ((tfRais.getText().length() == 0) || (tfMat.getText().length() == 0)) {
                Dialog.show("Alert", "Please fill all the fields", "OK", null);
            } else {
                try {
                   Fournisseur ch = new Fournisseur (tfRais.getText(), Integer.parseInt(tfNum.getText()), tfAd.getText(), tfAdM.getText(), tfMat.getText(),Integer.parseInt(tfCode.getText()) );
                    if (sc.addFournisseur(ch)) {
                        Dialog.show("SUCCESS", "chauffeur sent", "OK", null);
                    }  
                    
                } catch (NumberFormatException e) {
                    Dialog.show("ERROR", "cin must be a number", "OK", null);
                }

            }
        });

        this.addAll(tfRais, tfNum,tfAd,tfAdM ,tfMat,tfCode, btn);

        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
         
            new HomeAchat().showBack();
            
        });
    }
    
}
