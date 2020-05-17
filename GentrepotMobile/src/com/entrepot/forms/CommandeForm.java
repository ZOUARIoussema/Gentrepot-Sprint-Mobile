/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextComponent;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.CommandeVente;
import com.entrepot.models.ProduitAchat;
import com.entrepot.services.ServiceCommandeVente;
import com.entrepot.services.ServiceLigneCommande;
import com.entrepot.services.ServiceProduitAchat;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class CommandeForm extends Form{
    
        
    Form f;
    
    ServiceCommandeVente serviceCommandeVente =new ServiceCommandeVente();
    ServiceLigneCommande serviceLigneCommande;
    ArrayList<CommandeVente> commande = serviceCommandeVente.getAllCommandes();
    
    private Resources theme ;
    
    
    public CommandeForm(Form previous){
        
                theme = UIManager.initFirstTheme("/themeVente");

        setTitle("Commandes");
        
        f = new Form ("Shop");
        serviceLigneCommande  = new ServiceLigneCommande();
        
        this.setLayout(BoxLayout.y());
                
                for (CommandeVente p : commande){
                    
                    add(AddItems(p));
                
                    
                    show();
    }
                
                
                //add(AddItems(new ProduitAchat("2121", "aaaa", 54, 21)));
     getToolbar().addCommandToLeftBar("Back",null,ev ->{
            previous.showBack();
        });
        
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

    
    public Container AddItems( CommandeVente p) {
        
        Resources theme = UIManager.initFirstTheme("/theme");
        Container item = new Container(BoxLayout.x());
        
       
        
        
        
        
        Container data = new Container (BoxLayout.y());
        
        Label name = new Label ("etat : ");
        
        Font fnt = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);
        
        name.getUnselectedStyle().setFont(fnt);
        
        Label tfname = new Label(p.getEtat());
        
        Container libelle = new Container (BoxLayout.x());
        
        libelle.add(name);
        libelle.add(tfname);
        
        data.add(libelle);
        
        
        
        
        
          Label prix = new Label ("Taux de remise : ");
        
        
        prix.getUnselectedStyle().setFont(fnt);
        
        Label Lprix = new Label(Double.toString(p.getTauxRemise()));
        
        Container prixC = new Container (BoxLayout.x());
        
        prixC.add(prix);
        prixC.add(Lprix);
        
        data.add(prixC);
        
        
        
        
        
       Label classe = new Label ("Total: ");
        
        
        classe.getUnselectedStyle().setFont(fnt);
        
        Label Lclass = new Label(Double.toString(p.getTotalC()));
        
        Container classc = new Container (BoxLayout.x());
        
        classc.add(classe);
        classc.add(Lclass);
        
        data.add(classc);
        
        Button btnadd = new Button("Livrer");
        

        btnadd.addPointerReleasedListener(ev-> {
        Dialog.setDefaultBlurBackgroundRadius(15);
        
        if(Dialog.show("Confirmation", "Livrer" ,"oui","non")){
             
            
               
               
                System.out.println("Livraison en cours  ! ");
                                Dialog.show("Success", "Livraison en cours  !", "OK" , null);

          
        }
        
         
        });
        data.add(btnadd);
           item.add(data);
        return item;
        
        
    }

   
    }

    

