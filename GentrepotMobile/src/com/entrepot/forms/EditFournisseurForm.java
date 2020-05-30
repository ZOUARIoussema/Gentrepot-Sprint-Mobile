/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.Fournisseur;
import com.entrepot.services.ServiceFournisseur;

/**
 *
 * @author Mohamed
 */
public class EditFournisseurForm extends Form {
    Resources theme = UIManager.initFirstTheme("/themeLogistique");
    public static Fournisseur f ;
    public EditFournisseurForm(){
        super("Modifier Fournisseur", BoxLayout.y());
        this.getStyle().setBgImage(theme.getImage("kashmir.png"), focusScrolling);
        this.setLayout(new FlowLayout(CENTER, CENTER));
        
        TextField raisonSociale = new TextField();
            raisonSociale.setText(f.getRaisonSociale());
             TextField numeroTelephone = new TextField();
            numeroTelephone.setText(f.getNumeroTelephone()+"");
            TextField adresse = new TextField();
            adresse.setText(f.getAdresse());
            TextField adresseMail = new TextField();
            adresseMail.setText(f.getAdresseMail());
            TextField matriculeFiscale = new TextField();
            matriculeFiscale.setText(f.getMatriculeFiscale());
            TextField codePostale = new TextField();
            codePostale.setText(f.getCodePostale()+"");
            
            Button b = new Button("Modifier");
            ServiceFournisseur ws = new ServiceFournisseur();
            
            b.addActionListener(e->{
                
                f.setRaisonSociale(raisonSociale.getText());
                f.setNumeroTelephone(Integer.parseInt(numeroTelephone.getText()));
                f.setAdresseMail(adresseMail.getText());
                f.setAdresse(adresse.getText());
                f.setMatriculeFiscale(matriculeFiscale.getText());
                f.setCodePostale(Integer.parseInt(codePostale.getText()));
                ws.editFournisseur(f);
                Dialog.show("SUCCESS", "Fournisseur modifier", "OK", null);
                
                });
            this.addAll(raisonSociale,numeroTelephone,adresse,adresseMail,matriculeFiscale,codePostale,b);
            
            this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
             new ListeFournisseursForm().showBack();
        });
    }
    
}
