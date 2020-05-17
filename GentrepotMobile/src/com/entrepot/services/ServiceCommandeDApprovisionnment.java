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
import com.entrepot.models.CommandeDApprovisionnement;
import com.entrepot.models.Emplacement;
import com.entrepot.models.Fournisseur;
import com.entrepot.models.InventaireStock;
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
public class ServiceCommandeDApprovisionnment {
    private ConnectionRequest request;

    private boolean responseResult;
    public ArrayList<CommandeDApprovisionnement> coms;

    public ServiceCommandeDApprovisionnment() {
        request = DataSource.getInstance().getRequest();
    }

    public boolean addCom(CommandeDApprovisionnement com) {
        String url = Statics.BASE_URL2 + "/apiCommandeDAp/new?totalTva=" + com.getTotalTva() + "&totalC=" + com.getTotalC()  + "&tauxRemise=" + com.getTauxRemise() + "&id=" + com.getFournisseur().getId();

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
        String url = Statics.BASE_URL2 + "/apiCommandeDAp/all";

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
        String url = Statics.BASE_URL2 + " /apiCommandeDAp/delete?numeroC=" + l.getNumeroC();

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
        coms = new ArrayList<>();

             JSONArray jsonArray = new JSONArray(jsonText);      
             for(int i=0;i<jsonArray.length();i++)
             {
                JSONObject ob = jsonArray.getJSONObject(i);
                int numeroC = (int)Float.parseFloat(ob.get("numeroC").toString());
                double totalC = (double)Float.parseFloat(ob.get("numeroC").toString());
                double tauxRemise = (double)Float.parseFloat(ob.get("tauxRemise").toString());
                double totalTva = (double)Float.parseFloat(ob.get("totalTva").toString());
                int status = (int)Float.parseFloat(ob.get("status").toString());
                JSONObject dateCreation  = new JSONObject(ob.get("dateCreation").toString());                
                float da = Float.parseFloat(dateCreation.get("timestamp").toString()); 
                Date dCeation = new Date((long) (da - 3600) * 1000);                
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                String dp = df.format(dCeation);
                String etat = ob.get("etat").toString();
                JSONObject four  = new JSONObject(ob.get("fournisseur").toString());
                Fournisseur fournisseur = new Fournisseur((int)Float.parseFloat(four.get("id").toString()));
                
                coms.add(new CommandeDApprovisionnement(numeroC, totalC, dp, etat, tauxRemise, totalTva, fournisseur));
            }

        return coms;
        
    }
    
}
