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
import com.entrepot.models.LigneCommande;
import com.entrepot.models.ProduitAchat;
import com.entrepot.services.ServiceLigneCommande;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class FavoriesForm  extends Form{
    
    ServiceLigneCommande serviceLigneCommande =new  ServiceLigneCommande();
     private  ServiceLigneCommande ligneC;
    private ArrayList<LigneCommande>lc = new ArrayList<>();
      private Resources theme; 
      
       private ProduitsListForm form;
    private List<ProduitAchat> ListProduit ;
    public FavoriesForm (Form previous){
        
        setTitle("Favories");
        
         ligneC = new ServiceLigneCommande();
        ListProduit = new ArrayList<>();
   
    
     ListProduit = ligneC.getFavoris();
    
    for(int i = 0 ; i < ListProduit.size(); i++){
        
        add(this.AddProduit(ListProduit.get(i)));
    }
   getToolbar().addCommandToLeftBar("Back",null,ev ->{
            previous.showBack();
        });
    getToolbar().addCommandToOverflowMenu("Supprimer tous",null , (evt) -> {
             
             try{
                 ligneC.deleteFav();
                 this.refreshLayout();
             }catch(IOException ex){
                 
                 System.out.println(ex.getMessage());;
                 
                 
             }
         });
    }
  public void refreshLayout(){
    
    this.removeAll();
    
   ListProduit =  ligneC.getFavoris();
   
   for(int i = 0 ; i < ListProduit.size(); i++){
        
        this.add(this.AddProduit(ListProduit.get(i)));
    }
    
    this.revalidate();
}    
       public Container AddProduit (ProduitAchat p){
            theme = UIManager.initFirstTheme("/themeVente");
           Container item = new Container(BoxLayout.x());
        
        EncodedImage enco = EncodedImage.createFromImage(theme.getImage("Logo.png"), false);
        
        String url = "http://localhost/PROJET-SYMFONY-GENTREPOT/Gentrepot/web/uploads/images/"+p.getImage();
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
        
        
        
        
        
     
        
        
        item.add(data);
          
         Button delete = new Button("Supprimer");
          
          
          
          delete.addPointerReleasedListener(ev -> {
              if(Dialog.show("Confirmation"," Supprimer"+p.getLibelle()+" de la liste des favories  ?" ,"Oui","Non")){
                  
                  try{
                      ligneC.deleteFav(p);
                     FavoriesForm.this.refreshLayout();
                     
                     System.out.println("Suppression OK ! ");
                     
                  }catch( IOException ex){
                      
                      Dialog.show(("Erreur"),"Erreur de suppression" ,"Oui", null);
                  }
                  
              }
          });
        
          data.add(delete);
        
        
        
        
        
        return item;
        
        
}}
