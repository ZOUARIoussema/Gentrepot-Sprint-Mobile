/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.Emplacement;
import com.entrepot.models.Entrepot;
import com.entrepot.models.InventaireStock;
import com.entrepot.models.ProduitAchat;
import com.entrepot.services.ServiceEmplacement;
import com.entrepot.services.ServiceInventaireStock;
import com.entrepot.services.ServiceProduitAchat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author guiforodrigue
 */
public class FormCreerEmplacement extends Form{
    ServiceEmplacement se = new ServiceEmplacement();
    
    Resources theme = UIManager.initFirstTheme("/themeStockage");
    static int i = 0;
    public FormCreerEmplacement(){
        setTitle("CREER UN EMPLACEMENT");
        setLayout(new BorderLayout());
        Container formul = new Container(BoxLayout.y());
        
        getToolbar().addCommandToLeftBar("Back", null, ev->{
            new FormMenuEmplacement(this).show();
            //this.showBack();
        });
        
    TextField adr = new TextField("","Adresse",6 ,TextArea.ANY);    
    TextField txt1 = new TextField("","Adresse");
    String[] classe = { "A", "B", "C"};
        MultiButton c = new MultiButton("Quelle classe...");
        c.addActionListener(e -> {
            Dialog d = new Dialog();
            d.setLayout(BoxLayout.y());
            d.getContentPane().setScrollableY(true);
            for(int iter = 0 ; iter < classe.length ; iter++) {
                MultiButton mb = new MultiButton(classe[iter]);
                d.add(mb);
                mb.addActionListener(ee -> {
                    c.setTextLine1(mb.getTextLine1());
                    d.dispose();
                    c.revalidate();
                });
            }
            d.showPopupDialog(c);
    });
    TextField cap = new TextField("","Capacité stockage",4 ,TextArea.NUMERIC);
   
    Button btnV = new Button("Créer");
    Button btnA = new Button("Annuler");
    Label lv = new Label("");  
    formul.add(lv);
    formul.add(adr);
    formul.add(c);
    formul.add(cap);
    formul.add(btnV);
    formul.add(btnA);
    
    this.addComponent(BorderLayout.NORTH, formul);
      
    btnV.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
           if(  Dialog.show("Comfirmation", "Voulez-vous créer l'emplacement ? ", "oui", "non")){ 
                if(!c.getTextLine1().equals("Quelle classe...") && adr.getText().length() > 0 && cap.getText().length() > 0){
                    try{                         
                       Emplacement empl = new Emplacement(adr.getText(),Integer.valueOf(cap.getText()),0,c.getTextLine1(),new Entrepot("MA02SA"));   
                       Dialog ip = new InfiniteProgress().showInifiniteBlocking();
                       boolean addsp = se.addEmplas(empl);              
                       if (addsp) {
                           Dialog.show("Info", "Emplacement créé!", "OK", null); 
                           c.setTextLine1("Quelle classe...");
                           cap.setText("");
                           adr.setText("");
                           ip.dispose();
                       }
                    }catch(NumberFormatException e){
                           Dialog.show("Info", "La capacité est numérique Svp!\n", "OK", null);    
                    }    
                }
                else{
                    Dialog.show("Info", "Champs non remplis ? ", "OK", null);
                }
           }

        }
    });
        btnA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               if(  Dialog.show("Comfirmation", "Voulez-vous annuler la création ? ", "oui", "non")){               
                  
                       Dialog.show("Info", "Perte annulée!", "OK", null); 
                       c.setTextLine1("Quelle classe...");
                       cap.setText("");
                       adr.setText("");
                   }
                             
            }
        });
    }

}
