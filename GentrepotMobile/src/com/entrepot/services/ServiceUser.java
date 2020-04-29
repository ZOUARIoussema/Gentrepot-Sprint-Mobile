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
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
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
public class ServiceUser {

    private ConnectionRequest request;

    private boolean responseResult;
    public ArrayList<User> users;

    public ServiceUser() {
        request = DataSource.getInstance().getRequest();
    }

    public boolean addUser(User user) {
        String url = Statics.URL_t + "/apiUser/add?username=" + user.getUsername() + "&email=" + user.getEmail()
                + "&password=" +Password.hashPassword(user.getPassword())+"&role="+user.getRole() ;
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
    
    
    
     public boolean modifierUser(User user) {
        String url = Statics.URL_t + "/apiUser/update?id=" + user.getId() + "&paswd=" + user.getPassword();
             
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

    public ArrayList<User> getAllUsers() {
        String url = Statics.URL_t + "/apiUser/findAll";

        System.out.println(url);

        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                users = parseUsers(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return users;
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
                String email = obj.get("email").toString();
                //String emailCanonical = obj.get("emailCanonical").toString();
                String password = obj.get("password").toString();
                String roles = obj.get("roles").toString();
                users.add(new User(id, username, email, username, email,password, roles));
            }

        } catch (IOException ex) {
        }

        return users;
    }

    public User findUser(String login, String password) {

        User u = null;
          
            
        for(User us :this.getAllUsers() ){
            
             
            
           if( us.getUsername().equals(login)&&Password.checkPassword(password, us.getPassword()))
           {
               u=us;
               return u;
           }           
        }
                
        return u;

    }

}
