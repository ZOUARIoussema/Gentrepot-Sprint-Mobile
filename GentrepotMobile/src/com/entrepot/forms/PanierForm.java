/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.CommandeVente;
import com.entrepot.models.LigneCommande;
import com.entrepot.models.ProduitAchat;
import com.entrepot.services.ServiceCommandeVente;
import com.entrepot.services.ServiceLigneCommande;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class PanierForm extends Form {
    
    
   
         
    
    private Resources theme; 
    private  ServiceLigneCommande ligneC;
    private ArrayList<LigneCommande>lc = new ArrayList<>();
    private LigneCommande lcom =new LigneCommande();
    private ServiceCommandeVente commande; 
    private ProduitsListForm form;
    private List<ProduitAchat> ListProduit ;
    ProduitAchat p;
    CommandeVente v =new CommandeVente();
    LigneCommande l;    
   
  
    public PanierForm(Form previous ){
        
        setTitle("Votre panier");
        setLayout(BoxLayout.y());
        
        commande = new ServiceCommandeVente();
        ligneC = new ServiceLigneCommande();
        ListProduit = new ArrayList<>();
        ListProduit= ligneC.getAllProduits(); 
        theme = UIManager.initFirstTheme("/themeVente");
        
               

                                      commande.ajoutercom(v);
                                      
                                      
        for (int i = 0 ; i < ListProduit.size(); i++ ){
            
            add(AddProduit(ListProduit.get(i)) );
            
          v.setId( commande.getMaxIdCommnde());
         
           LigneCommande c = new LigneCommande(v, ListProduit.get(i), ListProduit.get(i).getPrixVente(), ListProduit.get(i).getQuantiteStock(), ListProduit.get(i).getQuantiteStock() * ListProduit.get(i).getPrixVente(),  ListProduit.get(i).getTva());
              lc.add(c);  
                    
            
        }

        getToolbar().addCommandToLeftBar("Back",null,ev ->{
            previous.showBack();
        });
            
         getToolbar().addCommandToOverflowMenu("Supprimer tous",null , (evt) -> {
             
             try{
                 ligneC.deleteAll();
                 this.refreshLayout();
             }catch(IOException ex){
                 
                 System.out.println(ex.getMessage());;
                 
                 
             }
         });
        
         
            getToolbar().addCommandToOverflowMenu("Actualiser",null , (evt) -> {
             
                 this.refreshLayout();
            
            
        });
            
            Double total=0.0 ;
          
                for (int i = 0 ; i < ListProduit.size(); i++ ){
                    
                    total= total+ListProduit.get(i).getQuantiteStock() * ListProduit.get(i).getPrixVente();
                    
                }
                
                
                System.out.println(total);
                     

       Label totalc =  new Label("Total Commande : " +Double.toString(total));

        
        
        add(totalc);
        
  
        
        
        
        //v.setLigneCommande(lc);
       
        Button valider = new Button("Valider commmande");
        
        add(valider);
        
        
        
        valider.addPointerReleasedListener(ev -> {
              if(Dialog.show("Confirmation"," Valider" ,"Oui","Non")){
                  
                 
                        System.out.println(v);
                  int  a=  commande.getMaxIdCommnde();
                 for (int i = 0 ; i < lc.size(); i++ ){
                     ligneC.ajouterlignecom(lc.get(i));
                     
                 }  
                    v.setTotalC(5);
                    System.out.println(v);

                     System.out.println("commande ajouté OK ! ");
                     
                     
                 
                     
                     
                  }
                  
              
        });
                
    }  
                
       public void refreshLayout(){
    
    this.removeAll();
    
    ListProduit = ligneC.getAllProduits();
    
    for(int i = 0 ; i < ListProduit.size(); i++){
        
        this.add(this.AddProduit(ListProduit.get(i)));
    }
    
    this.revalidate();
}    
    
      public Container AddProduit (ProduitAchat p){
          
           Container item = new Container(BoxLayout.x());
        
        EncodedImage enco = EncodedImage.createFromImage(theme.getImage("load.png"), false);
        
        String url = "http://localhost/IntegrationFinalSymfonyMaster/PROJET-SYMFONY-GENTREPOT/gentrepot/web/uploads/images/"+p.getImage();
        Image im =URLImage.createToStorage(enco,p.getImage(), url); 
        
        ImageViewer imv = new ImageViewer(im);
         item.add(imv);
        
        
        
        
        Container data = new Container (BoxLayout.y());
        
        Label name = new Label ("Libelle : ");
        
        Font fnt = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);
        
        name.getUnselectedStyle().setFont(fnt);
        
        Label tfname = new Label(p.getLibelle());
        
        Container libelle = new Container (BoxLayout.x());
        
        libelle.add(name);
        libelle.add(tfname);
        
        data.add(libelle);
        
        
        
        
        
          Label prix = new Label ("Prix : ");
        
        
        prix.getUnselectedStyle().setFont(fnt);
        
        Label Lprix = new Label(Double.toString(p.getPrixVente()));
        
        Container prixC = new Container (BoxLayout.x());
        
        prixC.add(prix);
        prixC.add(Lprix);
        
        data.add(prixC);
        
        
        
        
        
       Label classe = new Label ("Quantité: ");
        
        
        classe.getUnselectedStyle().setFont(fnt);
        
        Label Lclass = new Label(Integer.toString(p.getQuantiteStock()));
        
        Container classc = new Container (BoxLayout.x());
        
        classc.add(classe);
        classc.add(Lclass);
        
        data.add(classc);
        
        
        
          
       Label totalProduit = new Label ("Total: ");
        
        
        classe.getUnselectedStyle().setFont(fnt);
        
         Double total = p.getPrixVente()*p.getQuantiteStock();
        Label totalP = new Label(Double.toString(total));
        
        Container totalc = new Container (BoxLayout.x());
        
        totalc.add(totalProduit);
        totalc.add(totalP);
        
        data.add(totalc);
        
        
        
        
        
          Button delete = new Button("Supprimer");
          
          
          
          delete.addPointerReleasedListener(ev -> {
              if(Dialog.show("Confirmation"," Supprimer"+p.getLibelle()+" de la panier ?" ,"Oui","Non")){
                  
                  try{
                      ligneC.deleteProduit(p);
                     PanierForm.this.refreshLayout();
                     
                     System.out.println("Suppression OK ! ");
                     
                  }catch( IOException ex){
                      
                      Dialog.show(("Erreur"),"Erreur de suppression" ,"Oui", null);
                  }
                  
              }
          });
        
          data.add(delete);
        
        
        
        
        item.add(data);
          
        
        return item;
      }
      
    
    
}
