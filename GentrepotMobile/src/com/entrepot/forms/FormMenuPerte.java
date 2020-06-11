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
import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import static com.codename1.ui.layouts.BoxLayout.y;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.list.*;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.LignePerte;
import com.entrepot.models.Perte;
import com.entrepot.models.ProduitAchat;
import com.entrepot.services.ServiceLignePerte;
import com.entrepot.services.ServicePerte;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author guiforodrigue
 */
public class FormMenuPerte extends Form{
    ServiceLignePerte slp = new ServiceLignePerte();
    ArrayList<LignePerte> llp = new ArrayList<>();
    Resources theme = UIManager.initFirstTheme("/themeStockage");
    
    static int i = 0;
    public FormMenuPerte(Form previous){
        setTitle("GESTION DES PERTES");
        setLayout(new BorderLayout());
        
        Container menu = new Container(BoxLayout.y());
        //menu.add(new InfiniteProgress());                      
        Button btnAjout = new Button("PASSER EN PERTE");
        
        Button btnLister = new Button("LISTEZ LES PERTES");
        Button btnFiltre = new Button("FILTREZ LES PERTES");
        Label lv = new Label("");
        menu.add(lv);
        menu.add(btnAjout);
        menu.add(btnLister);
        menu.add(btnFiltre);
        btnAjout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               new FormPasserEnPerte().show();
               
            }
        });
        getToolbar().addCommandToLeftBar("Back", null, ev->{
            new FormStockageHome().show();
            //previous.showBack();
        });
        
        
        btnLister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               new FormListePerte().show();
               
            }
        });
        btnFiltre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               new FormFiltrePerte().show();
               
            }
        });
        Label l = new Label("Vue Glogale Sur Les pertes");
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
              if (i==0) series.add("INCONNU: "+values[i]+"  " ,values[i]);
              if (i==1) series.add("HORS USAGE: "+values[i]+"  " ,values[i]);
              if (i==2) series.add("VOL: "+values[i] ,values[i]);
          }
          
          return series;
        }
        public ChartComponent createPieChartForm() {
          // Générer les valeurs
          int vol = 0;
          int inconnu = 0;
          int horsUsage = 0;
          llp = slp.getAllLPertes();
          for(int i=0; i<llp.size();i++){
              if(llp.get(i).getRaisonPerte().equals("Vol")) vol += llp.get(i).getQuantite();
              if(llp.get(i).getRaisonPerte().equals("Hors usage")) horsUsage += llp.get(i).getQuantite();
              if(llp.get(i).getRaisonPerte().equals("Inconue")) inconnu += llp.get(i).getQuantite();
          }
          int[] values = new int[]{inconnu, horsUsage, vol};
          // Mettre le rendu en place
          int[] colors = new int[]{ColorUtil.BLUE, ColorUtil.YELLOW, ColorUtil.rgb(255,0,0)};
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
          Label l = new Label("Vue Glogale Sur Les pertes");
          
          // Create the chart ... pass the values and renderer to the chart object.
          PieChart chart = new PieChart(buildCategoryDataset("VUE GLOBALE SUR LES PERTE", values), renderer);
          
          // Wrap the chart in a Component so we can add it to a form
          ChartComponent c = new ChartComponent(chart);
          
          return c;
                   
       }    
        
          
}
