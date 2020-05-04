/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;



/**
 *
 * @author oussema
 */
public class VerifCodeForm extends Form {

    public VerifCodeForm() {

        this.setLayout(new FlowLayout(CENTER, CENTER));

        Container c = new Container(BoxLayout.y());

        TextField tCode = new TextField(null, "code verification");

        Button b = new Button("Valider");

        c.addAll(tCode, b);

        this.add(c);

        b.addActionListener(new com.codename1.ui.events.ActionListener() {
            @Override
            public void actionPerformed(com.codename1.ui.events.ActionEvent evt) {

                new ModifierProfilForm().show();

            }
        });

    }

}
