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
import com.entrepot.models.LignePerte;
import com.entrepot.models.Perte;
import com.entrepot.models.ProduitAchat;
import com.entrepot.services.ServiceLignePerte;
import com.entrepot.services.ServicePerte;
import com.entrepot.services.ServiceProduitAchat;
import java.util.Date;

/**
 *
 * @author guiforodrigue
 */
public class FormPasserEnPerte extends Form{
    ServiceProduitAchat spr = new ServiceProduitAchat();
    ServicePerte sp = new ServicePerte();
    ServiceLignePerte slp = new ServiceLignePerte();
    Resources theme = UIManager.initFirstTheme("/themeStockage");
    static int i = 0;
    public FormPasserEnPerte(){
        setTitle("PASSER EN PERTE");
        setLayout(new BorderLayout());
        Container formul = new Container(BoxLayout.y());
        Container bin = new Container(BoxLayout.y());
        
        getToolbar().addCommandToLeftBar("Back", null, ev->{
            new FormMenuPerte(this).show();
            //this.showBack();
        });
        
        String[] produits = { "Laptop", "Usb", "smartphone"};
        MultiButton p = new MultiButton("Quelle produit...");
        p.addActionListener(e -> {
            Dialog d = new Dialog();
            d.setLayout(BoxLayout.y());
            d.getContentPane().setScrollableY(true);
            for(int iter = 0 ; iter < spr.getAllProduits().size() ; iter++) {
                MultiButton mb = new MultiButton(spr.getAllProduits().get(iter).getLibelle());
                d.add(mb);
                mb.addActionListener(ee -> {
                    p.setTextLine1(mb.getTextLine1());
                    d.dispose();
                    p.revalidate();
                });
            }
            d.showPopupDialog(p);
        });
        
        TextField txt1 = new TextField("", "Qté",4 ,TextArea.NUMERIC);
       
                
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
        Button btnA = new Button("Annuler");
        Label lv = new Label("");
        Label lb = new Label("****************CORBEILLE******************");
        formul.add(lv);
        formul.add(p);
        formul.add(txt1);
        formul.add(b);
        formul.add(btnAdd);
        formul.add(btnV);
        formul.add(btnA);
        bin.add(lb);
        this.addComponent(BorderLayout.NORTH, formul);
        this.addComponent(BorderLayout.CENTER, bin);
        Date date = new Date();  
        Perte pert = new Perte(date);
        
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if(  Dialog.show("Comfirmation", "Vouler ajouter à la corbeille ? ", "oui", "non")){
                    Dialog ip = new InfiniteProgress().showInifiniteBlocking();
                    if(!b.getTextLine1().equals("Quelle produit...") && !p.getTextLine1().equals("Quelle raison...") && txt1.getText() != ""){
                       try{ 
                           pert.getLignePertes().add(new LignePerte(pert,FindByLibelle(spr,p.getTextLine1()),Integer.valueOf(txt1.getText()),b.getTextLine1()));

                           i += 1;
                           bin.add(new Label(i + "- "+ pert.getLignePertes().get(i-1).getQuantite()+" "+ pert.getLignePertes().get(i-1).getProduitAchat().getLibelle() + " : " + pert.getLignePertes().get(i-1).getRaisonPerte()));                   
                           bin.repaint();
                           p.setTextLine1("Quelle produit...");
                           txt1.setText("");
                           b.setTextLine1("Quelle raison...");
                           ip.dispose();
                        }
                       catch(NumberFormatException e){
                           Dialog.show("Info", "La Qté est numérique Svp!\n"+e, "OK", null);    
                       }   
                    }
                    else{
                        Dialog.show("Info", "Champs non remplis", "OK", null);
                    }
                }
            }
        });
        btnV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               if(  Dialog.show("Comfirmation", "Voulez-vous enrégistrer la perte ? ", "oui", "non")){ 
                   Dialog ip = new InfiniteProgress().showInifiniteBlocking();
                   boolean addsp = sp.addPerte(pert);
                   pert.setId(sp.getAllPertes().get(sp.getAllPertes().size()-1).getId());
                   for(int i=0;i<pert.getLignePertes().size();i++){
                       boolean addslp = slp.addLPerte(pert.getLignePertes().get(i));
                   }
                   if (addsp) {
                      Dialog.show("Info", "Perte enregistrée!", "OK", null); 
                      p.setTextLine1("Quelle produit...");
                       txt1.setText("");
                       b.setTextLine1("Quelle raison...");
                       pert.getLignePertes().clear();
                       bin.repaint();
                       ip.dispose();
                   }
               }
               
            }
        });
        btnA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               if(  Dialog.show("Comfirmation", "Voulez-vous annuler la perte ? ", "oui", "non")){               
                  
                       Dialog.show("Info", "Perte annulée!", "OK", null); 
                       p.setTextLine1("Quelle produit...");
                       txt1.setText("");
                       b.setTextLine1("Quelle raison...");
                       pert.getLignePertes().clear();
                       bin.removeAll();
                   }
                             
            }
        });
    }
    public ProduitAchat FindByLibelle(ServiceProduitAchat spr, String p){
        for (int i = 0; i < spr.getAllProduits().size(); i++){
            if(spr.getAllProduits().get(i).getLibelle().equals(p)){
                return spr.getAllProduits().get(i);
            }
        }
        return null;
    }
}
