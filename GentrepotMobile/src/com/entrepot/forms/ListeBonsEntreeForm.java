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
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import static com.entrepot.forms.ResevasionMecForm.ACCOUNT_SID;
import static com.entrepot.forms.ResevasionMecForm.AUTH_TOKEN;
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
public class ListeBonsEntreeForm extends Form {

    public static String codex;
    public static final String ACCOUNT_SID = "AC17206e77ae551637b207dba93abedf87";
    public static final String AUTH_TOKEN = "42d2f21e26649f216ec6848a68cba79c";
    String phonenumber = "+21625180502";

    Resources theme = UIManager.initFirstTheme("/themeLogistique");

    public ListeBonsEntreeForm() {
        super("Les bons d'entree ", BoxLayout.y());
        this.getStyle().setBgImage(theme.getImage("kashmir.png"), focusScrolling);

        ServiceProduitAchat sp = new ServiceProduitAchat();
        ServiceBonEntree sbe = new ServiceBonEntree();

        Map x = sp.getResponse("/apiBE/listBonEntree");
        ArrayList<BonEntree> listevents = sbe.getListbonEntree(x);
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
            Button checkout = new Button("Reserver");
            cont.add(checkout);

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

                    if (Dialog.show("Comfirmation", "Vouler vous supprimer ce inventaire ? ", "oui", "non")) {

                        sbe.deleteBonEntree(e);
                        new ListeBonsEntreeForm().showBack();
                    }

                }
            });

           
          //  Font fnt45 = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);
           // checkout.getUnselectedStyle().setFont(fnt45);
            checkout.addActionListener((evt) -> {
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
                        new PhoneNumber("+19286123819"), "un client veux de contacter : " + phonenumber + "," + saltStr).create();
                codex = saltStr;
                System.out.println("======================");
                System.out.println("======================");
                System.out.println("======================");
                System.out.println(codex);
                Dialog.show("succes", "un sms a éte envoyer au mecanicien el te contactera tres bienteaux ", "ok", null);
                new ListeBonsEntreeForm().show();
            });

        }
        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
            new HomeAchat().showBack();
        });

        this.getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_CAMERA, e -> {
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
        });
    }

}
