/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.entrepot.models.LignePerte;
import com.entrepot.models.Perte;
import com.entrepot.services.ServiceLignePerte;
import com.entrepot.services.ServicePerte;
import java.util.ArrayList;

/**
 *
 * @author guiforodrigue
 */
public class FormListePerte extends Form{
    
    
    public FormListePerte(){
        setTitle("LISTE DES PERTES");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        
        getToolbar().addCommandToLeftBar("Back", null, ev->{           
            new FormMenuPerte(this).show();
           
        });
        Dialog ip = new InfiniteProgress().showInifiniteBlocking();
        this.creerListePerte();
    }
    
    public void creerListePerte(){
        ServicePerte sp = new ServicePerte();
        ArrayList<Perte> lp = new ArrayList<>();
        ServiceLignePerte slp = new ServiceLignePerte();
        ArrayList<LignePerte> llp = new ArrayList<>();
        lp = sp.getAllPertes();           
            llp = slp.getAllLPertes();
            for(int i=0;i<lp.size();i++){
                for(int j=0;j<llp.size();j++){
                    if(lp.get(i).getId()==llp.get(j).getPerte().getId()){
                        lp.get(i).getLignePertes().add(llp.get(j));
                    }
                }

            }
            if(lp.get(0) == null){
                Dialog.show("Info", "Aucune perte!", "OK", null);    
            }
            else{
                for(int i=0;i<lp.size();i++){
                    this.add(new Label("----------------------------------------------------------"));
                    this.add(new Label(i+1 + "- Perte du " + new java.text.SimpleDateFormat("MM-dd-yyyy").format(lp.get(i).getDate())));
                    
                    for(int j=0;j<lp.get(i).getLignePertes().size();j++){
                        this.createLignePerteEntry(this,lp,j,i);
                        
                    }                    
                        
                }
            }
    }
    private void createLignePerteEntry(Form hi, ArrayList<Perte> lp, int j, int i) {
           ServiceLignePerte slp = new ServiceLignePerte();
           Label lgPertField = new Label(j+1 + "- " +lp.get(i).getLignePertes().get(j).getQuantite()+" "+ lp.get(i).getLignePertes().get(j).getProduitAchat().getLibelle() + " " + lp.get(i).getLignePertes().get(j).getRaisonPerte());
           Button delete = new Button();
           
           FontImage.setMaterialIcon(delete, FontImage.MATERIAL_DELETE);
           Button updat = new Button();
           FontImage.setMaterialIcon(updat, FontImage.MATERIAL_UPDATE);
           Container content = BorderLayout.center(lgPertField);
           
           content.add(BorderLayout.EAST, BoxLayout.encloseX(delete, updat));
           delete.addActionListener(e -> {
               //Dialog ip = new InfiniteProgress().showInifiniteBlocking();
               slp.deleteLPerte(lp.get(i).getLignePertes().get(j));
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
