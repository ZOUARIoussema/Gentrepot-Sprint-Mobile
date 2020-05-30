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
import com.entrepot.models.SousCategorieAchat;
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
public class ServiceSousCategorieAchat {
    
    private ConnectionRequest request;

    private boolean responseResult;
    public ArrayList<SousCategorieAchat> tasks;

    public ServiceSousCategorieAchat () {
        request = DataSource.getInstance().getRequest();
    }
    
    public ArrayList<SousCategorieAchat> getSous() {

        ArrayList<SousCategorieAchat> list = new ArrayList<>();

        for (SousCategorieAchat ch : this.getAllSs()) {

            
                list.add(ch);
            
        }

        return list;

    }
    
    public ArrayList<SousCategorieAchat> getAllSs() {
        String url = Statics.BASE_URL + "/listSousCat";

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
    
    /*public ArrayList<SousCategorieAchat> parseTasks(String jsonText) {
        try {
            tasks = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                int id = (int)Float.parseFloat(obj.get("id").toString());
                String nom = obj.get("nom").toString();
                

                tasks.add(new SousCategorieAchat(id, nom));
            }

        } catch (IOException ex) {
        }

        return tasks;
    }*/
    public ArrayList<SousCategorieAchat> parseTasks(String jsonText) {
        try {
            tasks = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                int id = (int)Float.parseFloat(obj.get("id").toString());
                String nom = obj.get("nom").toString();
                tasks.add(new SousCategorieAchat(id, nom));
            }

        } catch (IOException ex) {
        }

        return tasks;
    }
}
