/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.entrepot.models.BonEntree;
import com.entrepot.models.BonRetour;
import com.entrepot.models.CommandeApp;
import com.entrepot.services.ServiceBonEntree;
import com.entrepot.services.ServiceProduitAchat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Mohamed
 */
public class AddBonEntreeForm extends Form {

    Resources theme = UIManager.initFirstTheme("/themeLogistique");
    ComboBox<String> c;

    public AddBonEntreeForm() {

        super("Ajouter bon d'entree", BoxLayout.y());
        c = new ComboBox();

        this.setLayout(new FlowLayout(CENTER, CENTER));
        this.getStyle().setBgImage(theme.getImage("kashmir.png"), focusScrolling);
        this.setLayout(BoxLayout.y());

        Container cont = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        ServiceProduitAchat ws = new ServiceProduitAchat();
        Map x = ws.getResponse("api/listComm");

        ArrayList<CommandeApp> listeCom = ws.getListcommande(x);
        for (CommandeApp e : listeCom) {
            c.addItem(e.getNumeroC() + "");
        }
        Picker datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        Picker datePicker1 = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        Picker datePicker2 = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        Label l = new Label("Numero commande ap : ");
        Label l1 = new Label("Date : ");
        Label l2 = new Label("Date production: ");
        Label l3 = new Label("Date expiration: ");
        Button b = new Button("Ajouter");

        cont.add(l);
        cont.add(c);

        cont.add(l1);
        cont.add(datePicker);
        cont.add(l2);
        cont.add(datePicker1);
        cont.add(l3);
        cont.add(datePicker2);
        cont.add(b);
        add(cont);
        b.addActionListener(e -> {
            
            BonEntree be = new BonEntree();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            Date da = datePicker.getDate();
            Date da1 = datePicker1.getDate();
            Date da2 = datePicker2.getDate();
            String st1 = df.format(da);
            String st2 = df.format(da1);
            String st3 = df.format(da2);
            be.setCap(Integer.parseInt(c.getSelectedItem()));
            be.setDate(st1);
            be.setDateExpiration(st3);
            be.setDateExpiration(st2);
            ServiceBonEntree sc = new ServiceBonEntree();
            
            if (sc.addBonEntree(be)) {
                        Dialog.show("SUCCESS", "chauffeur sent", "OK", null);
                    }
            // ListBonRetour lb = new ListBonRetour();
            // lb.show();
            

            
        });
        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {

                new HomeAchat().showBack();

            });
    }

}
