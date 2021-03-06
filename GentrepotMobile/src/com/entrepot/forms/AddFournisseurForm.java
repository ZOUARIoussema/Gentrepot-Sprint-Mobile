/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.Fournisseur;
import com.entrepot.services.ServiceFournisseur;

/**
 *
 * @author Mohamed
 */
public class AddFournisseurForm extends Form {
    Resources theme = UIManager.initFirstTheme("/themeLogistique");
    
    
   public AddFournisseurForm() {
        super("Ajouter Fournisseur", BoxLayout.y());
        
        this.getStyle().setBgImage(theme.getImage("kashmir.png"), focusScrolling);
        CreationMenu();
        this.setLayout(new FlowLayout(CENTER, CENTER));
        TextField tfRais = new TextField(null, "Raison Sociale");
        TextField tfNum = new TextField(null, "Numero De Telephone");
        TextField tfAd= new TextField(null, "Adresse");
         TextField tfAdM= new TextField(null, "Adresse Mail");
         TextField tfMat= new TextField(null, "matricule Fiscale");
         TextField tfCode= new TextField(null, "code Postale");
       
        Button btn = new Button("Ajouter le fournisseur");
        
        ServiceFournisseur sc = new ServiceFournisseur();
        
         
        

        btn.addActionListener((evt) -> {
            if ((tfRais.getText().length() == 0) || (tfNum.getText().length() == 0) || (tfAd.getText().length() == 0) || (tfAdM.getText().length() == 0) || (tfMat.getText().length() == 0) || (tfCode.getText().length() == 0)) {
                Dialog.show("Alert", "Veuillez remplir tous les champs !", "OK", null);
            } 
           //else if (sc.validerEmail(tfAdM.getText())) {
          // Dialog.show("Alerte", "Verifier l'adresse mail", "OK", null);
           
           //}
            
            else {
                try {
                    if (verifCode(tfCode.getText()) && verifNumerotel(tfNum.getText())) {
                   Fournisseur ch = new Fournisseur (tfRais.getText(), Integer.parseInt(tfNum.getText()), tfAd.getText(), tfAdM.getText(), tfMat.getText(),Integer.parseInt(tfCode.getText()) );
                    if (sc.addFournisseur(ch)) {
                        Dialog.show("SUCCESS", "Fournisseur ajouté !", "OK", null);
                        ListeFournisseursForm ls = new ListeFournisseursForm();
                        ls.showBack();
                    }  }
                    
                } catch (NumberFormatException e) {
                    Dialog.show("ERROR", "Vérifiez vos informations", "OK", null);
                }

            }
        });

        this.addAll(tfRais, tfNum,tfAd,tfAdM ,tfMat,tfCode, btn);

      //  this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
         
       //     new HomeAchat().showBack();
            
      //  });
        
        
    }
    public boolean verifNumerotel(String a) {

        if (a.length() != 8) {

           Dialog.show("Alerte", "Numero telephone comporte 8 chiffres", "OK", null);

            return false;
        }

        try {

            Integer numeroTel = Integer.parseInt(a);

        } catch (Exception ex) {

            Dialog.show("Alerte", "numero telephone incorrecte", "OK", null);
            
            return false;

        }

        return true;
    }
    
    public boolean verifCode(String a) {
        
         if (a.length() != 4) {

            Dialog.show("Alerte", "Code postal comporte 4 chiffres", "OK", null);

            return false;
        }
        

        try {

            Integer codePostale = Integer.parseInt(a);

        } catch (Exception ex) {

Dialog.show("Alerte", "code postal incorrecte", "OK", null);
            return false;

        }

        return true;
    }
    public void CreationMenu() {

        Image icon = theme.getImage("resp7.png");
        Container topBar = BorderLayout.east(new Label(icon));
        topBar.add(BorderLayout.SOUTH, new Label("Responsable Achat...", "SidemenuTagline"));

        topBar.setUIID("SideCommand");
        getToolbar().addComponentToSideMenu(topBar);
        getToolbar().addMaterialCommandToSideMenu("Ajouter Produit", FontImage.MATERIAL_ADD_CIRCLE, e -> new AddProduitForm().show()); 
getToolbar().addMaterialCommandToSideMenu("liste des Produits", FontImage.MATERIAL_PLAYLIST_ADD_CHECK, e -> new ListProduitAchatForm().show());
getToolbar().addMaterialCommandToSideMenu("Ajouter Fournisseur", FontImage.MATERIAL_GROUP_ADD, e -> new AddFournisseurForm().show());
getToolbar().addMaterialCommandToSideMenu("liste des Fournisseurs", FontImage.MATERIAL_PLAYLIST_ADD_CHECK, e ->new ListeFournisseursForm().show() );
getToolbar().addMaterialCommandToSideMenu("Ajouter Bon D'entree", FontImage.MATERIAL_POST_ADD, e -> new AddBonEntreeForm().show());
getToolbar().addMaterialCommandToSideMenu("liste des Bons D'entree", FontImage.MATERIAL_PLAYLIST_ADD_CHECK, e ->new ListeBonsEntreeForm().show());
getToolbar().addMaterialCommandToSideMenu("Ajouter Bon De retour", FontImage.MATERIAL_POST_ADD, e ->new AddBonRetourForm().show());
getToolbar().addMaterialCommandToSideMenu("liste Bons De retour", FontImage.MATERIAL_PLAYLIST_ADD_CHECK, e ->new ListeBonsRetourForm().show());
getToolbar().addMaterialCommandToSideMenu("log-out", FontImage.MATERIAL_INFO, e ->new AuthentificationForm().show());


     

    }
}