/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.Validator;

/**
 *
 * @author oussema
 */
public class VerifCodeForm extends Form {

    Resources theme = UIManager.initFirstTheme("/themeTresorerie");

    public VerifCodeForm() {

        this.getStyle().setBgImage(theme.getImage("loginBack.png"), focusScrolling);

        this.setLayout(new FlowLayout(CENTER, CENTER));

        Container c = new Container(BoxLayout.y());

        TextField tCode = new TextField(null, "code verification");

        Button b = new Button("Valider");

        Validator v = new Validator();

        v.setShowErrorMessageForFocusedComponent(true);

        v.addConstraint(tCode, new LengthConstraint(1, " champ vide  "));

        v.addSubmitButtons(b);
        
        ImageViewer image=new ImageViewer(theme.getImage("valider.png").scaled(350, 350));
        

        c.addAll(image, tCode, b);

        this.add(c);

        b.addActionListener(new com.codename1.ui.events.ActionListener() {
            @Override
            public void actionPerformed(com.codename1.ui.events.ActionEvent evt) {

                
                if(EnvoyerCodeRecuperationForm.code.equals(tCode.getText())){
                    
                
                new ModifierProfilForm(AuthentificationForm.user).show();
                }else
                {
                       Dialog.show("Erreur", "code invalide", "cancel", "ok");
                }

            }
        });

    }

}
