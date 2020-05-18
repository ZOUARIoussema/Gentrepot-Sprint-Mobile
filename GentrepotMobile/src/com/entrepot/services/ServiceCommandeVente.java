/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.entrepot.models.CommandeVente;
import com.entrepot.utls.DataSource;
import com.entrepot.utls.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author oussema
 */
public class ServiceCommandeVente {
    private ConnectionRequest request;
  private boolean responseResult;
    public ArrayList<CommandeVente> commande;
    public int id_commande;
    
    
    public ServiceCommandeVente() {
        request = DataSource.getInstance().getRequest();
    }
    public boolean ajoutercom(CommandeVente v){
        
        String url = Statics.BASE_URL+ "/apiCommandeVente/ajout?totalC="+v.getTotalC()+"&etat="+v.getEtat()+"&dateC="+v.getDateC()+"&tauxRemise="+v.getTauxRemise()+"&ligneCommande="+v.getLigneCommande(); 
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
    
    
    
    
    
     
     
      public ArrayList<CommandeVente> getAllCommandes() {
        String url = Statics.BASE_URL+"/apiCommandeVente/affiche";

        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                commande = parseCommande(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return commande;
    }

   public ArrayList<CommandeVente> parseCommande(String jsonText) {
        try {
            commande = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> commandeListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>)commandeListJson.get("root");
            for (Map<String, Object> obj : list) {
                Double totalC = (double)Float.parseFloat(obj.get("1").toString());

                String etat = obj.get("2").toString();

               

                commande.add(new CommandeVente(totalC,etat));
            }

        } catch (IOException ex) { 
            
        }

        return commande;
    }
   
   public int getMaxIdCommnde() {
        String url = Statics.BASE_URL+"/apiCommandeVente/max";
        request.setUrl(url);
        System.out.println(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                id_commande = parseidCommande (new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return id_commande ;
    }
   
   public int parseidCommande (String jsonText) {
        try {

            JSONParser jp = new JSONParser();
            Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {

                 id_commande = (int) Float.parseFloat(obj.get("1").toString());
           
            }

        } catch (IOException ex) {
        }

        return id_commande;
    }
    
}
