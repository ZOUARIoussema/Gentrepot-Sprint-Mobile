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
import com.codename1.components.ToastBar;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.entrepot.services.ServiceLettreDeRelance;

/**
 *
 * @author oussema
 */
public class MenueAgentCaisseForm extends Form {

    Resources theme = UIManager.initFirstTheme("/themeTresorerie");
    
    ServiceLettreDeRelance serviceLettreDeRelance = new ServiceLettreDeRelance();

    public void CreationMenu() {
        
        this.getToolbar().setUIID("SideCommande");
        
        
        
         this.getToolbar().addCommandToOverflowMenu("Modifier Profile",null,(evt) -> {
             
             new ModifierProfilForm().show();
       });

        this.getToolbar().addMaterialCommandToSideMenu("Ajouter Inventaire Caisse", FontImage.MATERIAL_ADD, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                new AjouterInventaireCaisseForm().show();

            }
        });

        this.getToolbar().addMaterialCommandToSideMenu("Liste Inventaire Caisse", FontImage.MATERIAL_ARCHIVE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                new ListeInventaireCaisseForm().show();

            }
        });
        this.getToolbar().addMaterialCommandToSideMenu("Ajouter Lettre de relance", FontImage.MATERIAL_ADD, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                new AjouterLettreDeRelanceForm().show();

            }
        });
        this.getToolbar().addMaterialCommandToSideMenu("Liste lettre de relance", FontImage.MATERIAL_ARCHIVE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                new ListeLettreDeRelanceForm().show();

            }
        });

        this.getToolbar().addMaterialCommandToSideMenu("Deconnecter", FontImage.MATERIAL_EXIT_TO_APP, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                new AuthentificationForm().show();

            }
        });
        //this.getToolbar().getStyle().setBgColor(0xD3D3D3);

    }

    public MenueAgentCaisseForm() {

        this.setTitle("Menue");
        
        CreationMenu();
        
          ToastBar.showMessage("Agent caisse connecter", FontImage.MATERIAL_STAR, 30000);
          
          this.getStyle().setBgImage(theme.getImage("loginBack.png"), focusScrolling);
          
           double[] values = new double[]{12, 14, 11, 10, 19};

        int[] colors = new int[]{ColorUtil.BLUE, ColorUtil.GREEN, ColorUtil.MAGENTA, ColorUtil.YELLOW, ColorUtil.CYAN};
        DefaultRenderer renderer = buildCategoryRenderer(colors);
        renderer.setZoomButtonsVisible(true);
        renderer.setZoomEnabled(true);
        renderer.setChartTitleTextSize(60);
        renderer.setDisplayValues(true);
        renderer.setShowLabels(true);
        renderer.setLabelsTextSize(40);
        renderer.setLegendTextSize(40);
        renderer.setChartTitle("Statistique recouvrement client");
        
        renderer.setScale((float) 0.75);
        
        renderer.setShowLegend(false);
        
        SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
        r.setGradientEnabled(true);
        r.setGradientStart(0, ColorUtil.BLUE);
        r.setGradientStop(0, ColorUtil.GRAY);
        r.setHighlighted(true);

        // Create the chart ... pass the values and renderer to the chart object.
        PieChart chart = new PieChart(buildCategoryDataset(), renderer);
        ChartComponent c = new ChartComponent(chart);

        Container co = new Container(BoxLayout.y());
        this.setLayout(new FlowLayout(CENTER, CENTER));
        co.add(c);
        this.add(co);

          
          
          

    }
    
     private DefaultRenderer buildCategoryRenderer(int[] colors) {
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        renderer.setMargins(new int[]{20, 30, 15, 0});
        for (int color : colors) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }

    protected CategorySeries buildCategoryDataset() {
        CategorySeries series = new CategorySeries("Statistique");
        
        series.add("Total vente", serviceLettreDeRelance.totalVente());
        series.add("Total payé", serviceLettreDeRelance.totalPayer());

        return series;
    }


}
