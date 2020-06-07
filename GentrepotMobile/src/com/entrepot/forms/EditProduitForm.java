/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.ProduitAchat;
import com.entrepot.models.SousCategorieAchat;
import com.entrepot.services.ServiceProduitAchat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Mohamed
 */
public class EditProduitForm extends Form{
    Resources theme = UIManager.initFirstTheme("/themeLogistique");
    public static ProduitAchat p ; 
     private String im ;
    // ComboBox<String> c;
     
     public EditProduitForm(){
         super("Modifier Produit", BoxLayout.y());
        this.getStyle().setBgImage(theme.getImage("kashmir.png"), focusScrolling);
        this.setLayout(new FlowLayout(CENTER, CENTER));
        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        FontImage icone = FontImage.createMaterial(FontImage.MATERIAL_IMAGE, s);
      //  c = new ComboBox();
        
        
        
        Container cont = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            TextField reference = new TextField();
            reference.setText(p.getReference());
             TextField libelle = new TextField();
            libelle.setText(p.getLibelle());
            TextField quantiteEnStock = new TextField();
            quantiteEnStock.setText(p.getQuantiteStock()+"");
            TextField classe = new TextField();
            classe.setText(p.getClasse());
            TextField quantiteStockSecurite = new TextField();
            quantiteStockSecurite.setText(p.getQuantiteStockSecurite()+"");
            TextField dernierPrixAchat = new TextField();
            dernierPrixAchat.setText(p.getDernierPrixAchat()+"");
            TextField tva = new TextField();
            tva.setText(p.getTva()+"");
            TextField dimension = new TextField();
            dimension.setText(p.getDimension()+"");
            TextField description = new TextField();
            description.setText(p.getDescription());
            TextField typeConditionnement = new TextField();
            typeConditionnement.setText(p.getTypeDeConditionnement());
            TextField prixVente = new TextField();
            prixVente.setText(p.getPrixVente()+"");
            Button img = new Button("Ajouter une image",icone);
            
            Button b = new Button("Modifier");
            ServiceProduitAchat ws = new ServiceProduitAchat();
        
           // Map x = ws.getResponse("api/listSousCat");
            
           //ArrayList<SousCategorieAchat> listSouscat = ws.getListSousCategorie(x);
           //for (SousCategorieAchat e : listSouscat) {
            //   c.addItem(e.getNom());
           //}
           
           
            
            Label l = new Label("Sous catégorie : ");
            cont.add(l);
            //cont.add(c);
            cont.add(reference);
            cont.add(libelle);
            cont.add(quantiteEnStock);
            cont.add(classe);
            cont.add(quantiteStockSecurite);
            cont.add(dernierPrixAchat);
            cont.add(tva);
            cont.add(dimension);
            cont.add(description);
            cont.add(typeConditionnement);
            cont.add(prixVente);
            cont.add(img);
            cont.add(b);
            add(cont);
            b.addActionListener(e->{
                
                p.setReference(reference.getText());
                p.setLibelle(libelle.getText());
                p.setQuantiteStock(Integer.parseInt(quantiteEnStock.getText()));
                p.setClasse(classe.getText());
                p.setQuantiteStockSecurite(Integer.parseInt(quantiteStockSecurite.getText()));
                p.setDernierPrixAchat(Double.parseDouble(dernierPrixAchat.getText()));
                p.setTva(Double.parseDouble(tva.getText()));
                p.setDimension(Double.parseDouble(dimension.getText()));
                p.setDescription(description.getText());
                p.setTypeDeConditionnement(typeConditionnement.getText());
                p.setPrixVente(Double.parseDouble(prixVente.getText()));
               // SousCategorieAchat sca = new SousCategorieAchat();
                 //   sca.setNom(c.getSelectedItem());
                   // p.setSousCategorieAchat(sca);
                if(im != null){
                   p.setImage(im); 
                }
                
                if(ws.editProduit(p)){
                Dialog.show("SUCCESS", "Produit modifier", "OK", null);
                
                }
          
            });
            img.addActionListener(e->{
                
                try {
                    String fileNameInServer = "";
                    MultipartRequest cr = new MultipartRequest();
                    String filepath = Capture.capturePhoto(-1, -1);
                    cr.setUrl("http://localhost/uploadimage.php");
                    cr.setPost(true);
                    String mime = "image/jpeg";
                    cr.addData("file", filepath, mime);
                    String out = new com.codename1.l10n.SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
                    cr.setFilename("file", out + ".jpg");//any unique name you want

                    fileNameInServer += out + ".jpg";
                    System.err.println("path2 =" + fileNameInServer);
                    im =fileNameInServer ;
                    InfiniteProgress prog = new InfiniteProgress();
                    Dialog dlg = prog.showInifiniteBlocking();
                    cr.setDisposeOnCompletion(dlg);
                    NetworkManager.getInstance().addToQueueAndWait(cr);
                } catch (IOException ex) {
                }
            });
        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
             new ProduitDetailsForm().showBack();
        });
        
     }
    
}
