/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import static com.codename1.io.Log.e;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
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
public class AddProduitForm extends Form {

    Resources theme = UIManager.initFirstTheme("/themeLogistique");
    private String im;
    ComboBox<String> c;

    public AddProduitForm() {
        super("Ajouter Produit", BoxLayout.y());
        c = new ComboBox();
        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        FontImage icone = FontImage.createMaterial(FontImage.MATERIAL_IMAGE, s);

        this.getStyle().setBgImage(theme.getImage("kashmir.png"), focusScrolling);
        this.setLayout(new FlowLayout(CENTER, CENTER));
        TextField tfRef = new TextField(null, "reference");
        TextField tfLib = new TextField(null, "Libelle");
        TextField tfQ = new TextField(null, "Quantite En Stock");
        TextField tfC = new TextField(null, "classe");
        TextField tfQss = new TextField(null, "Quantite En Stock Securite");
        TextField tfDpa = new TextField(null, "Dernier Prix D'Achat");
        TextField tfTva = new TextField(null, "TVA");
        TextField tfDim = new TextField(null, "Dimension");
        TextField tfDes = new TextField(null, "Description");
        TextField tfTc = new TextField(null, "Type De Conditionnement");
        TextField tfPv = new TextField(null, "Prix De Vente");
        Button img = new Button("Ajouter une image", icone);

        Button btn = new Button("Ajouter le produit");
        ServiceProduitAchat ws = new ServiceProduitAchat();
        Map x = ws.getResponse("api/listSousCat");

        ArrayList<SousCategorieAchat> listc = ws.getListSousCategorie(x);
        for (SousCategorieAchat e : listc) {
            c.addItem(e.getNom());
        }
        /*Container photos = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Label l = new Label("Sous catégorie : ");
            photos.add(l);
            photos.add(c);
            photos.add(tfRef);
            photos.add(tfLib);
            photos.add(tfQ);
            photos.add(tfC);
            photos.add(tfQss);
            photos.add(tfDpa);
            photos.add(tfTva);
            photos.add(tfDim);
            photos.add(tfDes);
            photos.add(tfTc);
            photos.add(tfPv);
            photos.add(img);
            photos.add(btn);
            add(photos);*/
        
        
        
        ServiceProduitAchat sc = new ServiceProduitAchat();

        btn.addActionListener((evt) -> {
            if ((tfRef.getText().length() == 0) || (tfPv.getText().length() == 0)) {
                Dialog.show("Alert", "Please fill all the fields", "OK", null);
            } else {
                try {
                    ProduitAchat ch = new ProduitAchat(tfRef.getText(), tfLib.getText(), Integer.parseInt(tfQ.getText()), tfC.getText(), Integer.parseInt(tfQss.getText()), Double.parseDouble(tfDpa.getText()), Double.parseDouble(tfTva.getText()), Double.parseDouble(tfDim.getText()), tfDes.getText(), tfTc.getText(), Double.parseDouble(tfPv.getText()));
                    ch.setImage(im);
                    SousCategorieAchat sca = new SousCategorieAchat();
                    sca.setNom(c.getSelectedItem());
                    ch.setSousCategorieAchat(sca);
                    if (sc.addProduit(ch)) {
                        Dialog.show("SUCCESS", "chauffeur sent", "OK", null);
                    }

                } catch (NumberFormatException e) {
                    Dialog.show("ERROR", "cin must be a number", "OK", null);
                }

            }
        });

        this.addAll(tfRef, tfLib, tfQ, tfC, tfQss, tfDpa, tfTva, tfDim, tfDes, tfTc, tfPv, c, img, btn);

        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {

            new HomeAchat().show();

        });

        img.addActionListener(e -> {

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
                im = fileNameInServer;
                InfiniteProgress prog = new InfiniteProgress();
                Dialog dlg = prog.showInifiniteBlocking();
                cr.setDisposeOnCompletion(dlg);
                NetworkManager.getInstance().addToQueueAndWait(cr);
            } catch (IOException ex) {
            }
        });

       // show();

    }

}
