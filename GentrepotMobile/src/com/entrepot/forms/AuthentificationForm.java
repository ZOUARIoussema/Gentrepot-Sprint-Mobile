/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.User;
import com.entrepot.services.ServiceUser;

/**
 *
 * @author oussema
 */
public class AuthentificationForm extends Form {

    
    public static User user=null;
    
    
    

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
       
        c.addAll(bConnection, bInscription);
        
        Container c2 = new Container(BoxLayout.y());
        
        c2.addAll(login,password,l,c);
        

        this.setLayout(BoxLayout.y());

        this.setTitle("Authentification");
        
        this.setLayout(new FlowLayout(CENTER,CENTER));
        this.add(c2);
        

        l.addPointerReleasedListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

               new EnvoyerCodeRecuperationForm().show();

            }
        });

        bConnection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                user = serviceUser.findUser(login.getText(), password.getText());

                if (user == null) {

                    Dialog.show("Erreur", "Login ou mot de passe invalide", "cancel", "ok");
                } else if (user.getRole().equals("[ROLE_ACAIS, ROLE_USER]")) {

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

    }

}
