/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.entrepot.services.Password;
import com.entrepot.services.ServiceUser;

/**
 *
 * @author oussema
 */
public class ModifierProfilForm extends Form {

    ServiceUser serviceUser = new ServiceUser();

    public ModifierProfilForm() {

        TextField login = new TextField(null, "Non d'utilisateur: " + AuthentificationForm.user.getUsername());
        login.setEditable(false);
        TextField adresseeMail = new TextField(null, "Adresse mail:" + AuthentificationForm.user.getEmail());
        adresseeMail.setEditable(false);
        TextField ancientMotPasse = new TextField(null, "Ancien mot de passe");
        ancientMotPasse.setConstraint(TextField.PASSWORD);
        TextField nouveauMotPasse = new TextField(null, "Nouveau mot de passe");
        nouveauMotPasse.setConstraint(TextField.PASSWORD);

        Button b = new Button("Valider");

        Container c = new Container(BoxLayout.y());

        c.addAll(login, adresseeMail, ancientMotPasse, nouveauMotPasse, b);

        this.setLayout(new FlowLayout(CENTER, CENTER));

        this.add(c);

        this.setTitle("Modifier Profile");

        b.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent evt) {

                if (!ancientMotPasse.getText().equals(null) && Password.checkPassword(ancientMotPasse.getText(), AuthentificationForm.user.getPassword())) {

                    if (!nouveauMotPasse.getText().equals(null)) {
                        AuthentificationForm.user.setPassword(Password.hashPassword(nouveauMotPasse.getText()));

                        if (serviceUser.modifierUser(AuthentificationForm.user)) {
                            ToastBar.showMessage("Profil est modifier avec succès", FontImage.MATERIAL_STAR, 16000);
                            new MenueAgentCaisseForm().show();
                        }

                        

                    } else {
                        Dialog.show("Erreur", "champ vide ", "cancel", "ok");
                    }

                } else {
                    Dialog.show("Erreur", " ancien mot de passe invalide", "cancel", "ok");

                }

            }
        });

    }

}
