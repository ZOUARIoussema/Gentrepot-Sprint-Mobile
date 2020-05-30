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
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextComponent;
import com.codename1.ui.TextField;
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
import com.entrepot.utls.Statics;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class ProduitsListForm extends Form {

    Form f;

    ServiceProduitAchat serviceProduitAchat = new ServiceProduitAchat();
    ServiceLigneCommande serviceLigneCommande;

    ArrayList<ProduitAchat> produit = serviceProduitAchat.getAllProduits();

    private Resources theme;

    public ProduitsListForm(Form previous) {

        setTitle("Boutique");

        f = new Form("Shop");
        serviceLigneCommande = new ServiceLigneCommande();
        Resources theme = UIManager.initFirstTheme("/themeVente");

        this.setLayout(BoxLayout.y());

        for (ProduitAchat p : produit) {

            add(AddItems(p));

            show();
        }

        //add(AddItems(new ProduitAchat("2121", "aaaa", 54, 21)));
        getToolbar().addCommandToLeftBar("Back", null, ev -> {
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

    ProduitsListForm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Container AddItems(ProduitAchat p) {

        Resources theme = UIManager.initFirstTheme("/themeVente");
        Container item = new Container(BoxLayout.x());

        EncodedImage enco = EncodedImage.createFromImage(theme.getImage("load.png"), false);

        String url = "http://localhost/IntegrationFinalSymfonyMaster/PROJET-SYMFONY-GENTREPOT/gentrepot/web/uploads/images/" + p.getImage();
        Image im = URLImage.createToStorage(enco, p.getImage(), url);

        ImageViewer imv = new ImageViewer(im);
        item.add(imv);

        Container data = new Container(BoxLayout.y());

        Label name = new Label("Libelle : ");

        Font fnt = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);

        name.getUnselectedStyle().setFont(fnt);

        Label tfname = new Label(p.getLibelle());

        Container libelle = new Container(BoxLayout.x());

        libelle.add(name);
        libelle.add(tfname);

        data.add(libelle);

        Label prix = new Label("Prix : ");

        prix.getUnselectedStyle().setFont(fnt);

        Label Lprix = new Label(Double.toString(p.getPrixVente()));

        Container prixC = new Container(BoxLayout.x());

        prixC.add(prix);
        prixC.add(Lprix);

        data.add(prixC);

        Label classe = new Label("Classe: ");

        classe.getUnselectedStyle().setFont(fnt);

        Label Lclass = new Label(p.getClasse());

        Container classc = new Container(BoxLayout.x());

        classc.add(classe);
        classc.add(Lclass);

        data.add(classc);

        Button btnadd = new Button("Ajouter au panier");
        TextComponent qte = new TextComponent().label("Quantite ");

        btnadd.addPointerReleasedListener(ev -> {
            Dialog.setDefaultBlurBackgroundRadius(15);

            if (Dialog.show("Confirmation", "Ajouter " + p.getLibelle() + " au panier", "oui", "non")) {

                p.setQuantiteStock(Integer.parseInt(qte.getText()));

                serviceLigneCommande.insertProduit(p);

                System.out.println("Insertion OK ! ");
                Dialog.show("Success", "Produit ajouté", "OK", null);

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
        Style s = new Style(0xffff33, 0, fnta, (byte) 0);
        Image fullStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        s.setOpacity(100);
        s.setFgColor(0);
        Image emptyStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        initStarRankStyle(starRank.getSliderEmptySelectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderEmptyUnselectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderFullSelectedStyle(), fullStar);
        initStarRankStyle(starRank.getSliderFullUnselectedStyle(), fullStar);
        starRank.setPreferredSize(new Dimension(fullStar.getWidth() * 5, fullStar.getHeight()));

        starRank.addActionListener(ev -> {
            Dialog.setDefaultBlurBackgroundRadius(15);

            if (Dialog.show("Confirmation", "Ajouter " + p.getLibelle() + " au favories", "oui", "non")) {

                serviceLigneCommande.insertProduit(p);

                System.out.println("Insertion OK ! ");
                Dialog.show("Success", "Produit ajouté", "OK", null);

            }

        });

        data.add(starRank);

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
