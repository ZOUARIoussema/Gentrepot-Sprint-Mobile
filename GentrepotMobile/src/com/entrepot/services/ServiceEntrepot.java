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
import com.entrepot.models.Entrepot;
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
public class ServiceEntrepot {
    private ConnectionRequest request;

    private boolean responseResult;
    public ArrayList<Entrepot> enps;

    public ServiceEntrepot() {
        request = DataSource.getInstance().getRequest();
    }

    public boolean addCom(Entrepot com) {
        String url = Statics.BASE_URL2 + "/apiEntrepot/new?adresse=" + com.getAdresse() + "$adresseMail=" + com.getAdresseMail() + "$matriculeFiscal=" + com.getMatriculeFiscale() + "$numeroTel=" + com.getNumeroTel() + "$raisonSocial=" + com.getRaisonSociale();

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

    public ArrayList<Entrepot> getAllEnps() {
        String url = Statics.BASE_URL2 + "/apiEntrepot/all";

        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                enps = parseEnps(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return enps;
    }

    public ArrayList<Entrepot> parseEnps(String jsonText) {
        try {
            enps = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                
                String matriculeFiscale = obj.get("matriculeFiscale").toString();
                String adresse = obj.get("adresse").toString();
                String raisonSociale = obj.get("raisonSociale").toString();
                String adresseMail = obj.get("adresseMail").toString();
                String numeroTel = obj.get("numeroTel").toString();
                
                enps.add(new Entrepot( matriculeFiscale, adresse, raisonSociale, adresseMail, numeroTel));
            }

        } catch (IOException ex) {
        }

        return enps;
    }
}
