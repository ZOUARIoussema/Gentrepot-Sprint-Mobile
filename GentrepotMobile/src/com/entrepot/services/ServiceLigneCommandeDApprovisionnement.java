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
import com.entrepot.models.CommandeDApprovisionnement;
import com.entrepot.models.LigneCommandeDApprovisionnement;
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
public class ServiceLigneCommandeDApprovisionnement {
    private ConnectionRequest request;

    private boolean responseResult;
    public ArrayList<LigneCommandeDApprovisionnement> lcoms;

    public ServiceLigneCommandeDApprovisionnement() {
        request = DataSource.getInstance().getRequest();
    }

    public boolean addLCom(LigneCommandeDApprovisionnement lcom) {
        String url = Statics.BASE_URL + "/apiLigneCommandeDAp/new" + lcom.getQuantite() + "/" + lcom.getTva() + "/" + lcom.getPrix() + "/" + lcom.getPrix()*lcom.getQuantite();

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

    public ArrayList<LigneCommandeDApprovisionnement> getAllLComs() {
        String url = Statics.BASE_URL + "/apiLigneCommandeDAp/all";

        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                lcoms = parseLComs(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return lcoms;
    }

    public boolean deletePerte(LigneCommandeDApprovisionnement l) {
        String url = Statics.BASE_URL + " /apiLigneCommandeDAp/delete?id=" + l.getId();

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
    public ArrayList<LigneCommandeDApprovisionnement> parseLComs(String jsonText) {
        try {
            lcoms = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                int id = (int)Float.parseFloat(obj.get("id").toString());
                double prix = (double)Float.parseFloat(obj.get("prix").toString());
                double tva = (double)Float.parseFloat(obj.get("tva").toString());
                double total = (double)Float.parseFloat(obj.get("total").toString());
                int quantite = (int)Float.parseFloat(obj.get("quantite").toString());
                ProduitAchat produitAchat = (ProduitAchat)obj.get("produitAchat");
                CommandeDApprovisionnement commandeDApprovisionnement = (CommandeDApprovisionnement)obj.get("commandeDApprovisionnement");
                lcoms.add(new LigneCommandeDApprovisionnement( id, commandeDApprovisionnement, produitAchat, prix, quantite,  total, tva));
            }

        } catch (IOException ex) {
        }

        return lcoms;
    }
}
