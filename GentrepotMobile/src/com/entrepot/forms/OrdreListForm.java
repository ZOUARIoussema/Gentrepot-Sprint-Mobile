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
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import static com.codename1.ui.layouts.BoxLayout.x;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.BonEntree;
import com.entrepot.models.OrdreMission;
import com.entrepot.services.ServiceBonLivraison;
import com.entrepot.services.ServiceOrdreMission;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Rym
 */
public class OrdreListForm extends Form{
     Resources res = UIManager.initFirstTheme("/themeLogistique");
      Resources theme = UIManager.initFirstTheme("/themeLogistique");
        Resources theme2 = UIManager.initFirstTheme("/themeTresorerie");
        ServiceOrdreMission or =new ServiceOrdreMission();
        ServiceBonLivraison sb = new ServiceBonLivraison() ;
     public OrdreListForm() {
         
        super("liste des chauffeur ", BoxLayout.y());
        this.getStyle().setBgImage(theme.getImage("kashmir.png"), focusScrolling);
        
    Container aff =new Container(BoxLayout.y());
      

         for (OrdreMission o :or. getAllOrdrer()) {
             Container cat =new Container(BoxLayout.y());
             Container carte = new Container(BoxLayout.x());
             Container cDetaille = new Container(BoxLayout.y());
       
           Label labelDate = new Label("Date retout: " + new SimpleDateFormat("MM-dd-yyyy").format(o.getDateRetour()));
            Label labDate = new Label("Date retout: " + new SimpleDateFormat("MM-dd-yyyy").format(o.getDateSortie()));
           Label labeDate = new Label("Date creation: " + new SimpleDateFormat("MM-dd-yyyy").format(o.getDateCreation()));
              Label labelC = new Label("Chauffeur: " + o.getChauffeur().getNom());
                Label labelA = new Label("AideChauffeur: " + o.getAideChauffeur().getNom());
                Label labelV = new Label("Vehicule: " + o.getVehicule().getMatricule());
             Button supp = new Button("Delete");
             
            cat.addAll(labelDate,labDate,labeDate,labelC,labelA,labelV,supp);
           
               ImageViewer i = new ImageViewer(theme2.getImage("iconLettre.png").scaled(300, 300));
                carte.addAll(i,cat);
              aff.add(carte);
              
              
               supp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {

                    if (Dialog.show("Comfirmation", "Vouler vous supprimer ce inventaire ? ", "oui", "non")) {

                        or.deleteOrdre(o);
                        new ListeBonsEntreeForm().showBack();
                    }

                }
            });
              
         }
              
               add(LayeredLayout.encloseIn(
            aff
              
        ));
        
               
        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
             new HomeLogistiqueForm().show();
        });
        
      
    }
}