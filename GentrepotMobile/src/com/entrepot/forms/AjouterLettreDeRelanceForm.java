/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.FactureVente;
import com.entrepot.models.LettreDeRelance;
import com.entrepot.services.ServiceLettreDeRelance;
import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author oussema
 */
public class AjouterLettreDeRelanceForm extends Form {

    ServiceLettreDeRelance serviceLettreDeRelance = new ServiceLettreDeRelance();
    
    
     Resources theme = UIManager.initFirstTheme("/themeTresorerie");

    public AjouterLettreDeRelanceForm() {

        CreationMenu();

        for (FactureVente f : serviceLettreDeRelance.getAllFacture()) {

            Container cG = new Container(BoxLayout.x());
            Container cD = new Container(BoxLayout.y());
            
            Container cF = new Container(BoxLayout.x());
            
            
            Button b = new Button("Ajouter lettre de relance");
            
            
 
            Label espace = new Label(" ");
            Label numeroF = new Label("Numero facture: "+String.valueOf(f.getNumeroF()));
            
            cF.addAll(b);
            
            Label dateC = new Label("Date creation: "+new SimpleDateFormat("MM-dd-yyyy").format(f.getDateCreation()));
            Label total = new Label("Toatl: "+String.valueOf(f.getTotalTTC()));
            Label etat = new Label("Etat: "+String.valueOf(f.getEtat()));
            Label dateE = new Label("Date echaillance du paiement: "+new SimpleDateFormat("MM-dd-yyyy").format(f.getDateEchaillancePaiement()));
            

           cD.addAll(espace,numeroF,dateC,total,etat,dateE,cF);
           cG.add(new ImageViewer(theme.getImage("inv.png")));
           cG.add(cD);
           
           this.add(cG);
           
           
           
           numeroF.addPointerPressedListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    
                    System.out.println("numero facture selectionner "+f.getNumeroF());
                    
                    LettreDeRelance lettreDeRelance = new LettreDeRelance(new Date(), f);
                    
                    if(serviceLettreDeRelance.addLettre(lettreDeRelance)){
                        
                        new ListeLettreDeRelanceForm().show();
                    }
                   
                }
            });
           
           
           cG.setLeadComponent(numeroF);
           

        }

    }

    public void CreationMenu() {

        this.getToolbar().addMaterialCommandToSideMenu("Ajouter Inventaire Caisse", FontImage.MATERIAL_ADD, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                new AjouterInventaireCaisseForm().show();

            }
        });

        this.getToolbar().addMaterialCommandToSideMenu("Liste Inventaire Caisse", FontImage.MATERIAL_ARCHIVE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                new ListeInventaireCaisseForm().show();

            }
        });
        this.getToolbar().addMaterialCommandToSideMenu("Ajouter Lettre de relance", FontImage.MATERIAL_ADD, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                new AjouterLettreDeRelanceForm().show();

            }
        });
        this.getToolbar().addMaterialCommandToSideMenu("Liste lettre de relance", FontImage.MATERIAL_ARCHIVE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                new ListeLettreDeRelanceForm().show();

            }
        });

        this.getToolbar().addMaterialCommandToSideMenu("Deconnecter", FontImage.MATERIAL_EXIT_TO_APP, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                new AuthentificationForm().show();

            }
        });

    }

}
