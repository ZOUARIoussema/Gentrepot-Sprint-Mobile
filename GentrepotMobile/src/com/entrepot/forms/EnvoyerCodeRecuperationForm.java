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
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.util.Base64;
import com.entrepot.services.MailService;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author oussema
 */
public class EnvoyerCodeRecuperationForm extends Form {

    public static String code;

    public EnvoyerCodeRecuperationForm() {

        TextField login = new TextField(null, "Non d'utilisateur");

        TextField adresseMail = new TextField(null, "Adresse mail");

        adresseMail.getStyle().setFgColor(0xf67777);

        Button b = new Button("Envoyer");

        Container c = new Container(BoxLayout.y());

        c.addAll(login, adresseMail, b);

        this.add(c);

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                Random r = new Random();
                code = "" + r.nextInt(10000);
                while (code.length() < 4) {
                    code = "0" + code;
                }
                
                
                
                

                MailService.EnvoyerMail("oussema.zouari@esprit.tn", "Code recuperation ", "code:" + code);
                
                new VerifCodeForm().show();

            }
        });

    }

}
