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
import com.entrepot.models.BonEntree;
import com.entrepot.services.ServiceBonEntree;
import com.entrepot.services.ServiceProduitAchat;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Mohamed
 */
public class ListeBonsEntreeForm extends Form {

    Resources theme = UIManager.initFirstTheme("/themeLogistique");

    public ListeBonsEntreeForm() {
        super("Les bons d'entree ", BoxLayout.y());
        this.getStyle().setBgImage(theme.getImage("kashmir.png"), focusScrolling);

        ServiceProduitAchat sp = new ServiceProduitAchat();
        ServiceBonEntree sbe = new ServiceBonEntree();

        Map x = sp.getResponse("api/apiBE/listBonEntree");
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
        }
        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
            new HomeAchat().showBack();
        });
        
        this.getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_CAMERA, e->{
               Image screenshot = Image.createImage(getWidth(), getHeight());
        revalidate();
        setVisible(true);
        paintComponent(screenshot.getGraphics(), true);

        String imageFile = FileSystemStorage.getInstance().getAppHomePath() + "screenshott.png";
               System.out.println(imageFile);
        try(OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
            ImageIO.getImageIO().save(screenshot, os, ImageIO.FORMAT_PNG, 1);
        } catch(IOException err) {
            Log.e(err);
        }
           });
    }

    }
