/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.entrepot.models.OrdreMission;
import com.entrepot.services.ServiceOrdreMission;

/**
 *
 * @author Rym
 */
public class AddOrdreForm extends Form{
     /*  public AddOrdreForm() {
        super("Ajouter chauffeur", BoxLayout.y());
           ComboBox<Integer>txV;
            ComboBox<String>txCh;
             ComboBox<String>txA;
             DatePicker C ;
         TextField tfS= new TextField(null, "dateSortie");
            TextField tfR= new TextField(null, "dateRetour");
            
       // TextField tfEtat= new TextField(null, "chauffeur etat");
        Button btn = new Button("Add the chauffeur");
        
           ServiceOrdreMission sc = new ServiceOrdreMission();

        btn.addActionListener((evt) -> {
            if ((tfName.getText().length() == 0) || (tfCin.getText().length() == 0)) {
                Dialog.show("Alert", "Please fill all the fields", "OK", null);
            } else {
                try {
                OrdreMission o = new OrdreMission(vehicule, chauffeur, aideChauffeur, dateCreation, dateSortie, dateRetour);
                   /* if (sc.addChauffeur(ch)) {
                        Dialog.show("SUCCESS", "chauffeur sent", "OK", null);
                    } else {
                        Dialog.show("ERROR", "Server error", "OK", null);
                   
                   
                } */  //sc.addOrdreMission(o);
                    
               // } catch (NumberFormatException e) {
                   // Dialog.show("ERROR", "cin must be a number", "OK", null);
              //  }

           // }
       // });

       // this.addAll(tfCin, tfName,tfPrenom,tfAdresse , btn);

        //this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
         
         //   new HomeLogistiqueForm().show();
            
       // });
  //  }
}
