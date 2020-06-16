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
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.InventaireStock;
import com.entrepot.services.ServiceInventaireStock;
import java.util.ArrayList;
/**
 *
 * @author guiforodrigue
 */
public class FormMenuInventaire extends Form{
    
     Resources theme = UIManager.initFirstTheme("/themeStockage");
     
     public FormMenuInventaire(Form previous){
        setTitle("GESTION DES INVENTAIRES");
        setLayout(new BorderLayout());
        
        Container menu = new Container(BoxLayout.y());                      
        Button btnAjout = new Button("FAIRE UN INVENTAIRE");        
        Button btnLister = new Button("LISTE DES INVENTAIRES");
        Button btnFiltre = new Button("FILTRER LES INVENTAIRES");
        Label lv = new Label("");
        menu.add(lv);
        menu.add(btnAjout);
        menu.add(btnLister);
        menu.add(btnFiltre); 

        btnAjout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               new FormFaireInventaire().show();
               
            }
        });
        getToolbar().addCommandToLeftBar("Back", null, ev->{
            new FormStockageHome().show();
            //previous.showBack();
        });
        
        btnLister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               new FormListeInventaire().show();
               
            }
        });
        btnFiltre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               new FormFiltreInventaire().show();
               
            }
        });
        Label l = new Label("Vue Glogale Sur Les inventaires");
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
              if (i==0) series.add("A: "+values[i]+"  " ,values[i]);
              if (i==1) series.add("B: "+values[i]+"  " ,values[i]);
              if (i==2) series.add("C: "+values[i] ,values[i]);
          }
          
          return series;
        }
        public ChartComponent createPieChartForm() {
          // Générer les valeurs
          int M = 0;
          int B = 0;
          ServiceInventaireStock si = new ServiceInventaireStock();
          ArrayList<InventaireStock> s = new ArrayList<>();
          
          s = si.getAllInvs();
          for(int i=0; i<s.size();i++){
              if(s.get(i).getEcart() == 0) B++;
              if(s.get(i).getEcart() != 0) M++;
          }
          int[] values = new int[]{B, M};
          // Mettre le rendu en place
          int[] colors = new int[]{ColorUtil.GREEN, ColorUtil.rgb(255,0,0)};
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
          Label l = new Label("Vue Glogale Sur Les inventaires par classe");
          
          // Create the chart ... pass the values and renderer to the chart object.
          PieChart chart = new PieChart(buildCategoryDataset("VUE GLOBALE SUR LES INVENTAIRES PAR CLASSE", values), renderer);
          
          // Wrap the chart in a Component so we can add it to a form
          ChartComponent c = new ChartComponent(chart);
          
          return c;
                   
       } 
}
