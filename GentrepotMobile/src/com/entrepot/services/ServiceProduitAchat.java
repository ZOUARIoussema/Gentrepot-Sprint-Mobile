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
import com.entrepot.models.CommandeApp;
import com.entrepot.models.CommandeDApprovisionnement;
import com.entrepot.models.Fournisseur;
import com.entrepot.models.ProduitAchat;
import com.entrepot.models.SousCategorieAchat;
import com.entrepot.utls.DataSource;
import com.entrepot.utls.Statics;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        request.addArgument("sousCat",p.getSousCategorieAchat().getNom());
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
    
    public  ArrayList<ProduitAchat> getAffProduits(Map m){
        ArrayList<ProduitAchat> listProduit = new ArrayList<>();
        ArrayList d = (ArrayList)m.get("produit");
      

        for(int i = 0; i<d.size();i++){
            Map f =  (Map) d.get(i);
            ProduitAchat p = new ProduitAchat();
            
            
            
            p.setReference((String)f.get("reference"));
            p.setLibelle((String)f.get("libelle"));
            Double ld = (Double) f.get("quantiteEnStock");
            p.setQuantiteStock(ld.intValue());
            p.setClasse((String) f.get("classe"));
            Double ll = (Double) f.get("quantiteStockSecurite");
            p.setQuantiteStockSecurite(ll.intValue());
            p.setDernierPrixAchat((Double) f.get("dernierPrixAchat"));
            p.setTva((Double) f.get("tVA"));
            p.setDimension((Double) f.get("dimension"));
            p.setDescription((String) f.get("description"));
            p.setTypeDeConditionnement((String) f.get("typeDeConditionnement"));
            p.setPrixVente((Double) f.get("prixVente"));
            p.setImage((String)f.get("image"));
            p.setImage1((String) f.get("image1"));
            p.setImage2((String) f.get("image2"));
            p.setImage3((String) f.get("image3"));
            p.setImage4((String) f.get("image4"));
            Map map1 = ((Map) f.get("sousCategorie"));
            
           
           SousCategorieAchat sca = new SousCategorieAchat();
          
           sca.setNom((String)map1.get("name"));
          
            p.setSousCategorieAchat(sca);
           
            listProduit.add(p);  
        }        
        return listProduit;
        
    }

    public static Map<String, Object> getResponse(String url) {
        url = Statics.BASE_URL + url;

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
    
    public  ArrayList<CommandeApp> getListcommande(Map m){
        ArrayList<CommandeApp> listcommande = new ArrayList<>();
        ArrayList d = (ArrayList)m.get("commande");
        System.out.println("roooooooooot "+d);
        //Map f =  (Map) d.get(0);
        System.out.println("dddddddddddddd :::::::::"+d.size());

        for(int i = 0; i<d.size();i++){
            Map f =  (Map) d.get(i);
            CommandeApp p = new CommandeApp();
            Double id = (Double) f.get("numeroC");
            
            p.setNumeroC(id.intValue());
            
            p.setTotal_c((Double) f.get("totalC"));
            p.setTaux_remise((Double) f.get("tauxRemise"));
            p.setTotal_tva((Double) f.get("totalTVA"));
            Map map1 = ((Map) f.get("dateCreation"));
            Date date1 = new Date((((Double)map1.get("timestamp")).longValue()*1000)); 
            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            String s1 = formatter.format(date1);
            p.setDateCreation(s1);
            p.setEtat((String)f.get("etat"));
            Map map2 = ((Map) f.get("fournisseur"));
             Fournisseur fo = new Fournisseur();
            Double idd = (Double) map2.get("id");
            
            fo.setId(idd.intValue());
            
            fo.setRaisonSociale((String)map2.get("raisonSociale"));
            Double numtlf = (Double) map2.get("numeroTelephone");
            fo.setNumeroTelephone(numtlf.intValue());
           
            fo.setAdresse((String) map2.get("adresse"));
            fo.setAdresseMail((String) map2.get("adresseMail"));
            Double ll = (Double) map2.get("codePostale");
            fo.setCodePostale(ll.intValue());
            fo.setMatriculeFiscale((String) map2.get("matriculeFiscale"));
            p.setF(fo);
            listcommande.add(p);  
        }        
        return listcommande;
        
    }
    
    public boolean editProduit(ProduitAchat p) {
       String url = Statics.BASE_URL + "/apiP/editP" ;
       request.setUrl(url);
       request.addRequestHeader("X-Requested-With", "XMLHttpRequest");
       
        request.addArgument("ref", p.getReference());
        request.addArgument("lib", p.getLibelle());
        request.addArgument("quantiteEnStock", p.getQuantiteStock()+ "");
        request.addArgument("classe", p.getClasse());
        request.addArgument("quantiteStockSecurite", p.getQuantiteStockSecurite() + "");
        request.addArgument("dernierPrixAchat", p.getDernierPrixAchat() + "");
        request.addArgument("TVA", p.getTva()+ "");
        request.addArgument("dimension", p.getDimension() + "");
        request.addArgument("description", p.getDescription());
        request.addArgument("typeDeConditionnement", p.getTypeDeConditionnement());
        request.addArgument("prixVente", p.getPrixVente() + "");
        request.addArgument("image", p.getImage());
        request.addArgument("sousCat", p.getSousCategorieAchat().getNom()+"");
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

        return responseResult;
    }
    
    public boolean deleteProd(ProduitAchat p) {
        String url = Statics.BASE_URL + "/apiP/deleteP/" + p.getReference();
                
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
