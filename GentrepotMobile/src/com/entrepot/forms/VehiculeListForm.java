/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.entrepot.services.ServiceChauffeur;
import com.entrepot.services.ServiceVehicule;

/**
 *
 * @author Rym
 */
public class VehiculeListForm extends Form {
     public VehiculeListForm() {
        super("liste des vehicules ", BoxLayout.y());

        this.add(new SpanLabel(new ServiceVehicule().getAllTasks().toString()));

        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
             new HomeLogistiqueForm().show();
        });
}
}
