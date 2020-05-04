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
import com.entrepot.models.Vehicule;
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
public class ServiceVehicule {
     private ConnectionRequest request;

    private boolean responseResult;
    public ArrayList<Vehicule> tasks;

    public ServiceVehicule () {
        request = DataSource.getInstance().getRequest();
    }
    public boolean addVehicule(Vehicule v) {
        String url = Statics.LOGISTIQUE_URL + "/apiv/ajout" +"?matricule"+v.getMatricule()+ "&capacite=" + v.getCapacite()+ "&type=" + v.getType()+ "&etat" + v.getEtat();

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
    
    public ArrayList<Vehicule> getAllTasks() {
        String url = Statics. LOGISTIQUE_URL + "/apiv/affiche";

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

    public ArrayList<Vehicule> parseTasks(String jsonText) {
        try {
            tasks = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                String type = obj.get("type").toString();
                String etat = obj.get("etat").toString();
                int capacite = (int)Float.parseFloat(obj.get("capacite").toString());
                int matricule = (int)Float.parseFloat(obj.get("matricule").toString());
                tasks.add(new Vehicule(etat, matricule, capacite, type));
            }

        } catch (IOException ex) {
        }

        return tasks;
    }
}
