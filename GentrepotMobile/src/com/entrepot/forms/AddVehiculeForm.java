/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.Vehicule;
import com.entrepot.services.ServiceVehicule;

/**
 *
 * @author Rym
 */
public class AddVehiculeForm extends Form {
    
     Resources theme = UIManager.initFirstTheme("/themeLogistique");
     public AddVehiculeForm() {
        super("Ajouter vehicule ", BoxLayout.y());
        this.getStyle().setBgImage(theme.getImage("kashmir.png"), focusScrolling);
        this.setLayout(new FlowLayout(CENTER, CENTER));
           this.getStyle().setBgImage(theme.getImage("kashmir.png"), focusScrolling);
        TextField tfMatricule = new TextField(null, "Matricule");
        TextField tfCapacite = new TextField(null, "capacite");
        TextField tftype= new TextField(null, "type");
        
      
        Button btn = new Button("Ajouter vehicule");
        
         ServiceVehicule sc = new ServiceVehicule();

        btn.addActionListener((evt) -> {
            if ((tftype.getText().length() == 0) || (tfMatricule.getText().length() == 0)) {
                Dialog.show("Alert", "Please fill all the fields", "OK", null);
            } else {
                try {
                   Vehicule v = new Vehicule(Integer.parseInt(tfMatricule.getText()), Integer.parseInt(tfCapacite.getText()), tftype.getText());
                   /* if (sc.addChauffeur(ch)) {
                        Dialog.show("SUCCESS", "chauffeur sent", "OK", null);
                    } else {
                        Dialog.show("ERROR", "Server error", "OK", null);
                   
                   
                    } */  sc.addVehicule(v);
                    
                } catch (NumberFormatException e) {
                    Dialog.show("ERROR", "cin must be a number", "OK", null);
                }

            }
        });

        this.addAll( tfMatricule,tfCapacite,tftype,btn);

        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
         
            new HomeLogistiqueForm().show();
            
        });
    }
}
