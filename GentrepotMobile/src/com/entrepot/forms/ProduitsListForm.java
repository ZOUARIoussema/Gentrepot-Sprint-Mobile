/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.entrepot.models.ProduitAchat;
import com.entrepot.services.ServiceProduitAchat;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class ProduitsListForm  extends Form{
    
    
    Form f;
    
    ServiceProduitAchat serviceProduitAchat =new ServiceProduitAchat();
    
    
    ArrayList<ProduitAchat> produit = serviceProduitAchat.getAllProduits();
    
    
    
    
    public ProduitsListForm(Resources theme){
        
        
        f = new Form ("Shop");
        
        this.setLayout(BoxLayout.y());
                
                for (ProduitAchat p : produit){
                    
                    add(AddItems(theme,p));
                
                    
                    show();
    }
    
   /*public ProduitsListForm(Form previous ) {
        
         
        
        super("Produits list", BoxLayout.y());
        

        this.add(new SpanLabel(new ServiceProduitAchat().getAllProduits().toString()));

          
        
       
        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
            previous.showBack();
        });
        
        
    */

   
   /* public Container addProduit (ProduitAchat produit){
        
        Container holder = new Container (BoxLayout.x());
        
        Container cntDetails = new Container(BoxLayout.y());
        
        Label titre = new Label(produit.getLibelle());
        
        cntDetails.add(titre);
        
    return holder;
}*/
    }

    
    public Container AddItems(Resources theme, ProduitAchat p) {
        
        
        Container item = new Container(BoxLayout.x());
        
        EncodedImage enco = EncodedImage.createFromImage(theme.getImage("load.png"), false);
        
        String url = "http://localhost/PROJET-SYMFONY-GENTREPOT/Gentrepot/web/uploads/images/"+p.getImage();
        Image im =URLImage.createToStorage(enco,p.getImage(), url); 
        
        ImageViewer imv = new ImageViewer(im);
         item.add(imv);
        
        
        
        
        Container data = new Container (BoxLayout.y());
        
        Label name = new Label ("Libelle : ");
        
        Font fnt = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);
        
        name.getUnselectedStyle().setFont(fnt);
        
        Label tfname = new Label(p.getLibelle());
        
        Container libelle = new Container (BoxLayout.x());
        
        libelle.add(name);
        libelle.add(tfname);
        
        data.add(libelle);
        
        
        
        
        
          Label prix = new Label ("Prix : ");
        
        
        prix.getUnselectedStyle().setFont(fnt);
        
        Label Lprix = new Label(Double.toString(p.getPrixVente()));
        
        Container prixC = new Container (BoxLayout.x());
        
        prixC.add(prix);
        prixC.add(Lprix);
        
        data.add(prixC);
        
        
        
        
        
       Label classe = new Label ("Classe: ");
        
        
        classe.getUnselectedStyle().setFont(fnt);
        
        Label Lclass = new Label(p.getClasse());
        
        Container classc = new Container (BoxLayout.x());
        
        classc.add(classe);
        classc.add(Lclass);
        
        data.add(classc);
        
        Button add = new Button("Ajouter au panier");
        
        
        data.add(add);
        
        item.add(data);
        return item;
        
        
    }

   
    }
