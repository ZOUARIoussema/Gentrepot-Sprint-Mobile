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
import com.entrepot.models.AideChauffeur;
import com.entrepot.models.BonLivraison;
import com.entrepot.models.Chauffeur;
import com.entrepot.models.OrdreMission;
import com.entrepot.models.Vehicule;
import com.entrepot.utls.DataSource;
import com.entrepot.utls.Statics;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author oussema
 */
public class ServiceOrdreMission {
      private ConnectionRequest request;

    private boolean responseResult;
    public ArrayList<OrdreMission> tasks;
    ServiceVehicule sv = new ServiceVehicule();
    ServiceChauffeur c = new ServiceChauffeur();
       ServiceAideChauffeur ca = new ServiceAideChauffeur();
    public ServiceOrdreMission () {
        request = DataSource.getInstance().getRequest();
    }
    public boolean addOrdreMission (OrdreMission o) {
        
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        String dateRString = format.format(o.getDateRetour());
        
          String dateSString = format.format(o.getDateSortie());
        
        
        
        
        String url = Statics.BASE_URL + "/apiordre/ajout"+"?vehicule="+ o.getVehicule().getId()+ "&chauffeur=" + o.getChauffeur().getCin()+ "&Aidechauffeur=" + o.getAideChauffeur().getCin()
                + "&dateCreation=" +o.getDateCreation() + "&dateSortie=" +dateSString + "&dateRetour=" +dateRString
                +"&id="+o.getId();
  
         System.out.println(url);
        
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
    public ArrayList<OrdreMission> getAllOrdrer() {
        String url = Statics.BASE_URL + "/apiordre/affiche";
        
        System.out.println(url);

        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseTasks(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return tasks;
    }

    public ArrayList<OrdreMission> parseTasks(String jsonText) {
        try {
            tasks = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                
               
               /* int mat = Integer.parseInt(obj.get("matricule").toString());
                Vehicule v = sv.getVehiculeByMat(mat);
                
                String cin = (obj.get("cin").toString());
                Chauffeur ch = c.getChauffeurByCin(cin);
                
               String Cin =(obj.get("cin").toString());
                AideChauffeur cha = ca.getAideChauffeurByCin(Cin);*/
               
                Map<String, Object> b = (Map<String, Object>) obj.get("bondelivraison ");
                int BonLiv = (int) Float.parseFloat(b.get("id").toString());
                
                  Map<String, Object> bl = (Map<String, Object>) obj.get("AideChauffeur ");
                String aide = (bl.get("cin").toString());
               
                 Map<String, Object> bliv = (Map<String, Object>) obj.get("Chauffeur ");
                String chauff= (bliv.get("cin").toString());
                
               Map<String, Object> bli = (Map<String, Object>) obj.get("vehicule ");
                int v = (int) Float.parseFloat(bli.get("id").toString());
                
                 /*Date dateSortie = (Date) (obj.get("datesortie"));
                  Date dateRetour = (Date) (obj.get("dateretour"));
                   Date dateCreation = (Date) (obj.get("datecreation"));*/
                 
                  Map<String, Object> dated = (Map<String, Object>) obj.get("datecreation");
                float da = Float.parseFloat(dated.get("timestamp").toString());
                Date dateCreation = new Date((long) (da - 3600) * 1000);
                
             Map<String, Object> date = (Map<String, Object>) obj.get("datesortie");
                float dat = Float.parseFloat(date.get("timestamp").toString());
                 Date dateSortie = new Date((long) (dat - 3600) * 1000);
                 
                   Map<String, Object> d= (Map<String, Object>) obj.get("dateretour"); 
                 float datr = Float.parseFloat(d.get("timestamp").toString());
                 Date dateRetour = new Date((long) (datr - 3600) * 1000);
                
                tasks.add(new OrdreMission(new Vehicule(v), new Chauffeur(chauff), new AideChauffeur(aide), dateCreation, dateSortie, dateRetour, (List<BonLivraison>) new BonLivraison(BonLiv)));
            }

        } catch (IOException ex) {
        }

        return tasks;
    }

  
}
