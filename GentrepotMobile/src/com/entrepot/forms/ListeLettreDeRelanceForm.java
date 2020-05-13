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
        
       //  this.getStyle().setBgImage(theme.getImage("backroundlist.jpg"), focusScrolling);

        CreationMenu();

        for (LettreDeRelance l : serviceLettreDeRelance.getAllLettre()) {

            
            Container cGlobal = new Container(BoxLayout.x());
            Container cDetaille = new Container(BoxLayout.y());

            Label espace = new Label(" ");
            Label labelNumeo = new Label("Numero: " + l.getId());
            
           //  labelNumeo.getAllStyles().setFgColor(0xFF0000);
            Label labelDate = new Label("Date creation: " + new SimpleDateFormat("MM-dd-yyyy").format(l.getDate()));
            Label labelF = new Label("Facture: " + l.getFactureVente().getNumeroF());

            
            Button b = new Button("Supprimer");
            
              Button bD = new Button("Detail");
              
              Container cb = new Container(BoxLayout.x());
            
            cb.addAll(bD,b);
            
              b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    
                       if(  Dialog.show("Comfirmation", "Vouler vous supprimer cette lettre ? ", "oui", "non")){
                     
                    serviceLettreDeRelance.deleteLettre(l);
                    
                    new ListeLettreDeRelanceForm().showBack();
                       }
                    
                  
                }
            });
              
              bD.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    
                    new DetailleLettreDeRelanceForm(l).show();
               
                }
            });
            
            
            
            cDetaille.addAll(espace, labelNumeo, labelDate, labelF,cb);

            cGlobal.add(new ImageViewer(theme.getImage("iconLettre.png").scaled(300, 300)));
            cGlobal.add(cDetaille);
          
            this.add(cGlobal);
            
             
          
              this.setTitle("Liste des lettres de relances");
            

        }

    }

    public void CreationMenu() {
        
         this.getToolbar().addCommandToOverflowMenu("Modifier Profile",null,(evt) -> {
             
             new ModifierProfilForm().show();
       });
         
           this.getToolbar().addMaterialCommandToSideMenu("Acceuille", FontImage.MATERIAL_HOME, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                new MenueAgentCaisseForm().show();

            }
        });

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
