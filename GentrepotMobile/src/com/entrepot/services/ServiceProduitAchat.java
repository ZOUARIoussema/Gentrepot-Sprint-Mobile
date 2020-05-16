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
import com.entrepot.models.ProduitAchat;
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
public class ServiceProduitAchat {
        private ConnectionRequest request;
  private boolean responseResult;
    public ArrayList<ProduitAchat> produits;
    
    
    
     public ServiceProduitAchat() {
        request = DataSource.getInstance().getRequest();
    }
     
      public ArrayList<ProduitAchat> getAllProduits() {
        String url = "http://localhost/PROJET-SYMFONY-GENTREPOT/Gentrepot/web/app_dev.php/api/apiProduit/affiche";

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

            List<Map<String, Object>> list = (List<Map<String, Object>>)produitsListJson.get("root");
            for (Map<String, Object> obj : list) {
                String reference = obj.get("reference").toString();
                String libelle = obj.get("libelle").toString();

                int quantiteEnStock = (int)Float.parseFloat(obj.get("quantiteEnStock").toString());
                String classe = obj.get("classe").toString();

               Double prixVente = (double)Float.parseFloat(obj.get("prixVente").toString());
                String image = obj.get("image").toString();

                produits.add(new ProduitAchat(reference,libelle,quantiteEnStock ,classe , prixVente , image));
            }

        } catch (IOException ex) {
        }

        return produits;
    }
    
}
