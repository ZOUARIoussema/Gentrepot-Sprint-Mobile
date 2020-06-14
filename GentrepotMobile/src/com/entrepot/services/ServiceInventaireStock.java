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
import java.util.Date;
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
        String url = Statics.BASE_URL + "/apiInvent/new?qteinv=" + lcom.getQunatiteInventiare()+ "&id=" + lcom.getEmplacement().getId() + "&reference=" + lcom.getProduitAchat().getReference();

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
            for (Map<String, Object> ob : list) {

                int id = (int)Float.parseFloat(ob.get("id").toString());
                int qunatiteInventiare = (int)Float.parseFloat(ob.get("quantiteInventaire").toString());
                int ecart = (int)Float.parseFloat(ob.get("ecart").toString());
                int quantiteTheorique = (int)Float.parseFloat(ob.get("quantiteTheorique").toString());
                Map<String, Object> dated = (Map<String, Object>) ob.get("dateCreation");
                float da = Float.parseFloat(dated.get("timestamp").toString());
                Date dp = new Date((long) (da - 3600) * 1000);
                Map<String, Object> f = (Map<String, Object>) ob.get("produit");
                String ref = f.get("reference").toString();
                String libelle = f.get("libelle").toString();
                ProduitAchat produitAchat = new ProduitAchat(ref, libelle);
                Map<String, Object> e = (Map<String, Object>) ob.get("emplacement");
                String adr = e.get("adresse").toString();
                String cl = e.get("classe").toString();
                int qt = (int)Float.parseFloat(e.get("quantiteStocker").toString());        
                int cp = (int)Float.parseFloat(e.get("capaciteStockage").toString());        
                int ide = (int)Float.parseFloat(e.get("id").toString());
                Emplacement emplacement = new Emplacement(ide,adr,cp,qt,cl);               
                invs.add(new InventaireStock( id, produitAchat, emplacement, dp, qunatiteInventiare, ecart, quantiteTheorique));
                        }

        } catch (IOException ex) {
        }

        return invs;
        
    }
}
