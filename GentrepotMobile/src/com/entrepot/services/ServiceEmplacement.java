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
import com.entrepot.models.Emplacement;
import com.entrepot.models.ProduitAchat;
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
public class ServiceEmplacement {
    private ConnectionRequest request;

    private boolean responseResult;
    public ArrayList<Emplacement> empls;

    public ServiceEmplacement() {
        request = DataSource.getInstance().getRequest();
    }

    public boolean addLPerte(Emplacement lpert) {
        String url = Statics.BASE_URL + "/apiEmp/new" + lpert.getAdresse() + "/" + lpert.getClasse() + "/" + lpert.getCapaciteStockage() + "/" + lpert.getQuantiteStocker();

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

    public ArrayList<Emplacement> getAllEmplas() {
        String url = Statics.BASE_URL + "/apiEmp/all";

        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                empls = parseEmpls(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return empls;
    }

    public boolean deleteEmplas(Emplacement l) {
        String url = Statics.BASE_URL + "/apiEmp/delete?id=" + l.getId();

        request.setUrl(url);

        System.out.println(url);

        request.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK

                System.out.println(request.getResponseCode());

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }
    public ArrayList<Emplacement> parseEmpls(String jsonText) {
        try {
            empls = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                int id = (int)Float.parseFloat(obj.get("id").toString());               
                String adresse = obj.get("adresse").toString();
                String classe = obj.get("classe").toString();
                int capaciteStockage = (int)Float.parseFloat(obj.get("capaciteStockage").toString());
                int quantiteStocker = (int)Float.parseFloat(obj.get("quantiteStocker").toString());
                Entrepot entrepot = (Entrepot)obj.get("entrepot");
                empls.add(new Emplacement( id, adresse, capaciteStockage, quantiteStocker, classe, entrepot));
            }

        } catch (IOException ex) {
        }

        return empls;
    }
}
