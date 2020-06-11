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
import com.entrepot.models.Perte;
import com.entrepot.models.LignePerte;
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
public class ServiceLignePerte {
    private ConnectionRequest request;

    private boolean responseResult;
    public ArrayList<LignePerte> lperts;

    public ServiceLignePerte() {
        request = DataSource.getInstance().getRequest();
    }

    public boolean addLPerte(LignePerte lpert) {
        String url = Statics.BASE_URL + "/apiLignePerte/new?refPro=" +lpert.getProduitAchat().getReference() + "&id=" +lpert.getPerte().getId() + "&quantite=" +lpert.getQuantite() + "&raisonPerte=" + lpert.getRaisonPerte();

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

    public ArrayList<LignePerte> getAllLPertes() {
        String url = Statics.BASE_URL + "/apiLignePerte/all";
                                          
        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                lperts = parseLComs(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return lperts;
    }

    public boolean deleteLPerte(LignePerte l) {
        String url = Statics.BASE_URL + "/apiLignePerte/delete?id=" + l.getId();

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
    public ArrayList<LignePerte> parseLComs(String jsonText) {
        try {
            lperts = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> ob : list) {

                int id = (int) Float.parseFloat(ob.get("id").toString());
                String raisonPerte = ob.get("raisonPerte").toString();
                int quantite = (int)Float.parseFloat(ob.get("quantite").toString());
                
                Map<String, Object> f = (Map<String, Object>) ob.get("produit");
                String ref = f.get("reference").toString();
                String libelle = f.get("libelle").toString();
                ProduitAchat produitAchat = new ProduitAchat(ref, libelle);
                Map<String, Object> p = (Map<String, Object>) ob.get("perte");
                int idp = (int)Float.parseFloat(p.get("id").toString());
                
                Map<String, Object> dated = (Map<String, Object>) p.get("dateCreation");
                float da = Float.parseFloat(dated.get("timestamp").toString());
                Date dp = new Date((long) (da - 3600) * 1000);
                Perte perte = new Perte(idp,dp);
                
                lperts.add(new LignePerte(id, perte, produitAchat, quantite, raisonPerte));
                
            }

        } catch (IOException ex) {
        }

        return lperts;
        
        
    }
}
