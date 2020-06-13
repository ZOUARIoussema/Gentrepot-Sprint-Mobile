/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.ScaleImageLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import static com.entrepot.forms.SMSForm.ACCOUNT_SID;
import static com.entrepot.forms.SMSForm.AUTH_TOKEN;
import com.entrepot.models.BonEntree;
import com.entrepot.services.ServiceBonEntree;
import com.entrepot.services.ServiceProduitAchat;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Mohamed
 */
public class LbeSort extends Form {

    public static String codex;
    public static final String ACCOUNT_SID = "AC259bf45943274ddfdde68e37a8ad9a13";
    public static final String AUTH_TOKEN = "6f2390afc17688e13b1e8b5b7b111d70";
    String phonenumber = "+21625180502";

    Resources theme = UIManager.initFirstTheme("/themeLogistique");

    public LbeSort() {
        super("Les bons d'entree ", BoxLayout.y());
        this.getStyle().setBgImage(theme.getImage("kashmir.png"), focusScrolling);
         CreationMenu();

        ServiceProduitAchat sp = new ServiceProduitAchat();
        ServiceBonEntree sbe = new ServiceBonEntree();

        Map x = sp.getResponse("/apiBE/listBonEntree");
        ArrayList<BonEntree> listevents = sbe.getListbonEntreesorted(x);
        for (BonEntree e : listevents) {
            Container cont = new Container(new BoxLayout(BoxLayout.Y_AXIS));

            Label b = new Label("Commande numéro : " + e.getCap());
            Label d = new Label("Date : " + e.getDate());
            Label p = new Label("Date de prod : " + e.getDateProduction());
            Label exx = new Label("Date exp : " + e.getDateExpiration());

            Button supp = new Button("Delete");

            cont.add(b);
            cont.add(d);
            cont.add(p);
            cont.add(exx);
            cont.add(supp);
           // Button checkout = new Button("Reserver");
           // cont.add(checkout);

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
            supp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {

                    if (Dialog.show("Comfirmation", "Vouler vous supprimer ce bon ? ", "oui", "non")) {

                        sbe.deleteBonEntree(e);
                        new ListeBonsEntreeForm().showBack();
                    }

                }
            });

           
          //  Font fnt45 = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);
           // checkout.getUnselectedStyle().setFont(fnt45);
         /*   checkout.addActionListener((evt) -> {
                System.out.println("=========================");
                String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
                StringBuilder salt = new StringBuilder();
                Random rnd = new Random();
                while (salt.length() < 5) { // length of the random string.
                    int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                    salt.append(SALTCHARS.charAt(index));
                }
                String saltStr = salt.toString();
                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                com.twilio.rest.api.v2010.account.Message messages = com.twilio.rest.api.v2010.account.Message.creator(new PhoneNumber(phonenumber),
                        new PhoneNumber("+19286123819")," bonjour").create();
                //"un client veux de contacter : " + phonenumber + "," + saltStr
                codex = saltStr;
                System.out.println("======================");
                System.out.println("======================");
                System.out.println("======================");
                System.out.println(codex);
                Dialog.show("succes", "un sms a éte envoyer au mecanicien el te contactera tres bienteaux ", "ok", null);
                new ListeBonsEntreeForm().show();
            });*/

        }
        // this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
  //           new HomeAchat().showBack();
    //    });
        
        this.getToolbar().addCommandToOverflowMenu("unsort", null, (evt) -> {

            new ListeBonsEntreeForm().showBack();
        });

      /*  this.getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_CAMERA, e -> {
            Image screenshot = Image.createImage(getWidth(), getHeight());
            revalidate();
            setVisible(true);
            paintComponent(screenshot.getGraphics(), true);

            String imageFile = FileSystemStorage.getInstance().getAppHomePath() + "screenshott.png";
            System.out.println(imageFile);
            try (OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
                ImageIO.getImageIO().save(screenshot, os, ImageIO.FORMAT_PNG, 1);
            } catch (IOException err) {
                Log.e(err);
            }
        });*/
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
