/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import models.Cell;
import models.CellsList;
import views.GamePanel;

/**
 *
 * @author hp
 */
//====================CONTROLLER PANEL====================
public class OptionPanel extends JPanel{
    private GamePanel gamePanel;
    private CellsList cells;
    private Cell[] cellsList;
    private JComboBox<String> speedMenu,shapeMenu, scaleMenu;
    private JButton nextButton,startButton,editButton;
    private JLabel generationsLabel;
    private int generations;
    private int timerDelay;
    private String shapeTypes[]={"Clear","Gosper Glider Gun","Lightweight Spaceship","Glider","10 Cell Row","Tumbler","Exploder","Small Exploder"};
    private String scaleTypes[]={"Big","Medium","Small"};
    private String speedTypes[]={"Slow","Normal","Fast"};
    private Timer timer;
    private boolean edit,first=true;
    private GameLogic gameLogic;
    int defaultShape, defaultSpeed, defaultScale, defaultEdit;
    
    public OptionPanel(CellsList cells, GamePanel gamePanel){
    //----------formatting panel----------
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(BorderFactory.createMatteBorder(-5,5,5,5,Color.LIGHT_GRAY));
        setBackground(Color.LIGHT_GRAY);
        
    //----------initializing data members----------
        this.cells=cells;
        this.cellsList=cells.getList();
        this.gamePanel=gamePanel;
        generations=0;
        timerDelay=1000;
        //----------genetations label----------
        generationsLabel=new JLabel("Generations: "+generations); 
        gameLogic = new GameLogic(this.cells,cellsList,generationsLabel,this.gamePanel);
        timer=new Timer(timerDelay, (ActionEvent e) -> {
            gameLogic.run();
        });
        
    
    
        
    //----------opening the file----------    
        File file=new File("settings.txt");
        try {
            Scanner in=new Scanner(file);
            defaultShape=in.nextInt();
            defaultSpeed=in.nextInt();
            defaultScale=in.nextInt();
            defaultEdit=in.nextInt();
        } catch (FileNotFoundException ex) {
            defaultShape=0;
            defaultSpeed=1;
            defaultScale=1;
            defaultEdit=1;
        }
        edit = defaultEdit != 0;
        OptionPanel.this.gamePanel.setEditing(edit);    
    
    //----------speed menu----------
        speedMenu=new JComboBox<String>();
        for(String s:speedTypes)
            speedMenu.addItem(s);
        
        speedMenu.addActionListener((ActionEvent e) -> {
            JComboBox source=(JComboBox)e.getSource();
            String selection=(String)source.getSelectedItem();
            switch(selection){
                case "Slow":
                    timerDelay=1000;
                    break;
                case "Normal":
                    timerDelay=500;
                    break;
                case "Fast":
                    timerDelay=50;
                    break;
                default:
                    System.out.println("Error");
            }
            timer.setDelay(timerDelay);
        });
        speedMenu.setSelectedIndex(defaultSpeed);

    //----------scale menu----------    
        scaleMenu=new JComboBox<String>();
        for(String s:scaleTypes)
            scaleMenu.addItem(s);
        
        scaleMenu.addActionListener((ActionEvent e) -> {
            JComboBox source=(JComboBox)e.getSource();
            String selection=(String)source.getSelectedItem();
            switch (selection) {
                case "Small":
                    Cell.SIDE=5;
                    break;
                case "Big":
                    Cell.SIDE=15;
                    break;
                default:
                    Cell.SIDE=7;
                    break;
            }
            gamePanel.repaint();
        });
        scaleMenu.setSelectedIndex(defaultScale);
        
    //----------shape menu----------    
        shapeMenu=new JComboBox<String>();
        for(String s:shapeTypes)
            shapeMenu.addItem(s);
        
        shapeMenu.addActionListener((ActionEvent e) -> {
            JComboBox s=(JComboBox)e.getSource();
            String selection=(String)s.getSelectedItem();
            switch(selection){
                case "Clear":
                    setShape("Clear");
                    break;
                case "Gosper Glider Gun":
                    setShape("GGG");
                    break;
                case "Lightweight Spaceship":
                    setShape("LWS");
                    break;
                case "Glider":
                    setShape("Glider");
                    break;
                case "10 Cell Row":
                    setShape("TCR");
                    break;
                case "Tumbler":
                    setShape("Tumbler");
                    break;
                case "Exploder":
                    setShape("Exploder");
                    break;
                case "Small Exploder":
                    setShape("Small Exploder");
                    break;
                default:
            }
            gamePanel.repaint();
        });
        shapeMenu.setSelectedIndex(defaultShape);  
        
    //----------next button----------
        nextButton=new JButton("Next");
        nextButton.addActionListener((ActionEvent e) -> {
            gameLogic.run();
        });
        
    //----------start button----------
        startButton=new JButton("Start");
        startButton.addActionListener(new ActionListener(){
            boolean start=true;
            @Override public void actionPerformed(ActionEvent e){
                if(start){
                    shapeMenu.setEnabled(false);
                    nextButton.setEnabled(false);
                    editButton.setEnabled(false);
                    startButton.setText("Stop");
                    timer.start();
                    OptionPanel.this.gamePanel.setEditing(false);
                }
                else{
                    shapeMenu.setEnabled(true);
                    nextButton.setEnabled(true);
                    editButton.setEnabled(true);
                    startButton.setText("Start");
                    timer.stop();
                    OptionPanel.this.gamePanel.setEditing(edit);
                }
                start=!start;
                OptionPanel.this.repaint();
            }
        });
        
    //----------edit button----------
        if(edit)
            editButton=new JButton("Edit: ON");
        else
            editButton=new JButton("Edit: OFF");
        editButton.addActionListener((ActionEvent e) -> {
            edit=!edit;
            if(edit)
                editButton.setText("Edit: ON");
            else
                editButton.setText("Edit: OFF");
            OptionPanel.this.gamePanel.setEditing(edit);
        });
              
    //----------adding components to the controller----------
        add(shapeMenu);
        add(nextButton);
        add(startButton);
        add(speedMenu);
        add(scaleMenu);
        add(editButton);
        add(generationsLabel);
    }
    
//============================================================    
    public void setShape(String shape){
        int frameRows=gamePanel.getHeight()/Cell.SIDE;
        int frameCols=gamePanel.getWidth()/Cell.SIDE;
        int midpoint;
    //----------default midpoint----------
        if(first){
            midpoint=((425/Cell.SIDE)/2)*cells.getNumColumns()+((684/Cell.SIDE)/2);
            first=!first;
        }
        else
            midpoint=(frameRows/2)*cells.getNumColumns()+frameCols/2;
    
    //----------clear before doing anything----------
        cells.clear();
        generations=0;
        generationsLabel.setText("Generations: "+generations); 
        gameLogic.resetGenerations();
        
    //----------drawing the shapes in center of screen----------
        switch(shape){
            case "Clear":                      
                break;
            case "GGG":
                cellsList[midpoint+3].setAlive(true);
                cellsList[midpoint+4].setAlive(true);
                cellsList[midpoint+3+2*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint+2+cells.getNumColumns()].setAlive(true);
                cellsList[midpoint+2+2*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint+4+cells.getNumColumns()].setAlive(true);
                
                cellsList[midpoint+14].setAlive(true);
                cellsList[midpoint+15].setAlive(true);
                cellsList[midpoint+14+cells.getNumColumns()].setAlive(true);
                cellsList[midpoint+15+cells.getNumColumns()].setAlive(true);
                
                cellsList[midpoint-20+2*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint-20+3*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint-19+2*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint-19+3*cells.getNumColumns()].setAlive(true);
                
                cellsList[midpoint-12+3*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint-12+4*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint-11+2*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint-10+2*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint-10+3*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint-11+4*cells.getNumColumns()].setAlive(true);
                
                cellsList[midpoint-4+4*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint-3+4*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint-4+5*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint-4+6*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint-2+5*cells.getNumColumns()].setAlive(true);
                
                cellsList[midpoint+15+7*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint+15+8*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint+15+9*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint+16+7*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint+17+8*cells.getNumColumns()].setAlive(true);
                
                cellsList[midpoint+4+12*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint+5+12*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint+6+12*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint+4+13*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint+5+14*cells.getNumColumns()].setAlive(true);
                break;
            case "LWS":
                for(int i=midpoint-1;i<midpoint+3;i++)
                    cellsList[i].setAlive(true);
                cellsList[midpoint+2+cells.getNumColumns()].setAlive(true);
                cellsList[midpoint+2+2*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint+1+3*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint-2+cells.getNumColumns()].setAlive(true);
                cellsList[midpoint-2+3*cells.getNumColumns()].setAlive(true);
                break;
            case "Glider":
                cellsList[midpoint].setAlive(true);
                cellsList[midpoint+1].setAlive(true);
                cellsList[midpoint+2].setAlive(true);
                cellsList[midpoint+2-(cells.getNumColumns())].setAlive(true);
                cellsList[midpoint+1-(cells.getNumColumns()*2)].setAlive(true);
                break;
            case "TCR":
                for(int i=midpoint-5;i<midpoint+5;i++)
                    cellsList[i].setAlive(true);
                break;
            case "Tumbler":
                for(int i=midpoint;i<midpoint+5*cells.getNumColumns();i+=cells.getNumColumns()){
                    cellsList[i].setAlive(true);
                    cellsList[i+2].setAlive(true);
                }
                for(int i=midpoint-2+3*cells.getNumColumns();i<midpoint-2+6*cells.getNumColumns();i+=cells.getNumColumns()){
                    cellsList[i].setAlive(true);
                    cellsList[i+6].setAlive(true);
                }
                cellsList[midpoint-1+5*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint+3+5*cells.getNumColumns()].setAlive(true);
                for(int i=midpoint+3;i<midpoint+3+2*cells.getNumColumns();i+=cells.getNumColumns()){
                    cellsList[i].setAlive(true);
                    cellsList[i-4].setAlive(true);
                }
                break;
            case "Exploder":
                cellsList[midpoint-2].setAlive(true);
                cellsList[midpoint-2+cells.getNumColumns()].setAlive(true);
                cellsList[midpoint-2+2*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint-2+3*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint-2+4*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint+2].setAlive(true);
                cellsList[midpoint+2+cells.getNumColumns()].setAlive(true);
                cellsList[midpoint+2+2*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint+2+3*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint+2+4*cells.getNumColumns()].setAlive(true);
                 
                cellsList[midpoint].setAlive(true);
                cellsList[midpoint+4*cells.getNumColumns()].setAlive(true);
                break;
            case "Small Exploder":
                for(int i=midpoint-1;i<midpoint+2;i++)
                    cellsList[i].setAlive(true);
                cellsList[midpoint-cells.getNumColumns()].setAlive(true);
                cellsList[midpoint-1+cells.getNumColumns()].setAlive(true);
                cellsList[midpoint+2*cells.getNumColumns()].setAlive(true);
                cellsList[midpoint+1+cells.getNumColumns()].setAlive(true);
                break;
        }    
    }
}
