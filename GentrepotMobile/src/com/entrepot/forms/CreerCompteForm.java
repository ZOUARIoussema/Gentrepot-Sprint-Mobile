/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.entrepot.models.User;
import com.entrepot.services.ServiceUser;

/**
 *
 * @author oussema
 */
public class CreerCompteForm extends Form {

    public CreerCompteForm() {
        
        
       

        TextField login = new TextField(null,"Non d'utilisateur");
        TextField adresseMail = new TextField(null,"Adresse mail");
        TextField motPasse = new TextField(null,"Mot de passe");
        motPasse.setConstraint(TextField.PASSWORD);
        Button bValider = new Button("Valider");
        
        
        this.setLayout(BoxLayout.y());
        
        this.setTitle("Creer compte");
        this.addAll(login,adresseMail,motPasse,bValider);
        
        
        bValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                 ServiceUser serviceUser = new ServiceUser();
                
                User u =new User( 0,login.getText(), adresseMail.getText(), login.getText(),  adresseMail.getText(), motPasse.getText(), "a:1:{i:0;s:10:\"ROLE_CLIEN\";}");
                
                System.out.println(  serviceUser.addUser(u));
            
            
            }
        });

    }

}
