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
import com.entrepot.models.CommandeDApprovisionnement;
import com.entrepot.models.LigneCommandeDApprovisionnement;
import com.entrepot.models.LignePerte;
import com.entrepot.models.Perte;
import com.entrepot.services.ServiceCommandeDApprovisionnment;
import com.entrepot.services.ServiceLigneCommandeDApprovisionnement;
import com.entrepot.services.ServiceLignePerte;
import com.entrepot.services.ServicePerte;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author guiforodrigue
 */
public class FormFiltreCommandeApp extends Form{
    static int d = 0;
    Form FormListeComFiltrer = new Form("LISTE FILTRER", new BoxLayout(BoxLayout.Y_AXIS));
    public FormFiltreCommandeApp(){
        setTitle("FILTRER LES COMMANDES");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        
        FormListeComFiltrer.getToolbar().addCommandToLeftBar("Back", null, ev->{
            new FormFiltreCommandeApp().show();           
        });
        getToolbar().addCommandToLeftBar("Back", null, ev->{
            new FormMenuCommandeApprovisionnement(this).show();
           
        });
        Picker dateFromPicker = new Picker();
        dateFromPicker.setType(Display.PICKER_TYPE_DATE);
        dateFromPicker.setDate(new Date());
        Picker dateToPicker = new Picker();
        dateToPicker.setType(Display.PICKER_TYPE_DATE);
        dateToPicker.setDate(new Date());
        Label l1 = new Label("Les commandes du :");
        Label l2 = new Label("Au :");
        this.add(l1).add(dateFromPicker).add(l2).add(dateToPicker);
        Button b = new Button("Chercher");
        this.add(b);
        b.addActionListener(e -> {
            Dialog ip = new InfiniteProgress().showInifiniteBlocking();
            creerListePerteParDate(dateFromPicker.getDate(), dateToPicker.getDate());
            if (d > 0){
                FormListeComFiltrer.show();
            }
            
        });
        
    }
    public void creerListePerteParDate(Date d1, Date d2){
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
                    
                    if(compareDate(d1,  lp.get(i).getDateCreation(), d2)){
                        d++;
                        FormListeComFiltrer.add(new Label("----------------------------------------------------------"));
                        FormListeComFiltrer.add(new Label("  Commande du " + new java.text.SimpleDateFormat("MM-dd-yyyy").format(lp.get(i).getDateCreation())+"\n Fournisseur: "+lp.get(i).getFournisseur().getAdresseMail()));
                    
                        for(int j=0;j<lp.get(i).getLigneCommandeDApprovisionnements().size();j++){
                            createLignePerteEntry(FormListeComFiltrer,lp,j,i);
                        
                        }                   
                        this.add(new Label(slp.getAllLComs().size()+" commandes au total."));    
                    }                                      
                }
                if(d == 0){
                    Dialog.show("Info", "Aucune commande comprise entre le "+d1.toString()+" et le "+d2.toString()+" !", "OK", null);
                }
            }
            
    }
    public boolean compareDate(Date d1, Date d2, Date d3){
        
        long t2 = d2.getTime();
        long t1 = d1.getTime();
        long t3 = d3.getTime();
        return (t2 >= t1)&&(t2 <= t3);
          
    }
    private void createLignePerteEntry(Form hi, ArrayList<CommandeDApprovisionnement> lp, int j, int i) {
           ServiceLigneCommandeDApprovisionnement slp = new ServiceLigneCommandeDApprovisionnement();
           Label lgComField = new Label(j+1 + "- " +lp.get(i).getLigneCommandeDApprovisionnements().get(j).getQuantite()+" "+ lp.get(i).getLigneCommandeDApprovisionnements().get(j).getProduitAchat().getLibelle() + " à " + lp.get(i).getLigneCommandeDApprovisionnements().get(j).getTotal()+" DT");
           Button delete = new Button();
           
           FontImage.setMaterialIcon(delete, FontImage.MATERIAL_DELETE);
           Button updat = new Button();
           FontImage.setMaterialIcon(updat, FontImage.MATERIAL_UPDATE);
           Container content = BorderLayout.center(lgComField);
           
           content.add(BorderLayout.EAST, BoxLayout.encloseX(delete, updat));
           delete.addActionListener(e -> {
               slp.deleteCom(lp.get(i).getLigneCommandeDApprovisionnements().get(j));
               content.setY(hi.getWidth());
               hi.getContentPane().animateUnlayoutAndWait(150, 255);
               hi.removeComponent(content);
               hi.getContentPane().animateLayout(150);
               Dialog.show("Info", "Supprimée!", "OK", null);
           });
           updat.addActionListener(e -> {
               
           });
           hi.add(content);
    }
    
}
