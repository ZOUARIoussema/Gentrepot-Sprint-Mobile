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
import com.codename1.ui.Form;

import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import static com.codename1.ui.Component.CENTER;
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
import com.codename1.ui.list.*;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.models.Emplacement;
import com.entrepot.models.Entrepot;
import com.entrepot.models.InventaireStock;
import com.entrepot.services.ServiceEmplacement;
import com.entrepot.services.ServiceEntrepot;
import com.entrepot.services.ServiceInventaireStock;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
/**
 *
 * @author guiforodrigue
 */
public class FormMenuEmplacement extends Form{
    
     Resources theme = UIManager.initFirstTheme("/themeStockage");
    
    public FormMenuEmplacement(Form previous){
        
        setTitle("GESTION DES ESPACES");
        setLayout(new BorderLayout());
        
        Container menu = new Container(BoxLayout.y());                      
        Button btnAjout = new Button("CREER UN EMPLACEMENT");        
        Button btnLister = new Button("LISTE LES EMPLACEMENTS");
        Button btnFiltre = new Button("FILTRER LES EMPLACEMENTS");
        Label lv = new Label("");
        menu.add(lv);
        menu.add(btnAjout);
        menu.add(btnLister);
        menu.add(btnFiltre); 
        
        btnAjout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               new FormCreerEmplacement().show();
               
            }
        });
        
        getToolbar().addCommandToLeftBar("Back", null, ev->{
            new FormStockageHome().show();
            //previous.showBack();
        });
        btnLister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               new FormListeEmplacement().show();
               
            }
        });
        btnFiltre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               new FormFiltreEmplacement().show();
               
            }
        });
       Label l = new Label("Vue Glogale Sur Les espaces");
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
          if (i==0) series.add("Occupé: "+values[i]+"  " ,values[i]);
          if (i==1) series.add("Vide: "+values[i]+"  " ,values[i]);
      }

      return series;
    }
    public ChartComponent createPieChartForm() {
      // Générer les valeurs
      ServiceEmplacement spr = new ServiceEmplacement();
      int O = 0;
      int V = 0;
      for(int i=0;i < spr.getAllEmplas().size();i++){
          O += spr.getAllEmplas().get(i).getQuantiteStocker();
          V += spr.getAllEmplas().get(i).getCapaciteStockage()-spr.getAllEmplas().get(i).getQuantiteStocker();
      }
      

      int[] values = new int[]{O, V};
      // Mettre le rendu en place
      int[] colors = new int[]{ColorUtil.rgb(255,0,0), ColorUtil.BLACK};
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
      Label l = new Label("VUE SUR L'EMPLACEMENT ");

      // Create the chart ... pass the values and renderer to the chart object.
      PieChart chart = new PieChart(buildCategoryDataset("VUE SUR L'EMPLACEMENT ", values), renderer);

      // Wrap the chart in a Component so we can add it to a form
      ChartComponent c = new ChartComponent(chart);

      return c;
                   
    } 
       
    
    /*public Emplacement FindByAdr(ServiceEmplacement spr, String p){
        for (int i = 0; i < spr.getAllEmplas().size(); i++){
            if(spr.getAllEmplas().get(i).getAdresse().equals(p)){
                return spr.getAllEmplas().get(i);
            }
        }
        return null;
    }*/
}
