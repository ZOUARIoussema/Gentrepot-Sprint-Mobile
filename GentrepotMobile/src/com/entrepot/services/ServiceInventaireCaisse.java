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
import com.entrepot.models.User;
import com.entrepot.utls.DataSource;
import com.entrepot.utls.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author oussema
 */
public class ServiceInventaireCaisse {
    
    
     private ConnectionRequest request;

    private boolean responseResult;
    public ArrayList<User> users;

    public ServiceInventaireCaisse() {
        request = DataSource.getInstance().getRequest();
    }

    public boolean addinventaireCaisse(InventaireCaisse inv) {
        String url = Statics.URL_t + "/apiInventaire/add?soldeCheque=" + inv.getSoldeCheque() 
                + "&soldeEspece=" + inv.getSoldeEspece()+ "&soldeTheorique=" 
                + inv.getSoldeTheorique()+"&dateC="+inv.getDateCreation()+"&soldecalculer="+inv.getSoldeCalculer()
                +"&ecart"+inv.getEcart();
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

    public ArrayList<User> parseUsers(String jsonText) {
        try {
            users = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                int id = (int) Float.parseFloat(obj.get("id").toString());
                String username = obj.get("username").toString();
                // String usernameCanonical = obj.get("usernameCanonical").toString();
                // String email = obj.get("email").toString();
                //String emailCanonical = obj.get("emailCanonical").toString();
                //  String password = obj.get("password").toString();
                //    String roles = obj.get("password").toString();
                users.add(new User(id, username));
            }

        } catch (IOException ex) {
        }

        return users;
    }

    
    
}
