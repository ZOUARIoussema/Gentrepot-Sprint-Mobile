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
import com.entrepot.models.BonEntree;
import com.entrepot.utls.DataSource;
import com.entrepot.utls.Statics;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author oussema
 */
public class ServiceBonEntree {
    private ConnectionRequest request;
    private boolean responseResult;
    public ArrayList<BonEntree> tasks;
    
    public ServiceBonEntree () {
        request = DataSource.getInstance().getRequest();
    }
    
    public boolean addBonEntree(BonEntree be) {
        String url = Statics.BASE_URL + "/apiBE/addBonEntree" ;
        request.setUrl(url);
        request.addRequestHeader("X-Requested-With", "XMLHttpRequest");

        
        request.addArgument("cap", be.getCap()+ "");
        request.addArgument("date", be.getDate() + "");
        request.addArgument("dateProd", be.getDateProduction()+"");
        request.addArgument("dateExp", be.getDateExpiration()+"");  
        
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
    
    
    
    public  ArrayList<BonEntree> getListbonEntree(Map m){
        ArrayList<BonEntree> listeBonsEntree = new ArrayList<>();
        ArrayList d = (ArrayList)m.get("bonEntree");
       

        for(int i = 0; i<d.size();i++){
            Map f =  (Map) d.get(i);
            BonEntree p = new BonEntree();
            Double id = (Double) f.get("id");
            
            p.setId(id.intValue());
           
            Map map1 = ((Map) f.get("date"));
            Map map2 = ((Map) f.get("dateProduction"));
            Map map3 = ((Map) f.get("dateExpiration"));
            Date date1 = new Date((((Double)map1.get("timestamp")).longValue()*1000)); 
            Date date2 = new Date((((Double)map2.get("timestamp")).longValue()*1000));
            Date date3 = new Date((((Double)map3.get("timestamp")).longValue()*1000));
            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            String s1 = formatter.format(date1);
            String s2 = formatter.format(date2);
            String s3 = formatter.format(date3);
            p.setDate(s1);
            p.setDateExpiration(s3);
            p.setDateProduction(s2);
            
            Map map4 = ((Map) f.get("numeroCCommandeAp"));
             
            Double idd = (Double) map4.get("numeroC");
            
           
            p.setCap(idd.intValue());
           
            listeBonsEntree.add(p);  
        }        
        return listeBonsEntree;
        
    }
    
    
    public  ArrayList<BonEntree> getListbonEntreesorted(Map m){
        ArrayList<BonEntree> listeBonsEntree = new ArrayList<>();
        ArrayList d = (ArrayList)m.get("bonEntree");
       

        for(int i = 0; i<d.size();i++){
            Map f =  (Map) d.get(i);
            BonEntree p = new BonEntree();
            Double id = (Double) f.get("id");
            
            p.setId(id.intValue());
           
            Map map1 = ((Map) f.get("date"));
            Map map2 = ((Map) f.get("dateProduction"));
            Map map3 = ((Map) f.get("dateExpiration"));
            Date date1 = new Date((((Double)map1.get("timestamp")).longValue()*1000)); 
            Date date2 = new Date((((Double)map2.get("timestamp")).longValue()*1000));
            Date date3 = new Date((((Double)map3.get("timestamp")).longValue()*1000));
            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            String s1 = formatter.format(date1);
            String s2 = formatter.format(date2);
            String s3 = formatter.format(date3);
            p.setDate(s1);
            p.setDateExpiration(s3);
            p.setDateProduction(s2);
            
            Map map4 = ((Map) f.get("numeroCCommandeAp"));
             
            Double idd = (Double) map4.get("numeroC");
            
           
            p.setCap(idd.intValue());
           
            listeBonsEntree.add(p);  
        }  
        Collections.sort(listeBonsEntree);
        return listeBonsEntree;
        
    }
    
    public boolean deleteBonEntree(BonEntree be) {
        String url = Statics.BASE_URL + "/apiBE/deletebonentree/" + be.getId();
                
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
