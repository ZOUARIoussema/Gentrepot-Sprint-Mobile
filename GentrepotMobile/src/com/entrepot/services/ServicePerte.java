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
import com.entrepot.models.FactureVente;
import com.entrepot.models.LettreDeRelance;
import com.entrepot.models.Perte;
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
 * @author rodrigue
 */
public class ServicePerte {
    private ConnectionRequest request;

    private boolean responseResult;
    public ArrayList<Perte> perts;

    public ServicePerte() {
        request = DataSource.getInstance().getRequest();
    }

    public boolean addPerte(Perte pert) {
        String url = Statics.BASE_URL + "/apiPerte/new" ;

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

    public ArrayList<Perte> getAllPertes() {
        String url = Statics.BASE_URL + "/apiPerte/all";

        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                perts = parsePertes(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return perts;
    }

    public boolean deletePerte(Perte l) {
        String url = Statics.BASE_URL + " /apiPerte/delete?id=" + l.getId();

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
    public ArrayList<Perte> parsePertes(String jsonText) {
        try {
            perts = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                
                int idp = (int) Float.parseFloat(obj.get("id").toString());

                Map<String, Object> dated = (Map<String, Object>) obj.get("dateCreation");
                float da = Float.parseFloat(dated.get("timestamp").toString());
                Date dp = new Date((long) (da - 3600) * 1000);
               
                perts.add(new Perte(idp,dp));
            }

        } catch (IOException ex) {
        }

        return perts;
       
    }
}
