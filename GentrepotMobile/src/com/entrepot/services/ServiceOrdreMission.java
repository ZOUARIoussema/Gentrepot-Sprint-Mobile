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
import com.entrepot.models.OrdreMission;
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
public class ServiceOrdreMission {
      private ConnectionRequest request;

    private boolean responseResult;
    public ArrayList<OrdreMission> tasks;
    ServiceVehicule sv = new ServiceVehicule();

    public ServiceOrdreMission () {
        request = DataSource.getInstance().getRequest();
    }
    public boolean addOrdreMission (OrdreMission o) {
        String url = Statics.BASE_URL + "/apiordre/ajout" +"?vehicule="+ o.getVehicule()+ "&chauffeur=" + o.getChauffeur()+ "&Aidechauffeur=" + o.getAideChauffeur() + "dateCreation=" +o.getDateCreation() ;
  
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
    public ArrayList<OrdreMission> getAllOrdrer() {
        String url = Statics.BASE_URL + "/apiordre/affiche";
        
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

    public ArrayList<OrdreMission> parseTasks(String jsonText) {
        try {
            tasks = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
               
                int mat = Integer.parseInt(obj.get("").toString());
                Vehicule v = sv.getVehiculeByMat(mat);
              //  tasks.add(new OrdreMission(v, chauffeur, aideChauffeur, dateCeation, dateSortie, dateRetour);
            }

        } catch (IOException ex) {
        }

        return tasks;
    }

  
}
