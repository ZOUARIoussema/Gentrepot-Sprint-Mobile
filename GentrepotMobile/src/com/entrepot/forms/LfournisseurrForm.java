/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.contacts.Contact;
import com.codename1.io.Log;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.Fournisseur;
import com.entrepot.services.ServiceFournisseur;
import com.entrepot.services.ServiceProduitAchat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Mohamed
 */
public class LfournisseurrForm extends Form {

    Resources theme = UIManager.initFirstTheme("/themeLogistique");

    public LfournisseurrForm() {
        this.getStyle().setBgImage(theme.getImage("kashmir.png"), focusScrolling);

        Image duke = null;
        try {
            duke = Image.createImage("/like.png");
        } catch (IOException err) {
            Log.e(err);
        }
        
        
        int fiveMM = Display.getInstance().convertToPixels(5);
        final Image finalDuke = duke.scaledWidth(fiveMM);
        Toolbar.setGlobalToolbar(true);
        new Form("Search", BoxLayout.y());
        this.add(new InfiniteProgress());
        Display.getInstance().scheduleBackgroundTask(() -> {
            // this will take a while...
            //Contact[] cnts = Display.getInstance().getAllContacts(true, true, true, true, false, false);
            ServiceProduitAchat sp = new ServiceProduitAchat();
        ServiceFournisseur sf = new ServiceFournisseur();
        Map x = sp.getResponse("/apiF/listF");
        ArrayList<Fournisseur> listefourniss = sf.getListFournisseurs(x);
            Display.getInstance().callSerially(() -> {
                this.removeAll();
                for (Fournisseur c : listefourniss) {
                    MultiButton m = new MultiButton();
                    m.setTextLine1(c.getRaisonSociale());
                    m.setTextLine2(c.getMatriculeFiscale());
                    m.setTextLine3(c.getAdresseMail());
                   // m.setTextLine4("+c.getNumeroTelephone()+");
                   // m.setTextLine5("");
                   // m.setTextLine6(c.getMatriculeFiscale());
                    
                    this.add(m);
                }
                this.revalidate();
            });
        });

        this.getToolbar().addSearchCommand(e -> {
            String text = (String) e.getSource();
            if (text == null || text.length() == 0) {
                // clear search
                for (Component cmp : this.getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
                this.getContentPane().animateLayout(150);
            } else {
                text = text.toLowerCase();
                for (Component cmp : this.getContentPane()) {
                    MultiButton mb = (MultiButton) cmp;
                    String line1 = mb.getTextLine1();
                    String line2 = mb.getTextLine2();
                    boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1
                            || line2 != null && line2.toLowerCase().indexOf(text) > -1;
                    mb.setHidden(!show);
                    mb.setVisible(show);
                }
                this.getContentPane().animateLayout(150);
            }
        }, 4);

        this.show();
    }

    

}
