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
import com.entrepot.models.CommandeDApprovisionnement;
import com.entrepot.models.LigneCommandeDApprovisionnement;
import com.entrepot.services.ServiceCommandeDApprovisionnment;
import com.entrepot.services.ServiceLigneCommandeDApprovisionnement;
import java.util.ArrayList;

/**
 *
 * @author guiforodrigue
 */
public class FormListeCommandeApp extends Form{

    public FormListeCommandeApp(){
        setTitle("LISTE DES COMMANDES");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        
        getToolbar().addCommandToLeftBar("Back", null, ev->{           
            new FormMenuCommandeApprovisionnement(this).show();
           
        });
        Dialog ip = new InfiniteProgress().showInifiniteBlocking();
        this.creerListeCom();
    }
    public void creerListeCom(){
        ServiceCommandeDApprovisionnment sp = new ServiceCommandeDApprovisionnment();
        ArrayList<CommandeDApprovisionnement> lp = new ArrayList<>();
        ServiceLigneCommandeDApprovisionnement slp = new ServiceLigneCommandeDApprovisionnement();
        ArrayList<LigneCommandeDApprovisionnement> llp = new ArrayList<>();
        lp = sp.getAllComs();           
            llp = slp.getAllLComs();
            for(int i=0;i<lp.size();i++){
                for(int j=0;j<llp.size();j++){
                    if(lp.get(i).getNumeroC()==llp.get(j).getCommandeDApprovisionnement().getNumeroC()){
                        lp.get(i).getLigneCommandeDApprovisionnements().add(llp.get(j));
                    }
                }

            }
            if(lp.get(0) == null){
                Dialog.show("Info", "Aucune commande!", "OK", null);    
            }
            else{
                for(int i=0;i<lp.size();i++){
                    this.add(new Label("----------------------------------------------------------"));
                    this.add(new Label(" Commande  du " + new java.text.SimpleDateFormat("MM-dd-yyyy").format(lp.get(i).getDateCreation())+"\n Fournisseur: "+lp.get(i).getFournisseur().getAdresseMail()));
                    
                    for(int j=0;j<lp.get(i).getLigneCommandeDApprovisionnements().size();j++){
                        this.createLigneComEntry(this,lp,j,i);
                        
                    }                    
                        
                }
            }
    }
    private void createLigneComEntry(Form hi, ArrayList<CommandeDApprovisionnement> lp, int j, int i) {
           ServiceLigneCommandeDApprovisionnement slp = new ServiceLigneCommandeDApprovisionnement();
           Label lgComField = new Label(j+1 + "- " +lp.get(i).getLigneCommandeDApprovisionnements().get(j).getQuantite()+" "+ lp.get(i).getLigneCommandeDApprovisionnements().get(j).getProduitAchat().getLibelle() + " à " + lp.get(i).getLigneCommandeDApprovisionnements().get(j).getTotal()+" DT");
           Button delete = new Button();           
           FontImage.setMaterialIcon(delete, FontImage.MATERIAL_DELETE);
           Button updat = new Button();
           FontImage.setMaterialIcon(updat, FontImage.MATERIAL_UPDATE);
           Container content = BorderLayout.center(lgComField);
           
           content.add(BorderLayout.EAST, BoxLayout.encloseX(delete, updat));
           delete.addActionListener(e -> {
               //Dialog ip = new InfiniteProgress().showInifiniteBlocking();
               slp.deleteCom(lp.get(i).getLigneCommandeDApprovisionnements().get(j));
               content.setY(hi.getWidth());
               hi.getContentPane().animateUnlayoutAndWait(150, 255);
               hi.removeComponent(content);
               hi.getContentPane().animateLayout(150);
               Dialog.show("Info", "Supprimée!", "OK", null);
               //ip.dispose();
           });
           updat.addActionListener(e -> {
               
           });
           hi.add(content);
    }
}
