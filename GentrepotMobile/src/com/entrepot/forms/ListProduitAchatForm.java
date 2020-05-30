/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.ProduitAchat;
import com.entrepot.services.ServiceProduitAchat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Mohamed
 */
public class ListProduitAchatForm extends Form {
    Resources theme = UIManager.initFirstTheme("/themeLogistique");
    
    
    public ListProduitAchatForm(){
        super("liste des produits ", BoxLayout.y());
        this.getStyle().setBgImage(theme.getImage("kashmir.png"), focusScrolling);
        
        
        
        ServiceProduitAchat ws = new ServiceProduitAchat();
    
    Map x = ws.getResponse("api/apiP/listP");
    ArrayList<ProduitAchat> listevents = ws.getAffProduits(x);
             for (ProduitAchat e : listevents) {
            Container photos = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            ImageViewer imv = null;
            Image img;
            EncodedImage encoded = null;
   
     Label b = new Label("Libelle : "+e.getLibelle());
            
           
           Button voir = new Button("Détails");
            
            try {
                encoded = EncodedImage.create("/like.png");
            } catch (IOException ex) {
            }
            img = URLImage.createToStorage(encoded, e.getImage(), "http://127.0.0.1:8000/uploads/" + e.getImage());
            imv = new ImageViewer(img);
            photos.add(imv);
            photos.add(b);
            
            photos.add(voir);
            try {
                ScaleImageLabel sep = new ScaleImageLabel(Image.createImage("/Separator.png"));
                photos.add(sep);
            } catch (IOException ex) {
            }
            add(photos);
            
            b.addPointerPressedListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent evt) {
                    
                }
            });
            
           /* voir.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                   ProductDetails.e = e ;
                   ProductDetails pd = new ProductDetails();
                   pd.show();
                }
            });*/
        }
        //show();
    
    this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
             new HomeAchat().showBack();
        });
}
}