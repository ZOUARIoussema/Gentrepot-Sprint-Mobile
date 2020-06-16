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
import java.util.Date;
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
        String url = Statics.BASE_URL + "/apiCommandeDAp/new?totalTva=" + com.getTotalTva() + "&totalC=" + com.getTotalC()  + "&tauxRemise=" + com.getTauxRemise() + "&id=" + com.getFournisseur().getId();

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
            for (Map<String, Object> ob : list) {
              
                int numeroC = (int)Float.parseFloat(ob.get("numeroC").toString());
                double totalC = (double)Float.parseFloat(ob.get("numeroC").toString());
                double tauxRemise = (double)Float.parseFloat(ob.get("tauxRemise").toString());
                double totalTva = (double)Float.parseFloat(ob.get("totalTVA").toString());
                String status = ob.get("etat").toString();
                Map<String, Object> dateCreation = (Map<String, Object>) ob.get("dateCreation");
                float da = Float.parseFloat(dateCreation.get("timestamp").toString());
                Date dp = new Date((long) (da - 3600) * 1000);               
                String etat = ob.get("etat").toString();
                Map<String, Object> fo = (Map<String, Object>) ob.get("fournisseur");
                int idf = (int)Float.parseFloat(fo.get("id").toString());
                String adrf = fo.get("adresseMail").toString();
                Fournisseur fournisseur = new Fournisseur(idf, adrf);
                
                coms.add(new CommandeDApprovisionnement(numeroC, totalC, dp, etat, tauxRemise, totalTva, fournisseur));
               
            }

        } catch (IOException ex) {
        }
        return coms;   
    }
    
}
