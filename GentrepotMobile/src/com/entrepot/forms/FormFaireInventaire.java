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
public class FormFaireInventaire extends Form{
    ServiceProduitAchat spr = new ServiceProduitAchat();
    ServiceInventaireStock sp = new ServiceInventaireStock();
    ServiceEmplacement slp = new ServiceEmplacement();
    Resources theme = UIManager.initFirstTheme("/themeStockage");
    static int i = 0;
    public FormFaireInventaire(){
        setTitle("FAIRE UN INVENTAIRE");
        setLayout(new BorderLayout());
        Container formul = new Container(BoxLayout.y());
        
        getToolbar().addCommandToLeftBar("Back", null, ev->{
            new FormMenuInventaire(this).show();
            //this.showBack();
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
    TextField qteI = new TextField("","qunatite Inventée",4 ,TextArea.NUMERIC);    

    Button btnV = new Button("Enregistrer");
    Button btnA = new Button("Annuler");
    Label lv = new Label("");  
    formul.add(ep);
    formul.add(p);
    formul.add(qteI);
    formul.add(btnV);
    formul.add(btnA);
    
    this.addComponent(BorderLayout.NORTH, formul);

    //Date date = new Date();  
       
    btnV.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
           if(  Dialog.show("Comfirmation", "Voulez-vous enrégistrer l'inventaire ? ", "oui", "non")){ 
               Dialog ip = new InfiniteProgress().showInifiniteBlocking();
               InventaireStock inv = new InventaireStock(FindByLibelle(spr, p.getTextLine1()),FindByAdr(se, ep.getTextLine1()),Integer.valueOf(qteI.getText()));
               boolean addsp = sp.addInv(inv);              
               if (addsp) {
                   Dialog.show("Info", "Inventaire enregistrée!", "OK", null); 
                   p.setTextLine1("Quelle produit...");
                   qteI.setText("");
                   ep.setTextLine1("Où...");
                   ip.dispose();
               }
           }

        }
    });
        btnA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               if(  Dialog.show("Comfirmation", "Voulez-vous annuler l'inventaire ? ", "oui", "non")){               
                  
                       Dialog.show("Info", "Perte annulée!", "OK", null); 
                       p.setTextLine1("Quelle produit...");
                       qteI.setText("");
                       ep.setTextLine1("Où...");
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
    public Emplacement FindByAdr(ServiceEmplacement spr, String p){
        for (int i = 0; i < spr.getAllEmplas().size(); i++){
            if(spr.getAllEmplas().get(i).getAdresse().equals(p)){
                return spr.getAllEmplas().get(i);
            }
        }
        return null;
    }
    
}
