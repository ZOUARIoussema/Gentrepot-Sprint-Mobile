/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.entrepot.models.Fournisseur;
import com.entrepot.utls.DataSource;
import com.entrepot.utls.Statics;
import java.util.ArrayList;

/**
 *
 * @author oussema
 */
public class ServiceFournisseur {
    private ConnectionRequest request;
    private boolean responseResult;
    public ArrayList<Fournisseur> tasks;
    
    public ServiceFournisseur () {
        request = DataSource.getInstance().getRequest();
    }
    
    public boolean addFournisseur(Fournisseur fournisseur) {
        String url = Statics.BASE_URL + "/apiF/addF" ;
        request.setUrl(url);
        request.addRequestHeader("X-Requested-With", "XMLHttpRequest");

        request.addArgument("raisonSociale", fournisseur.getRaisonSociale());
        request.addArgument("numeroTelephone", fournisseur.getNumeroTelephone() + "");
        request.addArgument("adresse", fournisseur.getAdresse());
        request.addArgument("adresseMail", fournisseur.getAdresseMail());
        request.addArgument("matriculeFiscale", fournisseur.getMatriculeFiscale());
        request.addArgument("codePostale", fournisseur.getCodePostale() + "");

        
        System.out.println(url);
        
        
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }
    
    
    
    
    
    
    
}
