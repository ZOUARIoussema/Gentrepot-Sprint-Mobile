/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.ImageViewer;
import static com.codename1.io.Log.p;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextComponent;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.entrepot.models.ProduitAchat;
import com.entrepot.services.ServiceLigneCommande;
import com.entrepot.services.ServiceProduitAchat;
import java.io.IOException;
import java.util.ArrayList;
//import java.util.logging.Level;
//import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class ProduitsListForm  extends Form{
    
       
    Form f;
    
    ServiceProduitAchat serviceProduitAchat =new ServiceProduitAchat();
    ServiceLigneCommande serviceLigneCommande;
    
    ArrayList<ProduitAchat> produit = serviceProduitAchat.getAllProduits();
    
    private Resources theme ;
    
    
    public ProduitsListForm(Form previous){
        
        
        
        
    







        setTitle("Boutique");
        
       Toolbar.setGlobalToolbar(true);
Style s = UIManager.getInstance().getComponentStyle("Title");
Form hi = new Form("Toolbar", new BoxLayout(BoxLayout.Y_AXIS));
TextField searchField = new TextField("", "Toolbar Search"); 
searchField.getHintLabel().setUIID("Title");
searchField.setUIID("Title");
searchField.getAllStyles().setAlignment(Component.LEFT);
getToolbar().setTitleComponent(searchField);
FontImage searchIcon = FontImage.createMaterial(FontImage.MATERIAL_SEARCH, s);
searchField.addDataChangeListener((i1, i2) -> { 
  String t = searchField.getText();
  if(t.length() < 1) {
  for(Component cmp : getContentPane()) {
  cmp.setHidden(false);
  cmp.setVisible(true);
  }
  } else {
  t = t.toLowerCase();
  for(Component cmp : getContentPane()) {
  String val = null;
  if(cmp instanceof Label) {
  val = ((Label)cmp).getText();
  } else {
  if(cmp instanceof TextArea) {
  val = ((TextArea)cmp).getText();
  } else { 
      if (cmp instanceof Container){
           if( cmp instanceof Label){
               val = ((Label)cmp).getText();
               
               System.out.println(val);
           }
          } 
      else { 
  val = (String)cmp.getPropertyValue("text");  
  }}
  }
  boolean show = val != null && val.toLowerCase().indexOf(t) > -1;
  cmp.setHidden(!show); 
  cmp.setVisible(show);
  }
  }
  getContentPane().animateLayout(250);
});
getToolbar().addCommandToRightBar("", searchIcon, (e) -> {
  searchField.startEditingAsync(); 
});

add("haja");


       
        f = new Form ("Shop");
        serviceLigneCommande  = new ServiceLigneCommande();
        theme = UIManager.initFirstTheme("/themeVente");
        
        this.setLayout(BoxLayout.y());
                
                for (ProduitAchat p : produit){
                    
                    add(AddItems(p));
                
                 
                    show();
    }
                
                
                //add(AddItems(new ProduitAchat("2121", "aaaa", 54, 21)));
     getToolbar().addCommandToLeftBar("Back",null,ev ->{
            previous.showBack();
        });
        
   /*public ProduitsListForm(Form previous ) {
        
         
        
        super("Produits list", BoxLayout.y());
        
        this.add(new SpanLabel(new ServiceProduitAchat().getAllProduits().toString()));
          
        
       
        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
            previous.showBack();
        });
        
        
    */

   
   /* public Container addProduit (ProduitAchat produit){
        
        Container holder = new Container (BoxLayout.x());
        
        Container cntDetails = new Container(BoxLayout.y());
        
        Label titre = new Label(produit.getLibelle());
        
        cntDetails.add(titre);
        
    return holder;
}*/
   
   
        
    }
    
    public Container AddItems( ProduitAchat p) {
        
        Resources theme = UIManager.initFirstTheme("/themeVente");
        Container item = new Container(BoxLayout.x());
        
     
        
        
        Container data = new Container (BoxLayout.y());
        
        Label name = new Label ("Libelle : ");
        
    
        
      
              
        
        Label tfname = new Label(p.getLibelle());
        
        Container libelle = new Container (BoxLayout.x());
        
        libelle.add(name);
        libelle.add(tfname);
        
        data.add(libelle);
          EncodedImage enco = EncodedImage.createFromImage(theme.getImage("Logo.png"),false);
        
        String url = "http://localhost/PROJET-SYMFONY-GENTREPOT/Gentrepot/web/uploads/images/"+p.getImage();
      Image im =URLImage.createToStorage(enco,p.getImage(), url); 
      
       ImageViewer imv = new ImageViewer(im);
    
         item.add(imv);
        
        
        
        
        
        
          Label prix = new Label ("Prix : ");
        
        
      
        
        Label Lprix = new Label(Double.toString(p.getPrixVente()));
        
        Container prixC = new Container (BoxLayout.x()); 
        
        prixC.add(prix);
        prixC.add(Lprix);
        
        data.add(prixC);
         
        
        
        
        
        
        
       Label classe = new Label ("Classe : ");
        
        
      
        
        Label Lclass = new Label(p.getClasse());
        
        Container classc = new Container (BoxLayout.x());
        
        classc.add(classe);
        classc.add(Lclass);
        
        data.add(classc);
        
        
       
        Button btnadd = new Button("Ajouter au panier");
        TextComponent qte = new TextComponent().label("Qte :  ");

        btnadd.addPointerReleasedListener(ev-> {
        Dialog.setDefaultBlurBackgroundRadius(15);
        
        if(Dialog.show("Confirmation", "Ajouter "+ p.getLibelle()+ " au panier","oui","non")){
             
            p.setQuantiteStock(Integer.parseInt(qte.getText()));
               
                serviceLigneCommande.insertProduit(p);
          
                System.out.println("Insertion OK ! ");
                                Dialog.show("Success", "Produit ajouté", "OK" , null);

          
        }
        
         
        });
        data.add(btnadd);
        data.add(qte);
        Slider starRank = new Slider();
         starRank.setEditable(true);
  starRank.setMinValue(0);
  starRank.setMaxValue(10);
  
  
  Font fnta = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
  derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);
  Style s = new Style(0xffff33, 0, fnta, (byte)0);
  Image fullStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
  s.setOpacity(100);
  s.setFgColor(0);
  Image emptyStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
  initStarRankStyle(starRank.getSliderEmptySelectedStyle(), emptyStar);
  initStarRankStyle(starRank.getSliderEmptyUnselectedStyle(), emptyStar);
  initStarRankStyle(starRank.getSliderFullSelectedStyle(), fullStar);
  initStarRankStyle(starRank.getSliderFullUnselectedStyle(), fullStar);
  starRank.setPreferredSize(new Dimension(fullStar.getWidth() * 5, fullStar.getHeight()));
  
  
   starRank.addActionListener(ev-> {
        Dialog.setDefaultBlurBackgroundRadius(15);
        
        if(Dialog.show("Confirmation", "Ajouter "+ p.getLibelle()+ " au favories","oui","non")){
             
            
               
                serviceLigneCommande.favProduit(p);
               
                System.out.println("Insertion OK ! ");
                                Dialog.show("Success", "Produit ajouté", "OK" , null);

          
        }
        
         
        });
  
  
  
        data.add(starRank);
          
         Container videe = new Container (BoxLayout.x());
           Label vide  = new Label("                                   ");
           videe.add(vide);
                    data.add(videe);
        
        
          Container vid = new Container (BoxLayout.x());
           Label videee  = new Label("                                   ");
           vid.add(videee);
                    data.add(vid);
        
        
        
        
        
        
        
        
        item.add(data);
          

        return item;
        
        
    }
private void initStarRankStyle(Style s, Image star) {
  s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
  s.setBorder(Border.createEmpty());
  s.setBgImage(star);
  s.setBgTransparency(0);
}




}