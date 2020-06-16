/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
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
import com.entrepot.models.CommandeDApprovisionnement;
import com.entrepot.models.Fournisseur;
import com.entrepot.models.LigneCommandeDApprovisionnement;
import com.entrepot.models.ProduitAchat;
import com.entrepot.services.MailService;
import com.entrepot.services.ServiceCommandeDApprovisionnment;
import com.entrepot.services.ServiceLigneCommandeDApprovisionnement;
import com.entrepot.services.ServiceProduitAchat;
import com.entrepot.services.ServiceFournisseur;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author guiforodrigue
 */
public class FormEffectuerCommandeApp extends Form{
    
    ServiceFournisseur sf = new ServiceFournisseur();
    ServiceProduitAchat spr = new ServiceProduitAchat();
    ServiceCommandeDApprovisionnment sp = new ServiceCommandeDApprovisionnment();
    ServiceLigneCommandeDApprovisionnement slp = new ServiceLigneCommandeDApprovisionnement();
    Resources theme = UIManager.initFirstTheme("/themeStockage");
    static int i = 0;
    
    public FormEffectuerCommandeApp(){
        setTitle("EFFECTUER UNE COMMANDE");
        setLayout(new BorderLayout());
        Container formul = new Container(BoxLayout.y());
        Container bin = new Container(BoxLayout.y());
        
        getToolbar().addCommandToLeftBar("Back", null, ev->{
            new FormMenuCommandeApprovisionnement(this).show();
        });
        ServiceProduitAchat spf = new ServiceProduitAchat();
        ServiceFournisseur sff = new ServiceFournisseur();
        Map x = spf.getResponse("/apiF/listF");
        ArrayList<Fournisseur> lis = sff.getListFournisseurs(x);
        //String[] fournisseurs = { "Intel", "Samsun", "Cisco"};
        MultiButton f = new MultiButton("A Quel fournisseur...");
        f.addActionListener(e -> {
            Dialog d = new Dialog();
            d.setLayout(BoxLayout.y());
            d.getContentPane().setScrollableY(true);
            System.out.println(lis.size());
            for(int iter = 0 ; iter < lis.size() ; iter++) {
                MultiButton mb = new MultiButton(lis.get(iter).getAdresseMail());
                d.add(mb);
                mb.addActionListener(ee -> {
                    f.setTextLine1(mb.getTextLine1());
                    d.dispose();
                    f.revalidate();
                });
            }
            d.showPopupDialog(f);
        });
        TextField r = new TextField("", "Taux de remise",3 ,TextArea.NUMERIC);
        Label lbd = new Label("------------------------------------------------------------");
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
   
       
        TextField qte = new TextField("", "Qté",4 ,TextArea.NUMERIC);
        TextField prix = new TextField("", "Prix unitaire",6,TextArea.NUMERIC);
        TextField tva = new TextField("", "TVA",3,TextArea.NUMERIC);
        Button btnAdd = new Button("Panier");
        Button btnV = new Button("Valider");
        Button btnA = new Button("Annuler");
        Label lv = new Label("");
        Label lb = new Label("********************PANIER*******************");
        formul.add(f);
        formul.add(r);
        formul.add(lbd);
        formul.add(p);
        formul.add(qte);
        formul.add(prix);
        formul.add(tva);
        formul.add(btnAdd);
        formul.add(btnV);
        formul.add(btnA);
        bin.add(lb);
        this.addComponent(BorderLayout.NORTH, formul);
        this.addComponent(BorderLayout.CENTER, bin);
        Date date = new Date();  
        ServiceFournisseur sf = new ServiceFournisseur();
        CommandeDApprovisionnement com = new CommandeDApprovisionnement(date);
        
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if(  Dialog.show("Comfirmation", "Vouler ajouter au panier ? ", "oui", "non")){
                    if(!p.getTextLine1().equals("Quelle produit...") && qte.getText() != "" && prix.getText() != "" && tva.getText() != ""){
                       try{ 
                           com.getLigneCommandeDApprovisionnements().add(new LigneCommandeDApprovisionnement(com,FindByLibelle(spr,p.getTextLine1()),Double.valueOf(prix.getText()), Integer.valueOf(qte.getText()) , Double.valueOf(prix.getText())*Integer.valueOf(qte.getText()), Double.valueOf(tva.getText())));
              
                           i += 1;
                           bin.add(new Label(i + "-" + com.getLigneCommandeDApprovisionnements().get(i-1).getProduitAchat().getReference() + "->" + com.getLigneCommandeDApprovisionnements().get(i-1).getQuantite() + ",  " + com.getLigneCommandeDApprovisionnements().get(i-1).getPrix() + ",  " + com.getLigneCommandeDApprovisionnements().get(i-1).getQuantite()*com.getLigneCommandeDApprovisionnements().get(i-1).getPrix()));                   
                           bin.repaint();
                           p.setTextLine1("Quelle produit...");
                           qte.setText("");
                           prix.setText("");
                           tva.setText("");                           
                           
                        }
                       catch(NumberFormatException e){
                           Dialog.show("Info", "La Qté, le prix et la tva sont numériques Svp!\n"+e, "OK", null);    
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
               if(  Dialog.show("Comfirmation", "Voulez-vous enrégistrer la commande ? ", "oui", "non")){ 
                    if(!f.getTextLine1().equals("A Quel fournisseur...") && r.getText() != "")
                        try{
                           Dialog ip = new InfiniteProgress().showInifiniteBlocking();
                           boolean addsp = sp.addCom(com);
                           com.setNumeroC(sp.getAllComs().get(sp.getAllComs().size()-1).getNumeroC());
                           String body = " ";
                           
                           for(int i=0;i<com.getLigneCommandeDApprovisionnements().size();i++){
                               
                               boolean addslp = slp.addLCom(com.getLigneCommandeDApprovisionnements().get(i));
                               body = body +"\n"+com.getLigneCommandeDApprovisionnements().get(i).getQuantite()+" "+com.getLigneCommandeDApprovisionnements().get(i).getProduitAchat().getLibelle();
                           }
                           
                           MailService.EnvoyerMail(com.getFournisseur().getAdresseMail(), "COMMANDE D'APPROVISIONNEMENT N°" + com.getNumeroC()+"\n",body);
                           ToastBar.showMessage("Mail envoyé automatiquement au Fournisseur", FontImage.MATERIAL_STAR, 30000);
                           if (addsp) {
                               Dialog.show("Info", "Commande enregistrée!", "OK", null); 
                               p.setTextLine1("Quelle produit...");
                               qte.setText("");
                               prix.setText("");
                               tva.setText("");
                               r.setText("");
                               f.setTextLine1("A Quel fournisseur...");
                               com.getLigneCommandeDApprovisionnements().clear();
                               bin.repaint();
                               ip.dispose();
                           }
                         }
                       catch(NumberFormatException e){
                           Dialog.show("Info", "Le taux de remise est numérique Svp!\n"+e, "OK", null);    
                       }    
               }
               
            }
        });
        btnA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               if(  Dialog.show("Comfirmation", "Voulez-vous annuler la commande ? ", "oui", "non")){               
                  
                       Dialog.show("Info", "commande annulée!", "OK", null); 
                       p.setTextLine1("Quelle produit...");
                       qte.setText("");
                       prix.setText("");
                       tva.setText("");
                       r.setText("");
                       f.setTextLine1("A Quel fournisseur...");
                       com.getLigneCommandeDApprovisionnements().clear();
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
