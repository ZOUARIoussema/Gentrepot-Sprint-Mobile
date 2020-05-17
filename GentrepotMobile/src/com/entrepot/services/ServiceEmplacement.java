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
import com.entrepot.models.Entrepot;
import com.entrepot.models.Emplacement;
import com.entrepot.models.ProduitAchat;
import com.entrepot.utls.DataSource;
import com.entrepot.utls.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 *
 * @author oussema
 */
public class ServiceEmplacement {
    private ConnectionRequest request;

    private boolean responseResult;
    public ArrayList<Emplacement> empls;

    public ServiceEmplacement() {
        request = DataSource.getInstance().getRequest();
    }

    public boolean addEmplas(Emplacement lpert) {
        String url = Statics.BASE_URL2 + "/apiEmp/new?adresse=" + lpert.getAdresse() + "&capaciteStockage=" + lpert.getCapaciteStockage() + "&quantiteStocker" + lpert.getQuantiteStocker() + "&classe=" + lpert.getClasse() + "&matriculeFiscal=oo";
        
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

    public ArrayList<Emplacement> getAllEmplas() {
        String url = Statics.BASE_URL2 + "/apiEmp/all";

        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                empls = parseEmpls(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return empls;
    }

    public boolean deleteEmplas(Emplacement l) {
        String url = Statics.BASE_URL + "/apiEmp/delete?id=" + l.getId();

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
    public ArrayList<Emplacement> parseEmpls(String jsonText) {
        
             empls = new ArrayList<>();
             JSONArray jsonArray = new JSONArray(jsonText);      
             for(int i=0;i<jsonArray.length();i++)
             {
                JSONObject ob = jsonArray.getJSONObject(i);
                int id = (int)Float.parseFloat(ob.get("id").toString());               
                String adresse = ob.get("adresse").toString();
                String classe = ob.get("classe").toString();
                int capaciteStockage = (int)Float.parseFloat(ob.get("capaciteStockage").toString());
                int quantiteStocker = (int)Float.parseFloat(ob.get("quantiteStocker").toString());
                //String ent = ob.get("entrepot").toString().getString("matriculeFiscal");
                
                Entrepot entrepot = new Entrepot("MA02SA");
                Emplacement e = new Emplacement( id, adresse, capaciteStockage, quantiteStocker, classe, entrepot);
                
                empls.add(new Emplacement( id, adresse, capaciteStockage, quantiteStocker, classe, entrepot));
            
             }

        return empls;
    }
}
