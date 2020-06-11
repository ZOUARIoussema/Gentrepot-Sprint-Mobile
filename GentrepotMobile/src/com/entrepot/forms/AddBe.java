/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.entrepot.models.BonEntree;
import com.entrepot.models.CommandeApp;
import com.entrepot.models.Fournisseur;
import com.entrepot.services.ServiceProduitAchat;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.entrepot.models.Be;
import com.entrepot.services.ServiceBonEntree;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Mohamed
 */
public class AddBe extends Form{
    ComboBox<String> c;
    public AddBe(){
        c = new ComboBox();
        setName("Ajouter un bon d'entrée");
        setTitle("Ajouter un bon d'entrée");
        this.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_EVENT_AVAILABLE, s);
         FontImage icons = FontImage.createMaterial(FontImage.MATERIAL_EVENT_NOTE, s);
         FontImage icone = FontImage.createMaterial(FontImage.MATERIAL_IMAGE, s);
          /*  getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> {
            ListeBonEntree lb = new ListeBonEntree();
            lb.show();
        });
           getToolbar().addCommandToRightSideMenu("Liste des fournisseurs", icons, (e) -> {
            Listfounisseurs lf = new Listfounisseurs();
            lf.show();
            
        });
         getToolbar().addCommandToRightSideMenu("Liste des Bons d'entrées", icons, (e) -> {
            ListeBonEntree lf = new ListeBonEntree();
            lf.show();
            
        });
         getToolbar().addCommandToRightSideMenu("Liste des Bons de retour", icons, (e) -> {
            ListBonRetour lf = new ListBonRetour();
            lf.show();
            
        });*/
            
            Container photos = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            ServiceProduitAchat ws = new ServiceProduitAchat();
            Map x = ws.getResponse("/listComm");
            ServiceProduitAchat ds = new ServiceProduitAchat();
           ArrayList<CommandeApp> listevents = ds.getListcommande(x);
           for (CommandeApp e : listevents) {
               c.addItem(e.getNumeroC()+"");
           }
            Picker datePicker = new Picker();
            datePicker.setType(Display.PICKER_TYPE_DATE);
            Picker datePicker1 = new Picker();
            datePicker.setType(Display.PICKER_TYPE_DATE);
            Picker datePicker2 = new Picker();
            datePicker.setType(Display.PICKER_TYPE_DATE);
            Label l = new Label("Numero commande ap : ");
            Label l1 = new Label("Date : ");
            Label l2 = new Label("Date production: ");
            Label l3 = new Label("Date expiration: ");
            Button b = new Button("Ajouter");
            
           
            photos.add(l);
            photos.add(c);
            
            photos.add(l1);
            photos.add(datePicker);
            photos.add(l2);
            photos.add(datePicker1);
            photos.add(l3);
            photos.add(datePicker2);
            photos.add(b);
            add(photos);
            b.addActionListener(e->{
                
                Be be = new Be();
                 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
               
               Date da = datePicker.getDate();
               Date da1 = datePicker1.getDate();
               Date da2 = datePicker2.getDate();
               String st1 = df.format(da);
               String st2 = df.format(da1);
               String st3 = df.format(da2);
                        be.setCap(Integer.parseInt(c.getSelectedItem()));
                        be.setDate(st1);
                        be.setDateExpiration(st3);
       be.setDateExpiration(st2);
                ServiceBonEntree w = new ServiceBonEntree();
                w.addBonEntreee(be);
            /*    ListeBonEntree lb = new ListeBonEntree();
                lb.show();*/
                
                
       
            /**c.addPointerPressedListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent evt) {
                    WebService ws = new WebService();
                    String status = ws.getStatus("check/"+6+"/"+e.getId());
                    if(status.equals("subscribed")){
                        MatiereVideos.ml = e ;
                        System.out.println(e.getId());
                        MatiereVideos m = new MatiereVideos();
                        m.f.show();
                    }else{
                        
                    }

                };**/
            });
            
           
      
        show();
    }
    
}
