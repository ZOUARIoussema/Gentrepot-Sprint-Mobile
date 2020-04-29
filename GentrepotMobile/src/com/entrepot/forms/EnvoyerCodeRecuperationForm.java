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
import java.io.IOException;
import java.util.Map;
import java.util.Random;


/**
 *
 * @author oussema
 */
public class EnvoyerCodeRecuperationForm extends Form {

    String accountSID = "ACc03b63cd26ee3a0eab39fbf3ea1843d8";
    String authToken = "078c70c9576471b9571b13ff82454050";

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
                String val = "" + r.nextInt(10000);
                while (val.length() < 4) {
                    val = "0" + val;
                }

                  Message m = new Message("Code: "+val );
                  
                  
            /*    try {
                    Display.getInstance().sendSMS("+21624812689", "");
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }*/
                  
                 
                 Display.getInstance().sendMessage(new String[]{"oussema.zouari@esprit.tn"}, "Code de verifcation", m);
                
                
                
                
                
              /*  Response<Map> result = Rest.post("https://api.twilio.com/2010-04-01/Accounts/" + accountSID + "/Messages.json").
        queryParam("To", "+21624812689").
        queryParam("From", "+21624812689").
        queryParam("Body", val).
        header("Authorization", "Basic " + Base64.encodeNoNewline((accountSID + ":" + authToken).getBytes())).
        getAsJsonMap();*/
                
                
                
            }
        });

    }

}
