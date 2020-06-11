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
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.entrepot.models.LignePerte;
import com.entrepot.models.Perte;
import com.entrepot.services.ServiceLignePerte;
import com.entrepot.services.ServicePerte;
import java.util.ArrayList;
import java.util.Date;


/**
 *
 * @author guiforodrigue
 */
public class FormFiltrePerte extends Form{
    static int d = 0;
    Form FormListePerteFiltrer = new Form("LISTE FILTRER", new BoxLayout(BoxLayout.Y_AXIS));
    public FormFiltrePerte(){
        setTitle("FILTRER LES PERTES");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        
        FormListePerteFiltrer.getToolbar().addCommandToLeftBar("Back", null, ev->{
            new FormFiltrePerte().show();           
        });
        getToolbar().addCommandToLeftBar("Back", null, ev->{
            new FormMenuPerte(this).show();
           
        });
        Picker dateFromPicker = new Picker();
        dateFromPicker.setType(Display.PICKER_TYPE_DATE);
        dateFromPicker.setDate(new Date());
        Picker dateToPicker = new Picker();
        dateToPicker.setType(Display.PICKER_TYPE_DATE);
        dateToPicker.setDate(new Date());
        Label l1 = new Label("Les pertes du :");
        Label l2 = new Label("Au :");
        this.add(l1).add(dateFromPicker).add(l2).add(dateToPicker);
        Button b = new Button("Chercher");
        this.add(b);
        b.addActionListener(e -> {
            Dialog ip = new InfiniteProgress().showInifiniteBlocking();
            creerListePerteParDate(dateFromPicker.getDate(), dateToPicker.getDate());
            if (d > 0){
                FormListePerteFiltrer.show();
            }
            
        });
        
    }
    public void creerListePerteParDate(Date d1, Date d2){
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
                    
                    if(compareDate(d1,  lp.get(i).getDate(), d2)){
                        d++;
                        FormListePerteFiltrer.add(new Label("----------------------------------------------------------"));
                        FormListePerteFiltrer.add(new Label("  Perte du " + new java.text.SimpleDateFormat("MM-dd-yyyy").format(lp.get(i).getDate())));
                    
                        for(int j=0;j<lp.get(i).getLignePertes().size();j++){
                            createLignePerteEntry(FormListePerteFiltrer,lp,j,i);
                        
                        }                   
                        this.add(new Label(slp.getAllLPertes().size()+" Pertes au total."));    
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
               slp.deleteLPerte(lp.get(i).getLignePertes().get(j));
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
