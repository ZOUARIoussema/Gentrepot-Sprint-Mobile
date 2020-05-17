/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.MultiButton;
import static com.codename1.io.Log.e;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.entrepot.models.CommandeDApprovisionnement;
import com.entrepot.models.Fournisseur;
import com.entrepot.models.LigneCommandeDApprovisionnement;
import com.entrepot.models.ProduitAchat;
import com.entrepot.services.ServiceCommandeDApprovisionnment;
import com.entrepot.services.ServiceLigneCommandeDApprovisionnement;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author guiforodrigue
 */
public class FormMenuCommandeApprovisionnement extends Form{
    static int i=0;
    public FormMenuCommandeApprovisionnement(Form previous){
        setTitle("GESTION COMMANDES D'APPROVISIONNEMENT");
        setLayout(new FlowLayout(CENTER, CENTER));
        
        
        Form ajout = new Form("EFFECTUEZ VOTRE COMMANDE D'APPROVISIONNEMENT", BoxLayout.y());
        Form liste = new Form("LISTE DES COMMANDES D'APPROVISIONNEMENT", BoxLayout.y());
        Form filtre = new Form("FILTREZ LES COMMANDES D'APPROVISIONNEMENT", BoxLayout.y());
        Button btnAjout = new Button("EFFECTUEZ VOTRE COMMANDE");
        Button btnLister = new Button("LISTEZ LES COMMANDES");
        Button btnFiltre = new Button("FILTREZ LES COMMANDES");
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
    
    
        String[] fournisseurs = { "Intel", "Samsun", "Cisco"};
        MultiButton f = new MultiButton("A Quel fournisseur...");
        f.addActionListener(e -> {
            Dialog d = new Dialog();
            d.setLayout(BoxLayout.y());
            d.getContentPane().setScrollableY(true);
            for(int iter = 0 ; iter < fournisseurs.length ; iter++) {
                MultiButton mb = new MultiButton(fournisseurs[iter]);
                d.add(mb);
                mb.addActionListener(ee -> {
                    f.setTextLine1(mb.getTextLine1());
                    d.dispose();
                    f.revalidate();
                });
            }
            d.showPopupDialog(f);
        });
        TextField r = new TextField("","Taux remise");
        Label lb = new Label("----------------------------------");
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
        Button btnAdd = new Button("Panier");
        Button btnV = new Button("Valider");
        TextField qte = new TextField("","Qté");
        TextField  prix = new TextField("","Prix unitaire");
        TextField tva = new TextField("","TVA");
        Label lp = new Label("--------------PANIER------------");
        ajout.add(f);
        ajout.add(r);
        ajout.add(lb);
        ajout.add(p);
        ajout.add(qte);
        ajout.add(prix);
        ajout.add(tva);
        ajout.add(btnAdd);
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
        Date date = new Date();  
        CommandeDApprovisionnement com = new CommandeDApprovisionnement(formatter.format(date));
        
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               com.getLigneCommandeDApprovisionnements().add(new LigneCommandeDApprovisionnement(com,new ProduitAchat("C14"),Double.valueOf(prix.getText()), Integer.valueOf(qte.getText()) , Double.valueOf(prix.getText())*Integer.valueOf(qte.getText()), Double.valueOf(tva.getText())));
               i += 1;
               if(i==1)ajout.add(lp);
               ajout.add(new Label(i + "-" + com.getLigneCommandeDApprovisionnements().get(i-1).getProduitAchat().getReference() + "->" + com.getLigneCommandeDApprovisionnements().get(i-1).getQuantite() + ",  " + com.getLigneCommandeDApprovisionnements().get(i-1).getPrix() + ",  " + com.getLigneCommandeDApprovisionnements().get(i-1).getQuantite()*com.getLigneCommandeDApprovisionnements().get(i-1).getPrix()));
               
               prix.setText("");
               tva.setText("");
               qte.setText("");
               p.setTextLine1("Quel produit...");
               ajout.removeComponent(btnV);
               ajout.add(btnV);
               Dialog.show("Info", "Produit ajouté au panier!", "OK", null);
            }
        });
        btnV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               ServiceCommandeDApprovisionnment sc = new ServiceCommandeDApprovisionnment();
               ServiceLigneCommandeDApprovisionnement slc = new ServiceLigneCommandeDApprovisionnement();
               com.setFournisseur(new Fournisseur(f.getTextLine1()));
               com.setTauxRemise(Double.valueOf(r.getText()));
               double ptht = 0;
               double ptt = 0;
               for(int j=0;j<com.getLigneCommandeDApprovisionnements().size();j++){
                   ptht += com.getLigneCommandeDApprovisionnements().get(j).getTotal();
                   ptt += com.getLigneCommandeDApprovisionnements().get(j).getTotal() + com.getLigneCommandeDApprovisionnements().get(j).getTotal()*com.getLigneCommandeDApprovisionnements().get(j).getTva()/100;
               }
               ptt += ptt - ptt*com.getTauxRemise()/100;
               com.setTotalC(ptht);
               com.setTotalTva(ptt);
               boolean addsp = sc.addCom(com);
               com.setNumeroC(sc.getAllComs().get(sc.getAllComs().size()-1).getNumeroC());
               for(int i=0;i<com.getLigneCommandeDApprovisionnements().size();i++){
                   boolean addslp = slc.addLCom(com.getLigneCommandeDApprovisionnements().get(i));
               }
               if (addsp) {
                  Dialog.show("Info", "Commande passée!", "OK", null); 
               }
               
               
            }
        });
        
        ServiceCommandeDApprovisionnment sc = new ServiceCommandeDApprovisionnment();
            ArrayList<CommandeDApprovisionnement> lsc = new ArrayList<>();
            lsc = sc.getAllComs();
            ServiceLigneCommandeDApprovisionnement slc = new ServiceLigneCommandeDApprovisionnement();
            ArrayList<LigneCommandeDApprovisionnement> lslc = new ArrayList<>();
            lslc = slc.getAllLComs();
            for(int a=0;a<lsc.size();a++){
                for(int j=0;j<lslc.size();j++){
                    if(lsc.get(a).getNumeroC()==lslc.get(j).getCommandeDApprovisionnement().getNumeroC()){
                        lsc.get(a).getLigneCommandeDApprovisionnements().add(lslc.get(j));
                    }
                }

            }
            for(int i=0;i<lsc.size();i++){
                liste.add(new Label(i+1 + "- Commande du " + lsc.get(i).getDateCreation()));
                for(int j=0;j<lsc.get(i).getLigneCommandeDApprovisionnements().size();j++){
                    liste.add(new Label(j+1 + "-" + lsc.get(i).getLigneCommandeDApprovisionnements().get(j).getProduitAchat().getLibelle()) + " ," + lsc.get(i).getLigneCommandeDApprovisionnements().get(j).getQuantite() + " ," + lsc.get(i).getLigneCommandeDApprovisionnements().get(j).getTotal());
                }
            }
    }
        
}
