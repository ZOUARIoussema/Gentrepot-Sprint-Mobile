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
import com.entrepot.models.Chauffeur;
import com.entrepot.models.OrdreMission;
import com.entrepot.models.Vehicule;
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
public class ServiceOrdreMission {
      private ConnectionRequest request;

    private boolean responseResult;
    public ArrayList<OrdreMission> tasks;
    ServiceVehicule sv = new ServiceVehicule();
    ServiceChauffeur c = new ServiceChauffeur();
       ServiceAideChauffeur ca = new ServiceAideChauffeur();
    public ServiceOrdreMission () {
        request = DataSource.getInstance().getRequest();
    }
    public boolean addOrdreMission (OrdreMission o) {
        String url = Statics.BASE_URL + "/apiordre/ajout"+"?vehicule="+ o.getVehicule()+ "&chauffeur=" + o.getChauffeur()+ "&Aidechauffeur=" + o.getAideChauffeur() + "&dateCreation=" +o.getDateCreation() + "&dateSortie=" +o.getDateSortie() + "&dateRetour=" +o.getDateRetour() ;
  
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
                
               
                int mat = Integer.parseInt(obj.get("matricule").toString());
                Vehicule v = sv.getVehiculeByMat(mat);
                
                String cin = (obj.get("cin").toString());
                Chauffeur ch = c.getChauffeurByCin(cin);
                
               String Cin =(obj.get("cin").toString());
                AideChauffeur cha = ca.getAideChauffeurByCin(Cin);
                 Date dateSortie = (Date) (obj.get("datesortie"));
                  Date dateRetour = (Date) (obj.get("dateretour"));
                   Date dateCreation = (Date) (obj.get("datecreation"));
             
                tasks.add(new OrdreMission(v, ch, cha, dateCreation,dateSortie, dateRetour));
            }

        } catch (IOException ex) {
        }

        return tasks;
    }

  
}
