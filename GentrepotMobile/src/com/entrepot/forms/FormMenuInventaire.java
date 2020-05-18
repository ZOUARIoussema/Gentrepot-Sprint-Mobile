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
import com.entrepot.models.InventaireStock;
import com.entrepot.models.ProduitAchat;
import com.entrepot.services.ServiceEmplacement;
import com.entrepot.services.ServiceEntrepot;
import com.entrepot.services.ServiceInventaireStock;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
/**
 *
 * @author guiforodrigue
 */
public class FormMenuInventaire extends Form{
    
     Resources theme = UIManager.initFirstTheme("/themeStockage");
    
     public FormMenuInventaire(Form previous){
        setTitle("GESTION DE L'EMPLACEMENT");
        setLayout(new FlowLayout(CENTER, CENTER));
        
        
        Form ajout = new Form("FAIRE UN INVENTAIRE", BoxLayout.y());
        Form liste = new Form("LISTE DES INVENTAIRES", BoxLayout.y());
        Form filtre = new Form("FILTRER LES INVENTAIRES", BoxLayout.y());
        Button btnAjout = new Button("FAIRE UN INVENTAIRE");
        Button btnLister = new Button("LISTE DES INVENTAIRES");
        Button btnFiltre = new Button("FILTRER LES INVENTAIRES");
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
        ServiceEmplacement se = new ServiceEmplacement();
        ArrayList<Emplacement> le = new ArrayList<>();
        le = se.getAllEmplas();
        String[] empl = new String[le.size()];
        for (int j=0;j<le.size();j++){
            empl[j] = le.get(j).getId()+"-"+le.get(j).getAdresse();
        }
        MultiButton ep = new MultiButton("Où...");
        ep.addActionListener(e -> {
            Dialog d = new Dialog();
            d.setLayout(BoxLayout.y());
            d.getContentPane().setScrollableY(true);
            for(int iter = 0 ; iter < empl.length ; iter++) {
                MultiButton mb = new MultiButton(empl[iter]);
                d.add(mb);
                mb.addActionListener(ee -> {
                    ep.setTextLine1(mb.getTextLine1());
                    d.dispose();
                    ep.revalidate();
                });
            }
            d.showPopupDialog(ep);
        });        
        String[] produits = { "Laptop", "Usb", "smartphone"};
        MultiButton p = new MultiButton("Quel produit...");
        p.addActionListener(e -> {
            Dialog d = new Dialog();
            d.setLayout(BoxLayout.y());
            d.getContentPane().setScrollableY(true);
            for(int iter = 0 ; iter < produits.length ; iter++) {
                MultiButton mb = new MultiButton(produits[iter]);
                d.add(mb);
                mb.addActionListener(ee -> {
                    p.setTextLine1(mb.getTextLine1());
                    d.dispose();
                    p.revalidate();
                });
            }
            d.showPopupDialog(p);
        });
        
        TextField qteI = new TextField("","qunatiteInventiare");
        
        Button btnul = new Button("Enregistrer");
        Button btnV = new Button("Annuler");
        ajout.add(ep);
        ajout.add(p);
        ajout.add(qteI);
        ajout.add(btnV);
        ajout.add(btnul);
        
        btnV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               ServiceInventaireStock sinv = new ServiceInventaireStock();
               String epl = ep.getTextLine1().substring(0,1);
               InventaireStock inV = new InventaireStock(new ProduitAchat("C14"),new Emplacement(Integer.valueOf(epl)),Integer.valueOf(qteI.getText()));
               boolean addsp = sinv.addInv(inV);             
               if (addsp) {
                  Dialog.show("Info", "Inventaire enregistrée!", "OK", null); 
                  qteI.setText("");
                  ep.setTextLine1("Où...");
                  p.setTextLine1("Quel produit...");
               }
               
            }
        });
        
        
        
        btnul.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {              
                  Dialog.show("Info", "Annuler l'inventaire!", "OK", null); 
                  ep.setTextLine1("Où....");
                  p.setTextLine1("Quel produit...");
                  qteI.setText("");
                  
            }
        });
        
        
        ServiceInventaireStock sp = new ServiceInventaireStock();
        ArrayList<InventaireStock> lp = new ArrayList<>();
        lp = sp.getAllInvs();               
        for(int i=0;i<lp.size();i++){
            liste.add(new Label(i+1 + "->" + lp.get(i).getDateCreation()+ "  " + lp.get(i).getEmplacement().getAdresse()+"  "+ lp.get(i).getProduitAchat())+ "  "+ lp.get(i).getEcart());
        }
    }
}
