/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.ui.Form;

import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.list.*;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.Emplacement;
import com.entrepot.models.Entrepot;
import com.entrepot.services.ServiceEmplacement;
import com.entrepot.services.ServiceEntrepot;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
/**
 *
 * @author guiforodrigue
 */
public class FormMenuEmplacement extends Form{
    
     Resources theme = UIManager.initFirstTheme("/themeStockage");
    
    public FormMenuEmplacement(Form previous){
        setTitle("GESTION DE L'EMPLACEMENT");
        setLayout(new FlowLayout(CENTER, CENTER));
        
        
        Form ajout = new Form("CREER UN EMPLACEMENT", BoxLayout.y());
        Form liste = new Form("LISTE LES EMPLACEMENTS", BoxLayout.y());
        Form filtre = new Form("FILTRER LES EMPLACEMENTS", BoxLayout.y());
        Button btnAjout = new Button("CREER UN EMPLACEMENT");
        Button btnLister = new Button("LISTE LES EMPLACEMENTS");
        Button btnFiltre = new Button("FILTREZ LES EMPLACEMENTS");
        this.add(btnAjout);
        this.add(btnLister);
        this.add(btnFiltre);
        btnAjout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               ajout.show();
               
            }
        });
        getToolbar().addCommandToLeftBar("Back", null, ev->{
            previous.showBack();
        });
        ajout.getToolbar().addCommandToLeftBar("Back", null, ev->{
            this.showBack();
        });
        btnLister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               liste.show();
               
            }
        });
        btnFiltre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               filtre.show();
               
            }
        });
        
       
        TextField txt1 = new TextField("","Adresse");
        TextField txt2 = new TextField("","Classe");
        TextField txt3 = new TextField("","Capacité stockage");
        TextField txt4 = new TextField("","Qté Stockée");
        
        Button btnAdd = new Button("Enregistrer");
        Button btnV = new Button("Annuler");
        ajout.add(txt1);
        ajout.add(txt2);
        ajout.add(txt3);
        ajout.add(txt4);
        ajout.add(btnAdd);
        ajout.add(btnV);
        
        
        
        
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               ServiceEntrepot sent = new ServiceEntrepot();
               ServiceEmplacement semp = new ServiceEmplacement();
               Emplacement emp = new Emplacement(txt1.getText(),Integer.valueOf(txt3.getText()),Integer.valueOf(txt4.getText()),txt2.getText());
               boolean addsp = semp.addEmplas(emp);             
               if (addsp) {
                  Dialog.show("Info", "Emplacement enregistrée!", "OK", null); 
                  txt1.setText("");
                  txt2.setText("");
                  txt3.setText("");
                  txt4.setText("");
               }
               
            }
        });
        btnV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               txt1.setText("");
               txt2.setText("");
               txt3.setText("");
               txt4.setText("");
            }
        });
        
        ServiceEmplacement sp = new ServiceEmplacement();
        ArrayList<Emplacement> lp = new ArrayList<>();
        lp = sp.getAllEmplas();               
        for(int i=0;i<lp.size();i++){
            liste.add(new Label(i+1 + "->" + lp.get(i).toString()));
        }
    }
}
