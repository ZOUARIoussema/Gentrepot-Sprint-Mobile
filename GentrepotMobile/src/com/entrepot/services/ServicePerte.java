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
        String url = Statics.BASE_URL2 + "/apiPerte/new" ;

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
        String url = Statics.BASE_URL2 + "/apiPerte/all";

        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                perts = parseComs(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return perts;
    }

    public boolean deletePerte(Perte l) {
        String url = Statics.BASE_URL2 + " /apiPerte/delete?id=" + l.getId();

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
    public ArrayList<Perte> parseComs(String jsonText) {
        perts = new ArrayList<>();

             JSONArray jsonArray = new JSONArray(jsonText);      
             for(int i=0;i<jsonArray.length();i++)
             {
                JSONObject ob = jsonArray.getJSONObject(i);
                int idp = (int)Float.parseFloat(ob.get("id").toString());
             
                JSONObject dateCreation  = new JSONObject(ob.get("dateCreation").toString());                
                float da = Float.parseFloat(dateCreation.get("timestamp").toString()); 
                Date dCeation = new Date((long) (da - 3600) * 1000);                
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                String dp = df.format(dCeation);
                System.out.println(dp);
                
                perts.add(new Perte(idp,dp));
            }

        return perts;
    }
}
