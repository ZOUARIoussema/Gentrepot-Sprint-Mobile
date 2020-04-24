/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

/**
 *
 * @author oussema
 */
public class AuthentificationForm extends Form {
    
    
    public AuthentificationForm(){
        
        
        Resources theme = UIManager.initFirstTheme("/themeTresorerie");
        
        TextField login  = new TextField(null,"Non d'utilisateur");
        TextField password = new TextField(null,"Mot de passe");
        password.setConstraint(TextField.PASSWORD);
        Label l = new Label("Mot de passe oblié");
        
        Button bConnection = new Button("Connecter");
        
        this.setLayout(BoxLayout.y());
        
        this.setTitle("Authentification");
        this.add(login);
        this.add(password);
        this.add(l);
        this.add(bConnection);
        
        
        
        
    }
    
    
    
}
