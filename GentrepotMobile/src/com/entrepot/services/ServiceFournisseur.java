

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
import com.entrepot.models.Fournisseur;
import com.entrepot.utls.DataSource;
import com.entrepot.utls.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author oussema
 */
public class ServiceFournisseur {
    private ConnectionRequest request;
    private boolean responseResult;
    public ArrayList<Fournisseur> fours;
    
    public ServiceFournisseur () {
        request = DataSource.getInstance().getRequest();
    }
    
    public boolean addFournisseur(Fournisseur fournisseur) {
        
        String url = Statics.BASE_URL + "/apiF/addF" ;
        request.setUrl(url);
        request.addRequestHeader("X-Requested-With", "XMLHttpRequest");
        if (!this.verifParMatricule(fournisseur.getMatriculeFiscale())) {

        request.addArgument("raisonSociale", fournisseur.getRaisonSociale());
        request.addArgument("numeroTelephone", fournisseur.getNumeroTelephone() + "");
        request.addArgument("adresse", fournisseur.getAdresse());
        request.addArgument("adresseMail", fournisseur.getAdresseMail());
        request.addArgument("matriculeFiscale", fournisseur.getMatriculeFiscale());
        request.addArgument("codePostale", fournisseur.getCodePostale() + "");

        
        System.out.println(url);
        
        
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        
    }else{
            responseResult= false;
            Dialog.show("Alerte", "Ce fournisseur existe deja !", "OK", null);
        }
     return responseResult;   
    }
    
    public boolean editFournisseur(Fournisseur f) {
        String url = Statics.BASE_URL + "/apiF/editF" ;
        request.setUrl(url);
        request.addRequestHeader("X-Requested-With", "XMLHttpRequest");

        request.addArgument("raisonSociale", f.getRaisonSociale());
        request.addArgument("id", f.getId() + "");
        request.addArgument("numeroTelephone", f.getNumeroTelephone() + "");
        request.addArgument("adresse", f.getAdresse());
        request.addArgument("adresseMail", f.getAdresseMail());
        request.addArgument("matriculeFiscale", f.getMatriculeFiscale());
        request.addArgument("codePostale", f.getCodePostale() + "");

        
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
    
    
    
    public  ArrayList<Fournisseur> getListFournisseurs(Map m){
        ArrayList<Fournisseur> listFournisseur = new ArrayList<>();
        ArrayList d = (ArrayList)m.get("fournisseur");
       
        for(int i = 0; i<d.size();i++){
            Map f =  (Map) d.get(i);
            Fournisseur p = new Fournisseur();
            Double id = (Double) f.get("id");
            
            p.setId(id.intValue());
            
            p.setRaisonSociale((String)f.get("raisonSociale"));
            Double numtlf = (Double) f.get("numeroTelephone");
            p.setNumeroTelephone(numtlf.intValue());
           
            p.setAdresse((String) f.get("adresse"));
            p.setAdresseMail((String) f.get("adresseMail"));
            Double ll = (Double) f.get("codePostale");
            p.setCodePostale(ll.intValue());
            p.setMatriculeFiscale((String) f.get("matriculeFiscale"));
          
          
            listFournisseur.add(p);  
        }        
        return listFournisseur;
        
    }
    
    public  ArrayList<Fournisseur> getListFournisseursSorted(Map m){
        ArrayList<Fournisseur> listFournisseur = new ArrayList<>();
        ArrayList d = (ArrayList)m.get("fournisseur");
        
       
        for(int i = 0; i<d.size();i++){
            Map f =  (Map) d.get(i);
            Fournisseur p = new Fournisseur();
            Double id = (Double) f.get("id");
            
            p.setId(id.intValue());
            
            p.setRaisonSociale((String)f.get("raisonSociale"));
            Double numtlf = (Double) f.get("numeroTelephone");
            p.setNumeroTelephone(numtlf.intValue());
           
            p.setAdresse((String) f.get("adresse"));
            p.setAdresseMail((String) f.get("adresseMail"));
            Double ll = (Double) f.get("codePostale");
            p.setCodePostale(ll.intValue());
            p.setMatriculeFiscale((String) f.get("matriculeFiscale"));
          
          
            listFournisseur.add(p);  
            
	   
        }   
        Collections.sort(listFournisseur);
        
        return listFournisseur;
        
    }
    
    public boolean deleteFournisseur(Fournisseur f) {
        String url = Statics.BASE_URL + "/apiF/deleteF/" + f.getId();
                
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
    
    public boolean verifParMatricule(String mt) {
        ServiceProduitAchat sp = new ServiceProduitAchat();
        Map x = sp.getResponse("/apiF/listF");

        for (Fournisseur f : this.getListFournisseurs(x)) {

            if (f.getMatriculeFiscale().toUpperCase().equals(mt.toUpperCase())) {
                return true;
            }

        }

        return false;
    }
    
   
    
    public ArrayList<Fournisseur> getAllFournisseurs() {
        String url = Statics.BASE_URL + "/apiF/listF";

        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                fours = parseFours(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return fours;
    }
    
    public Fournisseur chercherFour(ArrayList<Fournisseur> l, String m){
        for(int i=0;i < l.size();i++){
            if(l.get(i).getAdresseMail().equals(m)) return l.get(i);
        }
        return null;
    }
    
   
    public ArrayList<Fournisseur> parseFours(String jsonText) {
        try {
            fours = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                
                int idp = (int) Float.parseFloat(obj.get("id").toString());
                String rs = obj.get("raisonSociale").toString();
                String adr = obj.get("adresse").toString();
                String adrmail = obj.get("adresseMail").toString();
                int tel = (int)Double.parseDouble(obj.get("numeroTelephone").toString());
                int code = (int)Double.parseDouble(obj.get("codePostale").toString());
                String matri = obj.get("matriculeFiscale").toString();
                fours.add(new Fournisseur(idp,rs,tel,adr,adrmail,matri,code));
            }
        } catch (IOException ex) {
        }

        return fours;
       
    }
    
}


