/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
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

/**
 *
 * @author Mohamed
 */
public class ListeFournisseursForm extends Form {

    Resources theme = UIManager.initFirstTheme("/themeLogistique");

    public ListeFournisseursForm() {
        super("liste des fournisseur ", BoxLayout.y());
        this.getStyle().setBgImage(theme.getImage("kashmir.png"), focusScrolling);

        ServiceProduitAchat sp = new ServiceProduitAchat();
        ServiceFournisseur ds = new ServiceFournisseur();
        Map x = sp.getResponse("api/apiF/listF");
        ArrayList<Fournisseur> listefourniss = ds.getListFournisseurs(x);
        for (Fournisseur e : listefourniss) {
            Container cont = new Container(new BoxLayout(BoxLayout.Y_AXIS));

            Label b = new Label("Email : " + e.getAdresseMail());
            Label c = new Label("Raison sociale : " + e.getRaisonSociale());
            Label d = new Label("Matricule fiscale : " + e.getMatriculeFiscale());
            Label a = new Label("Addresse : " + e.getAdresse());
            Label tlf = new Label("Numéro  : " + e.getNumeroTelephone());
            Label cp = new Label("Code Postale : " + e.getCodePostale());

            Button voir = new Button("Edit");
            Button voir1 = new Button("Delete");

            cont.add(c);
            cont.add(d);
            cont.add(a);
            cont.add(tlf);
            cont.add(b);
            cont.add(cp);
            cont.add(voir);
            cont.add(voir1);
            
            try {
                ScaleImageLabel sep = new ScaleImageLabel(Image.createImage("/Separator.png"));
                cont.add(sep);
            } catch (IOException ex) {
            }
            add(cont);
            

            b.addPointerPressedListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent evt) {
                    
                }
            });
            voir.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                   EditFournisseurForm.f = e ;
                   EditFournisseurForm ef = new EditFournisseurForm();
                   ef.show();

                }
            });
            
            
        }
this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
             new HomeAchat().showBack();
        });
    }
}