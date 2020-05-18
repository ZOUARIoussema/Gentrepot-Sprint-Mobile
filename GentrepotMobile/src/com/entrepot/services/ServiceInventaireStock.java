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
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.entrepot.models.InventaireStock;
import com.entrepot.models.Emplacement;
import com.entrepot.models.Perte;
import com.entrepot.models.ProduitAchat;
import com.entrepot.utls.DataSource;
import com.entrepot.utls.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
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
        invs = new ArrayList<>();

             JSONArray jsonArray = new JSONArray(jsonText);      
             for(int i=0;i<jsonArray.length();i++)
             {
                JSONObject ob = jsonArray.getJSONObject(i);
                int id = (int)Float.parseFloat(ob.get("id").toString());
                int qunatiteInventiare = (int)Float.parseFloat(ob.get("qunatiteInventiare").toString());
                int ecart = (int)Float.parseFloat(ob.get("ecart").toString());
                int quantiteTheorique = (int)Float.parseFloat(ob.get("quantiteTheorique").toString());
                JSONObject dateCreation  = new JSONObject(ob.get("dateCreation").toString());                
                float da = Float.parseFloat(dateCreation.get("timestamp").toString()); 
                Date dCeation = new Date((long) (da - 3600) * 1000);                
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                String dp = df.format(dCeation);
                JSONObject produit  = new JSONObject(ob.get("produitAchat").toString());
                ProduitAchat produitAchat = new ProduitAchat(produit.get("reference").toString());
                JSONObject empl  = new JSONObject(ob.get("emplacement").toString());
                Emplacement emplacement = new Emplacement((int)Float.parseFloat(empl.get("id").toString()));
                
                invs.add(new InventaireStock( id, produitAchat, emplacement, dp, qunatiteInventiare, ecart, quantiteTheorique));
            
            }

        return invs;
        
    }
}
