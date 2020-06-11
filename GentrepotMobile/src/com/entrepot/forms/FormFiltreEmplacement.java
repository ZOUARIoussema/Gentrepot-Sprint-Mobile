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
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.entrepot.models.Emplacement;
import com.entrepot.models.InventaireStock;
import com.entrepot.services.ServiceEmplacement;
import com.entrepot.services.ServiceInventaireStock;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author guiforodrigue
 */
public class FormFiltreEmplacement extends Form{
    static int d = 0;
    Form FormListeEmplFiltrer = new Form("LISTE FILTRER", new BoxLayout(BoxLayout.Y_AXIS));
    public FormFiltreEmplacement(){
        setTitle("FILTRER LES EMPLACEMENTS");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        
        FormListeEmplFiltrer.getToolbar().addCommandToLeftBar("Back", null, ev->{
            new FormFiltreEmplacement().show();           
        });
        getToolbar().addCommandToLeftBar("Back", null, ev->{
            new FormMenuInventaire(this).show();
           
        });
        Label r1 = new Label("TRI PAR CLASSE DE PRODUIT");
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
        Button b = new Button("Chercher");
        Label lvi = new Label("");
        Label l1 = new Label("LES EMPLACEMENT VIDES");
        Button b1 = new Button("Chercher");
        this.add(r1);
        this.add(c);
        this.add(b);
        this.add(lvi);
        this.add(l1);
        this.add(b1);
        b.addActionListener(e -> {
            Dialog ip = new InfiniteProgress().showInifiniteBlocking();
            creerListeEmplParClasse(c.getTextLine1());
            if (d > 0){
                FormListeEmplFiltrer.show();
                
            }
            
        });
        b.addActionListener(e -> {
            Dialog ip = new InfiniteProgress().showInifiniteBlocking();
            ListeEmplVide();
            if (d > 0){
                FormListeEmplFiltrer.show();
                
            }
            
        });
        
    }
    public void creerListeEmplParClasse(String c){
        ServiceEmplacement sp = new ServiceEmplacement();
        ArrayList<Emplacement> lp = new ArrayList<>();
  
        lp = sp.getAllEmplas();           

        if(lp.get(0) == null){
            Dialog.show("Info", "Pas d'emplacement!", "OK", null);    
        }
        else{
            for(int i=0;i<lp.size();i++){

                if(lp.get(i).getClass().equals(c)){
                    d++;
                    FormListeEmplFiltrer.add(new Label("----------------------------------------------------------"));
                    createLigneEmplacementEntry(FormListeEmplFiltrer,lp,i);
         
                    this.add(new Label(sp.getAllEmplas().size()+" Emplacement au total."));    
                }                                      
            }
            if(d == 0){
                Dialog.show("Info", "Aucun emplacement de la classe "+ c +" !", "OK", null);
            }
        }

    }
    public void ListeEmplVide(){
        ServiceEmplacement sp = new ServiceEmplacement();
        ArrayList<Emplacement> lp = new ArrayList<>();
  
        lp = sp.getAllEmplas();           

        if(lp.get(0) == null){
            Dialog.show("Info", "Pas d'emplacement!", "OK", null);    
        }
        else{
            for(int i=0;i<lp.size();i++){

                if(lp.get(i).getQuantiteStocker() == 0){
                    d++;
                    FormListeEmplFiltrer.add(new Label("----------------------------------------------------------"));
                    createLigneEmplacementEntry(FormListeEmplFiltrer,lp,i);
         
                    this.add(new Label(sp.getAllEmplas().size()+" Emplacement vide au total."));    
                }                                      
            }
            if(d == 0){
                Dialog.show("Info", "Aucun emplacement de la classe vide", "OK", null);
            }
        }

    }

    private void createLigneEmplacementEntry(Form hi, ArrayList<Emplacement> lp, int i) {
           ServiceEmplacement slp = new ServiceEmplacement();
           Label lgPertField = new Label(i+1 + "- Adresse: " +lp.get(i).getAdresse());
           Button delete = new Button();
           
           FontImage.setMaterialIcon(delete, FontImage.MATERIAL_DELETE);
           Button updat = new Button();
           FontImage.setMaterialIcon(updat, FontImage.MATERIAL_DESCRIPTION);
           Container content = BorderLayout.center(lgPertField);
           
           content.add(BorderLayout.EAST, BoxLayout.encloseX(delete, updat));
           delete.addActionListener(e -> {
               //Dialog ip = new InfiniteProgress().showInifiniteBlocking();
               slp.deleteEmplas(lp.get(i));
               content.setY(hi.getWidth());
               hi.getContentPane().animateUnlayoutAndWait(150, 255);
               hi.removeComponent(content);
               hi.getContentPane().animateLayout(150);
               Dialog.show("Info", "Supprimée!", "OK", null);
               //ip.dispose();
           });
           updat.addActionListener(e -> {
               Dialog.show("Info","CLASSE DE PRODUIT: "+lp.get(i).getClasse()+
                       "\n CAPACITE DE STOCKAGE: "+lp.get(i).getCapaciteStockage()+
                       "\n QTE STOCKER: "+lp.get(i).getQuantiteStocker(), "OK", null);
           });
           hi.add(content);
    }
}
