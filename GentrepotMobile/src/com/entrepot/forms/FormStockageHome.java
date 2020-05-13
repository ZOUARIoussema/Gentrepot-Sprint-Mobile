/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;

/**
 *
 * @author guiforodrigue
 */
public class FormStockageHome extends Form{
    public FormStockageHome(){
        setTitle("Home");
        setLayout(new FlowLayout(CENTER, CENTER));
        
        Label lbWelcome = new Label("WELCOME TO Gentrepot Stockage");
        add(lbWelcome);
        
        getToolbar().addCommandToLeftSideMenu("Home", null, ev->{
            Dialog.show("Info", "Vous etes dans la form home", "OK", null);
        });
        
        getToolbar().addCommandToLeftSideMenu("Gestion commandes d'approvisionnement", null, ev->{
            new FormMenuCommandeApprovisionnement(this).show();
        });
        
        getToolbar().addCommandToLeftSideMenu("Gestion emplacements", null, ev->{
            new FormMenuEmplacement(this).show();
        });
        
        getToolbar().addCommandToLeftSideMenu("Gestion inventaires", null, ev->{
            new FormMenuInventaire(this).show();
        });
        
        getToolbar().addCommandToLeftSideMenu("Gestion pertes", null, ev->{
            new FormMenuPerte(this).show();
        });
                
        getToolbar().addCommandToRightBar("Logout", null, ev->{
            //previous.showBack();
        });
    }
}
