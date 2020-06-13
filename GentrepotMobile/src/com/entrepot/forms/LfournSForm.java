/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.Fournisseur;
import com.entrepot.services.ServiceFournisseur;
import com.entrepot.services.ServiceProduitAchat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import com.codename1.messaging.Message;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.layouts.BorderLayout;

/**
 *
 * @author Mohamed
 */
public class LfournSForm extends Form {

    Resources theme = UIManager.initFirstTheme("/themeLogistique");

    public LfournSForm() {
        super("liste des fournisseur ", BoxLayout.y());
        this.getStyle().setBgImage(theme.getImage("kashmir.png"), focusScrolling);
         CreationMenu();

        ServiceProduitAchat sp = new ServiceProduitAchat();
        ServiceFournisseur sf = new ServiceFournisseur();
        Map x = sp.getResponse("/apiF/listF");
        ArrayList<Fournisseur> listefourniss = sf.getListFournisseursSorted(x);
        for (Fournisseur e : listefourniss) {
            Container cont = new Container(new BoxLayout(BoxLayout.Y_AXIS));

            Label b = new Label("Email : " + e.getAdresseMail());
            Label c = new Label("Raison sociale : " + e.getRaisonSociale());
            Label d = new Label("Matricule fiscale : " + e.getMatriculeFiscale());
            Label a = new Label("Addresse : " + e.getAdresse());
            Label tlf = new Label("Numéro  : " + e.getNumeroTelephone());
            Label cp = new Label("Code Postale : " + e.getCodePostale());

            Button edit = new Button("Edit");
            Button supp = new Button("Delete");

            Button mail = new Button("Envoyer mail");

            mail.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent evt) {

                    Message m = new Message("Body of message");

                    Display.getInstance().sendMessage(new String[]{e.getAdresseMail()}, "Subject of message", m);

                }
            });

            Container coB = new Container(BoxLayout.x());

            coB.addAll(edit, supp, mail);

            cont.add(c);
            cont.add(d);
            cont.add(a);
            cont.add(tlf);
            cont.add(b);
            cont.add(cp);
            cont.add(coB);

            try {
                ScaleImageLabel sep = new ScaleImageLabel(Image.createImage("/Separator.png"));
                cont.add(sep);
            } catch (IOException ex) {
            }
            add(cont);

            b.addPointerPressedListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {

                }
            });
            edit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    EditFournisseurForm.f = e;
                    EditFournisseurForm ef = new EditFournisseurForm();
                    ef.show();

                }
            });

            supp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {

                    if (Dialog.show("Comfirmation", "Vouler vous supprimer ce fournisseur ? ", "oui", "non")) {

                        sf.deleteFournisseur(e);
                        new ListeFournisseursForm().showBack();
                    }

                }
            });

        }
        // this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
  //           new HomeAchat().showBack();
    //    });
        
       
       this.getToolbar().addCommandToOverflowMenu("unsort", null, (evt) -> {

            new ListeFournisseursForm().showBack();
        });
    }
    public void CreationMenu() {

        Image icon = theme.getImage("resp7.png");
        Container topBar = BorderLayout.east(new Label(icon));
        topBar.add(BorderLayout.SOUTH, new Label("Responsable Achat...", "SidemenuTagline"));

        topBar.setUIID("SideCommand");
        getToolbar().addComponentToSideMenu(topBar);
        getToolbar().addMaterialCommandToSideMenu("Ajouter Produit", FontImage.MATERIAL_ADD_CIRCLE, e -> new AddProduitForm().show()); 
getToolbar().addMaterialCommandToSideMenu("liste des Produits", FontImage.MATERIAL_PLAYLIST_ADD_CHECK, e -> new ListProduitAchatForm().show());
getToolbar().addMaterialCommandToSideMenu("Ajouter Fournisseur", FontImage.MATERIAL_GROUP_ADD, e -> new AddFournisseurForm().show());
getToolbar().addMaterialCommandToSideMenu("liste des Fournisseurs", FontImage.MATERIAL_PLAYLIST_ADD_CHECK, e ->new ListeFournisseursForm().show() );
getToolbar().addMaterialCommandToSideMenu("Ajouter Bon D'entree", FontImage.MATERIAL_POST_ADD, e -> new AddBonEntreeForm().show());
getToolbar().addMaterialCommandToSideMenu("liste des Bons D'entree", FontImage.MATERIAL_PLAYLIST_ADD_CHECK, e ->new ListeBonsEntreeForm().show());
getToolbar().addMaterialCommandToSideMenu("Ajouter Bon De retour", FontImage.MATERIAL_POST_ADD, e ->new AddBonRetourForm().show());
getToolbar().addMaterialCommandToSideMenu("liste Bons De retour", FontImage.MATERIAL_PLAYLIST_ADD_CHECK, e ->new ListeBonsRetourForm().show());
getToolbar().addMaterialCommandToSideMenu("log-out", FontImage.MATERIAL_INFO, e ->new AuthentificationForm().show());


     

    }
}
