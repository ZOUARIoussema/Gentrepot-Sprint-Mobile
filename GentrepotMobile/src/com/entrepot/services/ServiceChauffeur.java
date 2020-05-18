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
import com.entrepot.models.Chauffeur;
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
public class ServiceChauffeur {
     private ConnectionRequest request;

    private boolean responseResult;
    public ArrayList<Chauffeur> tasks;

    public ServiceChauffeur () {
        request = DataSource.getInstance().getRequest();
    }
    public boolean addChauffeur(Chauffeur chauf) {
        String url = Statics.BASE_URL + "/apichauf/ajout" +"?cin="+ chauf.getCin()+ "&nom=" + chauf.getNom()+ "&prenom=" + chauf.getPrenom() + "&adresse=" + chauf.getAdresse() + "&etat=" + chauf.getEtat();

        
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
     public ArrayList<Chauffeur> getAllTasks() {
        String url = Statics.BASE_URL + "/apichauf/affiche";

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

    public ArrayList<Chauffeur> parseTasks(String jsonText) {
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
                 int voyage = (int)Float.parseFloat(obj.get("voyage").toString());
                  String etat = obj.get("etat").toString();

                tasks.add(new Chauffeur(cin, nom, prenom, adresse, voyage, etat));
            }

        } catch (IOException ex) {
        }

        return tasks;
    }

    
}
