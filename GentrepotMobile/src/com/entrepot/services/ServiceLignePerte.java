/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.services;
import org.json.JSONObject;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.entrepot.models.Perte;
import com.entrepot.models.LignePerte;
import com.entrepot.models.ProduitAchat;
import com.entrepot.utls.DataSource;
import com.entrepot.utls.Statics;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
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
        String url = Statics.BASE_URL2 + "/apiLignePerte/new?" + "refPro=" +lpert.getProduitAchat().getReference() + "&id=" +lpert.getPerte().getId() + "&quantite=" +lpert.getQuantite() + "&raisonPerte=" + lpert.getRaisonPerte();

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
        String url = Statics.BASE_URL2 + "/apiLignePerte/all";

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
        String url = Statics.BASE_URL2 + " /apiLignePerte/delete?id=" + l.getId();

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
        
            lperts = new ArrayList<>();

             JSONArray jsonArray = new JSONArray(jsonText);      
             for(int i=0;i<jsonArray.length();i++)
             {
                JSONObject ob = jsonArray.getJSONObject(i);
                int id = (int)Float.parseFloat(ob.get("id").toString());
                
                String raisonPerte = ob.get("raisonPerte").toString();
                int quantite = (int)Float.parseFloat(ob.get("quantite").toString());
                System.out.println(id); 
                JSONObject produit  = new JSONObject(ob.get("produitAchat").toString());
                ProduitAchat produitAchat = new ProduitAchat(produit.get("reference").toString());                
                JSONObject pert  = new JSONObject(ob.get("perte").toString());
                int idp = (int)Float.parseFloat(pert.get("id").toString());
                
                JSONObject dateCreation  = new JSONObject(pert.get("dateCreation").toString());                
                float da = Float.parseFloat(dateCreation.get("timestamp").toString()); 
                Date dCeation = new Date((long) (da - 3600) * 1000);                
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                String dp = df.format(dCeation);
                System.out.println(dp);
                Perte perte = new Perte(idp,dp);
                lperts.add(new LignePerte(id, perte, produitAchat, quantite, raisonPerte));
            }

        return lperts;
    }
}
