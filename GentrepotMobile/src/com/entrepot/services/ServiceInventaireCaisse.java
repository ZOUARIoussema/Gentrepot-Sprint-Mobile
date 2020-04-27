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
import com.entrepot.models.InventaireCaisse;

import com.entrepot.utls.DataSource;
import com.entrepot.utls.Statics;
import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author oussema
 */
public class ServiceInventaireCaisse {

    private ConnectionRequest request;

    public static double soldeCheque = 0;
    public static double soldeEspece = 0;

    private boolean responseResult;
    public ArrayList<InventaireCaisse> inventaires;

    public ServiceInventaireCaisse() {
        request = DataSource.getInstance().getRequest();
    }

    public boolean addinventaireCaisse(InventaireCaisse inv) {
        String url = Statics.URL_t + "/apiInventaire/add?soldeCheque=" + inv.getSoldeCheque()
                + "&soldeEspece=" + inv.getSoldeEspece() + "&soldeTheorique="
                + inv.getSoldeTheorique() + "&dateC=" + inv.getDateCreation() + "&soldecalculer=" + inv.getSoldeCalculer()
                + "&ecart=" + inv.getEcart();
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

    public ArrayList<InventaireCaisse> parseInventaire(String jsonText) {

        try {
            inventaires = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                int id = (int) Float.parseFloat(obj.get("id").toString());

                double soldeCheque = Float.parseFloat(obj.get("soldeCheque").toString());
                double soldeEspece = Float.parseFloat(obj.get("soldeEspece").toString());

                Map<String, Object> dated = (Map<String, Object>) obj.get("dateCreation");
                float da = Float.parseFloat(dated.get("timestamp").toString());
                Date d = new Date((long) (da - 3600) * 1000);

                //  String ch=new SimpleDateFormat("MM-dd-yyyy").format(d);
                double soldeCalculer = Float.parseFloat(obj.get("soldeCalculer").toString());
                double soldeTheorique = Float.parseFloat(obj.get("soldeTheorique").toString());
                double ecart = Float.parseFloat(obj.get("ecart").toString());

                inventaires.add(new InventaireCaisse(d, soldeCalculer, soldeTheorique, soldeCheque, soldeEspece, ecart));

            }

        } catch (IOException ex) {
        }

        return inventaires;
    }

    public ArrayList<InventaireCaisse> getAllInventaire() {
        String url = Statics.URL_t + "/apiInventaire/findAll";

        System.out.println(url);

        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                inventaires = parseInventaire(new String(request.getResponseData()));
                request.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return inventaires;
    }

    public void parseRecupere() {

        String url = Statics.URL_t + "/apiInventaire/recupere";

        System.out.println(url);

        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                  
                try {

                    JSONParser jp = new JSONParser();
                    
                    
                    System.out.println(new String(request.getResponseData()));
                    
                    
                   
                    
                    Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(new String(request.getResponseData()).toCharArray()));

                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
                    for (Map<String, Object> obj : list) {

                        
                        System.out.println("parse "+ (obj.get("cheque").toString()));
                        
                        soldeCheque = Float.parseFloat(obj.get("cheque").toString());
                        soldeEspece = Float.parseFloat(obj.get("espece").toString());

                        return;
                    }

                } catch (IOException ex) {

                    System.out.println(ex.getMessage());
                }

                request.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

    }

}
