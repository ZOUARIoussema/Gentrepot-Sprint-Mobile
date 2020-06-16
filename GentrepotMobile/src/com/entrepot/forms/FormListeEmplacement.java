/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.entrepot.models.Emplacement;
import com.entrepot.models.InventaireStock;
import com.entrepot.models.ProduitAchat;
import com.entrepot.services.ServiceEmplacement;
import com.entrepot.services.ServiceInventaireStock;
import com.entrepot.services.ServiceProduitAchat;
import java.util.ArrayList;

/**
 *
 * @author guiforodrigue
 */
public class FormListeEmplacement extends Form{
    public FormListeEmplacement(){
        setTitle("LISTE DES EMPLACEMENTS");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        
        getToolbar().addCommandToLeftBar("Back", null, ev->{           
            new FormMenuEmplacement(this).show();
           
        });
        Dialog ip = new InfiniteProgress().showInifiniteBlocking();
        this.creerListeEmplacement();
    }
     
    public void creerListeEmplacement(){
        ServiceEmplacement se = new ServiceEmplacement();
        ArrayList<Emplacement> le = new ArrayList<>();
        ServiceProduitAchat sp = new ServiceProduitAchat();
        ArrayList<ProduitAchat> lp = new ArrayList<>();
        le = se.getAllEmplas();                     
        if(le.get(0) == null){
            Dialog.show("Info", "Aucun emplacement!", "OK", null);    
        }
        else{
            for(int i=0;i<le.size();i++){
                this.createLigneEmplacementEntry(this,le,i);

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
