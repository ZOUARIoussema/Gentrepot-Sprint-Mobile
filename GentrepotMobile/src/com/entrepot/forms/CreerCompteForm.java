/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.ImageViewer;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
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
        
        v.setShowErrorMessageForFocusedComponent(true);
      
        v.addConstraint(login, new LengthConstraint(1, "champ vide"));
        v.addConstraint(motPasse, new LengthConstraint(6, "longeur inferieur a six caractre"));
        v.addConstraint(adresseMail, RegexConstraint.validEmail("adreese mail invalide"));
               
        
        
       

        //RegexConstraint emailConstraint = new RegexConstraint("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$", "Invalid Email Address");
        v.addSubmitButtons(bValider);
       // v.setShowErrorMessageForFocusedComponent(true);

        this.setLayout(BoxLayout.y());

        this.setTitle("Creer compte");
       
        
        
        
         this.setLayout(new FlowLayout(CENTER, CENTER));
         
         Container c  = new Container(BoxLayout.y());
         
         
         ImageViewer image =  new ImageViewer(theme.getImage("adduser.png"));
         
         c.addAll(image,login, adresseMail, motPasse, bValider);
      
         
         this.add(c);
         
         
        
        

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
