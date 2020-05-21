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
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import com.entrepot.models.InventaireCaisse;
import com.entrepot.services.ServiceInventaireCaisse;
import java.text.SimpleDateFormat;

/**
 *
 * @author oussema
 */
public class ListeInventaireCaisseForm extends Form {

    ServiceInventaireCaisse serviceInventaireCaisse = new ServiceInventaireCaisse();

    Resources theme = UIManager.initFirstTheme("/themeTresorerie");

    public ListeInventaireCaisseForm() {

        this.getStyle().setBgImage(theme.getImage("loginBack.png"), focusScrolling);

        CreationMenu();

        for (InventaireCaisse inventaireCaisse : serviceInventaireCaisse.getAllInventaire()) {

            Container cGlobal = new Container(BoxLayout.x());
            Container cDetaille = new Container(BoxLayout.y());

            cDetaille.add(new Label(" "));
            cDetaille.add(new Label("Date creation  : " + new SimpleDateFormat("MM-dd-yyyy").format(inventaireCaisse.getDateCreation())));
            cDetaille.add(new Label("Solde en espece: " + inventaireCaisse.getSoldeEspece()));
            cDetaille.add(new Label("Solde en cheque: " + inventaireCaisse.getSoldeCheque()));
            cDetaille.add(new Label("Solde total theorique: " + inventaireCaisse.getSoldeTheorique()));
            cDetaille.add(new Label("Solde calculer : " + inventaireCaisse.getSoldeCalculer()));
            cDetaille.add(new Label("Ecart : " + inventaireCaisse.getEcart()));

            Container cButton = new Container(BoxLayout.x());

            Button bModifier = new Button("Modifier");

            Button bSupprimer = new Button("Supprimer");

            bSupprimer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {

                    if (Dialog.show("Comfirmation", "Vouler vous supprimer ce inventaire ? ", "oui", "non")) {

                        serviceInventaireCaisse.deleteInventaireCaisse(inventaireCaisse);
                        new ListeInventaireCaisseForm().show();
                    }

                }
            });

            bModifier.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {

                    new AjouterInventaireCaisseForm(inventaireCaisse).show();

                }
            });

            cButton.addAll(bModifier, bSupprimer);

            cDetaille.add(cButton);

            cGlobal.add(new ImageViewer(theme.getImage("iconLettre.png").scaled(300, 300)));
            cGlobal.add(cDetaille);
            this.add(cGlobal);

            this.setTitle("Liste des inventaires caisse");

        }

    }

    public void CreationMenu() {

        Image icon = theme.getImage("iconeUser.png");
        Container topBar = BorderLayout.east(new Label(icon));
        topBar.add(BorderLayout.SOUTH, new Label("Agent de caisse", "SidemenuTagline"));

        topBar.setUIID("SideCommand");
        getToolbar().addComponentToSideMenu(topBar);

        this.getToolbar().addCommandToOverflowMenu("Modifier Profile", null, (evt) -> {

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

        this.getToolbar().addMaterialCommandToSideMenu("Liste Inventaire Caisse", FontImage.MATERIAL_PLAYLIST_ADD_CHECK, new ActionListener() {
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
        this.getToolbar().addMaterialCommandToSideMenu("Liste lettre de relance", FontImage.MATERIAL_PLAYLIST_ADD_CHECK, new ActionListener() {
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
