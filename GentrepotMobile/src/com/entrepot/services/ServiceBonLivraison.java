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
import com.entrepot.models.BonLivraison;
import com.entrepot.models.CommandeVente;
import com.entrepot.utls.DataSource;
import com.entrepot.utls.Statics;
import java.util.ArrayList;

/**
 *
 * @author oussema
 */
public class ServiceBonLivraison {
    
    
     private ConnectionRequest request;
  private boolean responseResult;
 
    
    
    public ServiceBonLivraison() {
        request = DataSource.getInstance().getRequest();
    }
    public boolean ajouterBon(BonLivraison v){
        
        //String url = "http://localhost/PROJET-SYMFONY-GENTREPOT/Gentrepot/web/app_dev.php/api/apiCommandeVente/ajout?totalC="+v.getTotalC()+"&etat="+v.getEtat()+"&dateC="+v.getDateC()+"&tauxRemise="+v.getTauxRemise()+"&ligneCommande="+v.getLigneCommande(); 
            
        String url =Statics.BASE_URL+"/apiBon/ajout/"+v.getCommandeVente().getId()+"?&Nom="+v.getNom()+"&Prenom="+v.getPrenom()+"&AdresseLivraison="+v.getAdresseLivraison();
        System.out.println(url);

        request.setUrl(url);
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