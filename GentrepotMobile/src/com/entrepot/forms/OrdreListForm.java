/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.services.ServiceOrdreMission;

/**
 *
 * @author Rym
 */
public class OrdreListForm extends Form{
     Resources res = UIManager.initFirstTheme("/themeLogistique");
      Resources theme = UIManager.initFirstTheme("/themeLogistique");
        Resources theme2 = UIManager.initFirstTheme("/themeTresorerie");
     public OrdreListForm() {
        super("liste des chauffeur ", BoxLayout.y());
        this.getStyle().setBgImage(theme.getImage("kashmir.png"), focusScrolling);
    //ImageViewer i = new ImageViewer(theme2.getImage("iconLettre.png").scaled(300, 300));
        this.add(new SpanLabel(new ServiceOrdreMission().getAllOrdrer().toString()));

        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
             new HomeLogistiqueForm().show();
        });
    }
}
