/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.ImageViewer;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.FactureVente;
import com.entrepot.models.LettreDeRelance;
import com.entrepot.services.ServiceLettreDeRelance;
import java.text.SimpleDateFormat;

/**
 *
 * @author oussema
 */
public class DetailleLettreDeRelanceForm extends Form {

    ServiceLettreDeRelance serviceLettreDeRelance = new ServiceLettreDeRelance();
    Resources theme = UIManager.initFirstTheme("/themeTresorerie");

    public DetailleLettreDeRelanceForm(LettreDeRelance l) {

        // this.getStyle().setBgImage(theme.getImage("loginBack.png"), focusScrolling);
        CreationMenu();

        FactureVente facture = serviceLettreDeRelance.findFactureById(l.getFactureVente().getNumeroF());

        Label la = new Label("Lettre:");
        la.setUIID("Label");
        Label numeroL = new Label("Numero de lettre: " + l.getId());
        Label dateL = new Label("Date creation lettre: " + new SimpleDateFormat("MM-dd-yyyy").format(l.getDate()));

        Label laF = new Label("Facture: ");

        Label numeroF = new Label("Numero de facture: " + facture.getNumeroF());
        Label etat = new Label("Etat   : " + facture.getEtat());
        Label total = new Label("Total   : " + facture.getTotalTTC());
        Label rest = new Label("Reste   : " + facture.getRestePaye());
        Label tp = new Label("Total   payé : " + facture.getTotalPaye());
        Label de = new Label("Date echaillance de paiment  : " + new SimpleDateFormat("MM-dd-yyyy").format(facture.getDateEchaillancePaiement()));

        Button b = new Button("Envoyer mail");

        Container c = new Container(BoxLayout.y());

        c.addAll(new ImageViewer(theme.getImage("iconLettre.png").scaled(400, 400)), la, numeroL, dateL, laF, numeroF, etat, total, de, rest, tp, b);

        this.setLayout(new FlowLayout(CENTER, CENTER));

        this.add(c);

        this.setTitle("Detail lettre de relance");

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                Message m = new Message("Facture impayé: ");

                // Display.getInstance().sendMessage(new String[]{""}, "Lettre de relance", m);
                new EnvoiLettreRelanceParMail(l).show();

            }
        });

    }

    public void CreationMenu() {
        
        

        this.getToolbar().setUIID("SideCommande");

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
        //this.getToolbar().getStyle().setBgColor(0xD3D3D3);

    }

}
