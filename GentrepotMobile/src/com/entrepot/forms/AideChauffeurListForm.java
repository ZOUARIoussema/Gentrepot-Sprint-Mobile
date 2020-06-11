/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.processing.Result;
import com.codename1.ui.AutoCompleteTextField;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.AideChauffeur;
import com.entrepot.services.ServiceAideChauffeur;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 *
 * @author Rym
 */
public class AideChauffeurListForm extends Form {

    Resources res = UIManager.initFirstTheme("/themeLogistique");
    ServiceAideChauffeur sa = new ServiceAideChauffeur();

    Resources theme = UIManager.initFirstTheme("/themeLogistique");
    Resources theme2 = UIManager.initFirstTheme("/themeTresorerie");

    public AideChauffeurListForm() {

        super("liste des Aide chauffeurs", BoxLayout.y());
        this.getStyle().setBgImage(theme.getImage("kashmir.png"), focusScrolling);

        Image img = res.getImage("kashmir.png");
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Container aff = new Container(BoxLayout.y());

        for (AideChauffeur c : sa.getAllAideChauffeur()) {
            Container cat = new Container(BoxLayout.y());
            Container carte = new Container(BoxLayout.x());

            //      
            Label cin = new Label(c.getCin());
            
            Label nom = new Label(c.getNom());
            Label prenom = new Label(c.getPrenom());
            Label adr = new Label(c.getAdresse());

            Button books = new Button();
            cat.addAll(cin, nom, prenom, adr);
            ImageViewer i = new ImageViewer(theme2.getImage("iconLettre.png").scaled(300, 300));

            //  ImageViewer i = new ImageViewer(img);
            carte.addAll(i, cat);
            aff.add(carte);

        }
        // this.setBgImage(img);
        add(LayeredLayout.encloseIn(
                aff
        ));

        /* this.add(new SpanLabel(new ServiceAideChauffeur().getAllTasks().toString()));*/
        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
            new HomeLogistiqueForm().show();
        });

    }
}
