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
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
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
    List<Chauffeur> listeCh = serviceChauffeur.getChauf();
    List<AideChauffeur> listeAide = serviceAideChauffeur.getAllAideChauffeur();
    
     Resources theme = UIManager.initFirstTheme("/themeLogistique");
        ServiceOrdreMission sc = new ServiceOrdreMission();
    public AddOrdreForm() {
        super("Ajouter ordre", BoxLayout.y());
        
        this.setLayout(new FlowLayout(CENTER, CENTER));
          this.getStyle().setBgImage(theme.getImage("kashmir.png"), focusScrolling);
           

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

        Button btn = new Button("Ajouter");

        Label labelDateSortie = new Label("Date dsortie");
        Picker datePickerS = new Picker();
        datePickerS.setType(Display.PICKER_TYPE_DATE);
          //TextField tfId = new TextField(null, "id");
        Label labelDateRetour = new Label("Date retour");
        Picker datePickerR = new Picker();
        datePickerS.setType(Display.PICKER_TYPE_DATE);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                Vehicule v = listeV.get(comboV.getSelectedIndex());

                AideChauffeur aideChauffeur =listeAide.get(comboAide.getSelectedIndex());
                
                Chauffeur ch = listeCh.get(comboCh.getSelectedIndex());
                 
               

                OrdreMission ordreMission = new OrdreMission( v, ch, aideChauffeur, new Date(), datePickerS.getDate(), datePickerR.getDate());

                // System.out.println(ordreMission);
                              sc.addOrdreMission(ordreMission);
            }
            
        });

        c.addAll(textVehicule, comboV, textChauffeur, comboCh, textAideChauf, comboAide, labelDateSortie, datePickerS, labelDateRetour, datePickerR, btn);

        this.add(c);
          this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
         
            new HomeLogistiqueForm().show();
            
        });
     
    }
   
}


