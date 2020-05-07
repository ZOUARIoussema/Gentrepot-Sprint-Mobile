/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.ImageViewer;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import com.entrepot.models.LettreDeRelance;
import com.entrepot.services.MailService;

/**
 *
 * @author oussema
 */
public class EnvoiLettreRelanceParMail extends Form {

    Resources theme = UIManager.initFirstTheme("/themeTresorerie");

    public EnvoiLettreRelanceParMail(LettreDeRelance l) {

        this.getStyle().setBgImage(theme.getImage("loginBack.png"), focusScrolling);

        TextField textFieldM = new TextField(null, "Adresse mail");

        Button bE = new Button("Envoyer");

        this.setLayout(new FlowLayout(CENTER, CENTER));

        Container c = new Container(BoxLayout.y());
        c.addAll(new ImageViewer(theme.getImage("iconeGmail.png").scaled(300, 200)), textFieldM, bE);
        this.add(c);

        this.setTitle("Envoyer Mail");

        Validator v = new Validator();

        v.addConstraint(textFieldM, RegexConstraint.validEmail("adreese mail invalide")).setShowErrorMessageForFocusedComponent(true);

        v.addSubmitButtons(bE);

        bE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                MailService.EnvoyerMail(textFieldM.getText(), "Lettre de relance", "Factuure impayé n°" + l.getFactureVente().getNumeroF());

                ToastBar.showMessage("ce mail est envoyé automatiquement", FontImage.MATERIAL_STAR, 30000);

                new ListeLettreDeRelanceForm().show();

            }
        });

        this.getToolbar().addCommandToLeftBar("Retour", null, (ev) -> {
            new DetailleLettreDeRelanceForm(l).show();

        });

    }

}
