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
import java.util.ArrayList;

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
        String url = Statics.BASE_URL + "/apiBR/addBonRetour" ;
        request.setUrl(url);
        request.addRequestHeader("X-Requested-With", "XMLHttpRequest");

        
        request.addArgument("cap", be.getCommandeDApprovisionnement() + "");
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
    
}
