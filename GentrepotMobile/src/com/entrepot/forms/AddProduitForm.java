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
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
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
        CreationMenu();
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
        ServiceProduitAchat sp = new ServiceProduitAchat();
        Map x = sp.getResponse("/listSousCat");

        ArrayList<SousCategorieAchat> listc = sp.getListSousCategorie(x);
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
            if ((tfRef.getText().length() == 0) || (tfLib.getText().length() == 0) || (tfQ.getText().length() == 0) || (tfC.getText().length() == 0) || (tfQss.getText().length() == 0) || (tfDpa.getText().length() == 0) || (tfTva.getText().length() == 0) || (tfDim.getText().length() == 0) || (tfDes.getText().length() == 0) || (tfTc.getText().length() == 0) || (tfPv.getText().length() == 0)) {
                Dialog.show("Alert", "Veuillez remplir tous les champs !", "OK", null);
            } else {
                try {
                    ProduitAchat ch = new ProduitAchat(tfRef.getText(), tfLib.getText(), Integer.parseInt(tfQ.getText()), tfC.getText(), Integer.parseInt(tfQss.getText()), Double.parseDouble(tfDpa.getText()), Double.parseDouble(tfTva.getText()), Double.parseDouble(tfDim.getText()), tfDes.getText(), tfTc.getText(), Double.parseDouble(tfPv.getText()));
                    ch.setImage(im);
                    SousCategorieAchat sca = new SousCategorieAchat();
                    sca.setNom(c.getSelectedItem());
                    ch.setSousCategorieAchat(sca);
                    if (sc.addProduit(ch)) {
                        Dialog.show("SUCCESS", "Produit ajouté !", "OK", null);
                        ListProduitAchatForm lp = new ListProduitAchatForm();
                        lp.showBack();
                    }

                } catch (NumberFormatException e) {
                    Dialog.show("ERROR", "Vérifiez vos informations", "OK", null);
                }

            }
        });

        this.addAll(tfRef, tfLib, tfQ, tfC, tfQss, tfDpa, tfTva, tfDim, tfDes, tfTc, tfPv, c, img, btn);

      //  this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {

       //     new HomeAchat().show();

       // });

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
    public void CreationMenu() {

        Image icon = theme.getImage("resp7.png");
        Container topBar = BorderLayout.east(new Label(icon));
        topBar.add(BorderLayout.SOUTH, new Label("Responsable Achat...", "SidemenuTagline"));

        topBar.setUIID("SideCommand");
        getToolbar().addComponentToSideMenu(topBar);
        getToolbar().addMaterialCommandToSideMenu("Ajouter Produit", FontImage.MATERIAL_ADD_CIRCLE, e -> new AddProduitForm().show()); 
getToolbar().addMaterialCommandToSideMenu("liste des Produits", FontImage.MATERIAL_PLAYLIST_ADD_CHECK, e -> new ListProduitAchatForm().show());
getToolbar().addMaterialCommandToSideMenu("Ajouter Fournisseur", FontImage.MATERIAL_GROUP_ADD, e -> new AddFournisseurForm().show());
getToolbar().addMaterialCommandToSideMenu("liste des Fournisseurs", FontImage.MATERIAL_PLAYLIST_ADD_CHECK, e ->new ListeFournisseursForm().show() );
getToolbar().addMaterialCommandToSideMenu("Ajouter Bon D'entree", FontImage.MATERIAL_POST_ADD, e -> new AddBonEntreeForm().show());
getToolbar().addMaterialCommandToSideMenu("liste des Bons D'entree", FontImage.MATERIAL_PLAYLIST_ADD_CHECK, e ->new ListeBonsEntreeForm().show());
getToolbar().addMaterialCommandToSideMenu("Ajouter Bon De retour", FontImage.MATERIAL_POST_ADD, e ->new AddBonRetourForm().show());
getToolbar().addMaterialCommandToSideMenu("liste Bons De retour", FontImage.MATERIAL_PLAYLIST_ADD_CHECK, e ->new ListeBonsRetourForm().show());
getToolbar().addMaterialCommandToSideMenu("log-out", FontImage.MATERIAL_INFO, e ->new AuthentificationForm().show());


     

    }

}
