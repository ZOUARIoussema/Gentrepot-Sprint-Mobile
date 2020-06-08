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
public class LpSortForm extends Form {
    Resources theme = UIManager.initFirstTheme("/themeLogistique");
    
    
    public LpSortForm(){
        super("liste des produits ", BoxLayout.y());
        this.getStyle().setBgImage(theme.getImage("kashmir.png"), focusScrolling);
        
        
        
        ServiceProduitAchat ws = new ServiceProduitAchat();
    
    Map x = ws.getResponse("/apiP/listP");
    ArrayList<ProduitAchat> listevents = ws.getAffProduitsSorted(x);
             for (ProduitAchat e : listevents) {
            Container photos = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            ImageViewer imv = null;
            Image img;
            EncodedImage encoded = null;
   
     Label b = new Label("Libelle : "+e.getLibelle());
            
           
           Button detail = new Button("Détails");
            
            try {
                encoded = EncodedImage.create("/loading.png");
            } catch (IOException ex) {
            }
            
            img = URLImage.createToStorage(encoded, e.getImage(), "http://127.0.0.1:8000/uploads/" + e.getImage());
            //img = URLImage.createToStorage(encoded, e.getImage(), "http://127.0.0.1:8000/uploads/20200604110305.jpg");
            imv = new ImageViewer(img);
            photos.add(imv);
            photos.add(b);
            
            photos.add(detail);
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
            
            detail.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                   ProduitDetailsForm.e = e ;
                   ProduitDetailsForm pd = new ProduitDetailsForm();
                   pd.show();
                }
            });
        }
        //show();
    
    this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
             new HomeAchat().showBack();
        });
    this.getToolbar().addCommandToRightBar("unsort", null, (evt) -> {
            new ListProduitAchatForm().showBack();
        });
}
}