/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

/**
 *
 * @author guiforodrigue
 */
public class FormStockageHome extends Form{
    
     Resources theme = UIManager.initFirstTheme("/themeStockage");
    
    public FormStockageHome(){
        setTitle("Home");
        setLayout(new FlowLayout(CENTER, CENTER));
        
        Label lbWelcome = new Label("WELCOME TO Gentrepot Stockage");
        add(lbWelcome);
        
        getToolbar().addMaterialCommandToLeftSideMenu(" ", ' ', ev->{
            
        });
        getToolbar().addMaterialCommandToLeftSideMenu("Home", FontImage.MATERIAL_HOME, ev->{
            Dialog.show("Info", "Vous etes dans la form home", "OK", null);
        });
        
        getToolbar().addMaterialCommandToLeftSideMenu("Gestion commandes d'approvisionnement", FontImage.MATERIAL_LOCAL_SHIPPING, ev->{
            Dialog ip = new InfiniteProgress().showInifiniteBlocking();
            new FormMenuCommandeApprovisionnement(this).show();
        });
        
        getToolbar().addMaterialCommandToLeftSideMenu("Gestion emplacements", FontImage.MATERIAL_ROOM, ev->{
            Dialog ip = new InfiniteProgress().showInifiniteBlocking();
            new FormMenuEmplacement(this).show();
        });
        
        getToolbar().addMaterialCommandToLeftSideMenu("Gestion inventaires", FontImage.MATERIAL_ADD, ev->{
            Dialog ip = new InfiniteProgress().showInifiniteBlocking();
            new FormMenuInventaire(this).show();
        });
        
        getToolbar().addMaterialCommandToLeftSideMenu("Gestion pertes", FontImage.MATERIAL_TRENDING_DOWN, ev->{                        
            Dialog ip = new InfiniteProgress().showInifiniteBlocking();
               new FormMenuPerte(this).show();            
                       
        });
                
        getToolbar().addCommandToRightBar("Logout", null, ev->{
            new AuthentificationForm().show();
        });
     
    }
}
