/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import static com.codename1.ui.Component.CENTER;
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
import com.entrepot.models.BonRetour;
import com.entrepot.models.CommandeApp;
import com.entrepot.models.CommandeDApprovisionnement;
import com.entrepot.services.ServiceBonRetour;
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
public class AddBonRetourForm extends Form {

    Resources theme = UIManager.initFirstTheme("/themeLogistique");
    ComboBox<String> c;

    public AddBonRetourForm() {
        
        super("Ajouter bon de retour", BoxLayout.y());
        c = new ComboBox();

        this.setLayout(new FlowLayout(CENTER, CENTER));
        this.getStyle().setBgImage(theme.getImage("kashmir.png"), focusScrolling);
        this.setLayout(BoxLayout.y());

        

        Container cont = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        ServiceProduitAchat ws = new ServiceProduitAchat();
        Map x = ws.getResponse("/listComm");

        ArrayList<CommandeApp> listeCom = ws.getListcommande(x);
        for (CommandeApp e : listeCom) {
            c.addItem(e.getNumeroC() +"");
        }
        Picker datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        TextField t = new TextField();
        Label l = new Label("Numero commande ap : ");
        Label l1 = new Label("Date : ");
        t.setHint("Motif");
        Button b = new Button("Ajouter");

        cont.add(l);
        cont.add(c);
        cont.add(l1);
        cont.add(datePicker);
        cont.add(t);
        cont.add(b);

        add(cont);
        b.addActionListener(e -> {
            if (!t.getText().equals("")) {
                BonRetour be = new BonRetour();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

                Date da = datePicker.getDate();

                String st1 = df.format(da);
                CommandeApp ca = new CommandeApp();
                ca.setNumeroC(Integer.parseInt(c.getSelectedItem()));
                be.setCap(Integer.parseInt(c.getSelectedItem()));
                be.setDate(st1);
                be.setMotif(t.getText());

                ServiceBonRetour sc = new ServiceBonRetour();
                
                if (sc.addBonRetour(be)) {
                        Dialog.show("SUCCESS", "chauffeur sent", "OK", null);
                    }
                // ListBonRetour lb = new ListBonRetour();
                // lb.show();

            } else {
                Dialog.show("Erreur", "Vérifiez vos informations", "Ok", null);
            }
            
        });
        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {

                new HomeAchat().showBack();

            });
    }
}
