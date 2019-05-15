/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author hp
 */
//====================SETTINGS DIALOG====================
public class SettingsDialog extends JDialog{
    private JLabel scale,shape,speed,edit;
    private JRadioButton yes,no;
    JButton saveButton,cancelButton;
    private ButtonGroup buttonGroup;
    private JComboBox<String> shapeBox,scaleBox, speedBox;
    private JPanel shapePanel,scalePanel,speedPanel,editPanel,buttonPanel;
    private String shapeTypes[]={"Clear","Gosper Glider Gun","Lightweight Spaceship","Glider","10 Cell Row","Tumbler","Exploder","Small Exploder"};
    private String scaleTypes[]={"Big","Medium","Small"};
    private String speedTypes[]={"Slow","Normal","Fast"};
    private int scaleIndex, shapeIndex, speedIndex;
    private int editing=1;

    public SettingsDialog(JFrame frame){
        super(frame, "Settings", true);
        setSize(300,200);
        setResizable(false);
        setLocationByPlatform(true);
        setLayout(new GridLayout(5,1));

    //----------creating the components----------   
        shapePanel = new JPanel();
        scalePanel = new JPanel();
        speedPanel = new JPanel();
        editPanel = new JPanel();
        buttonPanel=new JPanel();

        scale=new JLabel("Scale: ");
        shape=new JLabel("Pattern: ");
        speed=new JLabel("Speed: ");
        edit=new JLabel("Edit: ");

        shapeBox = new JComboBox<String>();
        scaleBox=new JComboBox<String>();
        speedBox=new JComboBox<String>();
        yes=new JRadioButton("Yes",true);
        no=new JRadioButton("No");
        buttonGroup=new ButtonGroup();

    //----------adding shapes to combobox----------
        for(String s:shapeTypes)
            shapeBox.addItem(s);
        shapeBox.setSelectedIndex(0);
        shapeBox.addActionListener((ActionEvent e) -> {
            JComboBox s=(JComboBox)e.getSource();
            String selection=(String)s.getSelectedItem();
            switch(selection){
                case "Clear":
                    shapeIndex=0;
                    break;
                case "Gosper Glider Gun":
                    shapeIndex=1;
                    break;
                case "Lightweight Spaceship":
                    shapeIndex=2;
                    break;
                case "Glider":
                    shapeIndex=3;
                    break;
                case "10 Cell Row":
                    shapeIndex=4;
                    break;
                case "Tumbler":
                    shapeIndex=5;
                    break;
                case "Exploder":
                    shapeIndex=6;
                    break;
                case "Small Exploder":
                    shapeIndex=7;
                    break;
            }    
        });

    //----------adding to scale combobox----------    
        for(String s:scaleTypes)
            scaleBox.addItem(s);
        scaleBox.setSelectedIndex(1);
        scaleBox.addActionListener((ActionEvent e) -> {
            JComboBox source=(JComboBox)e.getSource();
            String selection=(String)source.getSelectedItem();
            switch(selection){
                case "Big":
                    scaleIndex=0;
                    break;
                case "Medium":
                    scaleIndex=1;
                    break;
                case "Small":
                    scaleIndex=2;
                    break;
            }
        });

    //----------adding to speed combobox----------
        for(String s:speedTypes)
            speedBox.addItem(s);
        speedBox.setSelectedIndex(1);
        speedBox.addActionListener((ActionEvent e) -> {
            JComboBox source=(JComboBox)e.getSource();
            String selection=(String)source.getSelectedItem();
            switch(selection){
                case "Fast":
                    speedIndex=2;
                    break;
                case "Normal":
                    speedIndex=1;
                    break;
                case "Slow":
                    speedIndex=0;
                    break;
            }
        });               
        yes.addActionListener((ActionEvent e) -> {
            editing=1;
        });
        no.addActionListener((ActionEvent e) -> {
            editing=0;
        });

    //----------save button----------
        saveButton=new JButton("Save");
        saveButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PrintWriter out=new PrintWriter("settings.txt");
                    out.println(shapeIndex+" "+speedIndex+" "+scaleIndex+" "+editing);
                    out.flush();
                    SettingsDialog.this.setVisible(false);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        });
    //---------cancel button---------------
        cancelButton=new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                SettingsDialog.this.setVisible(false);
            }
        });
    //----------adding to panels----------    
        buttonGroup.add(yes);
        buttonGroup.add(no);
        shapePanel.add(shape);
        shapePanel.add(shapeBox);
        speedPanel.add(speed);
        speedPanel.add(speedBox);
        scalePanel.add(scale);
        scalePanel.add(scaleBox);
        editPanel.add(edit);
        editPanel.add(yes);
        editPanel.add(no);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

    //----------adding to the dialog----------    
        add(shapePanel);
        add(speedPanel);
        add(scalePanel);
        add(editPanel);
        add(buttonPanel);
    }
}

