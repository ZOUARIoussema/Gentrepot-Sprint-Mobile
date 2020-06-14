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
public class FormListeInventaire extends Form{
     public FormListeInventaire(){
        setTitle("LISTE DES INVENTAIRES");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        
        getToolbar().addCommandToLeftBar("Back", null, ev->{           
            new FormMenuInventaire(this).show();
           
        });
        Dialog ip = new InfiniteProgress().showInifiniteBlocking();
        this.creerListeInventaire();
    }
     
    public void creerListeInventaire(){
        ServiceInventaireStock si = new ServiceInventaireStock();
        ArrayList<InventaireStock> li = new ArrayList<>();
        
        li = si.getAllInvs();           
        if(li.get(0) == null){
            Dialog.show("Info", "Aucun inventaire!", "OK", null);    
        }
        else{
            for(int i=0;i<li.size();i++){
                this.add(new Label("----------------------------------------------------------"));
                this.add(new Label(" Inventaire du " + new java.text.SimpleDateFormat("MM-dd-yyyy").format(li.get(i).getDateCreation())));
                this.createLigneInventaireEntry(this,li,i);
            }
        }
    } 
    
    private void createLigneInventaireEntry(Form hi, ArrayList<InventaireStock> lp, int i) {
           ServiceInventaireStock slp = new ServiceInventaireStock();
           Label lgPertField = new Label(i+1 + "- " +lp.get(i).getEcart()+" "+ lp.get(i).getProduitAchat().getLibelle() + " manquant à " + lp.get(i).getEmplacement().getAdresse());
           Button delete = new Button();
           
           FontImage.setMaterialIcon(delete, FontImage.MATERIAL_DELETE);
           Button updat = new Button();
           FontImage.setMaterialIcon(updat, FontImage.MATERIAL_DESCRIPTION);
           Container content = BorderLayout.center(lgPertField);
           
           content.add(BorderLayout.EAST, BoxLayout.encloseX(delete, updat));
           delete.addActionListener(e -> {
               //Dialog ip = new InfiniteProgress().showInifiniteBlocking();
               slp.deleteInv(lp.get(i));
               content.setY(hi.getWidth());
               hi.getContentPane().animateUnlayoutAndWait(150, 255);
               hi.removeComponent(content);
               hi.getContentPane().animateLayout(150);
               Dialog.show("Info", "Supprimée!", "OK", null);
               //ip.dispose();
           });
           updat.addActionListener(e -> {
               Dialog.show("Info","Produit: "+lp.get(i).getProduitAchat().getLibelle()+
                       "\n Emplacement: "+lp.get(i).getEmplacement().getAdresse()+
                       "\n Qté Théorique: "+lp.get(i).getQuantiteTheorique()+
                       "\n Qté inventée: "+lp.get(i).getQunatiteInventiare(), "OK", null);
           });
           hi.add(content);
    }
    
}
