/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

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
import static com.codename1.ui.layouts.BoxLayout.y;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.list.*;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.LignePerte;
import com.entrepot.models.Perte;
import com.entrepot.models.ProduitAchat;
import com.entrepot.services.ServiceLignePerte;
import com.entrepot.services.ServicePerte;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author guiforodrigue
 */
public class FormMenuPerte extends Form{
    
     Resources theme = UIManager.initFirstTheme("/themeStockage");
    
    static int i = 0;
    public FormMenuPerte(Form previous){
        setTitle("GESTION DES PERTES");
        setLayout(new FlowLayout(CENTER, CENTER));
        
        
        Form ajout = new Form("PASSER EN PERTE", BoxLayout.y());
        Form liste = new Form("LISTE DES PERTES", BoxLayout.y());
        Form filtre = new Form("FILTREZ LES PERTE", BoxLayout.y());
        Button btnAjout = new Button("PASSER EN PERTE");
        Button btnLister = new Button("LISTEZ LES PERTES");
        Button btnFiltre = new Button("FILTREZ LES PERTES");
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
        liste.getToolbar().addCommandToLeftBar("Back", null, ev->{
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
        
        String[] produits = { "Laptop", "Usb", "smartphone"};
        MultiButton p = new MultiButton("Quelle produit...");
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
        TextField txt1 = new TextField("","Qté");
        String[] characters = { "Vol", "Hors usage", "Inconue"};
        MultiButton b = new MultiButton("Quelle raison...");
        b.addActionListener(e -> {
            Dialog d = new Dialog();
            d.setLayout(BoxLayout.y());
            d.getContentPane().setScrollableY(true);
            for(int iter = 0 ; iter < characters.length ; iter++) {
                MultiButton mb = new MultiButton(characters[iter]);
                d.add(mb);
                mb.addActionListener(ee -> {
                    b.setTextLine1(mb.getTextLine1());
                    d.dispose();
                    b.revalidate();
                });
            }
            d.showPopupDialog(b);
        });
        Button btnAdd = new Button("Corbeille");
        Button btnV = new Button("Valider");
        ajout.add(p);
        ajout.add(txt1);
        ajout.add(b);
        ajout.add(btnAdd);
        
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
        Date date = new Date();  
        Perte pert = new Perte(formatter.format(date));
        
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               pert.getLignePertes().add(new LignePerte(pert,new ProduitAchat("C14"),Integer.valueOf(txt1.getText()),b.getTextLine1()));
               i += 1;
               ajout.add(new Label(i + "-" + pert.getLignePertes().get(i-1).getProduitAchat().getReference() + "->" + pert.getLignePertes().get(i-1).getRaisonPerte()));
               p.setTextLine1("Quelle produit...");
               txt1.setText("");
               b.setTextLine1("Quelle raison...");
               ajout.removeComponent(btnV);
               ajout.add(btnV);
            }
        });
        btnV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               ServicePerte sp = new ServicePerte();
               ServiceLignePerte slp = new ServiceLignePerte();
               boolean addsp = sp.addPerte(pert);
               pert.setId(sp.getAllPertes().get(sp.getAllPertes().size()-1).getId());
               for(int i=0;i<pert.getLignePertes().size();i++){
                   boolean addslp = slp.addLPerte(pert.getLignePertes().get(i));
               }
               if (addsp) {
                  Dialog.show("Info", "Perte enregistrée!", "OK", null); 
               }
               
               
            }
        });
        
            ServicePerte sp = new ServicePerte();
            ArrayList<Perte> lp = new ArrayList<>();
            lp = sp.getAllPertes();
            ServiceLignePerte slp = new ServiceLignePerte();
            ArrayList<LignePerte> llp = new ArrayList<>();
            llp = slp.getAllLPertes();
            for(int i=0;i<lp.size();i++){
                for(int j=0;j<llp.size();j++){
                    if(lp.get(i).getId()==llp.get(j).getPerte().getId()){
                        lp.get(i).getLignePertes().add(llp.get(j));
                    }
                }

            }
            for(int i=0;i<lp.size();i++){
                liste.add(new Label(i+1 + "Perte du " + lp.get(i).getDate()));
                for(int j=0;j<lp.get(i).getLignePertes().size();j++){
                    liste.add(new Label(j+1 + "-" + lp.get(i).getLignePertes().get(j).getProduitAchat().getLibelle()) + " ," + lp.get(i).getLignePertes().get(j).getQuantite() + " ," + lp.get(i).getLignePertes().get(j).getRaisonPerte());
                }
            }
             
    }
}
