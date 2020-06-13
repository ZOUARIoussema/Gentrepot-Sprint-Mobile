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
import com.entrepot.models.LigneCommandeDApprovisionnement;
import com.entrepot.models.ProduitAchat;
import com.entrepot.utls.DataSource;
import com.entrepot.utls.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
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
        String url = Statics.BASE_URL + "/apiLigneCommandeDAp/new?qte=" + lcom.getQuantite() + "&tva=" + lcom.getTva() + "&prix=" + lcom.getPrix() + "&total=" + lcom.getPrix()*lcom.getQuantite()+ "&numeroC=" + lcom.getCommandeDApprovisionnement().getNumeroC() + "&refPro=" +lcom.getProduitAchat().getReference();

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

    public boolean deleteCom(LigneCommandeDApprovisionnement l) {
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
            for (Map<String, Object> ob : list) {

                int id = (int)Float.parseFloat(ob.get("id").toString());
                double prix = (double)Float.parseFloat(ob.get("prix").toString());
                double tva = (double)Float.parseFloat(ob.get("tva").toString());
                double total = (double)Float.parseFloat(ob.get("total").toString());
                int quantite = (int)Float.parseFloat(ob.get("quantite").toString()); 
                Map<String, Object> f = (Map<String, Object>) ob.get("produit");
                String ref = f.get("reference").toString();
                String libelle = f.get("libelle").toString();
                ProduitAchat produitAchat = new ProduitAchat(ref, libelle); 
                Map<String, Object> com = (Map<String, Object>) ob.get("commande");
                int numeroC = (int)Float.parseFloat(com.get("numeroC").toString());
                Map<String, Object> fo = (Map<String, Object>) com.get("fournisseur");
                int idf = (int)Float.parseFloat(fo.get("id").toString());
                String adrf = fo.get("adresseMail").toString();
                Map<String, Object> dated = (Map<String, Object>) com.get("dateCreation");
                float da = Float.parseFloat(dated.get("timestamp").toString());
                Date dp = new Date((long) (da - 3600) * 1000);
                //JSONObject dateCreation  = new JSONObject(ob.get("dateCreation").toString());                
                
                CommandeDApprovisionnement commandeDApprovisionnement = new CommandeDApprovisionnement(numeroC,dp,new Fournisseur(idf,adrf));
                lcoms.add(new LigneCommandeDApprovisionnement( id, commandeDApprovisionnement, produitAchat, prix, quantite,  total, tva));
            }

        } catch (IOException ex) {
        }

        return lcoms;
       
    }
}
