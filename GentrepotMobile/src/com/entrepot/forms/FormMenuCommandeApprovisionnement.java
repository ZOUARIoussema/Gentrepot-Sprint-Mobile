/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.PieChart;
import com.codename1.components.MultiButton;
import static com.codename1.io.Log.e;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.CommandeDApprovisionnement;
import com.entrepot.models.Fournisseur;
import com.entrepot.models.LigneCommandeDApprovisionnement;
import com.entrepot.models.ProduitAchat;
import com.entrepot.services.ServiceCommandeDApprovisionnment;
import com.entrepot.services.ServiceLigneCommandeDApprovisionnement;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author guiforodrigue
 */
public class FormMenuCommandeApprovisionnement extends Form{
    ServiceCommandeDApprovisionnment slp = new ServiceCommandeDApprovisionnment();
    ArrayList<CommandeDApprovisionnement> llp = new ArrayList<>();
    Resources theme = UIManager.initFirstTheme("/themeStockage");
    
    
    static int i=0;
    public FormMenuCommandeApprovisionnement(Form previous){
        setTitle("GESTION DES COMMANDES");
        setLayout(new BorderLayout());
        
        Container menu = new Container(BoxLayout.y());
        //menu.add(new InfiniteProgress());                      
        Button btnAjout = new Button("EFFECTUEZ VOTRE COMMANDE");
        
        Button btnLister = new Button("LISTEZ LES COMMANDES");
        Button btnFiltre = new Button("FILTREZ LES COMMANDES");
        Label lv = new Label("");
        menu.add(lv);
        menu.add(btnAjout);
        menu.add(btnLister);
        menu.add(btnFiltre);
        btnAjout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               new FormEffectuerCommandeApp().show();
               
            }
        });
        getToolbar().addCommandToLeftBar("Back", null, ev->{
            new FormStockageHome().show();
            //previous.showBack();
        });
        
        
        btnLister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               new FormListeCommandeApp().show();
               
            }
        });
        btnFiltre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               new FormFiltreCommandeApp().show();
               
            }
        });            
    
        Label l = new Label("Vue Glogale Sur Les commandes");
        this.addComponent(BorderLayout.NORTH, menu);
        
        this.addComponent(BorderLayout.SOUTH, this.createPieChartForm());
        
    }
    
    /**
     * Creates a renderer for the specified colors.
     * @param colors the colors
     * @return
     */
    private DefaultRenderer buildCategoryRenderer(int[] colors) {
          DefaultRenderer renderer = new DefaultRenderer();
          renderer.setLabelsTextSize(50);
          renderer.setLegendTextSize(50);
          renderer.setMargins(new int[]{20, 30, 15, 0});
          for (int color : colors) {
          SimpleSeriesRenderer r = new SimpleSeriesRenderer();
          r.setColor(color);
          renderer.addSeriesRenderer(r);
          }
          return renderer;
        }
        /**
         * Builds a category series using the provided values.
         *
         * @param titles the series titles
         * @param values the values
         * @return the category series
         */
        protected CategorySeries buildCategoryDataset(String title, int[] values) {
          CategorySeries series = new CategorySeries(title);
          
          for (int i=0;i<values.length;i++) {
              if (i==0) series.add("facturer: "+values[i]+"  " ,values[i]);
              if (i==1) series.add("non_facturer: "+values[i]+"  " ,values[i]);
          }
          
          return series;
        }
        public ChartComponent createPieChartForm() {
          // Générer les valeurs
          int non_facturer = 0;
          int facturer = 0;
          
          llp = slp.getAllComs();
          for(int i=0; i<llp.size();i++){
              if(llp.get(i).getEtat().equals("non_facturer")){ non_facturer += 1;}
              else{
                 facturer += 1;
              }
              
          }
          int[] values = new int[]{non_facturer, facturer};
          // Mettre le rendu en place
          int[] colors = new int[]{ColorUtil.rgb(255,0,0), ColorUtil.GREEN};
          DefaultRenderer renderer = buildCategoryRenderer(colors);
          renderer.setZoomButtonsVisible(true);
          renderer.setZoomEnabled(true);
          renderer.setChartTitleTextSize(50);//20
          renderer.setDisplayValues(true);
          renderer.setShowLabels(true);
          SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
          r.setGradientEnabled(true);
          r.setGradientStart(0, ColorUtil.BLUE);
          r.setGradientStop(0, ColorUtil.GREEN);
          r.setHighlighted(true);
          Label l = new Label("Vue Glogale Sur l'état des commandes");
          
          // Create the chart ... pass the values and renderer to the chart object.
          PieChart chart = new PieChart(buildCategoryDataset("VUE GLOBALE SUR L'ETAT DES COMMANDES", values), renderer);
          
          // Wrap the chart in a Component so we can add it to a form
          ChartComponent c = new ChartComponent(chart);
          
          return c;
                   
       }  
        
}
