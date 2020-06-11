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
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.entrepot.models.InventaireStock;
import com.entrepot.services.ServiceInventaireStock;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author guiforodrigue
 */
public class FormFiltreInventaire extends Form{
    static int d = 0;
    Form FormListeInvFiltrer = new Form("LISTE FILTRER", new BoxLayout(BoxLayout.Y_AXIS));
    public FormFiltreInventaire(){
        setTitle("FILTRER LES INVENTAIRES");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        
        FormListeInvFiltrer.getToolbar().addCommandToLeftBar("Back", null, ev->{
            new FormFiltreInventaire().show();           
        });
        getToolbar().addCommandToLeftBar("Back", null, ev->{
            new FormMenuInventaire(this).show();
           
        });
        Picker dateFromPicker = new Picker();
        dateFromPicker.setType(Display.PICKER_TYPE_DATE);
        dateFromPicker.setDate(new Date());
        Picker dateToPicker = new Picker();
        dateToPicker.setType(Display.PICKER_TYPE_DATE);
        dateToPicker.setDate(new Date());
        Label l1 = new Label("Les inventaires du :");
        Label l2 = new Label("Au :");
        this.add(l1).add(dateFromPicker).add(l2).add(dateToPicker);
        Button b = new Button("Chercher");
        this.add(b);
        b.addActionListener(e -> {
            Dialog ip = new InfiniteProgress().showInifiniteBlocking();
            creerListeInvParDate(dateFromPicker.getDate(), dateToPicker.getDate());
            if (d > 0){
                FormListeInvFiltrer.show();
            }
            
        });
        
    }
    public void creerListeInvParDate(Date d1, Date d2){
        ServiceInventaireStock sp = new ServiceInventaireStock();
        ArrayList<InventaireStock> lp = new ArrayList<>();
  
        lp = sp.getAllInvs();           

        if(lp.get(0) == null){
            Dialog.show("Info", "Pas d'inventaire!", "OK", null);    
        }
        else{
            for(int i=0;i<lp.size();i++){

                if(compareDate(d1,  lp.get(i).getDateCreation(), d2)){
                    d++;
                    FormListeInvFiltrer.add(new Label("----------------------------------------------------------"));
                    FormListeInvFiltrer.add(new Label("  Inventaire du " + new java.text.SimpleDateFormat("MM-dd-yyyy").format(lp.get(i).getDateCreation())));
                    createLigneInventaireEntry(FormListeInvFiltrer,lp,i);
         
                        

                                     
                    this.add(new Label(sp.getAllInvs().size()+" inventaires au total."));    
                }                                      
            }
            if(d == 0){
                Dialog.show("Info", "Aucune perte comprise entre le "+d1.toString()+" et le "+d2.toString()+" !", "OK", null);
            }
        }

    }
    public boolean compareDate(Date d1, Date d2, Date d3){
        
        long t2 = d2.getTime();
        long t1 = d1.getTime();
        long t3 = d3.getTime();
        return (t2 >= t1)&&(t2 <= t3);
          
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
