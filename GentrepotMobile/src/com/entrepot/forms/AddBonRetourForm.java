/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import static com.entrepot.forms.AddBonEntreeForm.ACCOUNT_SID;
import static com.entrepot.forms.AddBonEntreeForm.AUTH_TOKEN;
import static com.entrepot.forms.AddBonEntreeForm.codex;
import com.entrepot.models.BonRetour;
import com.entrepot.models.CommandeApp;
import com.entrepot.models.CommandeDApprovisionnement;
import com.entrepot.services.ServiceBonRetour;
import com.entrepot.services.ServiceProduitAchat;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Mohamed
 */
public class AddBonRetourForm extends Form {
    public static String codex;
    public static final String ACCOUNT_SID = "AC259bf45943274ddfdde68e37a8ad9a13";
    public static final String AUTH_TOKEN = "4abfada1bfe1402fde7e64cf13d1c81e";
    String phonenumber = "+21625180502";

    Resources theme = UIManager.initFirstTheme("/themeLogistique");
    ComboBox<String> c;

    public AddBonRetourForm() {
        
        super("Ajouter bon de retour", BoxLayout.y());
        c = new ComboBox();

        this.setLayout(new FlowLayout(CENTER, CENTER));
        this.getStyle().setBgImage(theme.getImage("kashmir.png"), focusScrolling);
        CreationMenu();
        this.setLayout(BoxLayout.y());

        

        Container cont = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        ServiceProduitAchat ws = new ServiceProduitAchat();
        Map x = ws.getResponse("/listComm");

        ArrayList<CommandeApp> listeCom = ws.getListcommande(x);
        for (CommandeApp e : listeCom) {
            c.addItem(e.getNumeroC() +"");
        }
        Picker datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        TextField t = new TextField();
        Label l = new Label("Numero commande ap : ");
        Label l1 = new Label("Date : ");
        t.setHint("Motif");
        Button b = new Button("Ajouter");

        cont.add(l);
        cont.add(c);
        cont.add(l1);
        cont.add(datePicker);
        cont.add(t);
        cont.add(b);

        add(cont);
        b.addActionListener(e -> {
            
            if ((t.getText().length() == 0) ) {
                Dialog.show("Alert", "veuillez mentionner le motif de retour !", "OK", null);
            } 
            else if (!t.getText().equals("")) {
                BonRetour be = new BonRetour();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

                Date da = datePicker.getDate();

                String st1 = df.format(da);
                
                CommandeApp ca = new CommandeApp();
                ca.setNumeroC(Integer.parseInt(c.getSelectedItem()));
                
                be.setCap(Integer.parseInt(c.getSelectedItem()));
                be.setDate(st1);
                be.setMotif(t.getText());

                ServiceBonRetour sc = new ServiceBonRetour();
                
                if (sc.addBonRetour(be)) {
                        Dialog.show("SUCCESS", "Bon de retour ajouté !", "OK", null);
                        
                        System.out.println("=========================");
                String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
                StringBuilder salt = new StringBuilder();
                Random rnd = new Random();
                while (salt.length() < 5) { // length of the random string.
                    int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                    salt.append(SALTCHARS.charAt(index));
                }
                String saltStr = salt.toString();
                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                com.twilio.rest.api.v2010.account.Message messages = com.twilio.rest.api.v2010.account.Message.creator(new PhoneNumber(phonenumber),
                        new PhoneNumber("+19286123819"),"Un bon de correspondant à la commande d'approvisionnement numero: "+be.getCap()+" a ete cree le "+be.getDate()+" son motif de retour est : "+be.getMotif()).create();
                //"un client veux de contacter : " + phonenumber + "," + saltStr
                codex = saltStr;
                System.out.println("======================");
                System.out.println("======================");
                System.out.println("======================");
                System.out.println(codex);
                Dialog.show("succes", "Un sms a bien éte envoyer !", "ok", null);
                
                    
                 ListeBonsRetourForm lb = new ListeBonsRetourForm();
                 lb.showBack();}

            } else {
                Dialog.show("Erreur", "Vérifiez vos informations", "Ok", null);
            }
            
        });
      //  this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {

       //         new HomeAchat().showBack();

        //    });
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
