/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;

/**
 *
 * @author guiforodrigue
 */
public class FormMenuCommandeApprovisionnement extends Form{
    public FormMenuCommandeApprovisionnement(Form previous){
        setTitle("GESTION COMMANDES D'APPROVISIONNEMENT");
        setLayout(new FlowLayout(CENTER, CENTER));
        
        Form ajout = new Form("EFFECTUEZ VOTRE COMMANDE D'APPROVISIONNEMENT", BoxLayout.y());
        Form liste = new Form("LISTE DES COMMANDES D'APPROVISIONNEMENT", BoxLayout.y());
        Form filtre = new Form("FILTREZ LES COMMANDES D'APPROVISIONNEMENT", BoxLayout.y());
        Button btnAjout = new Button("EFFECTUEZ VOTRE COMMANDE");
        Button btnLister = new Button("LISTEZ LES COMMANDES");
        Button btnFiltre = new Button("FILTREZ LES COMMANDES");
        
        this.add(btnAjout);
        this.add(btnLister);
        this.add(btnFiltre);
        btnAjout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               ajout.show();
               
            }
        });
        getToolbar().addCommandToLeftBar("Back", null, ev->{
            previous.showBack();
        });
        
        btnLister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               liste.show();
               
            }
        });
        btnFiltre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               filtre.show();
               
            }
        });
    }
    
    /*btnReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               home.show();
               
            }
        });*/
        
}
