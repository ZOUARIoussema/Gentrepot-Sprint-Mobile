/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.entrepot.models.ProduitAchat;
import com.entrepot.models.SousCategorieAchat;
import com.entrepot.utls.DataSource;
import com.entrepot.utls.Statics;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author oussema
 */
public class ServiceProduitAchat {

    private ConnectionRequest request;
    private boolean responseResult;
    public ArrayList<ProduitAchat> produits;
    static Map h;

    public ServiceProduitAchat() {
        request = DataSource.getInstance().getRequest();
    }

    public boolean addProduit(ProduitAchat p) {
        String url = Statics.BASE_URL + "/apiP/addP";
        request.setUrl(url);
        request.addRequestHeader("X-Requested-With", "XMLHttpRequest");

        request.addArgument("ref", p.getReference());
        request.addArgument("lib", p.getLibelle());
        request.addArgument("quantiteEnStock", p.getQuantiteStock() + "");
        request.addArgument("classe", p.getClasse());
        request.addArgument("quantiteStockSecurite", p.getQuantiteStockSecurite() + "");
        request.addArgument("dernierPrixAchat", p.getDernierPrixAchat() + "");
        request.addArgument("TVA", p.getTva() + "");
        request.addArgument("dimension", p.getDimension() + "");
        request.addArgument("description", p.getDescription());
        request.addArgument("typeDeConditionnement", p.getTypeDeConditionnement());
        request.addArgument("prixVente", p.getPrixVente() + "");
        request.addArgument("image", p.getImage());
        request.addArgument("sousCat",p.getSousCategorieAchat()+"");
        request.setPost(true);

        System.out.println(url);

         request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
       /* request.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {

                byte[] data = (byte[]) evt.getMetaData();
                String s = new String(data);
                System.out.println(s);
                if (s.equals("Done")) {
                    Dialog.show("Confirmation", "success", "Ok", null);
                } else {
                    Dialog.show("Erreur", "erreur", "Ok", null);
                }
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(request);*/
        return responseResult;
    }

    public ArrayList<ProduitAchat> getAllProduits() {
        String url = Statics.BASE_URL + "/apiProduit/affiche";

        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                produits = parseProduits(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return produits;
    }

    public ArrayList<ProduitAchat> parseProduits(String jsonText) {
        try {
            produits = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> produitsListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) produitsListJson.get("root");
            for (Map<String, Object> obj : list) {
                String reference = obj.get("reference").toString();
                String libelle = obj.get("libelle").toString();

                int quantiteEnStock = (int) Float.parseFloat(obj.get("quantiteEnStock").toString());
                String classe = obj.get("classe").toString();

                Double prixVente = (double) Float.parseFloat(obj.get("prixVente").toString());
                String image = obj.get("image").toString();

                produits.add(new ProduitAchat(reference, libelle, quantiteEnStock, classe, prixVente, image));
            }

        } catch (IOException ex) {
        }

        return produits;
    }

    public static Map<String, Object> getResponse(String url) {
        url = "http://127.0.0.1:8000/" + url;

        ConnectionRequest r = new ConnectionRequest();

        r.setUrl(url);
        r.setPost(false);
        System.out.println("url   :   " + r);
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        r.setDisposeOnCompletion(dlg);
        r.addResponseListener((evt) -> {
            try {
                JSONParser p = new JSONParser();
                Reader targetReader = new InputStreamReader(new ByteArrayInputStream(r.getResponseData()));
                System.out.println(targetReader);
                h = p.parseJSON(targetReader);

            } catch (IOException ex) {
                //Logger.getLogger(MyApplication.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(r);
        return h;
    }
    
    public  ArrayList<SousCategorieAchat> getListSousCategorie(Map m){
        ArrayList<SousCategorieAchat> listDisponibilite = new ArrayList<>();
        ArrayList d = (ArrayList)m.get("sousCategorie");
        System.out.println("roooooooooot "+d);
        //Map f =  (Map) d.get(0);
        System.out.println("dddddddddddddd :::::::::"+d.size());

        for(int i = 0; i<d.size();i++){
            Map f =  (Map) d.get(i);
            SousCategorieAchat p = new SousCategorieAchat();
            Double id = (Double) f.get("id");
            
            p.setId(id.intValue());
            
            p.setNom((String)f.get("name"));
            
            listDisponibilite.add(p);  
        }        
        return listDisponibilite;
        
    }
}
