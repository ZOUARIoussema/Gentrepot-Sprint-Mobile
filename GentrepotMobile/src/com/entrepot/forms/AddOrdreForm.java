/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.Calendar;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.Picker;
import com.entrepot.models.OrdreMission;
import com.entrepot.services.ServiceOrdreMission;
import com.entrepot.services.ServiceVehicule;
import java.util.Date;
import com.entrepot.models.*;
import com.entrepot.services.ServiceAideChauffeur;
import com.entrepot.services.ServiceChauffeur;
import java.util.List;

/**
 *
 * @author Rym
 */
public class AddOrdreForm extends Form {

    ServiceVehicule serviceVehicule = new ServiceVehicule();
    ServiceChauffeur serviceChauffeur = new ServiceChauffeur();
    ServiceAideChauffeur serviceAideChauffeur = new ServiceAideChauffeur();

    List<Vehicule> listeV = serviceVehicule.getAllVehiculeD();
    List<Chauffeur> listeCh = serviceChauffeur.getAllChauffeurs();
    List<AideChauffeur> listeAide = serviceAideChauffeur.getAllAideChauffeur();

    public AddOrdreForm() {
        super("Ajouter chauffeur", BoxLayout.y());

        Label textVehicule = new Label("Vehicule");
        ComboBox<Integer> comboV = new ComboBox();
        ComboBox comboCh = new ComboBox();
        ComboBox comboAide = new ComboBox();

        for (Vehicule v : listeV) {

            comboV.addItem(v.getMatricule());

        }

        Label textChauffeur = new Label("Chauffeur");

        for (Chauffeur ch : listeCh) {

            comboCh.addItem(ch.getNom());

        }

        Label textAideChauf = new Label("Aide chauffeur");

        for (AideChauffeur aide : listeAide) {

            comboAide.addItem(aide.getCin());

        }

        this.setLayout(BoxLayout.y());

        Container c = new Container(BoxLayout.y());

        Button btnV = new Button("Valider");

        Label labelDateSortie = new Label("Date dsortie");
        Picker datePickerS = new Picker();
        datePickerS.setType(Display.PICKER_TYPE_DATE);

        Label labelDateRetour = new Label("Date retour");
        Picker datePickerR = new Picker();
        datePickerS.setType(Display.PICKER_TYPE_DATE);

        btnV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                Vehicule v = listeV.get(comboV.getSelectedIndex());

                AideChauffeur aideChauffeur =listeAide.get(comboAide.getSelectedIndex());
                
                Chauffeur ch = listeCh.get(comboCh.getSelectedIndex());
                 
               

                OrdreMission ordreMission = new OrdreMission(v, ch, aideChauffeur, new Date(), datePickerS.getDate(), datePickerR.getDate());

                 System.out.println(ordreMission);
                 
               
            }
        });

        c.addAll(textVehicule, comboV, textChauffeur, comboCh, textAideChauf, comboAide, labelDateSortie, datePickerS, labelDateRetour, datePickerR, btnV);

        this.add(c);

    }
}

// TextField tfEtat= new TextField(null, "chauffeur etat");
// Button btn = new Button("Add the chauffeur");
//   ServiceOrdreMission sc = new ServiceOrdreMission();
// btn.addActionListener((evt) -> {
/* if ((tfName.getText().length() == 0) || (tfCin.getText().length() == 0)) {
                Dialog.show("Alert", "Please fill all the fields", "OK", null);
            } else {
                try {
                OrdreMission o = new OrdreMission(, chauffeur, aideChauffeur, dateCreation, dateSortie, dateRetour);
                    if (sc.addChauffeur(ch)) {
                        Dialog.show("SUCCESS", "chauffeur sent", "OK", null);
                    } else {
                        Dialog.show("ERROR", "Server error", "OK", null);
                   
                   
                }  sc.addOrdreMission(o);
                    
                } catch (NumberFormatException e) {
                    Dialog.show("ERROR", "cin must be a number", "OK", null);
               }

            }
        });

        this.addAll(tfCin, tfName,tfPrenom,tfAdresse , btn);

        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
         
            new HomeLogistiqueForm().show();
            
        });
   }*/
//}
