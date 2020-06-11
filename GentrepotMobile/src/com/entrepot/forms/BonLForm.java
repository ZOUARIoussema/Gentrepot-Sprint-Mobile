/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.BonLivraison;
import com.entrepot.models.CommandeVente;
import com.entrepot.models.ProduitAchat;
import com.entrepot.models.User;
import com.entrepot.services.MailService;
import com.entrepot.services.ServiceBonLivraison;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class BonLForm extends Form{
    
    Form f;  
    
    
    CommandeVente v;
      

    ServiceBonLivraison serviceBonLivraison = new ServiceBonLivraison();
    
    
    
    public BonLForm(Form previous , CommandeVente v){
        
          getToolbar().addCommandToLeftBar("Back",null,ev ->{
            previous.showBack();
        });
              this.setTitle("Bon de livraison");

     this.setLayout(BoxLayout.yCenter());
          f = new Form ("Bon de livraison");
        Resources theme = UIManager.initFirstTheme("/themeVente");
        
      
        add(AddItems(v));
        
    }
        public Container AddItems(CommandeVente v) {

       Container data = new Container (BoxLayout.yCenter());
      
        TextField nom = new TextField( "","nom");
       
         Label etat = new Label (v.getEtat())   ;

         TextField prenom = new TextField("","Prenom");
         
         TextField adresse = new TextField("","Adresse");
         
         Button btn = new Button("Livrer");
         
         
            
         
         data.add(nom);
         data.add(prenom);
         data.add(adresse);
         data.add(btn);
         data.add(etat);
          btn.addPointerReleasedListener(ev-> {
        Dialog.setDefaultBlurBackgroundRadius(15);
        
        if(Dialog.show("Confirmation", "Livrer cette commande ?","oui","non")){
            BonLivraison b = new BonLivraison(adresse.getText(), "en cours", nom.getText(), prenom.getText(), v);
               System.out.println(v.getId());
               System.out.println("aaa");

              System.out.println(nom.getText());
            b.setNom((nom.getText()));
             
             b.setAdresseLivraison(adresse.getText());
             b.setPrenom(prenom.getText());
         
         
       serviceBonLivraison.ajouterBon(b); 
          
              MailService.EnvoyerMail(  AuthentificationForm.user.getEmail(), "livraison", "votre livraison en cours");
               
                System.out.println("Insertion OK ! ");
                                Dialog.show("Success", "Livraison en cours", "OK" , null);

          
        }
        
         
        });
         
return data;
        }
}
