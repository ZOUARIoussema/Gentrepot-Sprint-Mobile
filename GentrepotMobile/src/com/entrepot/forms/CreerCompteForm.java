/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import com.entrepot.models.User;
import com.entrepot.services.ServiceUser;

/**
 *
 * @author oussema
 */
public class CreerCompteForm extends Form {

    Resources theme = UIManager.initFirstTheme("/themeTresorerie");

    public CreerCompteForm() {

        this.getStyle().setBgImage(theme.getImage("loginBack.png"), focusScrolling);

        TextField login = new TextField(null, "Non d'utilisateur");
        TextField adresseMail = new TextField(null, "Adresse mail");
        TextField motPasse = new TextField(null, "Mot de passe");
        motPasse.setConstraint(TextField.PASSWORD);
        Button bValider = new Button("Valider");

        Validator v = new Validator();
        v.addConstraint(motPasse, new LengthConstraint(6, "eeee"));
        v.addConstraint(adresseMail, RegexConstraint.validEmail("ppppp"));

        //RegexConstraint emailConstraint = new RegexConstraint("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$", "Invalid Email Address");
        v.addSubmitButtons(bValider);
        v.setShowErrorMessageForFocusedComponent(true);

        this.setLayout(BoxLayout.y());

        this.setTitle("Creer compte");
        this.addAll(login, adresseMail, motPasse, bValider);

        bValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                ServiceUser serviceUser = new ServiceUser();

                if (login.getText().equals(null)) {

                    Dialog.show("Erreur", "champ non d'utilisateur vide ", "cancel", "ok");

                } else if (adresseMail.getText().equals(null)) {
                    Dialog.show("Erreur", "champ adreese mail vide ", "cancel", "ok");
                }/*else
                         if(){
                             
                         }*/


                User u = new User(0, login.getText(), adresseMail.getText(), login.getText(), adresseMail.getText(), motPasse.getText(), "client");

                if (serviceUser.addUser(u)) {

                    new AuthentificationForm().show();
                }

            }
        });

    }

}
