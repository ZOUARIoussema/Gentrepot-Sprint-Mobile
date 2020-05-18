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
import com.entrepot.models.LigneCommande;
import com.entrepot.models.ProduitAchat;
import com.entrepot.utls.SingletonDataBase;
import com.entrepot.utls.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author oussema
 */
public class ServiceLigneCommande {
    
      private ConnectionRequest request = new ConnectionRequest();
    private boolean responseResult;
    public final SingletonDataBase db ;
    private ArrayList<ProduitAchat> pa= new ArrayList<>();
    
    public ServiceLigneCommande(){
        
        db =SingletonDataBase.getInstance();
    }
    
   
    public boolean ajouterlignecom(LigneCommande lc){
        
         String url = Statics.BASE_URL+ "/apiLigneCommande/ajout?Quantite="+lc.getQuantite()+"&Produit="+lc.getRefp()+"&Total="+lc.getTotal()+"&Commande="+lc.getCommandeVente().getId()+"&Prix="+lc.getPrix()+"&Tva="+lc.getTva(); 
    
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
    
    
    public void insertProduit (ProduitAchat p) { 
        
        System.out.println(p);
        
      /*  try {
            db.getBase().execute("Insert into cart (id , produit , quantite , prix , image) values "
                    + "('"+p.getReference()+"', '" +p.getLibelle()+"', "+p.getQuantiteStock()+" ,"+p.getPrixVente()+" ,'"+p.getImage() +"') ");
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }*/
      pa.add(p);
      LigneCommande.pan=pa;
        
       
    }
    
        public void deleteProduit(ProduitAchat p) throws IOException{

        
       // db.getBase().execute("Delete from cart where id=" +p.getReference());
       pa.remove(p);
       LigneCommande.pan=pa;
    }
    
    public void UpdateProduit(ProduitAchat p) throws IOException{
        
        db.getBase().execute("UPDATE cart SET quantite=? WHERE id="+p.getReference());
    }
   
    public void deleteAll() throws IOException{ 
        
       // db.getBase().execute("Delete from cart" );
       pa.remove(pa);
       LigneCommande.pan=pa;
        
    } 
            
           
    public  List <ProduitAchat> getAllProduits(){
        
      List<ProduitAchat> listS=  LigneCommande.pan;
       /* List<ProduitAchat> listS= new ArrayList<ProduitAchat>();
        
        try {
            Cursor c = db.getBase().executeQuery("Select * from cart");
            
            while (c.next()){
                
                ProduitAchat p = new ProduitAchat();
                Row r = c.getRow();
                
                p.setReference(r.getString(0));
                p.setLibelle(r.getString(1));
                p.setQuantiteStock(r.getInteger(2));
                p.setPrixVente(r.getDouble(3));

                p.setImage(r.getString(4));

                listS.add(p);
                
                
                
            }
            c.close();
            
        }catch(IOException ex){
            
            System.out.println(ex.getMessage());
        }*/
        return listS;
    }
    
    
}
