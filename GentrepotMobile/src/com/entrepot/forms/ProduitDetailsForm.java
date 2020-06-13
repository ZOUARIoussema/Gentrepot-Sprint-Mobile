/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.entrepot.models.ProduitAchat;
import com.entrepot.services.ServiceProduitAchat;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Mohamed
 */
public class ProduitDetailsForm extends Form{
    Resources theme = UIManager.initFirstTheme("/themeLogistique");
    public static ProduitAchat e ;
    
    public ProduitDetailsForm(){
        super("Detail produit ", BoxLayout.y());
        this.getStyle().setBgImage(theme.getImage("kashmir.png"), focusScrolling);
        ServiceProduitAchat sp = new ServiceProduitAchat();
        
        
        Container cont = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            ImageViewer imv = null;
            Image img;
            EncodedImage encoded = null;
            
            Label ref = new Label("reference : "+e.getReference());
            Label desc = new Label("Description : "+e.getDescription());
            Label classe = new Label("Classe : "+e.getClasse());
            Label q = new Label("Quantité en stock : "+e.getQuantiteStock());
            Label souscat = new Label("Sous catégorie : "+e.getSousCategorieAchat().getNom());
            Label b = new Label("Libelle : "+e.getLibelle());
            Label l = new Label("Prix : "+e.getPrixVente());
            
            
           
           Button edit = new Button("Edit");
           Button delete = new Button("Delete");
            
            try {
                encoded = EncodedImage.create("/loading.png");
            } catch (IOException ex) {
            }
            img = URLImage.createToStorage(encoded, e.getImage(), "http://127.0.0.1:8000/uploads/" + e.getImage());
            imv = new ImageViewer(img);
            cont.add(imv);
            cont.add(ref);
            cont.add(b);
            cont.add(classe);
            cont.add(desc);
            cont.add(q);
            cont.add(l);
            cont.add(souscat);
            
           
             cont.add(edit);
              cont.add(delete);
            try {
                ScaleImageLabel sep = new ScaleImageLabel(Image.createImage("/Separator.png"));
                cont.add(sep);
            } catch (IOException ex) {
            }
            add(cont);
            
            b.addPointerPressedListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent evt) {
                    
                }
            });
            
           edit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                   EditProduitForm.p = e ; 
                   EditProduitForm ep = new EditProduitForm();
                   ep.show();

                }
            });
            
           
          delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {

                    if (Dialog.show("Comfirmation", "Vouler vous supprimer ce produit ? ", "oui", "non")) {
                        
                        sp.deleteProd(e);
                        new ListProduitAchatForm().showBack();
                    }

                }
            });
            
          this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
             new ListProduitAchatForm().showBack();
        });
           this.getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_CAMERA, e->{
               Image screenshot = Image.createImage(getWidth(), getHeight());
        revalidate();
        setVisible(true);
        paintComponent(screenshot.getGraphics(), true);

        String imageFile = FileSystemStorage.getInstance().getAppHomePath() + "screenshot.png";
               System.out.println(imageFile);
        try(OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
            ImageIO.getImageIO().save(screenshot, os, ImageIO.FORMAT_PNG, 1);
        } catch(IOException err) {
            Log.e(err);
        }
           });
    }
    
}
