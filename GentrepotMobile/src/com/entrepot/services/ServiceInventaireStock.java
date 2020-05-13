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
import com.entrepot.models.InventaireStock;
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
public class ServiceInventaireStock {
    private ConnectionRequest request;

    private boolean responseResult;
    public ArrayList<InventaireStock> invs;

    public ServiceInventaireStock() {
        request = DataSource.getInstance().getRequest();
    }

    public boolean addInv(InventaireStock lcom) {
        String url = Statics.BASE_URL + "/apiInvent/new" + lcom.getQuantiteTheorique() + "/" + lcom.getEcart() + "/" + lcom.getQunatiteInventiare();

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

    public ArrayList<InventaireStock> getAllInvs() {
        String url = Statics.BASE_URL + "/apiInvent/all";

        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                invs = parseInvs(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return invs;
    }

    public boolean deleteInv(InventaireStock l) {
        String url = Statics.BASE_URL + "/apiInvent/delete?id=" + l.getId();

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
    public ArrayList<InventaireStock> parseInvs(String jsonText) {
        try {
            invs = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                int id = (int)Float.parseFloat(obj.get("id").toString());
                int qunatiteInventiare = (int)Float.parseFloat(obj.get("qunatiteInventiare").toString());
                int ecart = (int)Float.parseFloat(obj.get("ecart").toString());
                int quantiteTheorique = (int)Float.parseFloat(obj.get("quantiteTheorique").toString());
                ProduitAchat produitAchat = (ProduitAchat)obj.get("produitAchat");
                String dateCreation = obj.get("dateCreation").toString();
                Emplacement emplacement = (Emplacement)obj.get("emplacement");
                invs.add(new InventaireStock( id, produitAchat, emplacement, dateCreation, qunatiteInventiare, ecart, quantiteTheorique));
            }

        } catch (IOException ex) {
        }

        return invs;
    }
}
