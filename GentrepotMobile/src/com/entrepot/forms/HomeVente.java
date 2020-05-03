/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrepot.forms;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author LENOVO
 */
public class HomeVente extends Form{
    
    
     public HomeVente() {
        super("Home", BoxLayout.y());
        
     //   Button btnAddTask = new Button("Add Task");
        Button btnTasksList = new Button("Produits List");
        
        /*btnAddTask.addActionListener((evt) -> {
            new AddTaskForm(this).show(); 
        });*/
        btnTasksList.addActionListener((evt) -> { 
            //new ProduitsListForm(this).show();
        });
         
        this.addAll(new Label("Choose an option :"), btnTasksList);
    }
    
    
}
