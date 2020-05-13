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
import com.entrepot.models.Fournisseur;
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
public class ServiceCommandeDApprovisionnment {
    private ConnectionRequest request;

    private boolean responseResult;
    public ArrayList<CommandeDApprovisionnement> coms;

    public ServiceCommandeDApprovisionnment() {
        request = DataSource.getInstance().getRequest();
    }

    public boolean addCom(CommandeDApprovisionnement com) {
        String url = Statics.BASE_URL + "/apiCommandeDAp/new" + com.getEtat() + "/" + com.getTotalTva() + "/" + com.getTotalC() + "/" + com.getTauxRemise();

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

    public ArrayList<CommandeDApprovisionnement> getAllComs() {
        String url = Statics.BASE_URL + "/apiCommandeDAp/all";

        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                coms = parseComs(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return coms;
    }

    public boolean deletePerte(CommandeDApprovisionnement l) {
        String url = Statics.BASE_URL + " /apiCommandeDAp/delete?numeroC=" + l.getNumeroC();

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
    public ArrayList<CommandeDApprovisionnement> parseComs(String jsonText) {
        try {
            coms = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                int numeroC = (int)Float.parseFloat(obj.get("numeroC").toString());
                double totalC = (double)Float.parseFloat(obj.get("numeroC").toString());
                double tauxRemise = (double)Float.parseFloat(obj.get("tauxRemise").toString());
                double totalTva = (double)Float.parseFloat(obj.get("totalTva").toString());
                int status = (int)Float.parseFloat(obj.get("status").toString());
                String dateCreation = obj.get("dateCreation").toString();
                String etat = obj.get("etat").toString();
                Fournisseur fournisseur = (Fournisseur)obj.get("fournisseur");
                coms.add(new CommandeDApprovisionnement(numeroC, totalC, dateCreation, etat, tauxRemise, totalTva, fournisseur));
            }

        } catch (IOException ex) {
        }

        return coms;
    }
    
}
