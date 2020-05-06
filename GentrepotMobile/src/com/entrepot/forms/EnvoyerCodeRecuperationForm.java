/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.SpanLabel;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
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
import com.codename1.util.Base64;
import com.entrepot.models.User;
import com.entrepot.services.MailService;
import com.entrepot.services.ServiceUser;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author oussema
 */
public class EnvoyerCodeRecuperationForm extends Form {

    public static String code;

    ServiceUser serviceUser = new ServiceUser();

    Resources theme = UIManager.initFirstTheme("/themeTresorerie");

    public EnvoyerCodeRecuperationForm() {

        this.getStyle().setBgImage(theme.getImage("loginBack.png"), focusScrolling);

        TextField login = new TextField(null, "Non d'utilisateur");

        TextField adresseMail = new TextField(null, "Adresse mail");

        adresseMail.getStyle().setFgColor(0xf67777);

        Button b = new Button("Envoyer");

        Container c = new Container(BoxLayout.y());

        c.addAll(login, adresseMail, b);

        this.setLayout(new FlowLayout(CENTER, CENTER));

        this.add(c);

        Validator v = new Validator();

        v.setShowErrorMessageForFocusedComponent(true);

        v.addConstraint(login, new LengthConstraint(1, " champ vide  "));
        v.addConstraint(adresseMail, RegexConstraint.validEmail("adreese mail invalide"));

        v.addSubmitButtons(b);

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                User u = serviceUser.findUserLoginMail(login.getText(), adresseMail.getText());
                
                
                if(u!=null){
                    
                AuthentificationForm.user=u;

                Random r = new Random();
                code = "" + r.nextInt(10000);
                while (code.length() < 4) {
                    code = "0" + code;
                }

                MailService.EnvoyerMail(adresseMail.getText(), "Code recuperation ", "code:" + code);

                new VerifCodeForm().show();
            }else
                {
                      Dialog.show("Erreur", "non d'utilisateur ou adresse mail incorrecte", "cancel", "ok");
                    
                }

            }
        });

    }

}
