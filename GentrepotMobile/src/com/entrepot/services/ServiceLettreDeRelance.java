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
import com.entrepot.models.FactureVente;
import com.entrepot.models.InventaireCaisse;
import com.entrepot.models.LettreDeRelance;
import com.entrepot.models.User;
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
public class ServiceLettreDeRelance {

    private ConnectionRequest request;
    private boolean responseResult;
    public ArrayList<FactureVente> factures;

    public ArrayList<LettreDeRelance> lettres;

    public ServiceLettreDeRelance() {
        request = DataSource.getInstance().getRequest();
    }

    public boolean addLettre(LettreDeRelance l) {
        String url = Statics.BASE_URL + "/apiLettre/new?idF=" + l.getFactureVente().getNumeroF();

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

    public boolean deleteLettre(LettreDeRelance l) {
        String url = Statics.BASE_URL + "/apiLettre/delete?idL=" + l.getId();

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

    public ArrayList<FactureVente> getAllFacture() {
        String url = Statics.BASE_URL + "/apiLettre/allF";

        System.out.println(url);

        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                factures = parseFacture(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return factures;
    }

    public List<FactureVente> getAllFactureImpayees() {

        List<FactureVente> liste = new ArrayList<FactureVente>();

        for (FactureVente f : this.getAllFacture()) {
            if (f.getRestePaye() != 0) {
                liste.add(f);
            }

        }

        return liste;

    }

    public ArrayList<LettreDeRelance> getAllLettre() {
        String url = Statics.BASE_URL + "/apiLettre/all";

        System.out.println(url);

        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                lettres = parseLettre(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return lettres;
    }

    public ArrayList<FactureVente> parseFacture(String jsonText) {
        try {
            factures = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {

                int id = (int) Float.parseFloat(obj.get("id").toString());
                String etat = obj.get("etat").toString();

                double total = Float.parseFloat(obj.get("totalTTC").toString());
                double totalP = Float.parseFloat(obj.get("totalPaye").toString());
                double rest = Float.parseFloat(obj.get("resteAPaye").toString());

                Map<String, Object> dated = (Map<String, Object>) obj.get("dateCreation");
                float da = Float.parseFloat(dated.get("timestamp").toString());
                Date dCeation = new Date((long) (da - 3600) * 1000);

                Map<String, Object> dated2 = (Map<String, Object>) obj.get("dateEchaillancePaiement");
                float da2 = Float.parseFloat(dated2.get("timestamp").toString());
                Date dateEchaillancePaiement = new Date((long) (da2 - 3600) * 1000);

                factures.add(new FactureVente(id, dCeation, dateEchaillancePaiement, total, etat, totalP,rest));
            }

        } catch (IOException ex) {
        }

        return factures;
    }

    public ArrayList<LettreDeRelance> parseLettre(String jsonText) {
        try {
            lettres = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {

                int id = (int) Float.parseFloat(obj.get("id").toString());

                Map<String, Object> dated = (Map<String, Object>) obj.get("dateCreation");
                float da = Float.parseFloat(dated.get("timestamp").toString());
                Date dCeation = new Date((long) (da - 3600) * 1000);

                Map<String, Object> f = (Map<String, Object>) obj.get("factureVente");
                int facture = (int) Float.parseFloat(f.get("id").toString());

                lettres.add(new LettreDeRelance(id, dCeation, new FactureVente(facture)));
            }

        } catch (IOException ex) {
        }

        return lettres;
    }

    public FactureVente findFactureById(int id) {
        FactureVente fa = null;

        for (FactureVente facture : this.getAllFacture()) {

            if (facture.getNumeroF() == id) {

                fa = facture;
                return fa;
            }

        }

        return fa;
    }

    public double totalVente() {

        double t = 0;

        for (FactureVente f : this.getAllFacture()) {

            if (new SimpleDateFormat("yyyy").format(f.getDateCreation()).equals(new SimpleDateFormat("yyyy").format(new Date()))) {

                t = t + f.getTotalTTC();
            }

        }

        return t;
    }

    public double totalPayer() {

        double t = 0;

        for (FactureVente f : this.getAllFacture()) {

            if (new SimpleDateFormat("yyyy").format(f.getDateCreation()).equals(new SimpleDateFormat("yyyy").format(new Date()))) {
                t = t + f.getTotalPaye();
            }
        }

        return t;
    }

}
