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
import com.entrepot.models.BonLivraison;

import com.entrepot.utls.DataSource;
import com.entrepot.utls.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 *
 * @author oussema
 */
public class ServiceBonLivraison {
    
    
    private ConnectionRequest request;

    private boolean responseResult;
    public ArrayList<BonLivraison> tasks;
    
    public ServiceBonLivraison() {
        request = DataSource.getInstance().getRequest();
    }
  
    
    
    public ArrayList<BonLivraison> getAllBon() {
        String url = Statics.BASE_URL + "/apio/affiche";

        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseTasks(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return tasks;
    }

     public ArrayList<BonLivraison> parseTasks(String jsonText) {
        try {
            tasks = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
              
                String etat = obj.get("etat").toString();
                 String adresseLivraison = obj.get("adresseLivraison").toString();
                   String nom = obj.get("nom").toString();
                   String prenom = obj.get("prenom").toString();
                   
                   
                   
                   
                Map<String, Object> dated2 = (Map<String, Object>) obj.get("datesortie");
                float da2 = Float.parseFloat(dated2.get("timestamp").toString());
                Date dateSortie = new Date((long) (da2 - 3600) * 1000);
                
                 
                   Map<String, Object> dated1 = (Map<String, Object>) obj.get("dateCreation");
                float da1 = Float.parseFloat(dated1.get("timestamp").toString());
                Date datecreation = new Date((long) (da1 - 3600) * 1000);
                
                
                  
                    int id = (int) Float.parseFloat(obj.get("id").toString());
                BonLivraison bo =new BonLivraison(adresseLivraison, etat, datecreation, dateSortie, nom, prenom) ;
                bo.setId(id);
                
                tasks.add(bo);
            }

        } catch (IOException ex) {
        }

        return tasks;
    } 
    
}
   