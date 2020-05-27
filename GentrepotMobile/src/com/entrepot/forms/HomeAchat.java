/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

/**
 *
 * @author Mohamed
 */
public class HomeAchat extends Form {
    
    Resources theme = UIManager.initFirstTheme("/themeLogistique");
     public HomeAchat() { 
          super("Logistique Home", BoxLayout.y());
 Image icon = theme.getImage("resp7.png"); 
 Container topBar = BorderLayout.east(new Label(icon));
topBar.add(BorderLayout.SOUTH, new Label("Responsable Achat...", "SidemenuTagline")); 
topBar.setUIID("SideCommand");
getToolbar().addComponentToSideMenu(topBar);
getToolbar().addMaterialCommandToSideMenu("Ajouter Produit", FontImage.MATERIAL_GROUP_ADD, e -> new AddProduitForm().show()); 
getToolbar().addMaterialCommandToSideMenu("liste des Produits", FontImage.MATERIAL_PLAYLIST_ADD_CHECK, e -> new AideChauffeurListForm().show());
getToolbar().addMaterialCommandToSideMenu("Ajouter Fournisseur", FontImage.MATERIAL_GROUP_ADD, e -> new AddFournisseurForm().show());
getToolbar().addMaterialCommandToSideMenu("liste des Fournisseurs", FontImage.MATERIAL_PLAYLIST_ADD_CHECK, e ->new ChauffeurListForm().show() );
getToolbar().addMaterialCommandToSideMenu("Ajouter Bon D'entree", FontImage.MATERIAL_ADD_CIRCLE, e -> new AddVehiculeForm().show());
getToolbar().addMaterialCommandToSideMenu("liste des Bons D'entree", FontImage.MATERIAL_PLAYLIST_ADD_CHECK, e ->new VehiculeListForm().show());
getToolbar().addMaterialCommandToSideMenu("Ajouter Bon De retour", FontImage.MATERIAL_POST_ADD, e ->new AddOrdreForm().show());
getToolbar().addMaterialCommandToSideMenu("liste Bons De retour", FontImage.MATERIAL_PLAYLIST_ADD_CHECK, e ->new OrdreListForm().show());
getToolbar().addMaterialCommandToSideMenu("log-out", FontImage.MATERIAL_INFO, e ->new AuthentificationForm().show());

       this.getStyle().setBgImage(theme.getImage("log1.jpg"), focusScrolling);
     }
    
}
