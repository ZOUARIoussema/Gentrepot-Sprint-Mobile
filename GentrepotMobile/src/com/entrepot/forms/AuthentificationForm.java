/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.User;

import com.entrepot.services.ServiceUser;

/**
 *
 * @author oussema
 */
public class AuthentificationForm extends Form {

    public AuthentificationForm() {

        ServiceUser serviceUser = new ServiceUser();

        Resources theme = UIManager.initFirstTheme("/themeTresorerie");

        TextField login = new TextField(null, "Non d'utilisateur");
        TextField password = new TextField(null, "Mot de passe");
        password.setConstraint(TextField.PASSWORD);
        Label l = new Label("Mot de passe oublié");

        Button bConnection = new Button("Connecter");
        Button bInscription = new Button("Inscription");

        Container c = new Container(BoxLayout.x());
        c.addAll(bConnection,bInscription);
        
        
        this.setLayout(BoxLayout.y());

        this.setTitle("Authentification");
        this.add(login);
        this.add(password);
        this.add(l);
        this.add(c);
         

        bConnection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                User u = serviceUser.findUser(login.getText(), password.getText());

                System.out.println(serviceUser.findUser(login.getText(), password.getText()));

                if (u == null) {

                    Dialog.show("Erreur", "Login ou mot de passe invalide", "cancel", "ok");
                } else if (u.getRole().equals("[ROLE_ACAIS, ROLE_USER]")) {

                    new MenueAgentCaisseForm().show();
                }

            }
        });
        
        
        bInscription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
               new CreerCompteForm().show();
             
            }
        });

        /* for (User u :serviceUser.getAllUsers()){
            
            System.out.println(u);
        }*/
    }

}
