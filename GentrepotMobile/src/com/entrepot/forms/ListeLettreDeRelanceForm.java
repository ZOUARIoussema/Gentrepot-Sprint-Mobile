/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.LettreDeRelance;
import com.entrepot.services.ServiceLettreDeRelance;
import java.text.SimpleDateFormat;

/**
 *
 * @author oussema
 */
public class ListeLettreDeRelanceForm extends Form {

    ServiceLettreDeRelance serviceLettreDeRelance = new ServiceLettreDeRelance();
    Resources theme = UIManager.initFirstTheme("/themeTresorerie");
    
     

    public ListeLettreDeRelanceForm() {

        CreationMenu();

        for (LettreDeRelance l : serviceLettreDeRelance.getAllLettre()) {

            
            Container cGlobal = new Container(BoxLayout.x());
            Container cDetaille = new Container(BoxLayout.y());

            Label espace = new Label(" ");
            Label labelNumeo = new Label("Numero: " + l.getId());
            Label labelDate = new Label("Date creation: " + new SimpleDateFormat("MM-dd-yyyy").format(l.getDate()));
            Label labelF = new Label("Facture: " + l.getFactureVente().getNumeroF());

            
            Button b = new Button("Suuprimer");
            
            
              b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    
                     
                    serviceLettreDeRelance.deleteLettre(l);
                    
                    new ListeLettreDeRelanceForm().showBack();
                    
                    
                  
                }
            });
            
            
            
            cDetaille.addAll(espace, labelNumeo, labelDate, labelF,b);

            cGlobal.add(new ImageViewer(theme.getImage("inv.png")));
            cGlobal.add(cDetaille);
          
            this.add(cGlobal);
            
            
            
           
            
            
            
          
            
            
          
              this.setTitle("Liste des lettres de relances");
            

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
