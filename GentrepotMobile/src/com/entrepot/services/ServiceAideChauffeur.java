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
import com.entrepot.models.AideChauffeur;
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
public class ServiceAideChauffeur {
      private ConnectionRequest request;

    private boolean responseResult;
    public ArrayList<AideChauffeur> tasks;

    public ServiceAideChauffeur () {
        request = DataSource.getInstance().getRequest();
    }
     public boolean addAideChauffeur(AideChauffeur aide) {
        String url = Statics.BASE_URL + "/apiAchauf/ajout" +"?cin="+ aide.getCin()+ "&nom=" + aide.getNom()+ "&prenom=" + aide.getPrenom() + "&adresse=" + aide.getAdresse();
  
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
 public ArrayList<AideChauffeur> getAllAideChauffeur() {
        String url = Statics.BASE_URL + "/apiaide/affiche";
        
        System.out.println(url);

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

    public ArrayList<AideChauffeur> parseTasks(String jsonText) {
        try {
            tasks = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                String cin = obj.get("cin").toString();
                String nom = obj.get("nom").toString();
                String prenom = obj.get("prenom").toString();
                String adresse = obj.get("adresse").toString();
                tasks.add(new AideChauffeur(cin, nom, prenom, adresse));
            }

        } catch (IOException ex) {
        }

        return tasks;
    }
    
}
