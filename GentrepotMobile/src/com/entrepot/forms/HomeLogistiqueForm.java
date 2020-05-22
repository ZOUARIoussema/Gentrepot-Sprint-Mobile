/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import static com.codename1.io.Log.p;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import static com.codename1.ui.FontImage.MATERIAL_ARCHIVE;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

/**
 *
 * @author Rym
 */
public class HomeLogistiqueForm extends Form{
    Resources theme = UIManager.initFirstTheme("/themeLogistique");
     public HomeLogistiqueForm() { 
          super("Logistique Home", BoxLayout.y());
 Image icon = theme.getImage("resp7.png"); 
 Container topBar = BorderLayout.east(new Label(icon));
topBar.add(BorderLayout.SOUTH, new Label("Chef de parc...", "SidemenuTagline")); 
topBar.setUIID("SideCommand");
getToolbar().addComponentToSideMenu(topBar);
getToolbar().addMaterialCommandToSideMenu("Ajouter Aide Chauffeur", FontImage.MATERIAL_GROUP_ADD, e -> new AddAideChauForm().show()); 
getToolbar().addMaterialCommandToSideMenu("liste des Aide Chauffeurs", FontImage.MATERIAL_PLAYLIST_ADD_CHECK, e -> new AideChauffeurListForm().show());
getToolbar().addMaterialCommandToSideMenu("Ajouter Chauffeur", FontImage.MATERIAL_GROUP_ADD, e -> new AddChauffeurForm().show());
getToolbar().addMaterialCommandToSideMenu("liste des chauffeurs", FontImage.MATERIAL_PLAYLIST_ADD_CHECK, e ->new ChauffeurListForm().show() );
getToolbar().addMaterialCommandToSideMenu("Ajouter vehicule", FontImage.MATERIAL_ADD_CIRCLE, e -> new AddVehiculeForm().show());
getToolbar().addMaterialCommandToSideMenu("liste des vehicules", FontImage.MATERIAL_PLAYLIST_ADD_CHECK, e ->new VehiculeListForm().show());
getToolbar().addMaterialCommandToSideMenu("Ajouter Ordre de Mission", FontImage.MATERIAL_POST_ADD, e ->new AddOrdreForm().show());
getToolbar().addMaterialCommandToSideMenu("liste Ordre de Mission", FontImage.MATERIAL_PLAYLIST_ADD_CHECK, e ->new OrdreListForm().show());
getToolbar().addMaterialCommandToSideMenu("log-out", FontImage.MATERIAL_INFO, e ->new AuthentificationForm().show());

       this.getStyle().setBgImage(theme.getImage("log1.jpg"), focusScrolling);
     }
}
