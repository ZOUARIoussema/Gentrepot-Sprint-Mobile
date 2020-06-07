/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.entrepot.models.BonRetour;
import com.entrepot.utls.DataSource;
import com.entrepot.utls.Statics;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author oussema
 */
public class ServiceBonRetour {
    private ConnectionRequest request;
    private boolean responseResult;
    public ArrayList<BonRetour> tasks;
    
    public ServiceBonRetour () {
        request = DataSource.getInstance().getRequest();
    }
    
    public boolean addBonRetour(BonRetour br) {
        String url = Statics.BASE_URL + "/apiBR/addBonRetour" ;
        request.setUrl(url);
        request.addRequestHeader("X-Requested-With", "XMLHttpRequest");

        request.addArgument("cap", br.getCap()+ "");
        request.addArgument("date", br.getDate() + "");
        request.addArgument("motif", br.getMotif());

        
        System.out.println(url);
        
        
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
    
    public  ArrayList<BonRetour> getListbonRetour(Map m){
        ArrayList<BonRetour> listBonsRetour = new ArrayList<>();
        ArrayList d = (ArrayList)m.get("bonretour");
        
        for(int i = 0; i<d.size();i++){
            Map f =  (Map) d.get(i);
            BonRetour p = new BonRetour();
            Double id = (Double) f.get("id");
            
            p.setId(id.intValue());
           
            Map map1 = ((Map) f.get("date"));
            Date date1 = new Date((((Double)map1.get("timestamp")).longValue()*1000)); 
           
            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            String s1 = formatter.format(date1);
           
            p.setDate(s1);
            p.setMotif((String)f.get("motifDeRetour"));
            
            Map map4 = ((Map) f.get("numeroCCommandeAp"));
             
            Double idd = (Double) map4.get("numeroC");
            
           
            p.setCap(idd.intValue());
           
            listBonsRetour.add(p);  
        }        
        return listBonsRetour;
        
    }
    
    public boolean deleteBonRetour(BonRetour be) {
        String url = Statics.BASE_URL + "/apiBR/deletebonretour/" + be.getId();
                
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
    
}
